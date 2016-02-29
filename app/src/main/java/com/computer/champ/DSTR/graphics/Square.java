package com.computer.champ.DSTR.graphics;

import com.computer.champ.DSTR.graphics.element.Element;

public class Square extends Element {

    static float vertexData[] = {     // wind normals in counterclockwise order ->
            // front
            0.5f, 0.5f, 0.5f,   // top-right-front
            0.0f, 0.0f, -1.0f,
            -0.5f, 0.5f, 0.5f,  // top-left-front
            0.0f, 0.0f, -1.0f,
            -0.5f, -0.5f, 0.5f, // bottom-left-front
            0.0f, 0.0f, -1.0f,

            0.5f, 0.5f, 0.5f,   // top-right-front
            0.0f, 0.0f, -1.0f,
            -0.5f, -0.5f, 0.5f, // bottom-left-front
            0.0f, 0.0f, -1.0f,
            0.5f, -0.5f, 0.5f,  // bottom-right-front
            0.0f, 0.0f, -1.0f,

            // right
            0.5f, 0.5f, -0.5f,  // top-right-back
            1.0f, 0.0f, 0.0f,
            0.5f, 0.5f, 0.5f,   // top-right-front
            1.0f, 0.0f, 0.0f,
            0.5f, -0.5f, 0.5f,  // bottom-right-front
            1.0f, 0.0f, 0.0f,

            0.5f, 0.5f, -0.5f,  // top-right-back
            1.0f, 0.0f, 0.0f,
            0.5f, -0.5f, 0.5f,  // bottom-right-front
            1.0f, 0.0f, 0.0f,
            0.5f, -0.5f, -0.5f, // bottom-right-back
            1.0f, 0.0f, 0.0f,

            // back
            0.5f, 0.5f, -0.5f,  // top-right-back
            0.0f, 0.0f, 1.0f,
            -0.5f, 0.5f, -0.5f, // top-left-back
            0.0f, 0.0f, 1.0f,
            -0.5f, -0.5f, -0.5f,// bottom-left-back
            0.0f, 0.0f, 1.0f,

            -0.5f, 0.5f, 0.5f,  // top-left-front
            0.0f, 0.0f, 1.0f,
            -0.5f, 0.5f, -0.5f, // top-left-back
            0.0f, 0.0f, 1.0f,
            -0.5f, -0.5f, 0.5f, // bottom-left-front
            0.0f, 0.0f, 1.0f,

            // left
            -0.5f, 0.5f, -0.5f, // top-left-back
            -1.0f, 0.0f, 0.0f,
            -0.5f, -0.5f, -0.5f,// bottom-left-back
            -1.0f, 0.0f, 0.0f,
            -0.5f, -0.5f, 0.5f, // bottom-left-front
            -1.0f, 0.0f, 0.0f,

            -0.5f, 0.5f, 0.5f,  // top-left-front
            -1.0f, 0.0f, 0.0f,
            -0.5f, 0.5f, -0.5f, // top-left-back
            -1.0f, 0.0f, 0.0f,
            -0.5f, -0.5f, 0.5f, // bottom-left-front
            -1.0f, 0.0f, 0.0f,

            // top
            -0.5f, 0.5f, -0.5f, // top-left-back
            0.0f, 1.0f, 0.0f,
            -0.5f, 0.5f, 0.5f,  // top-left-front
            0.0f, 1.0f, 0.0f,
            0.5f, 0.5f, 0.5f,   // top-right-front
            0.0f, 1.0f, 0.0f,

            0.5f, 0.5f, 0.5f,   // top-right-front
            0.0f, 1.0f, 0.0f,
            0.5f, 0.5f, -0.5f,  // top-right-back
            0.0f, 1.0f, 0.0f,
            -0.5f, 0.5f, -0.5f, // top-left-back
            0.0f, 1.0f, 0.0f,

            // bottom
            0.5f, -0.5f, 0.5f,  // bottom-right-front
            0.0f, -1.0f, 0.0f,
            0.5f, -0.5f, -0.5f, // bottom-right-back
            0.0f, -1.0f, 0.0f,
            -0.5f, -0.5f, -0.5f,// bottom-left-back
            0.0f, -1.0f, 0.0f,

            0.5f, -0.5f, 0.5f,  // bottom-right-front
            0.0f, -1.0f, 0.0f,
            -0.5f, -0.5f, -0.5f,// bottom-left-back
            0.0f, -1.0f, 0.0f,
            -0.5f, -0.5f, 0.5f, // bottom-left-front
            0.0f, -1.0f, 0.0f
    };

    static float colour[] = {
            0.75f,  0.0f, 0.0f, 1.0f  // some colour
    };

    public Square() {
        super(new float[]{ 0.0f, 0.0f, 0.0f }, // position
                vertexData,                    // vertex data
                colour);                       // colours
    }
}
