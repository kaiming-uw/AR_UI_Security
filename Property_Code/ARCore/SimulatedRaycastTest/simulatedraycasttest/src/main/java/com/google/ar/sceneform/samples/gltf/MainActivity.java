package com.google.ar.sceneform.samples.gltf;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

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
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.SceneView;
import com.google.ar.sceneform.Sceneform;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.lang.ref.WeakReference;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements
        FragmentOnAttachListener,
        BaseArFragment.OnTapArPlaneListener,
        BaseArFragment.OnSessionConfigurationListener,
        ArFragment.OnViewCreatedListener {

    public static final int CLICK_WAIT_TIME_MS = 2000;
    private ArFragment arFragment;
    private Renderable model;
    private ViewRenderable viewRenderable;
    private AnchorNode anchor;
    private int experimentNum;
    private ComponentA componentA;
    private ComponentB componentB;
    public static final Random RANDOM = new Random();

    private ConstraintLayout mMessageView;
    private TextView mMessageText;
    private Button mNextButton;
    private Button mSwitchButton;
    private Button mSimRaycastButton;
    public static final Handler HANDLER = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getSupportFragmentManager().addFragmentOnAttachListener(this);

        if (savedInstanceState == null) {
            if (Sceneform.isSupported(this)) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.arFragment, ArFragment.class, null)
                        .commit();
            }
        }

        // Add the cube information label objects
        Globals.CLICK_INFO_LABELS.add(findViewById(R.id.cube1_info));


        // Set experiment control objects
        mMessageView = findViewById(R.id.verification_message);
        mMessageText = findViewById(R.id.verification_text);
        mNextButton = findViewById(R.id.next_button);
        mSwitchButton = findViewById(R.id.switch_button);
        mSimRaycastButton = findViewById(R.id.simraycast_button);

        loadModels();
    }

    @Override
    public void onAttachFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        if (fragment.getId() == R.id.arFragment) {
            arFragment = (ArFragment) fragment;
            arFragment.setOnSessionConfigurationListener(this);
            arFragment.setOnViewCreatedListener(this);
            arFragment.setOnTapArPlaneListener(this);
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
        arFragment.setOnViewCreatedListener(null);

        // Fine adjust the maximum frame rate
        arSceneView.setFrameRateFactor(SceneView.FrameRate.FULL);
    }

    public void loadModels() {
        WeakReference<MainActivity> weakActivity = new WeakReference<>(this);
        ModelRenderable.builder()
                .setSource(this, Uri.parse("https://storage.googleapis.com/ar-answers-in-search-models/static/Tiger/model.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(model -> {
                    MainActivity activity = weakActivity.get();
                    if (activity != null) {
                        activity.model = model;
                    }
                })
                .exceptionally(throwable -> {
                    Toast.makeText(
                            this, "Unable to load model", Toast.LENGTH_LONG).show();
                    return null;
                });
        ViewRenderable.builder()
                .setView(this, R.layout.view_model_title)
                .build()
                .thenAccept(viewRenderable -> {
                    MainActivity activity = weakActivity.get();
                    if (activity != null) {
                        activity.viewRenderable = viewRenderable;
                    }
                })
                .exceptionally(throwable -> {
                    Toast.makeText(this, "Unable to load model", Toast.LENGTH_LONG).show();
                    return null;
                });
    }

    @Override
    public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
//        if (model == null || viewRenderable == null) {
//            Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
//            return;
//        }
        if (this.anchor == null) {
            // Create the Anchor.
            Anchor anchor = hitResult.createAnchor();
            AnchorNode anchorNode = new AnchorNode(anchor);
            anchorNode.setParent(arFragment.getArSceneView().getScene());
            this.anchor = anchorNode;
            Context context = getApplicationContext();

            this.componentA = new ComponentA(context, this.arFragment, this.anchor);
            this.componentB = new ComponentB(context, this.arFragment, this.anchor);

            this.experimentNum = 0;

            mMessageView.setVisibility(View.VISIBLE);
            mMessageText.setText("Start experiment by clicking Launch ComponentA");
            mNextButton.setText("Launch ComponentA");
            mNextButton.setOnClickListener((view -> {
                this.experimentNum++;
                mMessageView.setVisibility(View.GONE);
                if (this.experimentNum == 1 && this.anchor != null)
                {
                    this.componentA.launch();
                    mMessageText.setText("Verify cube 1 and click Launch ComponentB");
                    mNextButton.setText("Launch ComponentB");
                    mMessageView.setVisibility(View.VISIBLE);

                }
                else if(this.experimentNum == 2 && this.anchor != null)
                {
                    this.componentB.launch(this.componentA.cube1);
                    mMessageText.setText("Test Simulated Raycasts");
//                    mNextButton.setText("Experiment: " + (this.experimentNum - 2 + 1));
                    mNextButton.setVisibility(View.GONE);
                    mSwitchButton.setVisibility(View.VISIBLE);
                    mSimRaycastButton.setVisibility(View.VISIBLE);
                    this.experimentNum = 1;
                    mMessageView.setVisibility(View.VISIBLE);
                }
//                else if (this.experimentNum < 5)
//                {
//                    this.componentA.next(this.experimentNum - 2);
//                    this.componentB.next(this.experimentNum - 2);
//                    if(this.experimentNum == 4)
//                    {
//                        mMessageText.setText("Verify whether the click count changed. Then refresh experiment.");
//                        mNextButton.setText("Experiments Finished");
//                    } else
//                    {
//                        mMessageText.setText("Verify whether the click count changed. Then click experiment: " + (this.experimentNum - 2 + 1));
//                        mNextButton.setText("Experiment: " + (this.experimentNum - 2 + 1));
//                    }
//                    mMessageView.setVisibility(View.VISIBLE);
//                }
//                else {
//                    experimentNum = 0;
//                    componentA.next(experimentNum);
//                    componentB.next(experimentNum);
//                }
            }));
            mSwitchButton.setOnClickListener((view1 -> {
                if (this.experimentNum == 1)
                {
                    this.experimentNum = 2;
                }
                else
                {
                    this.experimentNum = 1;
                }
            }));

            mSimRaycastButton.setOnClickListener((view2 -> {
                this.componentB.next(this.experimentNum);
            }));

            // Create the transformable model and add it to the anchor.
//        TransformableNode model = new TransformableNode(arFragment.getTransformationSystem());
//        model.setParent(anchorNode);
//        model.setRenderable(this.model)
//                .animate(true).start();
//        model.select();

//        Node titleNode = new Node();
//        titleNode.setParent(model);
//        titleNode.setEnabled(false);
//        titleNode.setLocalPosition(new Vector3(0.0f, 1.0f, 0.0f));
//        titleNode.setRenderable(viewRenderable);
//        titleNode.setEnabled(true);
//
        }
    }
}
