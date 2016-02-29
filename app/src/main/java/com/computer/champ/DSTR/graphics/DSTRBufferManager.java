package com.computer.champ.DSTR.graphics;

import com.computer.champ.DSTR.graphics.element.Element;

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
            total += e.getVertexList().capacity();
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
