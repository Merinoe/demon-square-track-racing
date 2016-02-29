package com.computer.champ.DSTR.graphics;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import com.computer.champ.DSTR.graphics.element.Element;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class DSTRRenderer implements GLSurfaceView.Renderer {

    private Triangle tri;

    private int glProgram;
    private DSTRBufferManager bufferManager;

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    public DSTRRenderer() {}

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);

        bufferManager = new DSTRBufferManager();

        // initialize a triangle
        tri = new Triangle();
        bufferManager.add(tri);

        // load shaders
        int vertexShader = DSTRShaderManager.loadVertexShader();
        int fragmentShader = DSTRShaderManager.loadFragmentShader();

        // create empty OpenGL ES Program
        glProgram = GLES20.glCreateProgram();

        GLES20.glAttachShader(glProgram, vertexShader);
        GLES20.glAttachShader(glProgram, fragmentShader);
        GLES20.glLinkProgram(glProgram);
    }

    private float[] mRotationMatrix = new float[16];
    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        float fov = 45.0f;
        float tanMath = fov * (float)Math.PI / 360.0f;
        float top = (float) (Math.tan(tanMath) * 0.1);
        float bottom = -top;
        float left = (15.0f / 9.0f) * bottom;
        float right = (15.0f / 9.0f) * top;

        Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, 0.1f, 1000.0f);
//        Matrix.perspectiveM(mProjectionMatrix, 0, 45.0f, 15.0f / 9.0f, 0.1f, 1000.0f);

        Matrix.setLookAtM(
                mViewMatrix,     // resulting viewing frustrum
                0,               // not used
                0, 0, -2,        // eye
                0f, 0f, 0f,      // focus
                0f, 1.0f, 0.0f); // top

        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // test rotation
        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);

        Matrix.setRotateM(
                mRotationMatrix,
                0,       // not used
                angle,   // amount rotated
                0,
                1.0f,    // axis of rotation
                0);
        Matrix.multiplyMM(mMVPMatrix, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        render();
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    public void render() {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(glProgram);

        // load shader attributes and uniforms
        DSTRShaderManager.loadHandles(glProgram);

        // draw each element
        for (Element e : bufferManager.getElements()) {
            // viewing projections
            GLES20.glUniformMatrix4fv(
                    DSTRShaderManager.getMvpHandle(),
                    1,
                    false,
                    mMVPMatrix,
                    0);

            // position data
            GLES20.glEnableVertexAttribArray(DSTRShaderManager.getPositionHandle());
            GLES20.glVertexAttribPointer(
                    DSTRShaderManager.getPositionHandle(),
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