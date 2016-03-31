package com.computer.team8.DSTR;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.computer.team8.DSTR.graphics.track.Track;
import com.computer.team8.DSTR.graphics.track.TrackManager;
import com.computer.team8.DSTR.multiplayer.DSTRBluetooth;
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
        network = new DSTRNetworkManager();

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
//        Track currentTrack = TrackManager.getCurrentTrack();
//        ArrayList<Float> tPoints = currentTrack.getTrack();
//        String trackMessage = "";
//
//        // format current track to string for BT
//        for (float f : tPoints) {
//            trackMessage += Math.round(f);
//            trackMessage += ",";
//        }
//
//        // remove last comma
//        trackMessage.substring(0, trackMessage.length() - 1);
//        // termination string
//        trackMessage += "$$$$";
//
//        // send the message
//        int i = 0;
//        while (i < tPoints.size()) {
//            if (DSTRBluetooth.isConnected()) {
//                boolean result = network.sendMessage(trackMessage);
//                if (result) {
//                    ++i;
//                }
//            }
//        }
//
//        // send current track choice to DE2
//        if (DSTRBluetooth.isConnected()) {
//            network.sendMessage(trackMessage);
//        } else {
//            System.out.println("No Bluetooth connection in place");
//        }

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
