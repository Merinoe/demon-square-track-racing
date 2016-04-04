package com.computer.team8.DSTR.graphics.track;

import com.computer.team8.DSTR.graphics.base.Drawable;

import java.util.ArrayList;


public class Track extends Drawable {

    public enum TrackDifficulty
    {
        EASY, MEDIUM, HARD, CUSTOM
    }

    public static int laps = 3;
    public TrackDifficulty difficulty = TrackDifficulty.CUSTOM;
    public static int lapCounter = 0;
    public static final float TRACK_WIDTH = 20.0f;
    public static final float[] TRACK_SCALE = { 1, 1, 1 };
    public static final float[] TRACK_POSITION = { 0, 0, 0 };
    public static final float[] TRACK_COLOUR = { 0, 0, 1, 1 };
    public static final float[] TRACK_ORIENTATION = {
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
    };

    protected ArrayList<Float> points;

    public Track() {
        points = new ArrayList<>();
        updateBuffer(this.points);
    }

    public Track(ArrayList<Float> incomingTrack) {
        points = incomingTrack;
        updateBuffer(this.points);
    }

    /* get */

    public int getNumPoints() {
        return points.size() / 3;
    }

    public ArrayList<Float> getTrack() {
        return points;
    }

    /* set */

    public void setTrack(ArrayList<Float> incomingTrack) {
        points = incomingTrack;
    }

    public String getTrackForSend() {
        String newTrack = "";

        int i;
        for (i = 0; i < points.size(); i += 2) {
            newTrack += "(";
            newTrack += points.get(i) + ",";

            if (i + 1 < points.size()) {
                newTrack += points.get(i + 1);
            } else {
                newTrack += "0";
            }
            newTrack += ")";
        }

        return newTrack;
    }
}
