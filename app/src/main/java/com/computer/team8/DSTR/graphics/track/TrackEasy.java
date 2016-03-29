package com.computer.team8.DSTR.graphics.track;

public class TrackEasy extends Track {

    public TrackEasy()
    {
        super();
        difficulty = TrackDifficulty.EASY;

        this.points.add(-20.0f);
        this.points.add(0.0f);
        this.points.add(-20.0f);

        this.points.add(-21.0f);
        this.points.add(2.0f);
        this.points.add(-19.0f);

        this.points.add(-22.0f);
        this.points.add(5.0f);
        this.points.add(-18.0f);
        this.updateBuffer(this.points);
    }

}
