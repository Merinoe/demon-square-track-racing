package com.computer.champ.DSTR.graphics;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.computer.champ.DSTR.graphics.element.Element;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class DSTRRenderer implements GLSurfaceView.Renderer {

    private int glProgram;
    private DSTRBufferManager bufferManager;

    public DSTRRenderer() {}

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);

        bufferManager = new DSTRBufferManager();

        // initialize a triangle
        bufferManager.add(new Triangle());

        // load shaders
        int vertexShader = DSTRShaderManager.loadVertexShader();
        int fragmentShader = DSTRShaderManager.loadFragmentShader();

        // create empty OpenGL ES Program
        glProgram = GLES20.glCreateProgram();

        GLES20.glAttachShader(glProgram, vertexShader);
        GLES20.glAttachShader(glProgram, fragmentShader);
        GLES20.glLinkProgram(glProgram);
    }

    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        render();
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    public void render() {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(glProgram);

        // load shader attributes and uniforms
        DSTRShaderManager.loadHandles(glProgram);

        // draw each element
        for (Element e : bufferManager.getElements()) {
            // position data
            GLES20.glEnableVertexAttribArray(DSTRShaderManager.getPositionHandle());
            GLES20.glVertexAttribPointer(DSTRShaderManager.getPositionHandle(),
                    3,
                    GLES20.GL_FLOAT,
                    false,
                    0,
                    e.getVertexList());

            // colour data
            GLES20.glUniform4fv(DSTRShaderManager.getColourHandle(), 1, e.getColour(), 0);

            // draw shape
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, e.getVertexList().capacity() / 3);
        }
    }
}