package com.example.samespacecases;

import android.content.Context;
import android.widget.TextView;

import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.ArrayList;
import java.util.List;

public class Globals {
    public static final float CUBE_WIDTH = 0.1f;
    public static final List<TextView> CLICK_INFO_LABELS = new ArrayList<>();

    public static Context gContext;
    public static ArFragment gArFragment;
    public static AnchorNode gAnchorNode;
}
