package com.computer.team8.DSTR.graphics.track;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

public class Track {
    protected ArrayList<Float> points;
    protected FloatBuffer processedData;

    public Track() {
        points = new ArrayList<>();
        updateBuffer();
    }

    public Track(ArrayList<Float> incomingTrack) {
        points = incomingTrack;
        updateBuffer();
    }

    public void updateBuffer() {
        if (processedData != null) {
            processedData.clear();
        }
        ByteBuffer vb = ByteBuffer.allocateDirect(points.size() * 4);
        vb.order(ByteOrder.nativeOrder());
        processedData = vb.asFloatBuffer();

        for (Float f : points) {
            processedData.put(f);
        }

        processedData.position(0);
    }

    /* get */

    public int getNumPoints() {
        return points.size() / 3;
    }

    public ArrayList<Float> getTrack() {
        return points;
    }

    public float[] getTrackData() {
        float[] fPoints = new float[points.size()];
        int i = 0;
        for (Float f : points) {
            fPoints[i++] = (f != null ? f : Float.NaN);
        }
        return fPoints;
    }

    public FloatBuffer getTrackBuffer() {
        return processedData;
    }

    /* set */

    public void setTrack(ArrayList<Float> incomingTrack) {
        points = incomingTrack;
    }

    public void setTrackData(float[] incoming) {
        int i = 0;
        for (float f : incoming) {
            points.add(f);
        }
    }
}
