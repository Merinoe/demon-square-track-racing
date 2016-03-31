package com.computer.team8.DSTR;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.computer.team8.DSTR.graphics.track.Track;
import com.computer.team8.DSTR.graphics.track.TrackManager;
import com.computer.team8.DSTR.multiplayer.DSTRBluetooh;
import com.computer.team8.DSTR.multiplayer.DSTRNetworkManager;
import com.computer.team8.DSTR.projectui.BackgroundMusic;

import java.util.ArrayList;

public class SelectTrackActivity extends Activity {
    BackgroundMusic bgm;

    // bluetooth
    DSTRNetworkManager network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_track);

        bgm = new BackgroundMusic(this);
        bgm.addFile(R.raw.selection, "title");
        bgm.play("title");


        TrackManager.fetchTracks();
        updateTrackView();
    }

    private void updateTrackView()
    {
        Track  currentTrack = TrackManager.getCurrentTrack();

        TextView txtDifficulty;
        txtDifficulty = (TextView)findViewById(R.id.txtDifficulty);
        txtDifficulty.setText("Difficulty: " + currentTrack.difficulty.toString().toLowerCase());

        TextView txtNum;
        txtNum = (TextView)findViewById(R.id.txtTrackNum);
        txtNum.setText("Track: " + TrackManager.getCurrentTrackNum() + "/" + TrackManager.numTracks());
    }

    public void prevTrack(View view)
    {
        TrackManager.toPrevTrack();
        updateTrackView();
    }

    public void nextTrack(View view)
    {
        TrackManager.toNextTrack();
        updateTrackView();
    }

    public void returnToMenu(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void playGame(View view)
    {
        Track currentTrack = TrackManager.getCurrentTrack();

        // send current track choice to DE2
        if (DSTRBluetooh.isConnected()) {
            ArrayList<Float> tPoints = currentTrack.getTrack();
            String trackMessage = null;

            for (float f : tPoints) {
                trackMessage += f;
            }
            trackMessage += "$$$$";

            network.sendMessage(trackMessage);
        } else {
            System.out.println("No Bluetooth connection in place");
        }

        Intent intent = new Intent(this, OpenGLActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPause()
    {
        bgm.stop();
        super.onPause();
    }

    @Override
    public void onResume()
    {
        bgm.play("title");
        super.onResume();
    }

    @Override
    public void onDestroy()
    {
        bgm.destroy();
        super.onDestroy();
    }

}
