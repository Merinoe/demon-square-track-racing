package com.computer.champ.DSTR.Graphics;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class DSTRRenderer implements GLSurfaceView.Renderer {

    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private int mProgram;
    private FloatBuffer vertexBuffer;

    private Triangle mTriangle;

    public DSTRRenderer() {
    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);

        // initialize a triangle
        mTriangle = new Triangle();

//        // initialize vertex byte buffer for shape coordinates
//        ByteBuffer bb = ByteBuffer.allocateDirect(
//                        // (number of coordinate values * 4 bytes per float)
//                        3 * 4);
//        // use the device hardware's native byte order
//        bb.order(ByteOrder.nativeOrder());
//
//        // create a floating point buffer from the ByteBuffer
//        vertexBuffer = bb.asFloatBuffer();
//        // add the coordinates to the FloatBuffer
//        vertexBuffer.put(mTriangle.triangleCoords);
//        // set the buffer to read the first coordinate
//        vertexBuffer.position(0);


        int vertexShader = DSTRShaderLoader.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = DSTRShaderLoader.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        // create empty OpenGL ES Program
        mProgram = GLES20.glCreateProgram();

        // add the vertex shader to program
        GLES20.glAttachShader(mProgram, vertexShader);

        // add the fragment shader to program
        GLES20.glAttachShader(mProgram, fragmentShader);

        // creates OpenGL ES program executables
        GLES20.glLinkProgram(mProgram);
    }

    public void onDrawFrame(GL10 unused) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        draw();
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }


    private int mPositionHandle;
    private int mColorHandle;

    private final int vertexCount = 3;
    private final int vertexStride = 3 * 4; // 4 bytes per vertex

    private float[] colour = {0.0f, 1.0f, 0.0f, 1.0f };

    public void draw() {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, 3,
                GLES20.GL_FLOAT, false,
                vertexStride, mTriangle.getVerts());

        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, colour, 0);

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}