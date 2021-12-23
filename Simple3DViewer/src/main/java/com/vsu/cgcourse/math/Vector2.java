package com.vsu.cgcourse.math;

public class Vector2{
    private float x;
    private float y;
    private final float EPS = 1e-7f;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public Vector2(float[] vector){
        this.x = vector[0];
        this.y = vector[1];
    }
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Vector2 vectorsSum(final Vector2 vector) {
        Vector2 vector2 = new Vector2(x + vector.getX(), y + vector.getY());
        return vector2;
    }

    public Vector2 vectorsDifference(final Vector2 vector) {
        return new Vector2(x - vector.getX(), y - vector.getY());
    }

    public Vector2 scalarMultiplication(final float n) {
        return new Vector2(x * n, y * n);
    }

    public Vector2 scalarDivision(final float n) throws Exception {
        if (Math.abs(n) > EPS)
            return new Vector2(x / n, y / n);
        else
            throw new Exception("Division by zero!");
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public Vector2 normalization() throws Exception {
        return this.scalarDivision(this.length());
    }

    public float dotProduct(final Vector2 vector) {
        return x * vector.getX() + y * vector.getY();
    }

    @Override
    public boolean equals(Object obj){
        if (obj.getClass() != this.getClass()){
            return false;
        } else{
            Vector2 vector = (Vector2) obj;
            return Math.abs(x - vector.getX()) < EPS && Math.abs(y - vector.getY()) < EPS;
        }
    }

    @Override
    public String toString(){
        return x + " " + y;
    }
}
