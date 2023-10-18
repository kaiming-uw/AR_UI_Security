package com.example.samespacecases;

import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;

public class ComponentB implements com.example.samespacecases.ARComponent {
    public ArCube cube3;
    public ArCube cube4;

    @Override
    public void launch() {
        cube3 = new ArCube("3", new Color(0, 0, 255), Globals.gContext, Globals.gArFragment, Globals.gAnchorNode,
                ArCube.LabelPosition.TOP,
                Globals.CLICK_INFO_LABELS.get(2));
        cube4 = new ArCube("4", new Color(0, 0, 255), Globals.gContext, Globals.gArFragment, Globals.gAnchorNode,
                ArCube.LabelPosition.TOP,
                Globals.CLICK_INFO_LABELS.get(3),
                new Vector3(-Globals.CUBE_WIDTH * 2, 0, 0));
    }

    @Override
    public void next(int experimentNum) {

    }
}
