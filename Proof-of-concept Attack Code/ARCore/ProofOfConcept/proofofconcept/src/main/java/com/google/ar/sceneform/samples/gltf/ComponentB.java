package com.google.ar.sceneform.samples.gltf;

import android.content.Context;
import android.net.Uri;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.collision.Ray;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class ComponentB implements com.google.ar.sceneform.samples.gltf.ARComponent {
    public ArWall wall1;
    private Context context;
    private ArFragment arFragment;
    private AnchorNode anchorNode;
    private ArCube targetCube;
    private TransformableNode transformableNode;

    public ComponentB(Context context, ArFragment fragment, AnchorNode node)
    {
        this.context = context;
        this.arFragment = fragment;
        this.anchorNode = node;
        this.targetCube = null;
        this.wall1 = null;
    }

    @Override
    public void launch() {
        this.wall1 = new ArWall("Cube 2", new Color(0, 0, 255, 1f), this.context, this.arFragment, this.anchorNode,
                null, new Vector3(0, 0, Globals.CUBE_WIDTH * 2), false, false, true);

        // Place ad
        ModelRenderable.builder()
                .setSource(this.context, Uri.parse("https://students.washington.edu/arkabhat/applications/scene.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build()
                .thenAccept(model -> {
                    this.transformableNode = new TransformableNode(this.arFragment.getTransformationSystem());
                    this.transformableNode.setName("Advertisement");
                    this.transformableNode.setParent(this.anchorNode);
                    this.transformableNode.setLocalPosition(new Vector3(2, 0, 0));
                    this.transformableNode.setLocalScale(new Vector3(0.15f, 0.15f, 0.15f));
                    this.transformableNode.setRenderable(model);
                })
                .exceptionally(throwable -> {
                    Toast.makeText(
                            this.context, "Unable to load model", Toast.LENGTH_LONG).show();
                    return null;
                });
    }

    @Override
    public void next(int experimentNum) {
        this.wall1.removeCube();
        if (this.transformableNode != null) {
            // TODO: not sure why this would ever be null?
            this.anchorNode.removeChild(this.transformableNode);
        }
    }

    private static Ray getRay(Vector3 anchorPosition, boolean startInsideAnchor) {
        Vector3 startPoint = startInsideAnchor ? new Vector3(anchorPosition.x + Globals.CUBE_WIDTH / 4.0f, anchorPosition.y, anchorPosition.z)
                : new Vector3(anchorPosition.x + 2, anchorPosition.y, anchorPosition.z);
        Vector3 endPoint = anchorPosition;
        Vector3 direction = Vector3.subtract(endPoint, startPoint);
        return new Ray(startPoint, direction);
    }
}
