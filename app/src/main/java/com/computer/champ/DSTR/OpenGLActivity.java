package com.computer.champ.DSTR;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;

import com.computer.champ.DSTR.graphics.DSTRSurfaceView;

public class OpenGLActivity extends Activity {
    private GLSurfaceView glView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glView = new DSTRSurfaceView(this);
        setContentView(glView);
    }
}
