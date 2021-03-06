package com.computer.team8.DSTR.graphics;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.computer.team8.DSTR.graphics.camera.Camera;
import com.computer.team8.DSTR.graphics.element.Demon;
import com.computer.team8.DSTR.graphics.element.Element;
import com.computer.team8.DSTR.graphics.light.DirectionalLight;
import com.computer.team8.DSTR.graphics.track.Track;
import com.computer.team8.DSTR.graphics.track.TrackManager;
import com.computer.team8.DSTR.graphics.types.Vec3;
import com.computer.team8.DSTR.multiplayer.DSTRNetworkManager;
import com.computer.team8.DSTR.projectui.Preferences;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class DSTRRenderer implements GLSurfaceView.Renderer{

    // game state
    private static boolean RACING;

    // bluetooth
    DSTRNetworkManager network;

    // scene
    private DirectionalLight dirLight;
    private Track track;

    // elements
    private static Camera cam;
    private Demon demon;

    private int glProgram;
    private DSTRBufferManager bufferManager;

    public DSTRRenderer(Context context) {
        // init scene
        String color = Preferences.getColor(context);
        dirLight = new DirectionalLight();
        track = TrackManager.getCurrentTrack();

        // bluetooth
        network = new DSTRNetworkManager();

        // init elements
        cam = new Camera(new Vec3(0, 0, 0), // eye
                new Vec3(0, 0, 0),  // focus
                new Vec3(0, 1, 0)); // top
        demon = new Demon(color);
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
        GLES20.glClearColor(0.35f, 0.45f, 1.0f, 1.0f);

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

        // update demon's position and orientation on track
        if (RACING && demon.rideTrack()) {
            RACING = false;
            cam.setSubject(null);
            network.sendComplete();
        } else if (demon.hasFailed()) {
            // demon falls until limit
            if (demon.failFall()) {
                cam.setSubject(demon);
            } else {
                cam.setSubject(null);
            }
        }

        // send/receive data from DE2
        if (!demon.hasFailed()) {
            network.sendMessage(demon.getBluetoohData(), "fast");
        }

        // draw all Elements
        render();
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        cam.updateFOV(width, height);
    }

    public static Camera getCamera() {
        return cam;
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

        // directional light data
        GLES20.glUniform3fv(
                DSTRShaderManager.getHandle("fDirectionalLight"),
                1,
                dirLight.getOrientationData(),
                0);
        GLES20.glUniform1f(
                DSTRShaderManager.getHandle("fDirectionalIntensity"),
                dirLight.getDirectionalIntensity());
        GLES20.glUniform1f(
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

            // scale data
            GLES20.glUniform3fv(scaleHandle, 1, e.getScaleData(), 0);

            // model position data
            GLES20.glUniform3fv(modelPosHandle, 1, e.getPositionData(), 0);

            // orientation data
            GLES20.glUniformMatrix4fv(orientHandle, 1, false, e.getOrientation(), 0);

            // colour data
            GLES20.glUniform4fv(colourHandle, 1, e.getColourData(), 0);

            // draw shape
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, e.getBuffer().capacity() / 6);

            // reset attributes
            GLES20.glDisableVertexAttribArray(positionHandle);
            GLES20.glDisableVertexAttribArray(normalHandle);

            vertexData.clear();
        }


        FloatBuffer data = track.getBuffer();

        // position data
        data.position(0);
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(
                positionHandle,
                3,
                GLES20.GL_FLOAT,
                false,
                0,
                data);

        // normal data
        data.position(0);
        GLES20.glEnableVertexAttribArray(normalHandle);
        GLES20.glVertexAttribPointer(
                normalHandle,
                3,
                GLES20.GL_FLOAT,
                false,
                0,
                data);

        // scale data
        GLES20.glUniform3fv(scaleHandle, 1, Track.TRACK_SCALE, 0);

        // model position data
        GLES20.glUniform3fv(modelPosHandle, 1, Track.TRACK_POSITION, 0);

        // orientation data
        GLES20.glUniformMatrix4fv(orientHandle, 1, false, Track.TRACK_ORIENTATION, 0);

        // colour data
        GLES20.glUniform4fv(colourHandle, 1, Track.TRACK_COLOUR, 0);

        // draw shape
        GLES20.glLineWidth(Track.TRACK_WIDTH);
        GLES20.glDrawArrays(GLES20.GL_LINE_STRIP, 0, track.getNumPoints());
    }
}