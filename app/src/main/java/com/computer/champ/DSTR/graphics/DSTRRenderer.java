package com.computer.champ.DSTR.graphics;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import com.computer.champ.DSTR.graphics.element.Element;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class DSTRRenderer implements GLSurfaceView.Renderer {

    private Square sq, sq2;

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

        sq = new Square();
        bufferManager.add(sq);

        // load shaders
        int vertexShader = DSTRShaderManager.loadVertexShader();
        int fragmentShader = DSTRShaderManager.loadFragmentShader();

        // create empty OpenGL ES Program
        glProgram = GLES20.glCreateProgram();

        GLES20.glAttachShader(glProgram, vertexShader);
        GLES20.glAttachShader(glProgram, fragmentShader);
        GLES20.glLinkProgram(glProgram);

        // use culling to remove back faces.
        GLES20.glEnable(GLES20.GL_CULL_FACE);

        // enable depth testing
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);
        GLES20.glDepthMask(true);

        GLES20.glFrontFace(GLES20.GL_CCW);
    }

    private float[] mRotationMatrix = new float[16];
    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT);

        // view matrix
        Matrix.setLookAtM(
                mViewMatrix,       // resulting model/view
                0,                 // not used
                0.0f, 0.0f, -4.0f, // eye
                0.0f, 0.0f, 0.0f,  // focus
                0.0f, 1.0f, 0.0f); // top

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

        // draw all Elements
        render();
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float fov = 45.0f;
        float tanMath = fov * (float)Math.PI / 360.0f;
        float top = (float) (Math.tan(tanMath) * 0.1);
        float bottom = -top;
        float left = 0;
        float right = 0;
        if (width < height) {
            left = (9.0f / 15.0f) * bottom;
            right = (9.0f / 15.0f) * top;
        } else {
            left = (15.0f / 9.0f) * bottom;
            right = (15.0f / 9.0f) * top;
        }

        Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, 0.1f, 100.0f);
    }

    public static FloatBuffer makeFloatBuffer(float[] arr) {
        ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(arr);
        fb.position(0);
        return fb;
    }

    public void render() {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(glProgram);

        // load shader attributes and uniforms
        DSTRShaderManager.loadHandles(glProgram);

        // set general uniforms
        // viewing projections
        GLES20.glUniformMatrix4fv(
                DSTRShaderManager.getHandle("vMVP"),
                1,
                false,
                mMVPMatrix,
                0);
        GLES20.glUniformMatrix4fv(
                DSTRShaderManager.getHandle("vModelView"),
                1,
                false,
                mViewMatrix,
                0);

//        float[] camera = { 0.0f, 0.0f, -4.0f };
//        GLES20.glUniform3fv(DSTRShaderManager.getHandle("vCamera"), 1, camera, 0);

        // draw each element
        for (Element e : bufferManager.getElements()) {
            FloatBuffer vertexData = e.getVertexData();

            int stride = (3 + 3) * 4; // (POSITION_DATA + NORMAL_DATA) * BYTES_IN_FLOAT

            int positionHandle = DSTRShaderManager.getHandle("vPosition");
            int normalHandle = DSTRShaderManager.getHandle("vNormal");
            int colourHandle = DSTRShaderManager.getHandle("fColour");

//            System.out.println("P" + positionHandle + " " + "N" + normalHandle + " " + "C" + colourHandle);

            // position data
            vertexData.position(0);
            GLES20.glEnableVertexAttribArray(positionHandle);
            GLES20.glVertexAttribPointer(
                    positionHandle,
                    3,
                    GLES20.GL_FLOAT,
                    false,
                    stride,
                    vertexData);

            // normal data
            vertexData.position(3);
            GLES20.glEnableVertexAttribArray(normalHandle);
            GLES20.glVertexAttribPointer(
                    normalHandle,
                    3,
                    GLES20.GL_FLOAT,
                    false,
                    stride,
                    vertexData);

            // colour data
            GLES20.glUniform4fv(colourHandle, 1, e.getColour(), 0);

            // draw shape
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, e.getVertexData().capacity() / 6);

            // reset attributes
            GLES20.glDisableVertexAttribArray(positionHandle);
            GLES20.glDisableVertexAttribArray(normalHandle);
        }
    }
}