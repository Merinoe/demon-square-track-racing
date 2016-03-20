package com.computer.team8.DSTR.graphics.camera;

import android.opengl.Matrix;

import com.computer.team8.DSTR.graphics.element.Element;
import com.computer.team8.DSTR.graphics.types.Vec3;

public class Camera {
    public Vec3 eye;
    public Vec3 focus;
    public Vec3 top;
    public Vec3 lateral; // used for accurate veritcal rotations

    private Element origin = new Element();
    private Element subject;

    private float velX, velY;

    private float[] result = new float[4];

    private float[] mvp = new float[16];
    private float[] proj = new float[16];
    private float[] view = new float[16];

    // general use
    private float[] rotation = new float[16];

    // constants
    public final float MAX_ZOOM_SPEED = 4.5f;
    public final float MAX_ROTATE_H_SPEED = 8.5f;
    public final float MAX_ROTATE_V_SPEED = 3.5f;

    public Camera(Vec3 eye, Vec3 focus, Vec3 top) {
        this.eye = eye;
        this.focus = focus;
        this.top = top;
        this.lateral = new Vec3();
        this.subject = this.origin;
        updateLateral();
    }

    public void setSubject(Element e) {
        subject = e;
    }

    public void update() {
        this.focus = subject.getPosition().multiply(0.5f);

        Vec3 neg = this.subject.getOrientationVector().multiply(-3);

        Matrix.setRotateM(
                rotation,
                0,       // not used
                90.0f,   // amount rotated
                0,
                1,    // axis of rotation
                0);

        Matrix.multiplyMV(result, 0, rotation, 0, neg.getData(), 0);
        neg.set(result[0], result[1], result[2]);

        this.eye.x = this.focus.x + neg.x;
        this.eye.y = this.focus.y + 1;
        this.eye.z = this.focus.z + neg.z;

        if (velX > 0) {
            if (velX < 0.075f) {
                velX = 0.0f;
            } else {
                velX -= 0.075f;
            }

            this.rotateHorizontally(velX);

        } else if (velX < 0) {
            if (velX > -0.075f) {
                velX = 0.0f;
            } else {
                velX += 0.075f;
            }

            this.rotateHorizontally(velX);
        }

        if (velY > 0) {
            if (velY < 0.075f) {
                velY = 0.0f;
            } else {
                velY -= 0.075f;
            }

            this.rotateVertically(velY);

        } else if (velY < 0) {
            if (velY > -0.075f) {
                velY = 0.0f;
            } else {
                velY += 0.075f;
            }

            this.rotateVertically(velY);
        }

        Matrix.setLookAtM(
                view,  // resulting model/view
                0,
                eye.x, eye.y, eye.z,
                focus.x, focus.y, focus.z,
                top.x, top.y, top.z);
        Matrix.multiplyMM(mvp, 0, proj, 0, view, 0);
    }

    public void updateLateral() {
        Vec3 temp = new Vec3();
        temp.x = eye.x - focus.x;
        temp.y = eye.y - focus.y;
        temp.z = eye.z - focus.z;

        Matrix.setRotateM(
            rotation,
            0,       // not used
            90.0f,   // amount rotated
            0,
            1,    // axis of rotation
            0);

        Matrix.multiplyMV(result, 0, rotation, 0, temp.getData(), 0);
        lateral.set(result[0], result[1], result[2]);
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

    public void rotateHorizontally(float angle) {
        Matrix.setRotateM(
                rotation,
                0,       // not used
                angle,   // amount rotated
                0,
                1,    // axis of rotation
                0);
        float[] result = new float[4];
        Matrix.multiplyMV(result, 0, rotation, 0, eye.getData(), 0);
        setEye(new Vec3(result[0], result[1], result[2]));

        velX = angle;

        updateLateral();
    }

    public void rotateVertically(float angle) {
        Matrix.setRotateM(
                rotation,
                0,       // not used
                angle,   // amount rotated
                lateral.x,
                lateral.y,    // axis of rotation
                lateral.z);
        float[] result = new float[4];
        Matrix.multiplyMV(result, 0, rotation, 0, eye.getData(), 0);
        setEye(new Vec3(result[0], result[1], result[2]));

        velY = angle;

        updateLateral();
    }

    public void zoom(float amount) {
        eye = eye.multiply(1.0f - amount);
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
