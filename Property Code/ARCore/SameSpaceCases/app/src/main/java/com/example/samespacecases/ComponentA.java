package com.example.samespacecases;

import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;

public class ComponentA implements com.example.samespacecases.ARComponent {
    public ArCube cube1;
    public ArCube cube2;

    @Override
    public void launch() {
        cube1 = new ArCube("1", new Color(255, 0, 0), Globals.gContext, Globals.gArFragment, Globals.gAnchorNode,
                ArCube.LabelPosition.TOP,
                Globals.CLICK_INFO_LABELS.get(0),
                new Vector3(Globals.CUBE_WIDTH * 2, 0, 0));

        cube2 = new ArCube("2", new Color(255, 0, 0), Globals.gContext, Globals.gArFragment, Globals.gAnchorNode,
                ArCube.LabelPosition.TOP,
                Globals.CLICK_INFO_LABELS.get(1));
    }

    @Override
    public void next(int experimentNum) {


    }
}
