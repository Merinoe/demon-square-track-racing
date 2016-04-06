package com.computer.team8.DSTR.graphics.track;

public class TrackEasy extends Track {

    public TrackEasy()
    {
        super();
        difficulty = TrackDifficulty.EASY;

        this.points.add(20f);
        this.points.add(2f);
        this.points.add(20f);

        this.points.add(20f);
        this.points.add(10f);
        this.points.add(0f);

        this.points.add(20f);
        this.points.add(2f);
        this.points.add(-20f);

        this.points.add(0f);
        this.points.add(10f);
        this.points.add(-20f);

        this.points.add(-20f);
        this.points.add(2f);
        this.points.add(-20f);

        this.points.add(-20f);
        this.points.add(10f);
        this.points.add(0f);

        this.points.add(20f);
        this.points.add(2f);
        this.points.add(20f);

        this.updateBuffer(this.points);
    }

}
