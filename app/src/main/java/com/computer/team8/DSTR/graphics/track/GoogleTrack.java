package com.computer.team8.DSTR.graphics.track;

import java.util.ArrayList;
import java.util.Iterator;

public class GoogleTrack extends Track {
    public GoogleTrack(ArrayList<Float> track)
    {
        super();
        difficulty = TrackDifficulty.HARD;

        for (Iterator i = track.iterator(); i.hasNext();) {
            this.points.add((Float) i.next());
        }

        this.updateBuffer(this.points);
    }

}
