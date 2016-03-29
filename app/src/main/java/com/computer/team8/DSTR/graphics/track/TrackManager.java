package com.computer.team8.DSTR.graphics.track;

import java.util.ArrayList;

public class TrackManager {
    private static ArrayList<Track> tracks = new ArrayList<Track>();
    private static int currentIndex = 0;

    public static void fetchTracks()
    {
        tracks = new ArrayList<Track>();
        tracks.add(new TrackEasy());
        tracks.add(new TrackMedium());
        tracks.add(new TrackHard());
    }

    public static boolean toNextTrack()
    {
        if(currentIndex < tracks.size() - 1)
        {
            currentIndex ++;
            return true;
        }

        return false;
    }

    public static boolean toPrevTrack()
    {
        if(currentIndex > 0)
        {
            currentIndex --;
            return true;
        }

        return false;
    }

    public static Track getCurrentTrack()
    {
        return tracks.get(currentIndex);
    }


    public static int numTracks()
    {
        return tracks.size();
    }

    public static int getCurrentTrackNum()
    {
        return currentIndex+1;
    }

}
