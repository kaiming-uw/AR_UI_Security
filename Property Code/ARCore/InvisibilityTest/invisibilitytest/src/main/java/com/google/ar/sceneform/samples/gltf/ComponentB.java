package com.google.ar.sceneform.samples.gltf;

import android.content.Context;

import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.ux.ArFragment;

public class ComponentB implements com.google.ar.sceneform.samples.gltf.ARComponent {
    public ArWall wall1;
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
        this.wall1 = new ArWall("Cube 2", new Color(0, 0, 255, 1f), this.context, this.arFragment, this.anchorNode,
                Globals.CLICK_INFO_LABELS.get(1), new Vector3(0, 0, Globals.CUBE_WIDTH * 2), false, false, false);
    }

    @Override
    public void next(int experimentNum) {
        if (this.wall1 == null) {
            throw new IllegalStateException("Must call launch() before next()");
        }
        this.wall1.removeCube();
        switch (experimentNum) {
            case 1:
                this.wall1 = new ArWall("Cube 3.1", new Color(0, 0, 255, 0f), this.context, this.arFragment, this.anchorNode,
                        Globals.CLICK_INFO_LABELS.get(experimentNum + 2 - 1), new Vector3(0, 0,Globals.CUBE_WIDTH * 2), false, false, false);
                break;
            case 2:
                // disable render
                this.wall1 = new ArWall("Cube 3.2", new Color(0, 0, 255, 0), this.context, this.arFragment, this.anchorNode,
                        Globals.CLICK_INFO_LABELS.get(experimentNum + 2 - 1), new Vector3(0, 0,Globals.CUBE_WIDTH * 2), true, false, false);
                break;
            case 3:
                // TODO: imported transparent material
                this.wall1 = new ArWall("Cube 3.3", null, this.context, this.arFragment, this.anchorNode,
                        Globals.CLICK_INFO_LABELS.get(experimentNum + 2 - 1), new Vector3(0, 0,Globals.CUBE_WIDTH * 2), false, false, true);
                break;
            case 4:
                // null material
                this.wall1 = new ArWall("Cube 3.4", null, this.context, this.arFragment, this.anchorNode,
                        Globals.CLICK_INFO_LABELS.get(experimentNum + 2 - 1), new Vector3(0, 0,Globals.CUBE_WIDTH * 2), false, false, false);
                break;
            default:
                break;
        }
    }
}
