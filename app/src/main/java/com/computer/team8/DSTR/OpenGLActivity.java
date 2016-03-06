package com.computer.team8.DSTR;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;

import com.computer.team8.DSTR.graphics.DSTRSurfaceView;

public class OpenGLActivity extends Activity {
    private GLSurfaceView glView;

    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hide the status bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        // initialize OpenGL
        glView = new DSTRSurfaceView(this, mSensorManager, mSensor);
        setContentView(glView);
    }
}
