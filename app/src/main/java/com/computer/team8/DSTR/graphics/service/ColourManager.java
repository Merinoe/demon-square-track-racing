package com.computer.team8.DSTR.graphics.service;

import com.computer.team8.DSTR.graphics.types.Vec4;

import java.util.HashMap;

public class ColourManager {
    private static HashMap<String, Vec4> colours;
    private static ColourManager instance = new ColourManager();

    public ColourManager() {
        colours = new HashMap<>();
        colours.put("pure red", new Vec4(1.0f, 0.0f, 0.0f, 1.0f));
        colours.put("blood orange", new Vec4(1.0f, 0.5f, 0.0f, 1.0f));
        colours.put("pure green", new Vec4(0.0f, 1.0f, 0.0f, 1.0f));
        colours.put("chill grass", new Vec4(0.4f, 1.0f, 0.4f, 1.0f));
        colours.put("pure blue", new Vec4(0.0f, 0.0f, 1.0f, 1.0f));
        colours.put("lilac", new Vec4(0.6f, 0.6f, 1.0f, 1.0f));
        colours.put("grey 4", new Vec4(0.4f, 0.4f, 0.4f, 1.0f));
        colours.put("grey 5", new Vec4(0.5f, 0.5f, 0.5f, 1.0f));
        colours.put("dirt", new Vec4(0.75f, 0.5f, 0.5f, 1.0f));
    }

    public static ColourManager getInstance() {
        if(instance == null) {
            instance = new ColourManager();
        }
        return instance;
    }

    public static Vec4 getColour(String name) {
        return colours.get(name);
    }

    public static float[] getColourData(String name) {
        Vec4 c = colours.get(name);
        float[] colour = { c.x, c.y, c.z, c.w };
        return colour;
    }
}
