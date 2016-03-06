package com.computer.team8.DSTR.graphics;

import com.computer.team8.DSTR.graphics.element.Element;
import com.computer.team8.DSTR.graphics.element.Square;
import com.computer.team8.DSTR.graphics.types.Vec4;

import java.util.ArrayList;

public class DSTRBufferManager {
    private ArrayList<Element> elements;
    private ArrayList<Vec4> colours;

    public DSTRBufferManager() {
        elements = new ArrayList<>();
        colours = new ArrayList<>();
        colours.add(new Vec4(1.0f, 0.0f, 0.0f, 1.0f));
        colours.add(new Vec4(0.0f, 1.0f, 0.0f, 1.0f));
        colours.add(new Vec4(0.0f, 0.0f, 1.0f, 1.0f));
        colours.add(new Vec4(0.4f, 0.4f, 0.4f, 1.0f));
        colours.add(new Vec4(0.5f, 0.5f, 0.5f, 1.0f));
    }

    public void add(Element elem) {
        elements.add(elem);
    }
    public void remove(Element elem) { elements.remove(elem); }

    public ArrayList<Element> getElements() {
        return elements;
    }

    public void createLevel() {
        int colour = (int) (Math.random() * colours.size());
        Square sq = new Square(0, -0.5f, 0, colours.get(1));
        sq.setScale(10, 0.25f, 10);
        this.add(sq);

        sq = new Square(3, -1, 3, colours.get(2));
//        sq.setScale(100.0f);
        this.add(sq);
    }
}
