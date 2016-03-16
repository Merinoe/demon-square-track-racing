package com.computer.team8.DSTR.graphics.element;

import android.opengl.Matrix;

import com.computer.team8.DSTR.graphics.base.Drawable;
import com.computer.team8.DSTR.graphics.types.Vec3;
import com.computer.team8.DSTR.graphics.types.Vec4;

import java.util.ArrayList;
import java.util.List;

public class Element extends Drawable {

    protected Vec3 position;
    protected Vec4 colour;
    protected Vec3 scale;
    protected Vec3 lateral; // used for accurate veritcal rotations
    protected float[] orientation = new float[16];
    protected List<Float> data;

    // general use
    private float[] result = new float[16];
    private float[] rotation = new float[16];

    public Element() {
        position = new Vec3(0, 0, 0);
    }

    public Element(Vec3 pos, float[] data, Vec4 col) {
        this.data = new ArrayList<>();
        for (Float f : data) {
            this.data.add(f);
        }

        lateral = new Vec3();
        position = pos;
        colour = col;
        scale = new Vec3(1.0f, 1.0f, 1.0f);
        Matrix.setIdentityM(orientation, 0);

        updateBuffer(this.data);
    }

    /** GET ***/

    public Vec3 getPosition() {
        return position;
    }

    public float[] getPositionData() {
        float[] p = { position.x, position.y, position.z, 1 };
        return p;
    }

    public Vec3 getBottom() {
        Vec3 temp = new Vec3();
        temp.set(position.x, position.y - (scale.y / 2), position.z);
        return temp;
    }

    public Vec4 getColour() {
        return colour;
    }

    public float[] getColourData() {
        float[] c = { colour.x, colour.y, colour.z, colour.w };
        return c;
    }

    public float[] getOrientation() {
        return orientation;
    }

    public Vec3 getOrientationVector() {
        Vec3 temp = new Vec3(0, 0, 1);
        float[] result = new float[4];
        Matrix.multiplyMV(result, 0, orientation, 0, temp.getData(), 0);
        temp.set(result[0], result[1], result[2]);
        return temp;
    }

    public float[] getScaleData() {
        float[] s = { scale.x, scale.y, scale.z };
        return s;
    }

    public float getWidth() {
        return scale.x;
    }

    public float getHeight() {
        return scale.y;
    }

    public float getDepth() {
        return scale.z;
    }

    /*** SET **/

    public void setPosition(Vec3 v) {
        position = v;
    }

    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }

    public void setBottom(float x, float y, float z) {
        position.x = x;
        position.y = y + (scale.y / 2.0f);
        position.z = z;
    }

    public void setBottom(Vec3 v) {
        position.x = v.x;
        position.y = v.y + (scale.y / 2.0f);
        position.z = v.z;
    }

    public void setOrientation(float[] matrix) {
        this.orientation = matrix;
    }

    public void setScale(float amount) {
        scale.x = amount;
        scale.y = amount;
        scale.z = amount;
    }

    public void setScale(float x, float y, float z) {
        scale.x = x;
        scale.y = y;
        scale.z = z;
    }

    public void setXscale(float x) {
        scale.x = x;
    }
    public void setYscale(float y) {
        scale.y = y;
    }
    public void setZscale(float z) {
        scale.z = z;
    }

    public void roll(float angle) {
        Vec3 temp = new Vec3(1, 0, 0);
        float[] result = new float[4];
        Matrix.multiplyMV(result, 0, orientation, 0, temp.getData(), 0);

        Matrix.setRotateM(
                rotation,
                0,            // not used
                angle,        // amount rotated
                result[0],
                result[1],    // axis of rotation
                result[2]);
        Matrix.multiplyMM(orientation, 0, rotation, 0, orientation, 0);
    }

    public void updateLateral() {
        Vec3 temp = new Vec3(1, 0, 0);

        Matrix.setRotateM(
                rotation,
                0,       // not used
                90.0f,   // amount rotated
                0,
                1,    // axis of rotation
                0);

        Matrix.multiplyMV(result, 0, rotation, 0, temp.getData(), 0);
        lateral.set(result[0], result[1], result[2]);
    }

    public void rotateHorizontally(float angle) {
        Matrix.setRotateM(
                rotation,
                0,       // not used
                angle,   // amount rotated
                0,
                1,    // axis of rotation
                0);
        Matrix.multiplyMM(orientation, 0, rotation, 0, orientation, 0);
        updateLateral();
    }

    public void rotateVertically(float angle) {
        Matrix.setRotateM(
                rotation,
                0,       // not used
                angle,   // amount rotated
                lateral.x,
                lateral.y,    // axis of rotation
                lateral.z);
        Matrix.multiplyMV(orientation, 0, rotation, 0, orientation, 0);
        updateLateral();
    }
}
