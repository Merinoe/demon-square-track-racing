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
        ArrayList<Float> result = new ArrayList<>();

        // remove Y direction
        int i;
        for (i = 0; i < points.size(); ++i) {
            if ((i - 1) % 3 != 0) {
                result.add(points.get(i));
            }
        }

        // form new string of track
        for (i = 0; i < result.size(); ++i) {
            if (i % 2 == 0) {
                newTrack += "(";
            }

            newTrack += String.format("%2.0f", result.get(i));

            if (i % 2 != 0) {
                newTrack += ")";
            } else {
                newTrack += ",";
            }
        }

        return newTrack;
    }
}
