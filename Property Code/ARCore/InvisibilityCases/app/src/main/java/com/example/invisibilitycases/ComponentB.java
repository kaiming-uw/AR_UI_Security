package com.example.invisibilitycases;

import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;

public class ComponentB implements com.example.invisibilitycases.ARComponent {
    private ArWall mArWall;

    @Override
    public void launch() {
        this.mArWall = new ArWall("Cube 2", new Color(0, 0, 255, 0.5f), Globals.gContext, Globals.gArFragment, Globals.gAnchorNode,
                Globals.CLICK_INFO_LABELS.get(1), new Vector3(0, 0, Globals.CUBE_WIDTH * 2), false, false);
    }

    @Override
    public void next(int experimentNum) {
        if (this.mArWall == null) {
            throw new IllegalStateException("Must call launch() before next()");
        }
        this.mArWall.removeCube();
        switch (experimentNum) {
            case 1:
                this.mArWall = new ArWall("Cube 3" + experimentNum, new Color(0, 0, 255, 0), Globals.gContext, Globals.gArFragment, Globals.gAnchorNode,
                        Globals.CLICK_INFO_LABELS.get(2), new Vector3(0, 0,Globals.CUBE_WIDTH * 2), false, false);
                break;
            case 2:
                // disable render
                this.mArWall = new ArWall("Cube 3" + experimentNum, new Color(0, 0, 255, 0), Globals.gContext, Globals.gArFragment, Globals.gAnchorNode,
                        Globals.CLICK_INFO_LABELS.get(2), new Vector3(0, 0,Globals.CUBE_WIDTH * 2), true, false);
                break;
            case 3:
                // disable render and collider
                this.mArWall = new ArWall("Cube 3" + experimentNum, new Color(0, 0, 255, 0), Globals.gContext, Globals.gArFragment, Globals.gAnchorNode,
                        Globals.CLICK_INFO_LABELS.get(2), new Vector3(0, 0,Globals.CUBE_WIDTH * 2), true, true);
                break;
            case 4:
                // TODO: imported transparent material
                this.mArWall = new ArWall("Cube 3" + experimentNum, null, Globals.gContext, Globals.gArFragment, Globals.gAnchorNode,
                        Globals.CLICK_INFO_LABELS.get(2), new Vector3(0, 0,Globals.CUBE_WIDTH * 2), false, false);
                break;
            case 5:
                // null material
                this.mArWall = new ArWall("Cube 3" + experimentNum, null, Globals.gContext, Globals.gArFragment, Globals.gAnchorNode,
                        Globals.CLICK_INFO_LABELS.get(2), new Vector3(0, 0,Globals.CUBE_WIDTH * 2), false, false);
                break;
            default:
                break;
        }
    }
}