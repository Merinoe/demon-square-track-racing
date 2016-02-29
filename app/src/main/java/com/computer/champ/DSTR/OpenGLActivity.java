package com.computer.champ.DSTR;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.computer.champ.DSTR.graphics.DSTRSurfaceView;

public class OpenGLActivity extends Activity {
    private GLSurfaceView glView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        glView = new DSTRSurfaceView(this);
        setContentView(glView);
    }
}
