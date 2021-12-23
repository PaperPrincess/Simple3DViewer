package com.vsu.cgcourse.math;

public class Vector3{
    private float x;
    private float y;
    private float z;
    private final float EPS = 1e-7f;


    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector3() {
    }
    public Vector3(float[] vector){
        this.x = vector[0];
        this.y = vector[1];
        this.z = vector[2];
    }
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public void add(final Vector3 vector) {
        this.x += vector.getX();
        this.y += vector.getY();
        this.z += vector.getZ();
    }

    public Vector3 sub(final Vector3 vector) {
        return new Vector3(x - vector.getX(), y - vector.getY(), z - vector.getZ());
    }

    public Vector3 scalarMultiplication(final float n) {
        return new Vector3(x * n, y * n, z * n);
    }

    protected Vector3 scalarDivision(final float n) throws Exception {
        if (n > EPS)
            return new Vector3(x / n, y / n, z / n);
        else
            throw new Exception("Division by zero!");
    }

    public float length() {
        return (float) Math.sqrt(x*x + y*y + z*z);
    }

    public void normalize() throws Exception {
        Vector3 vector = scalarDivision(this.length());
        x = vector.getX();
        y = vector.getY();
        z = vector.getZ();
    }

    public float dot(final Vector3 vector) {
        return x * vector.getX() + y * vector.getY() + z * vector.getZ();
    }

    public Vector3 cross(final Vector3 vector) {
        return new Vector3(y * vector.getZ() - z * vector.getY(),
                z * vector.getX() - x * vector.getZ(), - y * vector.getX() + x * vector.getY());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        } else {
            Vector3 vector = (Vector3) obj;
            return Math.abs(x - vector.getX()) < EPS && Math.abs(y - vector.getY()) < EPS &&
                    Math.abs(z - vector.getZ()) < EPS;
        }
    }

    @Override
    public String toString(){
        return x + " " + y + " " + z;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }
}
