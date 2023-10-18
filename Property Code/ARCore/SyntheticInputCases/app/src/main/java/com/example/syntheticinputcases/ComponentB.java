package com.example.syntheticinputcases;

import android.os.SystemClock;
import android.view.MotionEvent;

import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.collision.Ray;
import com.google.ar.sceneform.math.Vector3;

public class ComponentB implements ARComponent {
    @Override
    public void launch() {

    }

    @Override
    public void next(int experimentNum) {
        Vector3 anchorPosition = Globals.gAnchorNode.getWorldPosition();
        if (experimentNum == 1) {
            Ray casting = getRay(anchorPosition, false);
            HitTestResult result = Globals.gArFragment.getArSceneView().getScene().hitTest(casting, true);

            long downTime = SystemClock.uptimeMillis();
            long eventTime = SystemClock.uptimeMillis() + 100;
            float x = anchorPosition.x + Globals.CUBE_WIDTH * 4.0f;
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
            result.getNode().onTouchEvent(result, syntheticMotionEventDown);
            result.getNode().onTouchEvent(result, syntheticMotionEventUp);
        } else if (experimentNum == 2) {
            Ray casting = getRay(anchorPosition, true);
            HitTestResult result = Globals.gArFragment.getArSceneView().getScene().hitTest(casting, true);

            long downTime = SystemClock.uptimeMillis();
            long eventTime = SystemClock.uptimeMillis() + 100;
            float x = anchorPosition.x + Globals.CUBE_WIDTH * 4.0f;
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
            result.getNode().onTouchEvent(result, syntheticMotionEventDown);
            result.getNode().onTouchEvent(result, syntheticMotionEventUp);
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
