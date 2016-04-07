package com.computer.team8.DSTR.graphics.track;

import java.util.ArrayList;
import java.util.Iterator;

public class GoogleTrack extends Track {
    public GoogleTrack(ArrayList<Float> track)
    {
        super();

        if(track.size() <= 9) {
            difficulty = TrackDifficulty.EASY;
        }
        else if((track.size() > 9) && (track.size() <= 18)){
            difficulty = TrackDifficulty.MEDIUM;
        }
        else difficulty = TrackDifficulty.HARD;

        for (Iterator i = track.iterator(); i.hasNext();) {
            this.points.add((Float) i.next());
        }

        this.updateBuffer(this.points);
    }

}
