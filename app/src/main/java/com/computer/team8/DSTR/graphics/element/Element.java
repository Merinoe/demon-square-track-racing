package com.computer.team8.DSTR.graphics.element;

import android.opengl.Matrix;

import com.computer.team8.DSTR.graphics.types.Vec3;
import com.computer.team8.DSTR.graphics.types.Vec4;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class Element {

    protected Vec3 position;
    protected Vec4 colour;
    protected float[] orientation = new float[16];
    protected List<Float> data;

    // general use
    private float[] rotation = new float[16];

    public Element(Vec3 pos, float[] data, Vec4 col) {
        this.data = new ArrayList<>();
        for (Float f : data) {
            this.data.add(f);
        }

        position = new Vec3(pos);
        colour = new Vec4(col);
        Matrix.setIdentityM(orientation, 0);
    }

    public void rotate(Vec3 v, float amount) {
        Matrix.setRotateM(
                rotation,
                0,        // not used
                amount,   // amount rotated
                v.x,
                v.y,      // axis of rotation
                v.z);

        float[] result = new float[16];
        Matrix.multiplyMM(result, 0, rotation, 0, orientation, 0);
        this.setOrientation(result);
    }

    /** GET ***/

    public Vec3 getPosition() {
        return position;
    }
    public float[] getPositionData() {
        float[] p = { position.x, position.y, position.z };
        return p;
    }

    public FloatBuffer getVertexData() {
        FloatBuffer list;
        ByteBuffer vb = ByteBuffer.allocateDirect(data.size() * 4);
        vb.order(ByteOrder.nativeOrder());
        list = vb.asFloatBuffer();

        for (Float f : data) {
            list.put(f);
        }

        list.position(0);
        return list;
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

    /*** SET **/

    public void setPosition(Vec3 v) {
        position.setX(v.getX());
        position.setY(v.getY());
        position.setZ(v.getZ());
    }

    public void setPosition(float x, float y, float z) {
        position.setX(x);
        position.setY(y);
        position.setZ(z);
    }

    public void setOrientation(float[] matrix) {
        this.orientation = matrix;
    }
}
