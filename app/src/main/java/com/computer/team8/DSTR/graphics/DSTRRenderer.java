package com.computer.team8.DSTR.graphics;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.computer.team8.DSTR.graphics.camera.Camera;
import com.computer.team8.DSTR.graphics.element.Demon;
import com.computer.team8.DSTR.graphics.element.Element;
import com.computer.team8.DSTR.graphics.light.DirectionalLight;
import com.computer.team8.DSTR.graphics.track.BuiltInTrack;
import com.computer.team8.DSTR.graphics.track.Track;
import com.computer.team8.DSTR.graphics.types.Vec3;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class DSTRRenderer implements GLSurfaceView.Renderer{

    // game state
    private static boolean RACING;

    // scene
    private DirectionalLight dirLight;
    private Track track;

    // elements
    private static Camera cam;
    private Demon demon;

    private int glProgram;
    private DSTRBufferManager bufferManager;

    public DSTRRenderer() {
        // init scene
        dirLight = new DirectionalLight();
        track = new BuiltInTrack();

        // init elements
        cam = new Camera(new Vec3(0, 0, 0), // eye
                new Vec3(0, 0, 0),  // focus
                new Vec3(0, 1, 0)); // top
        demon = new Demon();
        demon.setTrack(track);
        cam.setSubject(demon);

        RACING = true;

        bufferManager = new DSTRBufferManager();
        bufferManager.createLevel();
        bufferManager.add(demon);
    }

    public static boolean isRacing() { return RACING; }

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES30.glClearColor(0.25f, 0.25f, 1.0f, 1.0f);

        // load shaders
        int vertexShader = DSTRShaderManager.loadVertexShader();
        int fragmentShader = DSTRShaderManager.loadFragmentShader();

        // create empty OpenGL ES Program
        glProgram = GLES30.glCreateProgram();

        GLES30.glAttachShader(glProgram, vertexShader);
        GLES30.glAttachShader(glProgram, fragmentShader);
        GLES30.glLinkProgram(glProgram);

        // use culling to remove back faces.
        GLES30.glEnable(GLES30.GL_CULL_FACE);

        // enable depth testing
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        GLES30.glDepthFunc(GLES30.GL_LEQUAL);
        GLES30.glDepthMask(true);

        GLES30.glFrontFace(GLES30.GL_CCW);
    }

    public void onDrawFrame(GL10 unused) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);

        // update camera matrices
        cam.update();

        // update demon's position and orientation on track
        if (RACING && demon.rideTrack()) {
            RACING = false;
            cam.setSubject(null);
        } else if (demon.hasFailed()) {
            // demon falls until limit
            if (demon.failFall()) {
                cam.setSubject(demon);
            } else {
                cam.setSubject(null);
            }
        }

        // draw all Elements
        render();
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
        cam.updateFOV(width, height);
    }

    public static Camera getCamera() {
        return cam;
    }

    public void render() {
        // Add program to OpenGL ES environment
        GLES30.glUseProgram(glProgram);

        // load shader attributes and uniforms
        DSTRShaderManager.loadHandles(glProgram);

        // set general uniforms
        // viewing projections
        GLES30.glUniformMatrix4fv(
                DSTRShaderManager.getHandle("vMVP"),
                1,
                false,
                cam.getMVP(),
                0);
        GLES30.glUniformMatrix4fv(
                DSTRShaderManager.getHandle("vModelView"),
                1,
                false,
                cam.getView(),
                0);

        // directional light data
        GLES30.glUniform3fv(
                DSTRShaderManager.getHandle("fDirectionalLight"),
                1,
                dirLight.getOrientationData(),
                0);
        GLES30.glUniform1f(
                DSTRShaderManager.getHandle("fDirectionalIntensity"),
                dirLight.getDirectionalIntensity());
        GLES30.glUniform1f(
                DSTRShaderManager.getHandle("fAmbientIntensity"),
                dirLight.getAmbientIntensity());

        int stride = (3 + 3) * 4; // (POSITION_DATA + NORMAL_DATA) * BYTES_IN_FLOAT

        // vertex shader variable handles
        int orientHandle = DSTRShaderManager.getHandle("vOrientation");
        int positionHandle = DSTRShaderManager.getHandle("vPosition");
        int modelPosHandle = DSTRShaderManager.getHandle("vModelPosition");
        int scaleHandle = DSTRShaderManager.getHandle("vScale");
        int normalHandle = DSTRShaderManager.getHandle("vNormal");

        // fragment shader variable handles
        int colourHandle = DSTRShaderManager.getHandle("fColour");

        // draw each element
        for (Element e : bufferManager.getElements()) {
            FloatBuffer vertexData = e.getBuffer();

            // position data
            vertexData.position(0);
            GLES30.glEnableVertexAttribArray(positionHandle);
            GLES30.glVertexAttribPointer(
                    positionHandle,
                    3,
                    GLES30.GL_FLOAT,
                    false,
                    stride,
                    vertexData);

            // normal data
            vertexData.position(3);
            GLES30.glEnableVertexAttribArray(normalHandle);
            GLES30.glVertexAttribPointer(
                    normalHandle,
                    3,
                    GLES30.GL_FLOAT,
                    false,
                    stride,
                    vertexData);

            // scale data
            GLES30.glUniform3fv(scaleHandle, 1, e.getScaleData(), 0);

            // model position data
            GLES30.glUniform3fv(modelPosHandle, 1, e.getPositionData(), 0);

            // orientation data
            GLES30.glUniformMatrix4fv(orientHandle, 1, false, e.getOrientation(), 0);

            // colour data
            GLES30.glUniform4fv(colourHandle, 1, e.getColourData(), 0);

            // draw shape
            GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, e.getBuffer().capacity() / 6);

            // reset attributes
            GLES30.glDisableVertexAttribArray(positionHandle);
            GLES30.glDisableVertexAttribArray(normalHandle);

            vertexData.clear();
        }


        FloatBuffer data = track.getBuffer();

        // position data
        data.position(0);
        GLES30.glEnableVertexAttribArray(positionHandle);
        GLES30.glVertexAttribPointer(
                positionHandle,
                3,
                GLES30.GL_FLOAT,
                false,
                0,
                data);

        // normal data
        data.position(0);
        GLES30.glEnableVertexAttribArray(normalHandle);
        GLES30.glVertexAttribPointer(
                normalHandle,
                3,
                GLES30.GL_FLOAT,
                false,
                0,
                data);

        // scale data
        GLES30.glUniform3fv(scaleHandle, 1, Track.TRACK_SCALE, 0);

        // model position data
        GLES30.glUniform3fv(modelPosHandle, 1, Track.TRACK_POSITION, 0);

        // orientation data
        GLES30.glUniformMatrix4fv(orientHandle, 1, false, Track.TRACK_ORIENTATION, 0);

        // colour data
        GLES30.glUniform4fv(colourHandle, 1, Track.TRACK_COLOUR, 0);

        // draw shape
        GLES30.glLineWidth(Track.TRACK_WIDTH);
        GLES30.glDrawArrays(GLES30.GL_LINE_STRIP, 0, track.getNumPoints());
    }
}