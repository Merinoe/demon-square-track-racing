package com.computer.team8.DSTR.graphics;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.computer.team8.DSTR.graphics.camera.Camera;
import com.computer.team8.DSTR.graphics.types.Vec3;

public class DSTRSurfaceView extends GLSurfaceView {
    private final DSTRRenderer glRenderer;

    public DSTRSurfaceView(Context context) {
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        glRenderer = new DSTRRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(glRenderer);
    }


    float oldX, oldY;
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

        float rotateScalingFactor = 3;
        float zoomScalingFactor = 100;

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                Camera cam = DSTRRenderer.getCamera();

                // rotate
                if (e.getPointerCount() == 1) {
                    float dx = x - oldX;
                    float dy = y - oldY;

                    if (dx > cam.MAX_ROTATE_SPEED) {
                        dx = cam.MAX_ROTATE_SPEED;
                    } else if (dx < -cam.MAX_ROTATE_SPEED) {
                        dx = -cam.MAX_ROTATE_SPEED;
                    }

                    if (dy > cam.MAX_ROTATE_SPEED) {
                        dy = cam.MAX_ROTATE_SPEED;
                    } else if (dy < -cam.MAX_ROTATE_SPEED) {
                        dy = -cam.MAX_ROTATE_SPEED;
                    }

                    cam.rotate(-dx / rotateScalingFactor, new Vec3(0.0f, 1.0f, 0.0f));
                    cam.rotate(-dy / rotateScalingFactor, new Vec3(1.0f, 0.0f, 0.0f));

                    oldX = x;
                    oldY = y;

                // zoom
                } else if (e.getPointerCount() == 2) {  // two fingers for a zoom
                    float dy = y - oldY;

                    if (dy > cam.MAX_ZOOM_SPEED) {
                        dy = cam.MAX_ZOOM_SPEED;
                    } else if (dy < -cam.MAX_ZOOM_SPEED) {
                        dy = -cam.MAX_ZOOM_SPEED;
                    }

                    cam.zoom(-dy / zoomScalingFactor);

                    oldY = y;
                }
                break;

            default:
                oldX = 0;
                oldY = 0;
        }

        return true;
    }
}
