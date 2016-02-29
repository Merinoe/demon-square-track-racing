package com.computer.champ.DSTR.graphics.element;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class Element {

    protected float[] position;
    protected float[] colour;
    protected List<Float> data;

    public Element(float pos[], float[] data, float col[]) {
        this.data = new ArrayList<>();
        for (Float f : data) {
            this.data.add(f);
        }

        colour = new float[4];
        colour[0] = col[0];
        colour[1] = col[1];
        colour[2] = col[2];
        colour[3] = col[3];

        position = new float[3];
        position[0] = pos[0]; // x
        position[1] = pos[1]; // y
        position[2] = pos[2]; // z
    }

    /** GET ***/

    public float[] getPosition() {
        return position;
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

    public float[] getColour() {
        return colour;
    }

    /*** SET **/

    public void setPosition(float x, float y, float z) {
        position[0] = x;
        position[1] = y;
        position[2] = z;
    }

    public void setPosition(float[] pos) {
        position[0] = pos[0];
        position[1] = pos[1];
        position[2] = pos[2];
    }

}
