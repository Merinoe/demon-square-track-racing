package com.computer.team8.DSTR.graphics.types;

public class Vec3 {
    public float x;
    public float y;
    public float z;

    public Vec3() { this.set(1, 1, 1); }
    public Vec3(float x, float y, float z) {
        this.set(x, y, z);
    }
    public Vec3(Vec3 v) {
        this.set(v);

    }

    public Vec3 get() { return this; }
    public float[] getData() {
        return new float[] {x, y, z, 1.0f};
    }
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

    /* operations */

    public Vec3 subtract(Vec3 v) {
        Vec3 temp = new Vec3();
        temp.x = this.x - v.x;
        temp.y = this.y - v.y;
        temp.z = this.z - v.z;
        return temp;
    }

    public Vec3 multiply(float amount) {
        Vec3 temp = new Vec3();
        temp.x = this.x * amount;
        temp.y = this.y * amount;
        temp.z = this.z * amount;
        return temp;
    }

    public float magnitude() {
        return (float) Math.sqrt( Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2) );
    }

    public Vec3 normalize() {
        Vec3 retVec = new Vec3();
        float mag = this.magnitude();

        retVec.x = x / mag;
        retVec.y = y / mag;
        retVec.z = z / mag;

        return retVec;
    }
}
