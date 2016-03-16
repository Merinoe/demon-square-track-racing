package com.computer.team8.DSTR.projectui;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

/**
 * Created by Catherine on 3/8/2016.
 */
public class SoundEffects implements Sound{
    private HashMap<String, Integer> sfxLibrary;
    private SoundPool soundPool;
    private AudioManager audioManager;
    private Context context;

    public SoundEffects(Context context)
    {
        sfxLibrary = new HashMap<String, Integer>();
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0 );
        this.context = context;
    }

    public void addFile(int fileId, String fileName)
    {
        int soundId =  soundPool.load(context, fileId, 1);
        sfxLibrary.put(fileName, soundId);
    }

    public void play(String fileName)
    {
        if(sfxLibrary.containsKey(fileName))
        {
            soundPool.play(sfxLibrary.get(fileName), 1, 1, 1, 0, 1f);
        }
    }

    public void destroy()
    {
        soundPool.release();
        soundPool = null;
    }
}
