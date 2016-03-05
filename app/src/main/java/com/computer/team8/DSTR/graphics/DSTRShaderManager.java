package com.computer.team8.DSTR.graphics;

import android.opengl.GLES20;

import java.util.HashMap;
import java.util.Map;

public class DSTRShaderManager {

    private static final String vertexShader =
        "attribute vec3 vPosition;" +
        "attribute vec3 vNormal;" +
        "uniform mat4 vMVP;" +
        "uniform mat4 vModelView;" +
        "uniform mat4 vOrientation;" +
        "uniform vec3 vModelPosition;" +
        "varying vec3 fNormal;" +
        "void main() {" +
        "    vec4 pos = vMVP * vOrientation * ( vec4( vPosition, 1.0 ) + vModelPosition );" +
        "    fNormal = vec3( vModelView * vOrientation * vec4( vNormal, 1.0 ) );" +
        "    gl_Position = pos;" +
        "}";

    private static final String fragmentShader =
        "precision highp float;" +
        "uniform vec4 fColour;" +
        "uniform vec3 fDirectionalLight;" +
        "uniform float fDirectionalIntensity;" +
        "uniform float fAmbientIntensity;" +
        "varying vec3 fNormal;" +
        "void main() {" +
        "    float dirInt = fDirectionalIntensity * max( dot( normalize(fNormal), -normalize(fDirectionalLight) ), 0.0 );" +
        "    gl_FragColor = (fColour * fAmbientIntensity) + (fColour * dirInt);" +
        "}";

    private static Map<String, Integer> handleMap = new HashMap<>();

    public static void loadHandles(int program) {
        // vertex shader
        handleMap.put("vMVP", GLES20.glGetUniformLocation(program, "vMVP"));
        handleMap.put("vModelView", GLES20.glGetUniformLocation(program, "vModelView"));
        handleMap.put("vPosition", GLES20.glGetAttribLocation(program, "vPosition"));
        handleMap.put("vModelPosition", GLES20.glGetUniformLocation(program, "vModelPosition"));
        handleMap.put("vNormal", GLES20.glGetAttribLocation(program, "vNormal"));
        handleMap.put("vOrientation", GLES20.glGetUniformLocation(program, "vOrientation"));

        // fragment shader
        handleMap.put("fColour", GLES20.glGetUniformLocation(program, "fColour"));
        handleMap.put("fDirectionalLight", GLES20.glGetUniformLocation(program, "fDirectionalLight"));
        handleMap.put("fDirectionalIntensity", GLES20.glGetUniformLocation(program, "fDirectionalIntensity"));
        handleMap.put("fAmbientIntensity", GLES20.glGetUniformLocation(program, "fAmbientIntensity"));

    }

    public static int getHandle(String name) {
        return handleMap.get(name);
    }

    public static int loadVertexShader() {
        int shader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, vertexShader);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public static int loadFragmentShader() {
        int shader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, fragmentShader);
        GLES20.glCompileShader(shader);

        return shader;
    }
}
