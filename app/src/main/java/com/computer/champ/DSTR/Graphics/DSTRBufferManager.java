package com.computer.champ.DSTR.Graphics;

import com.computer.champ.DSTR.Graphics.Element.Element;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

public class DSTRBufferManager {
    private FloatBuffer vertexBuffer;
    private FloatBuffer colourBuffer;
    private ArrayList<Element> elements;

    public DSTRBufferManager() {
        elements = new ArrayList<>();
    }

    public void add(Element elem) {
        elements.add(elem);
    }

    public FloatBuffer getVertexBuffer() {
        ArrayList<Float> vertList = new ArrayList<>();
        ArrayList<Float> colourList = new ArrayList<>();

        for (Element e : elements) {
            Float[] vData = e.getVertexList();
            float[] cData = e.getColour();
            int i = 0;
            for (Float f : vData) {
                vertList.add(f);
                i++;
            }

            i = 0;
            for (float f : cData) {
                colourList.add(f);
                ++i;
            }
        }

        // load vertex buffer
        ByteBuffer vb = ByteBuffer.allocateDirect(vertList.size() * 4);
        vb.order(ByteOrder.nativeOrder());
        vertexBuffer = vb.asFloatBuffer();

        // load colour buffer
        ByteBuffer cb = ByteBuffer.allocateDirect(colourList.size() * 4);
        vb.order(ByteOrder.nativeOrder());
        colourBuffer = cb.asFloatBuffer();

        for (float f : vertList) {
            vertexBuffer.put(f);
        }
        for (float f : colourList) {
            colourBuffer.put(f);
        }

        vertexBuffer.position(0);
        return vertexBuffer;
    }

    public int getNumVerts() {
        int total = 0;
        for (Element e : elements) {
            total += e.getVertexList().length;
        }
        return total;
    }

    public int getNumColours() {
        int total = 0;
        for (Element e : elements) {
            total += e.getColour().length;
        }
        return total;
    }
}
