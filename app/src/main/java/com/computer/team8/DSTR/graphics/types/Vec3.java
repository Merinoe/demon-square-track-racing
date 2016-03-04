package com.computer.team8.DSTR.graphics.types;

public class Vec3 {
    public float x;
    public float y;
    public float z;

    public Vec3(float x, float y, float z) {
        this.set(x, y, z);
    }

    public Vec3(Vec3 v) {
        this.set(v);

    }

    public Vec3 get() { return this; }
    public float getX() { return this.x; }
    public float getY() { return this.y; }
    public float getZ() { return this.z; }

    public void set(Vec3 v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setZ(float z) { this.z = z; }
}
