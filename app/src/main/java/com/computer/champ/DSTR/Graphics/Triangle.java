package com.computer.champ.DSTR.Graphics;

import com.computer.champ.DSTR.Graphics.Element.Element;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Arrays;

public class Triangle extends Element {
    private FloatBuffer vertexBuffer;

    static float coords[] = {   // in counterclockwise order:
            0.0f,  0.622008459f, 0.0f, // top
            -0.5f, -0.311004243f, 0.0f, // bottom left
            0.5f, -0.311004243f, 0.0f  // bottom right
    };

    public Triangle() {
        super(new float[]{ 0.0f, 0.0f, 0.0f },       // position
              coords,                        // vertices
              new float[]{ 1.0f, 1.0f, 1.0f, 1.0f}); // colour
    }
}
