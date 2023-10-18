package com.google.ar.sceneform.samples.gltf;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

/**
 * Class that represents an interactable AR wall
 */
public class ArWall {
    private final String DEBUG_TAG = "ArWall";
    private final String name;
    private int numTaps;
    private AnchorNode parentNode;
    private TextView infoView;
    private TransformableNode transformableNode;
    private boolean disableRender;
    private boolean disableCollider;

    /**
     * Creates a new wall at the given offset of the given color
     *
     * @param name unique name of the wall
     * @param wallColor color for the wall
     * @param context context of the application
     * @param arFragment ArFragment that contains this wall
     * @param parent anchor for this wall
     * @param infoView view which will be used to display information about this wall
     * @param offset offset from parent anchor position
     * @param disableRender whether to disable rendering on this object
     * @param disableCollider whether to disable the collider on this object
     */
    public ArWall(String name, Color wallColor, Context context, ArFragment arFragment, AnchorNode parent,
                  TextView infoView, Vector3 offset, boolean disableRender, boolean disableCollider) {
        this.name = name;
        this.numTaps = 0;
        this.parentNode = parent;
        this.infoView = infoView;
        this.disableRender = disableRender;
        this.disableCollider = disableCollider;

        infoView.setText(name + ", Clicked " + numTaps);
        createCubeRemoveOnTap(context, arFragment, wallColor, offset);
    }

    /**
     * Helper method that creates a removeable cube
     *
     * @param context context of the application
     * @param arFragment ArFragment that contains this cube
     * @param wallColor color for the wall
     * @param offset offset from parent anchor position
     */
    private void createCubeRemoveOnTap(Context context, ArFragment arFragment, Color wallColor, Vector3 offset) {
        MaterialFactory.makeTransparentWithColor(context, wallColor)
                .thenAccept(material -> {
                    Vector3 vector3 = new Vector3(Globals.CUBE_WIDTH * 4, Globals.CUBE_WIDTH * 4, Globals.CUBE_WIDTH * 0.1f);
                    ModelRenderable model = null;
                    if (!disableRender) {
                        model = ShapeFactory.makeCube(vector3,
                                Vector3.zero(), material);
                        model.setShadowCaster(false);
                        model.setShadowReceiver(false);
                    }

                    // Create TransformableNode to handle input and attach wall model to
                    transformableNode = new TransformableNode(arFragment.getTransformationSystem());
                    transformableNode.setName(name);
                    transformableNode.setParent(parentNode);
                    transformableNode.setLocalPosition(offset);
                    transformableNode.setRenderable(model);
                    if (disableCollider) {
                        transformableNode.setCollisionShape(null);
                    }
                    transformableNode.select();
                    transformableNode.setOnTapListener((hitTestResult, tapMotionEvent) -> {
                        this.exploit();
                        String updateStateText = transformableNode.getName() + ", Clicked " + numTaps;
                        Toast.makeText(context, updateStateText, Toast.LENGTH_SHORT).show();
//                        parentNode.removeChild(transformableNode);
//                        createCubeRemoveOnTap(context, arFragment, new Color(MainActivity.RANDOM.nextFloat(), MainActivity.RANDOM.nextFloat(), MainActivity.RANDOM.nextFloat()), offset);
                        infoView.setText(updateStateText);
                    });
                });
    }

    /**
     * Removes the ArWall if it exists
     */
    public void removeCube() {
        Log.i(DEBUG_TAG, name + ": " + transformableNode);
        if (transformableNode != null) {
            // TODO: not sure why this would ever be null?
            infoView.setText(transformableNode.getName() + ": not initialized");
            parentNode.removeChild(transformableNode);
        }
    }
    public void exploit()
    {
        this.numTaps++;
    }
}
