package com.google.ar.sceneform.samples.gltf;

import android.content.Context;
import android.os.Build;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ArKeypad {
    private String name;
    private int numTaps;
    private AnchorNode parentNode;
    private TextView labelView;
    private TextView infoView;
    private TransformableNode transformableNode;
    private List<ArCube> keypadList;
    private Queue<Integer> clicks;
    private String passkey = "123456";
    private Context context;
    public ArKeypad(String name, Color cubeColor, Context context, ArFragment arFragment, AnchorNode parent,
                    ArCube.LabelPosition labelPosition, TextView infoView)
    {
        this.name = name;
        this.numTaps = 0;
        this.parentNode = parent;
        this.infoView = infoView;
        this.labelView = null;
        this.keypadList = new ArrayList<>();
        this.clicks = new LinkedList<Integer>();
        this.context = context;

        // Create keypad
        Vector3 offset_1 = new Vector3((float)-0.2, (float)0.6, 0);
        Vector3 offset_2 = new Vector3(0, (float)0.6, 0);
        Vector3 offset_3 = new Vector3((float)0.2, (float)0.6, 0);
        Vector3 offset_4 = new Vector3((float)-0.2, (float)0.4, 0);
        Vector3 offset_5 = new Vector3(0, (float)0.4, 0);
        Vector3 offset_6 = new Vector3((float)0.2, (float)0.4, 0);
        Vector3 offset_7 = new Vector3((float)-0.2, (float)0.2, 0);
        Vector3 offset_8 = new Vector3(0, (float)0.2, 0);
        Vector3 offset_9 = new Vector3((float)0.2, (float)0.2, 0);
        Vector3 offset_10 = new Vector3(0, (float)0, 0);
        this.keypadList.add(new ArCube("0", cubeColor, context, arFragment, parent, labelPosition, this.infoView, this.clicks, offset_1));
        this.keypadList.add(new ArCube("1", cubeColor, context, arFragment, parent, labelPosition, this.infoView, this.clicks, offset_2));
        this.keypadList.add(new ArCube("2", cubeColor, context, arFragment, parent, labelPosition, this.infoView, this.clicks, offset_3));
        this.keypadList.add(new ArCube("3", cubeColor, context, arFragment, parent, labelPosition, this.infoView, this.clicks, offset_4));
        this.keypadList.add(new ArCube("4", cubeColor, context, arFragment, parent, labelPosition, this.infoView, this.clicks, offset_5));
        this.keypadList.add(new ArCube("5", cubeColor, context, arFragment, parent, labelPosition, this.infoView, this.clicks, offset_6));
        this.keypadList.add(new ArCube("6", cubeColor, context, arFragment, parent, labelPosition, this.infoView, this.clicks, offset_7));
        this.keypadList.add(new ArCube("7", cubeColor, context, arFragment, parent, labelPosition, this.infoView, this.clicks, offset_8));
        this.keypadList.add(new ArCube("8", cubeColor, context, arFragment, parent, labelPosition, this.infoView, this.clicks, offset_9));
        this.keypadList.add(new ArCube("9", cubeColor, context, arFragment, parent, labelPosition, this.infoView, this.clicks, offset_10));

    }

    public boolean authenticate()
    {
        String submission = "";
        while(!this.clicks.isEmpty())
        {
            submission += this.clicks.poll();
        }
        String updateStateText = "You typed: " + submission;
        Toast.makeText(this.context, updateStateText, Toast.LENGTH_SHORT).show();
        return submission.equals(this.passkey);
    }

}
