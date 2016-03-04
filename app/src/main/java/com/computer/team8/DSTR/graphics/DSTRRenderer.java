package com.computer.team8.DSTR.graphics;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import com.computer.team8.DSTR.graphics.camera.Camera;
import com.computer.team8.DSTR.graphics.element.Element;
import com.computer.team8.DSTR.graphics.element.Square;
import com.computer.team8.DSTR.graphics.types.Vec3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class DSTRRenderer implements GLSurfaceView.Renderer {

    // elements
    private Square sq, sq2;
    private Camera cam;

    private int glProgram;
    private DSTRBufferManager bufferManager;

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    public DSTRRenderer() {}

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);

        // init elements
        cam = new Camera(new Vec3(0, 0, -4),
                         new Vec3(0, 0, 0),
                         new Vec3(0, 1, 0));
        sq = new Square();

        bufferManager = new DSTRBufferManager();
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

    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // update camera matrices
        cam.update();

        // draw all Elements
        render();
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        cam.updateFOV(width, height);
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
                cam.getMVP(),
                0);
        GLES20.glUniformMatrix4fv(
                DSTRShaderManager.getHandle("vModelView"),
                1,
                false,
                cam.getView(),
                0);

        // draw each element
        for (Element e : bufferManager.getElements()) {
            FloatBuffer vertexData = e.getVertexData();

            int stride = (3 + 3) * 4; // (POSITION_DATA + NORMAL_DATA) * BYTES_IN_FLOAT

            int positionHandle = DSTRShaderManager.getHandle("vPosition");
            int normalHandle = DSTRShaderManager.getHandle("vNormal");
            int colourHandle = DSTRShaderManager.getHandle("fColour");

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
            GLES20.glUniform4fv(colourHandle, 1, e.getColourData(), 0);

            // draw shape
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, e.getVertexData().capacity() / 6);

            // reset attributes
            GLES20.glDisableVertexAttribArray(positionHandle);
            GLES20.glDisableVertexAttribArray(normalHandle);
        }
    }
}