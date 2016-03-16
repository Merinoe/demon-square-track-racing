package com.computer.team8.DSTR.graphics.track;

public class BuiltInTrack extends Track {

    public BuiltInTrack() {
        super();

        this.points.add(0.0f);
        this.points.add(0.0f);
        this.points.add(0.0f);

        this.points.add(10.0f);
        this.points.add(5.0f);
        this.points.add(0.0f);


        this.points.add(10.0f);
        this.points.add(2.5f);
        this.points.add(-10.0f);

        this.points.add(-10.0f);
        this.points.add(3.5f);
        this.points.add(-10.0f);


        this.points.add(10.0f);
        this.points.add(0.0f);
        this.points.add(10.0f);

        this.points.add(10.0f);
        this.points.add(5.0f);
        this.points.add(15.0f);

        this.updateBuffer(this.points);
    }
}