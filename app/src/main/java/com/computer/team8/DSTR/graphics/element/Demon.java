package com.computer.team8.DSTR.graphics.element;

import android.util.Log;

import com.computer.team8.DSTR.graphics.service.ColourManager;
import com.computer.team8.DSTR.graphics.track.Track;
import com.computer.team8.DSTR.graphics.types.Vec3;
import com.computer.team8.DSTR.graphics.types.Vec4;

import java.util.ArrayList;

public class Demon extends Square {
    private int nextPoint;
    private int lastPoint;
    private ArrayList<Float> trackPoints;

    private final int STRIDE = 3;
    private int DEMON_TURN_SPEED = 6;
    private float DEMON_TURN_BUFFER = 0.08f;
    private float DEMON_UPPER_VELOCITY = 0.3f;
    private float DEMON_LOWER_VELOCITY = 0.03f;
    private float DEMON_ROLL_LIMIT = 50.0f;

    // failure dynamics
    private boolean DEMON_FAIL = false;
    private Vec3 DEMON_FAIL_POINT;
    private float DEMON_FALL_DIST = 0.0f;
    private float DEMON_FALL_LIMIT = 20.0f;
    private float DEMON_FALL_SPEED = 0.2f;

    public Demon() {
        super(new Vec4(1, 0, 0, 1));
        this.setScale(0.5f, 1, 1);
        this.setBottom(0, 0, 0);

        turnSpeed = DEMON_TURN_SPEED;

        nextPoint = 1;
    }

    public Demon(float x, float y, float z) {
        super(new Vec4(1, 0, 0, 1));
        this.setScale(0.5f, 1, 1);
        this.setBottom(x, y, z);

        turnSpeed = DEMON_TURN_SPEED;

        nextPoint = 1;
    }

    public Demon(String color) {

        super(ColourManager.getColour(color));
        this.setScale(0.5f, 1, 1);
        this.setBottom(0, 0, 0);

        turnSpeed = DEMON_TURN_SPEED;

        nextPoint = 1;
    }

    public boolean hasFailed() {
        return DEMON_FAIL;
    }

    public boolean failFall() {
        // reset from fall back on to track
        if (DEMON_FALL_DIST > DEMON_FALL_LIMIT) {
            setBottom(DEMON_FAIL_POINT);

            velocity = 0;
            DEMON_FAIL = false;
            DEMON_FALL_DIST = 0.0f;

            resetRoll();

            return true;

        // fall because you suck
        } else {
            Vec3 dir = getOrientationVector().normalize().multiply(velocity);
            setBottom(
                    getBottom().x + dir.x,
                    getBottom().y - DEMON_FALL_SPEED,
                    getBottom().z + dir.z
            );

            DEMON_FALL_DIST += DEMON_FALL_SPEED;
            rollSpeed = 0;
            return false;
        }
    }

    public void setTrack(Track track) {
        trackPoints = track.getTrack();
        setBottom(trackPoints.get(0), trackPoints.get(1) + 0.01f, trackPoints.get(2));
    }

    public boolean rideTrack() {
        if (hasFailed()) {
            rollSpeed = 0;
            return false;
        }
        if (getRollAngle() > DEMON_ROLL_LIMIT ||
            getRollAngle() < -DEMON_ROLL_LIMIT) {
            rollSpeed = 0;
            DEMON_FAIL = true;
            DEMON_FAIL_POINT = this.getBottom();
            return false;
        }

        if (velocity > DEMON_UPPER_VELOCITY) {
            velocity = DEMON_UPPER_VELOCITY;
        } else if (velocity < DEMON_LOWER_VELOCITY) {
            velocity = DEMON_LOWER_VELOCITY;
        }

        if (trackPoints != null) {
            if ((nextPoint * STRIDE) <= trackPoints.size() - STRIDE) {
                Vec3 next = new Vec3(
                        trackPoints.get(nextPoint * STRIDE),
                        trackPoints.get((nextPoint * STRIDE) + 1),
                        trackPoints.get((nextPoint * STRIDE) + 2)
                );

                Vec3 prev = new Vec3(
                        trackPoints.get((nextPoint - 1) * STRIDE),
                        trackPoints.get(((nextPoint - 1) * STRIDE) + 1),
                        trackPoints.get(((nextPoint - 1) * STRIDE) + 2)
                );

                // move the demon along the track
                Vec3 pos = this.getBottom();
                Vec3 dir = next.subtract(pos);
                if (dir.magnitude() > 0.15f) {
                    dir = dir.normalize().multiply(velocity);
                    setBottom(
                            pos.x + dir.x,
                            pos.y + dir.y + 0.01f,
                            pos.z + dir.z
                    );
                } else {
                    ++nextPoint;
                }

                feelSlope(next.y);

                next = next.subtract(prev);

                // turn to face the orientation of the next track segment
                Vec3 ori = getOrientationVector();
                next.y = 0;
                ori.y = 0;

                float dot = next.normalize().dot(ori);
                if (dot <= -DEMON_TURN_BUFFER) {
                    rotateHorizontally(turnSpeed);
                    roll(-velocity * 4f);
                } else if (dot >= DEMON_TURN_BUFFER) {
                    rotateHorizontally(-turnSpeed);
                    roll(velocity * 4f);
                }

                return false;
            } else {
                nextPoint = 1;
                ++Track.lapCounter;

                if (Track.lapCounter >= Track.laps) {
                    Track.lapCounter = 0;
                    return true;
                } else {
                    return false;
                }
            }
        }

        return true;
    }
}
