package com.example.samespacecases;

import android.content.Context;
import android.os.Build;
import android.widget.TextView;

import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

/**
 * Class that represents an interactable AR wall
 */
public class ArCube {

    private final String name;
    private int numTaps;
    private AnchorNode parentNode;
    private TextView labelView;
    private TextView infoView;
    private TransformableNode transformableNode;

    /**
     * Constructs an ArCube
     *
     * @param name unique name of the cube
     * @param cubeColor color for the cube
     * @param context context of the application
     * @param arFragment ArFragment that contains this cube
     * @param parent anchor for this cube
     * @param labelPosition position of label relative to cube
     * @param infoView view which will be used to display information about this cube
     */
    public ArCube(String name, Color cubeColor, Context context, ArFragment arFragment, AnchorNode parent,
                  LabelPosition labelPosition, TextView infoView) {
        this.name = name;
        this.numTaps = 0;
        this.parentNode = parent;
        this.labelView = null;
        this.infoView = infoView;

        MaterialFactory.makeTransparentWithColor(context, cubeColor)
                .thenAccept(material -> {
                    Vector3 vector3 = new Vector3(Globals.CUBE_WIDTH, Globals.CUBE_WIDTH, Globals.CUBE_WIDTH);
                    ModelRenderable model = ShapeFactory.makeCube(vector3,
                            Vector3.zero(), material);
                    model.setShadowCaster(false);
                    model.setShadowReceiver(false);

                    transformableNode = new TransformableNode(arFragment.getTransformationSystem());
                    transformableNode.setName(name);
                    transformableNode.setParent(parent);
                    transformableNode.setRenderable(model);
                    transformableNode.select();
                    transformableNode.setOnTapListener((hitTestResult, tapMotionEvent) -> {
                        numTaps++;
                        String updateStateText = "Cube " + transformableNode.getName() + ", Clicked " + numTaps;
                        infoView.setText(updateStateText);
                    });

                    ViewRenderable.builder().setView(context, R.layout.label_textbox).build()
                            .thenAccept(viewRenderable -> {
                                labelView = (TextView) viewRenderable.getView();
                                labelView.setText(transformableNode.getName());
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    labelView.setTextColor(android.graphics.Color.rgb(cubeColor.r, cubeColor.g, cubeColor.b));
                                }

                                Node titleNode = new Node();
                                titleNode.setParent(transformableNode);
                                titleNode.setEnabled(false);
                                titleNode.setLocalPosition(getLabelPosition(vector3, labelPosition));
                                titleNode.setRenderable(viewRenderable);
                                titleNode.setEnabled(true);
                            });
                });
    }

    /**
     * Constructs an ArCube at the given offset
     *
     * @param name unique name of the cube
     * @param cubeColor color for the cube
     * @param context context of the application
     * @param arFragment ArFragment that contains this cube
     * @param parent anchor for this cube
     * @param labelPosition position of label relative to cube
     * @param infoView view which will be used to display information about this cube
     * @param offset offset from parent anchor position
     */
    public ArCube(String name, Color cubeColor, Context context, ArFragment arFragment, AnchorNode parent,
                  LabelPosition labelPosition, TextView infoView, Vector3 offset) {
        this.name = name;
        this.numTaps = 0;
        this.parentNode = parent;
        this.labelView = null;
        this.infoView = infoView;

        MaterialFactory.makeTransparentWithColor(context, cubeColor)
                .thenAccept(material -> {
                    Vector3 vector3 = new Vector3(Globals.CUBE_WIDTH, Globals.CUBE_WIDTH, Globals.CUBE_WIDTH);
                    ModelRenderable model = ShapeFactory.makeCube(vector3,
                            Vector3.zero(), material);
                    model.setShadowCaster(false);
                    model.setShadowReceiver(false);

                    transformableNode = new TransformableNode(arFragment.getTransformationSystem());
                    transformableNode.setName(name);
                    transformableNode.setParent(parent);
                    transformableNode.setLocalPosition(offset);
                    transformableNode.setRenderable(model);
                    transformableNode.select();
                    transformableNode.setOnTapListener((hitTestResult, tapMotionEvent) -> {
                        numTaps++;
                        String updateStateText = "Cube " + transformableNode.getName() + ", Clicked " + numTaps;
                        infoView.setText(updateStateText);
                    });

                    ViewRenderable.builder().setView(context, R.layout.label_textbox).build()
                            .thenAccept(viewRenderable -> {
                                labelView = (TextView) viewRenderable.getView();
                                labelView.setText(transformableNode.getName());
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    labelView.setTextColor(android.graphics.Color.rgb(cubeColor.r, cubeColor.g, cubeColor.b));
                                }

                                Node titleNode = new Node();
                                titleNode.setParent(transformableNode);
                                titleNode.setEnabled(false);
                                titleNode.setLocalPosition(getLabelPosition(vector3, labelPosition));
                                titleNode.setRenderable(viewRenderable);
                                titleNode.setEnabled(true);
                            });
                });
    }

    public void removeCube() {
        this.parentNode.removeChild(this.transformableNode);
    }

    /**
     * Calculates the position for the label based on the given side of the cube
     *
     * @param cubePosition current position of the cube
     * @param labelPosition the side of the cube to place the label on
     * @return a Vector3 with coordinates for the desired label position
     */
    private Vector3 getLabelPosition(Vector3 cubePosition, LabelPosition labelPosition) {
        if (labelPosition == LabelPosition.LEFT) {
            return new Vector3(cubePosition.x - 0.1f, cubePosition.y, cubePosition.z);
        } else if (labelPosition == LabelPosition.RIGHT)  {
            return new Vector3(cubePosition.x + 0.1f, cubePosition.y, cubePosition.z);
        } else {
            return new Vector3(cubePosition.x - cubePosition.x / 2, cubePosition.y + 0.1f, cubePosition.z);
        }
    }

    public enum LabelPosition {
        LEFT,
        RIGHT,
        TOP
    }
}
