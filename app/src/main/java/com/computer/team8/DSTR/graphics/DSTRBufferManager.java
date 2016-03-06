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
        colours.add(new Vec4(1.0f, 0.0f, 0.0f, 1.0f)); // pure red
        colours.add(new Vec4(1.0f, 0.5f, 0.0f, 1.0f)); // blorange
        colours.add(new Vec4(0.0f, 1.0f, 0.0f, 1.0f)); // pure green
        colours.add(new Vec4(0.4f, 1.0f, 0.4f, 1.0f)); // chill grass
        colours.add(new Vec4(0.0f, 0.0f, 1.0f, 1.0f)); // pure blue
        colours.add(new Vec4(0.6f, 0.6f, 1.0f, 1.0f)); // lilac
        colours.add(new Vec4(0.4f, 0.4f, 0.4f, 1.0f)); // grey 4
        colours.add(new Vec4(0.5f, 0.5f, 0.5f, 1.0f)); // grey 5
        colours.add(new Vec4(0.75f, 0.5f, 0.5f, 1.0f)); // dirtish
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
        Square sq = new Square(colours.get(8));
        sq.setScale(25, 1, 25);
        sq.setBottom(0, -2, 0);
        this.add(sq);

        sq = new Square(colours.get(3));
        sq.setScale(25, 1, 25);
        sq.setBottom(0, -1, 0);
        this.add(sq);

        sq = new Square(colours.get(5));
        sq.setScale(1, 4, 1);
        sq.setBottom(5, 0, 0);
        this.add(sq);

        sq = new Square(colours.get(5));
        sq.setScale(1, 10, 1);
        sq.setBottom(-2, 0, -7);
        this.add(sq);

        sq = new Square(colours.get(5));
        sq.setScale(1, 6, 1);
        sq.setBottom(0, 0, 9);
        this.add(sq);

        // columns
//        sq = new Square(colours.get(7));
//        sq.setScale(25, 1, 25);
//        sq.setBottom(0, 0, 0);
//        this.add(sq);

//        sq = new Square(colours.get(6));
//        sq.setScale(25.0f, 175.0f, 64.0f);
//        sq.setBottom(80, 0, 34);
//        this.add(sq);
//
//        sq = new Square(colours.get(6));
//        sq.setScale(33.0f, 300.0f, 120.0f);
//        sq.setBottom(-20, 0, -60);
//        this.add(sq);
    }
}
