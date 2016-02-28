package com.computer.champ.DSTR.Graphics.Element;

import java.util.ArrayList;
import java.util.List;

public class Element {

    protected float[] position;
    protected List<Float> vertexList;
    protected float colour[] = { 1.0f, 1.0f, 1.0f, 1.0f };

    public Element() {
        vertexList = new ArrayList<Float>();
        position = new float[3];
        position[0] = 0.0f; // x
        position[1] = 0.0f; // y
        position[2] = 0.0f; // z
    }

    public Element(float pos[], float[] verts, float col[]) {
        vertexList = new ArrayList<Float>();
        for (Float f : verts) { vertexList.add(f); }
        colour = col;
        position = new float[3];
        position[0] = pos[0]; // x
        position[1] = pos[1]; // y
        position[2] = pos[2]; // z
    }

    /** GET ***/

    public float[] getPosition() {
        return position;
    }

    public Float[] getVertexList() {
        return vertexList.toArray(new Float[vertexList.size()]);
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
