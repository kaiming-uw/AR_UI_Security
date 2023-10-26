package com.google.ar.sceneform.samples.gltf;

import android.content.Context;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.collision.Ray;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.ux.ArFragment;

public class ComponentB implements com.google.ar.sceneform.samples.gltf.ARComponent {
    public ArWall wall1;
    private Context context;
    private ArFragment arFragment;
    private AnchorNode anchorNode;
    private ArCube targetCube;

    public ComponentB(Context context, ArFragment fragment, AnchorNode node)
    {
        this.context = context;
        this.arFragment = fragment;
        this.anchorNode = node;
        this.targetCube = null;
    }

    @Override
    public void launch() {

    }

    public void launch(ArCube target) {
        this.targetCube = target;
    }

    @Override
    public void next(int experimentNum) {
        Vector3 anchorPosition = this.anchorNode.getWorldPosition();
        String updateStateText = "Sending simulated raycast, Current click count " + this.targetCube.getNumTaps();
        Toast.makeText(this.context, updateStateText, Toast.LENGTH_SHORT).show();
        if (experimentNum == 1) {
            Ray casting = getRay(anchorPosition, false);
            HitTestResult result = this.arFragment.getArSceneView().getScene().hitTest(casting, true);

            long downTime = SystemClock.uptimeMillis();
            long eventTime = SystemClock.uptimeMillis() + 100;
            float x = anchorPosition.x + Globals.CUBE_WIDTH;
            float y = anchorPosition.y;
            // List of meta states found here: developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
            int metaState = 0;
            MotionEvent syntheticMotionEventDown = MotionEvent.obtain(
                    downTime,
                    eventTime,
                    MotionEvent.ACTION_DOWN,
                    x,
                    y,
                    metaState
            );
            MotionEvent syntheticMotionEventUp = MotionEvent.obtain(
                    downTime,
                    eventTime,
                    MotionEvent.ACTION_UP,
                    x,
                    y,
                    metaState
            );
            if (result != null)
            {
                result.getNode().onTouchEvent(result, syntheticMotionEventDown);
                result.getNode().onTouchEvent(result, syntheticMotionEventUp);
            }
        } else if (experimentNum == 2) {
            Ray casting = getRay(anchorPosition, true);
            HitTestResult result = this.arFragment.getArSceneView().getScene().hitTest(casting, true);

            long downTime = SystemClock.uptimeMillis();
            long eventTime = SystemClock.uptimeMillis() + 100;
            float x = anchorPosition.x;
            float y = anchorPosition.y;
            // List of meta states found here: developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
            int metaState = 0;
            MotionEvent syntheticMotionEventDown = MotionEvent.obtain(
                    downTime,
                    eventTime,
                    MotionEvent.ACTION_DOWN,
                    x,
                    y,
                    metaState
            );
            MotionEvent syntheticMotionEventUp = MotionEvent.obtain(
                    downTime,
                    eventTime,
                    MotionEvent.ACTION_UP,
                    x,
                    y,
                    metaState
            );
            if (result != null)
            {
                result.getNode().onTouchEvent(result, syntheticMotionEventDown);
                result.getNode().onTouchEvent(result, syntheticMotionEventUp);
            }
        }
    }

    private static Ray getRay(Vector3 anchorPosition, boolean startInsideAnchor) {
        Vector3 startPoint = startInsideAnchor ? new Vector3(anchorPosition.x + Globals.CUBE_WIDTH / 4.0f, anchorPosition.y, anchorPosition.z)
                : new Vector3(anchorPosition.x + 2, anchorPosition.y, anchorPosition.z);
        Vector3 endPoint = anchorPosition;
        Vector3 direction = Vector3.subtract(endPoint, startPoint);
        return new Ray(startPoint, direction);
    }
}
