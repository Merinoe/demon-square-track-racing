package com.computer.team8.DSTR.graphics.element;

import com.computer.team8.DSTR.graphics.track.Track;
import com.computer.team8.DSTR.graphics.types.Vec3;
import com.computer.team8.DSTR.graphics.types.Vec4;

import java.util.ArrayList;

public class Demon extends Square {
    private int nextPoint;
    private int lastPoint;
    private ArrayList<Float> trackPoints;

    private final int STRIDE = 3;
    private int DEMON_TURN_SPEED = 8;
    private float DEMON_VELOCITY = 0.3f;
    private float DEMON_ROLL_LIMIT = 60.0f;
    private boolean DEMON_FAIL = false;

    public Demon(float x, float y, float z) {
        super(new Vec4(1, 0, 0, 1));
        this.setScale(0.5f, 1, 1);
        this.setBottom(x, y, z);

        turnSpeed = DEMON_TURN_SPEED;

        nextPoint = 1;
    }

    public float getVelocityRatio() {
        return this.velocity / DEMON_VELOCITY;
    }

    public boolean hasFailed() {
        return DEMON_FAIL;
    }

    public void setTrack(Track track) {
        trackPoints = track.getTrack();
    }

    public boolean rideTrack() {
        if (getRollAngle() > DEMON_ROLL_LIMIT ||
            getRollAngle() < -DEMON_ROLL_LIMIT) {
            DEMON_FAIL = true;
            return true;
        }

        if (velocity > DEMON_VELOCITY) {
            velocity = DEMON_VELOCITY;
        } else if (velocity < 0.02f) {
            velocity = 0.02f;
        }

        if (trackPoints != null) {
            if ((nextPoint * STRIDE) <= trackPoints.size() - 3) {
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
                if (dir.magnitude() > 0.2f) {
                    dir = dir.normalize().multiply(velocity);
                    setBottom(
                            pos.x + dir.x,
                            pos.y + dir.y,
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
                if (dot <= -0.08f) {
                    rotateHorizontally(turnSpeed);
                    roll(-velocity * 3f);
                } else if (dot >= 0.08f) {
                    rotateHorizontally(-turnSpeed);
                    roll(velocity * 3f);
                }

                return false;
            } else {
                return true;
            }
        }

        return true;
    }
}
