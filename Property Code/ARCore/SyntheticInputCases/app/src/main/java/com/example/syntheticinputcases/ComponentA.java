package com.example.syntheticinputcases;

import android.view.View;

import com.google.ar.sceneform.rendering.Color;

public class ComponentA implements ARComponent {
    private ArCube cube1;

    @Override
    public void launch() {
        cube1 = new ArCube("1", new Color(255, 0, 0), Globals.gContext, Globals.gArFragment, Globals.gAnchorNode,
                ArCube.LabelPosition.LEFT,
                Globals.CLICK_INFO_LABELS.get(0));
    }

    @Override
    public void next(int experimentNum) {
        cube1.removeCube();
        cube1 = new ArCube("1", new Color(255, 0, 0), Globals.gContext, Globals.gArFragment, Globals.gAnchorNode,
                ArCube.LabelPosition.LEFT,
                Globals.CLICK_INFO_LABELS.get(0));
    }
}
