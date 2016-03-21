package com.computer.team8.DSTR.graphics.light;

import com.computer.team8.DSTR.graphics.types.Vec3;

public class DirectionalLight {
    private Vec3 orientation;
    private float directionalIntensity;
    private float ambientIntensity;

    public DirectionalLight() {
        orientation = new Vec3(0, 0.5f, 1);
        directionalIntensity = 0.775f;
        ambientIntensity = 0.3f;
    }

    /* get */

    public Vec3 getOrienation() {
        return orientation;
    }

    public float[] getOrientationData() {
        float[] o = { orientation.x, orientation.y, orientation.z };
        return o;
    }

    public float getDirectionalIntensity() {
        return directionalIntensity;
    }

    public float getAmbientIntensity() {
        return ambientIntensity;
    }

    /* set */

    public void setOrientation(Vec3 v) {
        orientation.x = v.x;
        orientation.y = v.y;
        orientation.z = v.z;
    }

    public void setOrientation(float x, float y, float z) {
        orientation.x = x;
        orientation.y = y;
        orientation.z = z;

    }


}
