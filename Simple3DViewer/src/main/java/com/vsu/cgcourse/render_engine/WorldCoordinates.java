package com.vsu.cgcourse.render_engine;

import com.vsu.cgcourse.math.Matrix4;

public class WorldCoordinates {

    public static Matrix4 scale(float x, float y, float z) {
        return new Matrix4(
                x, 0, 0, 0,
                0, y, 0, 0,
                0, 0, z, 0,
                0, 0, 0, 1);
    }

    public static Matrix4 rotationX(float angleX) {
        double angle = Math.toRadians(angleX);
        return new Matrix4(
                1, 0, 0, 0,
                0, (float) Math.cos(angle), (float) Math.sin(angle), 0,
                0, (float) -Math.sin(angle), (float) Math.cos(angle), 0,
                0, 0, 0, 1);
    }

    public static Matrix4 rotationY(float angleY) {
        double angle = Math.toRadians(angleY);
        return new Matrix4(
                (float) Math.cos(angle), 0, (float) Math.sin(angle), 0,
                0, 1, 0, 0,
                (float) -Math.sin(angle), 0, (float) Math.cos(angle), 0,
                0, 0, 0, 1);
    }

    public static Matrix4 rotationZ(float angleZ) {
        double angle = Math.toRadians(angleZ);
        return new Matrix4(
                (float) Math.cos(angle), (float) Math.sin(angle), 0, 0,
                (float) -Math.sin(angle), (float) Math.cos(angle), 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1);

    }

    public static Matrix4 translation(float tx, float ty, float tz) {
        return new Matrix4(
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                tx, ty, tz, 1);
    }
}
