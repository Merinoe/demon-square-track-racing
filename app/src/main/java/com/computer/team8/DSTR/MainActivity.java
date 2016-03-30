package com.computer.team8.DSTR;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.computer.team8.DSTR.projectui.BackgroundMusic;

public class MainActivity extends Activity {

    BackgroundMusic bgm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bgm = new BackgroundMusic(this);
        bgm.addFile(R.raw.title, "title");
        bgm.play("title");
    }

    public void toMakeChar(View view)
    {
        Intent intent = new Intent(this, CharacterCreation.class);
        startActivity(intent);
    }


    public void toCreateTrack(View view)
    {
        Intent intent = new Intent(this, GoogleMapsActivity.class);
        startActivity(intent);
    }

    public void toPlay(View view)
    {
        Intent intent = new Intent(this, SelectTrackActivity.class);
        startActivity(intent);
    }

    public void toMulti(View view)
    {
        Intent intent = new Intent(this, MultiplayerMenu.class);
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
