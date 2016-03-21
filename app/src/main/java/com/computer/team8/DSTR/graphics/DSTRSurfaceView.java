package com.computer.team8.DSTR.graphics;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.computer.team8.DSTR.graphics.camera.Camera;
import com.computer.team8.DSTR.graphics.element.Demon;
import com.computer.team8.DSTR.graphics.element.Element;
import com.computer.team8.DSTR.graphics.types.Vec3;

public class DSTRSurfaceView extends GLSurfaceView {
    private final DSTRRenderer glRenderer;
    private static Vec3 cameraPosition;

    public DSTRSurfaceView(Context context) {
        super(context);

        // Create an OpenGL ES 3.0 context
        setEGLContextClientVersion(3);

        glRenderer = new DSTRRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(glRenderer);
    }

    public static void saveGameState() {
        cameraPosition = DSTRRenderer.getCamera().eye;
    }

    public static void reloadGameState() {
        DSTRRenderer.getCamera().setEye(cameraPosition);
    }

    public static void onRotation(float rot) {
        if (DSTRRenderer.isRacing()) {
            Element e = DSTRBufferManager.get(Demon.class);
            assert e != null;
            e.roll(rot);
        }
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

                // rotateHorizontally
                if (e.getPointerCount() == 1) {
                    float dx = x - oldX;
                    float dy = y - oldY;

                    if (dx > cam.MAX_ROTATE_H_SPEED) {
                        dx = cam.MAX_ROTATE_H_SPEED;
                    } else if (dx < -cam.MAX_ROTATE_H_SPEED) {
                        dx = -cam.MAX_ROTATE_H_SPEED;
                    }

                    if (dy > cam.MAX_ROTATE_V_SPEED) {
                        dy = cam.MAX_ROTATE_V_SPEED;
                    } else if (dy < -cam.MAX_ROTATE_V_SPEED) {
                        dy = -cam.MAX_ROTATE_V_SPEED;
                    }

                    cam.rotateHorizontally(-dx / rotateScalingFactor);
                    cam.rotateVertically(-dy / rotateScalingFactor);

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
