package com.computer.champ.DSTR.graphics;

import com.computer.champ.DSTR.graphics.element.Element;

public class Triangle extends Element {
    static float coords[] = {   // in counterclockwise order:
            0.0f, 0.25f, 0.0f,    // top
            -0.25f, -0.25f, 0.0f, // left
            0.25f, -0.25f, 0.0f,   // right

            0.0f, 0.25f, 0.0f,// top
            0.25f, -0.25f, 0.0f,// right
            0.0f, 0.0f, -0.5f,// back

            0.0f, 0.25f, 0.0f,// top
            0.0f, 0.0f, -0.5f,// back
            -0.25f, -0.25f, 0.0f,// left

            -0.25f, -0.25f, 0.0f,// left
            0.0f, 0.0f, -0.5f,// back
            0.25f, -0.25f, 0.0f// right
    };

    static float colour[] = {
            0.2f,  0.35f, 0.6f, 1.0f
    };

    public Triangle() {
        super(new float[]{ 0.0f, 0.0f, 0.0f }, // position
              coords,                          // vertices
              colour);                        // colours
    }
}
