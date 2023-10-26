package com.google.ar.sceneform.samples.gltf;

import android.content.Context;

import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.ux.ArFragment;

public class ComponentA implements com.google.ar.sceneform.samples.gltf.ARComponent {
    public ArCube cube1;
    public ArCube cube2;
    private Context context;
    private ArFragment arFragment;
    private AnchorNode anchorNode;

    public ComponentA(Context context, ArFragment fragment, AnchorNode node)
    {
        this.context = context;
        this.arFragment = fragment;
        this.anchorNode = node;
    }

    @Override
    public void launch() {
        cube1 = new ArCube("1", new Color(255, 0, 0), this.context, this.arFragment, this.anchorNode,
                ArCube.LabelPosition.TOP,
               Globals.CLICK_INFO_LABELS.get(0),
                new Vector3(Globals.CUBE_WIDTH * 2, 0, 0));

        cube2 = new ArCube("2", new Color(255, 0, 0), this.context, this.arFragment, this.anchorNode,
                ArCube.LabelPosition.TOP,
                Globals.CLICK_INFO_LABELS.get(1));
    }

    @Override
    public void next(int experimentNum) {


    }
}
