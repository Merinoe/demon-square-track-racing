package com.computer.team8.DSTR.graphics.track;

public class TrackMedium extends Track{

    public TrackMedium ()
    {
        super();
        difficulty = TrackDifficulty.MEDIUM;

        this.points.add(-20.0f);
        this.points.add(10.0f);
        this.points.add(-13.0f);

        this.points.add(-20.0f);
        this.points.add(18.0f);
        this.points.add(-7.0f);

        this.points.add(-20.0f);
        this.points.add(20.0f);
        this.points.add(-5.0f);

        this.points.add(-19.0f);
        this.points.add(19.0f);
        this.points.add(-3.0f);

        this.points.add(-18.0f);
        this.points.add(16.0f);
        this.points.add(0.0f);

        this.updateBuffer(this.points);
    }
}
