package com.example.syntheticinputcases;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentOnAttachListener;

import com.google.ar.core.Anchor;
import com.google.ar.core.Config;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Session;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.SceneView;
import com.google.ar.sceneform.Sceneform;
import com.google.ar.sceneform.collision.Ray;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;

public class MainActivity extends AppCompatActivity implements
        FragmentOnAttachListener,
        BaseArFragment.OnTapArPlaneListener,
        BaseArFragment.OnSessionConfigurationListener,
        ArFragment.OnViewCreatedListener,
        View.OnTouchListener {
    private static final String DEBUG_TAG = "MainActivity";
    public static final Handler HANDLER = new Handler();
    public static final int CLICK_WAIT_TIME_MS = 2000;

    private ConstraintLayout mMessageView;
    private TextView mMessageText;
    private Button mNextButton;

    private int experimentNum;
    private boolean experimentStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().addFragmentOnAttachListener(this);

        Globals.CLICK_INFO_LABELS.add(findViewById(R.id.cube1_info));
        Globals.CLICK_INFO_LABELS.add(findViewById(R.id.cube2_info));
        Globals.CLICK_INFO_LABELS.add(findViewById(R.id.cube3_info));
        Globals.CLICK_INFO_LABELS.add(findViewById(R.id.cube4_info));

        mMessageView = findViewById(R.id.verification_message);
        mMessageText = findViewById(R.id.verification_text);
        mNextButton = findViewById(R.id.next_button);

        experimentStarted = false;

        if (savedInstanceState == null) {
            if (Sceneform.isSupported(this)) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.ar_fragment, ArFragment.class, null)
                        .commit();
            }
        }
    }

    @Override
    public void onAttachFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        if (fragment.getId() == R.id.ar_fragment) {
            Globals.gContext = getApplicationContext();
            Globals.gArFragment = (ArFragment) fragment;
            Globals.gArFragment.setOnSessionConfigurationListener(this);
            Globals.gArFragment.setOnViewCreatedListener(this);
            Globals.gArFragment.setOnTapArPlaneListener(this);
        }
    }

    @Override
    public void onSessionConfiguration(Session session, Config config) {
        if (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
            config.setDepthMode(Config.DepthMode.AUTOMATIC);
        }
    }

    @Override
    public void onViewCreated(ArSceneView arSceneView) {
        Globals.gArFragment.setOnViewCreatedListener(null);

        // Fine adjust the maximum frame rate
        arSceneView.setFrameRateFactor(SceneView.FrameRate.FULL);
//        arSceneView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.i(DEBUG_TAG, "onTouch: " + view.toString());
        return false;
    }

    @Override
    public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
        if (experimentStarted) {
            return;
        }
        experimentStarted = true;
        Anchor anchor = hitResult.createAnchor();
        Globals.gAnchorNode = new AnchorNode(anchor);
        Globals.gAnchorNode.setParent(Globals.gArFragment.getArSceneView().getScene());

        ComponentA componentA = new ComponentA();
        componentA.launch();

        HANDLER.postDelayed(() -> {
            ComponentB componentB = new ComponentB();
            componentB.launch();

            mMessageView.setVisibility(View.VISIBLE);
            mMessageText.setText("Component A rendered. Start experiment by launching component B?");
            mNextButton.setOnClickListener((view) -> {
                experimentNum++;
                mMessageView.setVisibility(View.GONE);
                componentA.next(experimentNum);
                HANDLER.postDelayed(() -> {
                    componentB.next(experimentNum);
                    mMessageView.setVisibility(View.VISIBLE);
                    switch (experimentNum) {
                        case 1:
                            mMessageText.setText("Currently on experiment " + experimentNum + ". Move on to next experiment by clicking \"Next Experiment\"");
                            mNextButton.setText("Next Experiment");
                            break;
                        case 2:
                            mMessageText.setText("Finished experiments. Start new trial by clicking \"Start New Trial\"");
                            mNextButton.setText("Start New Trial");
                            experimentNum = 0;
                            break;
                        default:
                            break;
                    }
                }, CLICK_WAIT_TIME_MS);
            });
        }, CLICK_WAIT_TIME_MS);
    }

//    private void componentA(AnchorNode anchorNode) {
//        cube1 = new ArCube("1", new Color(255, 0, 0), getApplicationContext(), Globals.gArFragment, anchorNode,
//                ArCube.LabelPosition.LEFT,
//                Globals.CLICK_INFO_LABELS.get(0));
//    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void componentB(AnchorNode anchorNode, MotionEvent motionEvent) {
//        Ray casting = getRay(anchorNode.getWorldPosition(), false);
//        HitTestResult result = Globals.gArFragment.getArSceneView().getScene().hitTest(casting, true);
//        Log.i(DEBUG_TAG, "result: " + result.getNode().toString());
//
//        long downTime = SystemClock.uptimeMillis();
//        long eventTime = SystemClock.uptimeMillis() + 100;
//        float x = motionEvent.getX() + Globals.CUBE_WIDTH * 4.0f;
//        float y = motionEvent.getY();
//        // List of meta states found here: developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
//        int metaState = 0;
//        MotionEvent syntheticMotionEventDown = MotionEvent.obtain(
//                downTime,
//                eventTime,
//                MotionEvent.ACTION_DOWN,
//                x,
//                y,
//                metaState
//        );
//        MotionEvent syntheticMotionEventUp = MotionEvent.obtain(
//                downTime,
//                eventTime,
//                MotionEvent.ACTION_UP,
//                x,
//                y,
//                metaState
//        );
//        result.getNode().onTouchEvent(result, syntheticMotionEventDown);
//        result.getNode().onTouchEvent(result, syntheticMotionEventUp);
//    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void componentBDrag(AnchorNode anchorNode, MotionEvent motionEvent) {
//        Ray casting = getRay(anchorNode.getWorldPosition(), false);
//        HitTestResult result = mArFragment.getArSceneView().getScene().hitTest(casting, true);
//        Log.i(DEBUG_TAG, "result: " + result.getNode().toString());
//
//        long downTime = SystemClock.uptimeMillis();
//        long eventTime = SystemClock.uptimeMillis() + 100;
//        float x = motionEvent.getX() + CUBE_WIDTH;
//        float y = motionEvent.getY();
//        // List of meta states found here: developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
//        int metaState = 0;
//        MotionEvent syntheticMotionEventDown = MotionEvent.obtain(
//                downTime,
//                eventTime,
//                MotionEvent.ACTION_DOWN,
//                x,
//                y,
//                metaState
//        );
//        result.getNode().onTouchEvent(result, syntheticMotionEventDown);
//        MotionEvent syntheticMotionEventMove;
//        for (int i = 0; i < 5; i++) {
//            syntheticMotionEventMove = MotionEvent.obtain(
//                    downTime,
//                    eventTime,
//                    MotionEvent.ACTION_MOVE,
//                    x,
//                    y,
//                    metaState
//            );
//            result.getNode().onTouchEvent(result, syntheticMotionEventMove);
//            x += CUBE_WIDTH;
//            downTime += 100;
//            eventTime += 100;
//        }
//        MotionEvent syntheticMotionEventUp = MotionEvent.obtain(
//                downTime,
//                eventTime,
//                MotionEvent.ACTION_UP,
//                x,
//                y,
//                metaState
//        );
//        result.getNode().onTouchEvent(result, syntheticMotionEventUp);
//    }
}