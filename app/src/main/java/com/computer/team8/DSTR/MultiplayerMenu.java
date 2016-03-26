package com.computer.team8.DSTR;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.computer.team8.DSTR.multiplayer.DSTRBluetooh;
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
        DSTRBluetooh.attachContext(this);

        if(DSTRBluetooh.bluetoothResult == DSTRBluetooh.Result.UNSUPPORTED) {
            (new MessageBox(this, "Bluetooth not supported on this device.")).show();
            return;
        }

        if(DSTRBluetooh.bluetoothResult == DSTRBluetooh.Result.BLUETOOTH_DISABLED) {
            (new MessageBox(this, "Bluetooth is not enabled on this device. Please enable it and try again.")).show();
            return;
        }


        DSTRBluetooh.connect("DTSR");

        if(DSTRBluetooh.bluetoothResult == DSTRBluetooh.Result.NOT_PAIRED) {
            (new MessageBox(this, "Device is not connected to DSTR. Please pair to the device and try again.")).show();
            return;
        }

        if(DSTRBluetooh.isConnected()) {
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