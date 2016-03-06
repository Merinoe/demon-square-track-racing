package com.computer.team8.DSTR.graphics.camera;

import android.opengl.Matrix;

import com.computer.team8.DSTR.graphics.types.Vec3;

public class Camera {
    public Vec3 eye;
    public Vec3 focus;
    public Vec3 top;

    private float[] mvp = new float[16];
    private float[] proj = new float[16];
    private float[] view = new float[16];

    // general use
    private float[] rotation = new float[16];

    // constants
    public final float MAX_ZOOM_SPEED = 5.0f;
    public final float MAX_ROTATE_SPEED = 3.0f;

    public Camera(Vec3 eye, Vec3 focus, Vec3 top) {
        this.eye = eye;
        this.focus = focus;
        this.top = top;
    }

    public void update() {
        Matrix.setLookAtM(
                view,  // resulting model/view
                0,
                eye.x, eye.y, eye.z,
                focus.x, focus.y, focus.z,
                top.x, top.y, top.z);
        Matrix.multiplyMM(mvp, 0, proj, 0, view, 0);
    }

    public void updateFOV(int width, int height) {
        float fov = 45.0f;
        float tanMath = fov * (float)Math.PI / 360.0f;
        float top = (float) (Math.tan(tanMath) * 0.1);
        float bottom = -top;
        float left;
        float right;

        if (width < height) {
            left = (9.0f / 15.0f) * bottom;
            right = (9.0f / 15.0f) * top;
        } else {
            left = (15.0f / 9.0f) * bottom;
            right = (15.0f / 9.0f) * top;
        }

        Matrix.frustumM(proj, 0, left, right, bottom, top, 0.1f, 1000.0f);
    }

    public void rotate(float angle, Vec3 v) {
        Matrix.setRotateM(
                rotation,
                0,       // not used
                angle,   // amount rotated
                v.x,
                v.y,    // axis of rotation
                v.z);
        float[] result = new float[4];
        Matrix.multiplyMV(result, 0, rotation, 0, eye.getData(), 0);
        setEye(new Vec3(result[0], result[1], result[2]));
    }

    public void zoom(float amount) {
        eye.multiply(1.0f - amount);
    }


    /* set */
    public void setEye(Vec3 v) { eye = v; }
    public void setFocus(Vec3 v) { focus = v; }
    public void setTop(Vec3 v) { top = v; }

    /* get */
    public Vec3 getEye() { return eye; }
    public Vec3 getFocus() { return focus; }
    public Vec3 getTop() { return top; }

    public float[] getMVP() {
        return mvp;
    }

    public float[] getView() {
        return view;
    }

    public float[] getProjection() {
        return proj;
    }
}
