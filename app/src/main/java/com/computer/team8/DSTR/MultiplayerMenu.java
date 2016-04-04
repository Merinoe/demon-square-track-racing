package com.computer.team8.DSTR;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.computer.team8.DSTR.multiplayer.DSTRBluetooth;
import com.computer.team8.DSTR.projectui.BackgroundMusic;
import com.computer.team8.DSTR.projectui.MessageBox;

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
        DSTRBluetooth.attachContext(this);

        if(DSTRBluetooth.bluetoothResult == DSTRBluetooth.Result.UNSUPPORTED) {
            (new MessageBox(this, "Bluetooth not supported on this device.")).show();
            return;
        }

        if(DSTRBluetooth.bluetoothResult == DSTRBluetooth.Result.BLUETOOTH_DISABLED) {
            (new MessageBox(this, "Bluetooth is not enabled on this device. Please enable it and try again.")).show();
            return;
        }

        DSTRBluetooth.connect("DSTR1");

        if(DSTRBluetooth.bluetoothResult == DSTRBluetooth.Result.FAIL)
        {
            (new MessageBox(this, "Failed to connect to device. Please try again.")).show();
            return;
        }

        if(DSTRBluetooth.isConnected()) {
            (new MessageBox(this, "Connection Established")).show();
        }

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