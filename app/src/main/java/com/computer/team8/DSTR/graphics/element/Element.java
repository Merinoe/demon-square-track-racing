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
    protected Vec3 scale;
    protected float[] orientation = new float[16];
    protected List<Float> data;
    protected FloatBuffer processedData;

    // general use
    private float[] result = new float[16];
    private float[] rotation = new float[16];

    public Element(Vec3 pos, float[] data, Vec4 col) {
        this.data = new ArrayList<>();
        for (Float f : data) {
            this.data.add(f);
        }

        position = pos;
        colour = col;
        scale = new Vec3(1.0f, 1.0f, 1.0f);
        Matrix.setIdentityM(orientation, 0);

        updateBuffer();
    }

    public void rotate(Vec3 v, float amount) {
        Matrix.setRotateM(
                rotation,
                0,        // not used
                amount,   // amount rotated
                v.x,
                v.y,      // axis of rotation
                v.z);
        Matrix.multiplyMM(result, 0, rotation, 0, orientation, 0);
        this.setOrientation(result);
    }

    public void updateBuffer() {
        ByteBuffer vb = ByteBuffer.allocateDirect(data.size() * 4);
        vb.order(ByteOrder.nativeOrder());
        processedData = vb.asFloatBuffer();

        for (Float f : data) {
            processedData.put(f);
        }

        processedData.position(0);
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
        return processedData;
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

    public float[] getScaleData() {
        float[] s = { scale.x, scale.y, scale.z };
        return s;
    }

    /*** SET **/

    public void setPosition(Vec3 v) {
        position.x = v.x;
        position.y = v.y;
        position.z = v.z;
    }

    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }

    public void setOrientation(float[] matrix) {
        this.orientation = matrix;
    }

    public void setScale(float amount) {
        scale.x = amount;
        scale.y = amount;
        scale.z = amount;
        updateBuffer();
    }

    public void setScale(float x, float y, float z) {
        scale.x = x;
        scale.y = y;
        scale.z = z;
        updateBuffer();
    }

    public void setXscale(float x) {
        scale.x = x;
        updateBuffer();
    }
    public void setYscale(float y) {
        scale.y = y;
        updateBuffer();
    }
    public void setZscale(float z) {
        scale.z = z;
        updateBuffer();
    }
}
