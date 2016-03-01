package com.computer.team8.DSTR;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;

import com.computer.team8.DSTR.graphics.DSTRSurfaceView;

public class OpenGLActivity extends Activity {
    private GLSurfaceView glView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hide the status bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        // initialize OpenGL
        glView = new DSTRSurfaceView(this);
        setContentView(glView);
    }
}
