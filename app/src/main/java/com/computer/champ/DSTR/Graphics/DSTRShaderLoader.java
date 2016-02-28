package com.computer.champ.DSTR.Graphics;

import android.opengl.GLES20;

public class DSTRShaderLoader {

    public static int loadShader(int type, String code) {
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, code);
        GLES20.glCompileShader(shader);

        return shader;
    }

}
