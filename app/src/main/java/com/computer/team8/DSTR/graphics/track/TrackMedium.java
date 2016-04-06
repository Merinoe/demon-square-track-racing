package com.computer.team8.DSTR.graphics.track;

public class TrackMedium extends Track{

    public TrackMedium ()
    {
        super();
        difficulty = TrackDifficulty.MEDIUM;

        this.points.add(40f);
        this.points.add(2f);
        this.points.add(0f);

        this.points.add(40f);
        this.points.add(6f);
        this.points.add(5f);

        this.points.add(40f);
        this.points.add(10f);
        this.points.add(10f);

        this.points.add(35f);
        this.points.add(4f);
        this.points.add(20f);

        this.points.add(30f);
        this.points.add(2f);
        this.points.add(22f);

        this.points.add(10f);
        this.points.add(8f);
        this.points.add(18f);

        this.points.add(0f);
        this.points.add(10f);
        this.points.add(10f);

        this.points.add(-10f);
        this.points.add(4f);
        this.points.add(10f);

        this.points.add(2f);
        this.points.add(2f);
        this.points.add(5f);

        this.points.add(20f);
        this.points.add(3f);
        this.points.add(0f);

        this.points.add(40f);
        this.points.add(2f);
        this.points.add(0f);

        this.updateBuffer(this.points);
    }
}
