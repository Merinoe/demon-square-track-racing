package com.computer.team8.DSTR.graphics.element;

import com.computer.team8.DSTR.graphics.types.Vec4;

public class Demon extends Square {
    public Demon(float x, float y, float z) {
        super(new Vec4(1, 0, 0, 1));
        this.setScale(0.5f, 1, 1);
        this.setBottom(0, 0, 0);
    }
}
