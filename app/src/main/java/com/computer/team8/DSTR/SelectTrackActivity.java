package com.computer.team8.DSTR;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.computer.team8.DSTR.graphics.track.Track;
import com.computer.team8.DSTR.graphics.track.TrackManager;
import com.computer.team8.DSTR.multiplayer.DSTRNetworkManager;
import com.computer.team8.DSTR.projectui.BackgroundMusic;
import com.computer.team8.DSTR.projectui.Preferences;

public class SelectTrackActivity extends Activity {
    BackgroundMusic bgm;
    Context activityContext;

    // bluetooth
    DSTRNetworkManager network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_track);
        activityContext = this;
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

        ImageView image;
        image = (ImageView)findViewById(R.id.trackImage);
        if(currentTrack.difficulty == Track.TrackDifficulty.HARD){
            image.setImageResource(R.drawable.track_hard);
        }

        else if (currentTrack.difficulty == Track.TrackDifficulty.MEDIUM){
            image.setImageResource(R.drawable.track_medium);
        }

        else {
            image.setImageResource(R.drawable.track_easy);
        }
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
        String name = Preferences.getName(this);
        String colour = Preferences.getColor(this);
        String track = TrackManager.getCurrentTrack().getTrackForSend();


        while (!network.sendMessage("S", "slow"));
        while (!network.sendMessage("$", "slow"));

        while (!network.sendMessage(name, "slow"));
        while (!network.sendMessage(",", "slow"));
        while (!network.sendMessage(colour, "slow"));
        while (!network.sendMessage("$", "slow"));

        while (!network.sendMessage(track, "slow"));
        while (!network.sendMessage("$", "slow"));
        while (!network.sendMessage("####", "slow"));
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
