package com.computer.team8.DSTR.graphics;

import com.computer.team8.DSTR.graphics.element.Element;

import java.util.ArrayList;

public class DSTRBufferManager {
    private ArrayList<Element> elements;

    public DSTRBufferManager() {
        elements = new ArrayList<>();
    }

    public void add(Element elem) {
        elements.add(elem);
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

    public int getNumVerts() {
        int total = 0;
        for (Element e : elements) {
            total += e.getVertexData().capacity();
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
