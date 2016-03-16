package com.computer.team8.DSTR.projectui;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.util.HashMap;

/**
 * Created by Catherine on 3/8/2016.
 */
public class BackgroundMusic implements Sound {
    private HashMap<String, Integer> bgmLibrary;
    private MediaPlayer bgmPlayer;
    private Context context;

    public BackgroundMusic(Context context)
    {
        bgmLibrary = new HashMap<String, Integer>();
        this.context = context;
    }

    public void addFile(int fileId, String fileName)
    {
        bgmLibrary.put(fileName, fileId);
    }

    public void play(String fileName)
    {
        if(bgmLibrary.containsKey(fileName))
        {
            if(bgmPlayer != null && bgmPlayer.isPlaying())
            {
                bgmPlayer.stop();
            }

            bgmPlayer = MediaPlayer.create(context, bgmLibrary.get(fileName));
            bgmPlayer.setLooping(true); // Set bgm to loop
            bgmPlayer.setVolume(100, 100);
            bgmPlayer.start();
        }
    }

    public void stop()
    {
        bgmPlayer.stop();
    }

    public void destroy()
    {
        bgmPlayer.stop();
        bgmPlayer.release();
        bgmPlayer = null;
    }
}
