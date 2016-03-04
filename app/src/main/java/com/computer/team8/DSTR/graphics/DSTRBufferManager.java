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
}
