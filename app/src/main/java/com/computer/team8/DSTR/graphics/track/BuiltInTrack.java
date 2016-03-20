package com.computer.team8.DSTR.graphics.track;

public class BuiltInTrack extends Track {

    public BuiltInTrack() {
        super();

        this.points.add(0.0f);
        this.points.add(0.0f);
        this.points.add(0.0f);

        this.points.add(0.0f);
        this.points.add(5.0f);
        this.points.add(5.0f);

        this.points.add(0.0f);
        this.points.add(2.5f);
        this.points.add(7.0f);

        this.points.add(10.0f);
        this.points.add(7.5f);
        this.points.add(0.0f);

        this.points.add(12.5f);
        this.points.add(5.0f);
        this.points.add(-10.0f);

        this.points.add(-15.0f);
        this.points.add(10.5f);
        this.points.add(-20.0f);

        this.points.add(17.5f);
        this.points.add(3.0f);
        this.points.add(17.5f);

        this.points.add(10.0f);
        this.points.add(12.0f);
        this.points.add(2.0f);

        this.points.add(17.0f);
        this.points.add(10.0f);
        this.points.add(22.0f);

        this.updateBuffer(this.points);
    }
}
