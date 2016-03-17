package com.computer.team8.DSTR;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.computer.team8.DSTR.projectui.*;

public class MultiplayerMenu extends Activity {

    BackgroundMusic bgm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_menu);

        bgm  = new BackgroundMusic(this);
        bgm.addFile(R.raw.multiplayer, "multi");
        bgm.play("multi");
    }

    public void toMain(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void executeConnect(View view)
    {
        //Display error message if unable to connect via bluetooth.

        MessageBox dialog = new MessageBox(this, "Warning: Your device is not connected to the main game machine");
        dialog.show();
    }

    public void toRankings(View view)
    {
        Log.i("MY_MESSAGE", "Rankings");
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
        bgm.play("multi");
        super.onResume();
    }

    @Override
    public void onDestroy()
    {
        bgm.destroy();
        super.onDestroy();
    }

}