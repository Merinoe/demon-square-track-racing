package com.computer.team8.DSTR.graphics.element;

import com.computer.team8.DSTR.graphics.element.Element;
import com.computer.team8.DSTR.graphics.types.Vec3;
import com.computer.team8.DSTR.graphics.types.Vec4;

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
            -0.5f, -0.5f, -0.5f,// bottom-left-back
            0.0f, 0.0f, 1.0f,
            -0.5f, 0.5f, -0.5f, // top-left-back
            0.0f, 0.0f, 1.0f,
            0.5f, 0.5f, -0.5f,  // top-right-back
            0.0f, 0.0f, 1.0f,

            0.5f, 0.5f, -0.5f,  // top-right-back
            0.0f, 0.0f, 1.0f,
            0.5f, -0.5f, -0.5f, // bottom-right-back
            0.0f, 0.0f, 1.0f,
            -0.5f, -0.5f, -0.5f,// bottom-left-back
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
            -0.5f, -0.5f, -0.5f,// bottom-left-back
            0.0f, -1.0f, 0.0f,
            0.5f, -0.5f, -0.5f, // bottom-right-back
            0.0f, -1.0f, 0.0f,

            0.5f, -0.5f, 0.5f,  // bottom-right-front
            0.0f, -1.0f, 0.0f,
            -0.5f, -0.5f, 0.5f, // bottom-left-front
            0.0f, -1.0f, 0.0f,
            -0.5f, -0.5f, -0.5f,// bottom-left-back
            0.0f, -1.0f, 0.0f
    };

    static Vec4 colour = new Vec4(0.75f, 0.0f, 0.0f, 1.0f);

    public Square() {
        super(new Vec3(0.0f, 0.0f, 0.0f), vertexData, colour);
    }

    public Square(float x, float y, float z) {
        super(new Vec3(x, y, z), vertexData, colour);
    }

    public Square(Vec3 v) {
            super(v, vertexData, colour);
    }
}
