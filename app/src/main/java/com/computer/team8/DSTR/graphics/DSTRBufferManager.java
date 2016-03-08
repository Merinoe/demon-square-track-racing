package com.computer.team8.DSTR.graphics;

import com.computer.team8.DSTR.graphics.element.Element;
import com.computer.team8.DSTR.graphics.element.Square;
import com.computer.team8.DSTR.graphics.service.ColourManager;
import com.computer.team8.DSTR.graphics.types.Vec4;

import java.util.ArrayList;
import java.util.HashMap;

public class DSTRBufferManager {
    private static ArrayList<Element> elements;
    private HashMap<String, Vec4> colours;

    public DSTRBufferManager() {
        elements = new ArrayList<>();
    }

    public void add(Element elem) {
        elements.add(elem);
    }
    public void remove(Element elem) { elements.remove(elem); }

    public static Element get(int index) {
        return elements.get(index);
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

    public void createLevel() {
        Square sq = new Square(ColourManager.getColour("dirt"));
        sq.setScale(50, 1, 50);
        sq.setBottom(0, -2, 0);
        this.add(sq);

        sq = new Square(ColourManager.getColour("chill grass"));
        sq.setScale(50, 1, 50);
        sq.setBottom(0, -1, 0);
        this.add(sq);

        sq = new Square(ColourManager.getColour("grey 5"));
        sq.setScale(1, 4, 1);
        sq.setBottom(5, 0, 0);
        this.add(sq);

        sq = new Square(ColourManager.getColour("grey 5"));
        sq.setScale(1, 10, 1);
        sq.setBottom(-2, 0, -7);
        this.add(sq);

        sq = new Square(ColourManager.getColour("grey 5"));
        sq.setScale(1, 6, 1);
        sq.setBottom(0, 0, 9);
        this.add(sq);
    }
}
