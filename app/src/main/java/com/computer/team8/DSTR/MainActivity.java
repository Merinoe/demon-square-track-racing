package com.computer.team8.DSTR;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.computer.team8.DSTR.CharacterCreation;
import com.computer.team8.DSTR.MultiplayerMenu;
import com.computer.team8.DSTR.OpenGLActivity;
import com.computer.team8.DSTR.R;
import com.computer.team8.DSTR.projectui.BackgroundMusic;

public class MainActivity extends AppCompatActivity {

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
        Log.i("MY_MESSAGE", "Create Track");
    }

    public void toPlay(View view)
    {
        Intent intent = new Intent(this, OpenGLActivity.class);
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
