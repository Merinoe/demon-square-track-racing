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

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("Down-> x: " + x + ", y: " + y);
                break;
            case MotionEvent.ACTION_MOVE:
                Camera cam = DSTRRenderer.getCamera();

                float dx = x - oldX;
                float dy = y - oldY;

                cam.rotate(-dx / 3, new Vec3(0.0f, 1.0f, 0.0f));
                cam.rotate(-dy / 3, new Vec3(1.0f, 0.0f, 0.0f));

                oldX = x;
                oldY = y;

                break;
            default:
        }

        return true;
    }
}
