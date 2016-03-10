package com.computer.team8.DSTR.graphics;

import com.computer.team8.DSTR.graphics.element.Element;
import com.computer.team8.DSTR.graphics.element.Square;
import com.computer.team8.DSTR.graphics.service.ColourManager;
import com.computer.team8.DSTR.graphics.track.BuiltInTrack;
import com.computer.team8.DSTR.graphics.track.Track;

import java.util.ArrayList;

public class DSTRBufferManager {
    private static ArrayList<Element> elements;
    private static Track track;

    public DSTRBufferManager() {
        elements = new ArrayList<>();
        track = new BuiltInTrack();
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

        sq = new Square(ColourManager.getColour("dirt"));
        sq.setScale(10, 1, 10);
        sq.setBottom(15, 0, 12);
        this.add(sq);
        sq = new Square(ColourManager.getColour("chill grass"));
        sq.setScale(10, 1, 10);
        sq.setBottom(15, 1, 12);
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
