package com.computer.champ.DSTR.graphics;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

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

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("Down-> x: " + x + ", y: " + y);
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("Moving-> x: " + x + ", y: " + y);
                break;
            default:

        }

        return true;
    }
}
