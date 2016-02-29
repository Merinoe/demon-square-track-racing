package com.computer.champ.DSTR.graphics;

import com.computer.champ.DSTR.graphics.element.Element;

public class Triangle extends Element {
    static float coords[] = {   // in counterclockwise order:
            0.0f,  0.622008459f, 0.0f, // top
            -0.5f, -0.311004243f, 0.0f, // bottom left
            0.5f, -0.311004243f, 0.0f  // bottom right
    };

    static float colour[] = {
            0.0f,  0.622008459f, 0.0f, 1.0f
    };

    public Triangle() {
        super(new float[]{ 0.0f, 0.0f, 0.0f }, // position
              coords,                          // vertices
              colour);                        // colours
    }
}
