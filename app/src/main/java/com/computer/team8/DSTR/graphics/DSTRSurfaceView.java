package com.computer.team8.DSTR.graphics;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.computer.team8.DSTR.graphics.camera.Camera;
import com.computer.team8.DSTR.graphics.types.Vec3;

public class DSTRSurfaceView extends GLSurfaceView implements SensorEventListener {
    private final DSTRRenderer glRenderer;
    private SensorManager sensorMan;
    private Sensor sensor;

    public DSTRSurfaceView(Context context, SensorManager man, Sensor sensor) {
        super(context);

        sensorMan = man;
        this.sensor = sensor;

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        glRenderer = new DSTRRenderer(sensor);

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(glRenderer);
    }

    float oldX, oldY;
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

        float rotateScalingFactor = 3;
        float zoomScalingFactor = 100;

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                Camera cam = DSTRRenderer.getCamera();

                // rotateHorizontally
                if (e.getPointerCount() == 1) {
                    float dx = x - oldX;
                    float dy = y - oldY;

                    if (dx > cam.MAX_ROTATE_H_SPEED) {
                        dx = cam.MAX_ROTATE_H_SPEED;
                    } else if (dx < -cam.MAX_ROTATE_H_SPEED) {
                        dx = -cam.MAX_ROTATE_H_SPEED;
                    }

                    if (dy > cam.MAX_ROTATE_V_SPEED) {
                        dy = cam.MAX_ROTATE_V_SPEED;
                    } else if (dy < -cam.MAX_ROTATE_V_SPEED) {
                        dy = -cam.MAX_ROTATE_V_SPEED;
                    }

                    cam.rotateHorizontally(-dx / rotateScalingFactor);
                    cam.rotateVertically(-dy / rotateScalingFactor);

                    oldX = x;
                    oldY = y;

                // zoom
                } else if (e.getPointerCount() == 2) {  // two fingers for a zoom
                    float dy = y - oldY;

                    if (dy > cam.MAX_ZOOM_SPEED) {
                        dy = cam.MAX_ZOOM_SPEED;
                    } else if (dy < -cam.MAX_ZOOM_SPEED) {
                        dy = -cam.MAX_ZOOM_SPEED;
                    }

                    cam.zoom(-dy / zoomScalingFactor);

                    oldY = y;
                }
                break;

            default:
                oldX = 0;
                oldY = 0;
        }

        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorMan.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorMan.unregisterListener(this);
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        System.out.println(event.values[0]);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        System.out.println("ACCURACY");
    }
}
