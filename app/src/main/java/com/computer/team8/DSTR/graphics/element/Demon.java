package com.computer.team8.DSTR.graphics.element;

import com.computer.team8.DSTR.graphics.track.Track;
import com.computer.team8.DSTR.graphics.types.Vec3;
import com.computer.team8.DSTR.graphics.types.Vec4;

import java.util.ArrayList;

public class Demon extends Square {
    private int nextPoint;
    private int lastPoint;
    private ArrayList<Float> trackPoints;

    private int STRIDE = 3;
    private int DEMON_TURN_SPEED = 3;
    private float DEMON_VELOCITY = 0.05f;

    public Demon(float x, float y, float z) {
        super(new Vec4(1, 0, 0, 1));
        this.setScale(0.5f, 1, 1);
        this.setBottom(x, y, z);

        nextPoint = 1;
    }

    public void setTrack(Track track) {
        this.trackPoints = track.getTrack();
    }

    public boolean rideTrack() {
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
            if (dir.magnitude() > 0.1f) {
                dir = dir.normalize().multiply(DEMON_VELOCITY);
                this.setBottom(
                        pos.x + dir.x,
                        pos.y + dir.y,
                        pos.z + dir.z
                );
            } else {
                ++nextPoint;
            }

            next = next.subtract(prev);

            // turn to face the orientation of the next track segment
            Vec3 ori = this.getOrientationVector();
            float dot = next.dot(ori);
            System.out.println("Dot: " + dot);
            if (dot >= -1.0f) {
                this.rotateHorizontally(DEMON_TURN_SPEED);
            } else if (dot <= 1.0f) {
                this.rotateHorizontally(-DEMON_TURN_SPEED);
            }
        } else {
            return true;
        }

        return false;
    }
}
