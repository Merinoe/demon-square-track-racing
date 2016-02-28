package com.computer.champ.DSTR.Graphics;

import android.opengl.GLES20;

public class DSTRShaderManager {

    private static final String vertexShader =
        "attribute vec4 vPosition;" +
        "void main() {" +
        "    gl_Position = vPosition;" +
        "}";

    private static final String fragmentShader =
        "precision mediump float;" +
        "uniform vec4 vColour;" +
        "void main() {" +
        "    gl_FragColor = vColour;" +
        "}";

    private static int positionHandle;
    private static int colourHandle;

    public static void loadHandles(int program) {
        positionHandle = GLES20.glGetAttribLocation(program, "vPosition");
        colourHandle = GLES20.glGetUniformLocation(program, "vColour");
    }

    public static int getPositionHandle() {
        return positionHandle;
    }

    public static int getColourHandle() {
        return colourHandle;
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
