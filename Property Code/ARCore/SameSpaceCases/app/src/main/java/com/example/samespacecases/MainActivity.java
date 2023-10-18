package com.example.samespacecases;

import android.os.Bundle;
import android.os.Handler;
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
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;

public class MainActivity extends AppCompatActivity implements FragmentOnAttachListener,
        BaseArFragment.OnTapArPlaneListener,
        BaseArFragment.OnSessionConfigurationListener,
        ArFragment.OnViewCreatedListener {
    private static final String DEBUG_TAG = "MainActivity";
    public static final Handler HANDLER = new Handler();
    public static final int CLICK_WAIT_TIME_MS = 2000;

    // Experiment control objects
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

        // Add the cube information label objects
        Globals.CLICK_INFO_LABELS.add(findViewById(R.id.cube1_info));
        Globals.CLICK_INFO_LABELS.add(findViewById(R.id.cube2_info));
        Globals.CLICK_INFO_LABELS.add(findViewById(R.id.cube3_info));
        Globals.CLICK_INFO_LABELS.add(findViewById(R.id.cube4_info));

        // Set experiment control objects
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
            // Set global context and ArFragment
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
    }

    @Override
    public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
        // Create the Anchor.
        if (experimentStarted) {
            return;
        }
        experimentStarted = true;
        Anchor anchor = hitResult.createAnchor();
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(Globals.gArFragment.getArSceneView().getScene());
        Globals.gAnchorNode = anchorNode;
        ComponentA componentA = new ComponentA();
        ComponentB componentB = new ComponentB();

        // Wait for componentA and ComponentB to finish initializing
        HANDLER.postDelayed(() -> {
            mMessageView.setVisibility(View.VISIBLE);
            mMessageText.setText("Start experiment by clicking Launch ComponentA");
            mNextButton.setText("Launch ComponentA");

            mNextButton.setOnClickListener((view) -> {
                experimentNum++;
                mMessageView.setVisibility(View.GONE);

                // Wait for component A to finish rendering
                HANDLER.postDelayed(() -> {
                    // Load next message
                    mMessageView.setVisibility(View.VISIBLE);
                    if (experimentNum == 2) {
                        componentB.launch();
                        mMessageText.setText("Finished experiments. Start a new trial by restarting the app.");
                        mNextButton.setVisibility(View.GONE);
                    } else if (experimentNum == 1){
                        componentA.launch();
                        mMessageText.setText("Click \"Launch ComponentB\" to proceed");
                        mNextButton.setText("Launch ComponentB");
                    }else {
                        experimentNum = 0;
                        componentA.next(experimentNum);
                        componentB.next(experimentNum);

                    }
                }, CLICK_WAIT_TIME_MS);
            });
        }, CLICK_WAIT_TIME_MS);
    }
}