package com.google.ar.sceneform.samples.gltf;

import android.content.Context;

import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.ux.ArFragment;

public class ComponentB implements com.google.ar.sceneform.samples.gltf.ARComponent {
    public ArCube cube3;
    public ArCube cube4;
    private Context context;
    private ArFragment arFragment;
    private AnchorNode anchorNode;

    public ComponentB(Context context, ArFragment fragment, AnchorNode node)
    {
        this.context = context;
        this.arFragment = fragment;
        this.anchorNode = node;
    }

    @Override
    public void launch() {
        cube3 = new ArCube("3", new Color(0, 0, 255), this.context, this.arFragment, this.anchorNode,
                ArCube.LabelPosition.TOP,
                Globals.CLICK_INFO_LABELS.get(2));
        cube4 = new ArCube("4", new Color(0, 0, 255), this.context, this.arFragment, this.anchorNode,
                ArCube.LabelPosition.TOP,
                Globals.CLICK_INFO_LABELS.get(3),
                new Vector3(-Globals.CUBE_WIDTH * 2, 0, 0));
    }

    @Override
    public void next(int experimentNum) {

    }
}
