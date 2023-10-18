package com.example.invisibilitycases;

import com.google.ar.sceneform.rendering.Color;

public class ComponentA implements com.example.invisibilitycases.ARComponent {
    private ArCube mArCube;

    @Override
    public void launch() {
        this.mArCube = new ArCube("Cube 1", new Color(255, 0, 0), Globals.gContext, Globals.gArFragment, Globals.gAnchorNode,
                ArCube.LabelPosition.LEFT,
                Globals.CLICK_INFO_LABELS.get(0));
    }

    @Override
    public void next(int experimentNum) {

    }
}
