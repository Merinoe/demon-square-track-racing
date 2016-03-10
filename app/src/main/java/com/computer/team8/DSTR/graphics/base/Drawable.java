package com.computer.team8.DSTR.graphics.base;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;

public class Drawable {
    protected FloatBuffer processedData;

    public void updateBuffer(List<Float> data) {
        if (processedData != null) {
            processedData.clear();
        }
        ByteBuffer vb = ByteBuffer.allocateDirect(data.size() * 4);
        vb.order(ByteOrder.nativeOrder());
        processedData = vb.asFloatBuffer();

        for (Float f : data) {
            processedData.put(f);
        }

        processedData.position(0);
    }

    public FloatBuffer getBuffer() {
        return processedData;
    }
}
