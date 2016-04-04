package com.computer.team8.DSTR.multiplayer;

import android.content.Context;
import android.os.AsyncTask;

import com.computer.team8.DSTR.projectui.Preferences;

import java.net.URL;

/**
 * Created by Catherine on 4/3/2016.
 */
public class PassData  extends AsyncTask<Context, Void, Integer>{

    @Override
    protected Integer doInBackground(Context... params) {
        DSTRNetworkManager network = new DSTRNetworkManager();
        network.sendMsg("SSSS");
//        network.sendMsg("****");

//        response = network.getMessage();
//        Log.i("data", response);

        Context context = params[0];
        String name = Preferences.getName(context);
        String colour = Preferences.getColor(context); //has been initialized in single player.

        network.sendMsg(name);
        network.sendMsg("@@@@");
        return 0;
    }
}
