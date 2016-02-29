package com.computer.champ.DSTR.graphics;

import android.opengl.GLES20;

import java.util.HashMap;
import java.util.Map;

public class DSTRShaderManager {

    private static final String vertexShader =
        "uniform mat4 vMVP;" +
        "attribute vec3 vPosition;" +
        "attribute vec3 vNormal;" +
        "uniform vec3 vCamera;" +
        "varying float fCamera;" +
//        "varying vec3 fNormal;" +
        "void main() {" +
        "    vec4 pos = vMVP * vec4( vPosition, 1.0 );" +
        "    fCamera = length( vec4( vCamera, 1.0 ) - pos );" +
//        "    fNormal = normalize( gl_NormalMatrix * vNormal );" +
        "    gl_Position = pos;" +
        "}";

    private static final String fragmentShader =
        "precision highp float;" +
        "uniform vec4 fColour;" +
        "uniform float fCamera;" +
//        "uniform vec3 fNormal;" +
        "void main() {" +
//        "    float directionalLight = max( dot( normalize(fNormal), vec3(0.5, 0.5, 0) ), 0.0 );" +
        "    gl_FragColor = fColour;" + // * directionalLight;" +
        "}";

    private static Map<String, Integer> handleMap = new HashMap<>();

    public static void loadHandles(int program) {
        // vertex shader
        handleMap.put("vMVP", GLES20.glGetUniformLocation(program, "vMVP"));
        handleMap.put("vPosition", GLES20.glGetAttribLocation(program, "vPosition"));
        handleMap.put("vNormal", GLES20.glGetAttribLocation(program, "vNormal"));
        handleMap.put("vCamera", GLES20.glGetUniformLocation(program, "vCamera"));

        // fragment shader
        handleMap.put("fColour", GLES20.glGetUniformLocation(program, "fColour"));
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
