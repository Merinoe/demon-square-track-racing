package com.computer.team8.DSTR;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;

import com.computer.team8.DSTR.graphics.DSTRSurfaceView;
import com.computer.team8.DSTR.graphics.track.Track;
import com.computer.team8.DSTR.graphics.track.TrackManager;
import com.computer.team8.DSTR.projectui.BackgroundMusic;

public class OpenGLActivity extends Activity {
    private GLSurfaceView glView;
    private BackgroundMusic bgm;
    private Track.TrackDifficulty trackDifficulty;

    private SensorManager mSensorManager;

    private static boolean hasBooted = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hide the status bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        // initialize rotational sensor
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // initialize OpenGL
        glView = new DSTRSurfaceView(this);
        setContentView(glView);

        trackDifficulty = TrackManager.getCurrentTrack().difficulty;

        bgm = new BackgroundMusic(this);
        bgm.addFile(R.raw.game, "hard");
        bgm.addFile(R.raw.gameeasy, "easy");
        bgm.addFile(R.raw.gamemed, "med");
        playBgm();
    }

    private void playBgm()
    {
        if(trackDifficulty == trackDifficulty.EASY)
        {
            bgm.play("easy");
        }
        else if(trackDifficulty == trackDifficulty.MEDIUM)
        {
            bgm.play("med");
        }
        else
        {
            bgm.play("hard");
        }
    }

    private final SensorEventListener mSensorListener = new SensorEventListener() {
        public void onSensorChanged(SensorEvent se) {
            DSTRSurfaceView.onRotation(se.values[2]);
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(
                mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
                SensorManager.SENSOR_DELAY_GAME);

        if (OpenGLActivity.hasBooted) {
            DSTRSurfaceView.reloadGameState();
        } else {
            OpenGLActivity.hasBooted = true;
        }

        playBgm();
    }

    @Override
    public void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
        DSTRSurfaceView.saveGameState();
        bgm.stop();
    }

    public void onDestroy()
    {
        bgm.destroy();
        super.onDestroy();
    }
}
