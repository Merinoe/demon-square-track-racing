package com.computer.team8.DSTR.graphics.types;

public class Vec4 {
    public float x;
    public float y;
    public float z;
    public float w;

    public Vec4(float x, float y, float z, float w) {
        this.set(x, y, z, w);
    }

    public Vec4(Vec4 v) {
        set(v);
    }

    public Vec4 get() { return this; }
    public float[] getData() { return new float[]{x, y, z, w}; }
    public float getX() { return this.x; }
    public float getY() { return this.y; }
    public float getZ() { return this.z; }
    public float getW() { return this.w; }

    public void set(Vec4 v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
    }

    public void set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setZ(float z) { this.z = z; }
    public void setW(float w) { this.w = w; }
}
