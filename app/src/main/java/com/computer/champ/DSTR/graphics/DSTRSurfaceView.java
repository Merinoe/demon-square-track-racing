package com.computer.champ.DSTR.graphics;

import android.content.Context;
import android.opengl.GLSurfaceView;

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
}
