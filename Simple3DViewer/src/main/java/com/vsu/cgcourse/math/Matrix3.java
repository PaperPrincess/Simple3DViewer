package com.vsu.cgcourse.math;

public class Matrix3 {
    private float[][] a = new float[3][3];
    private final float EPS = 1e-7f;

    public Matrix3(float a00, float a01, float a02,
                   float a10, float a11, float a12,
                   float a20, float a21, float a22) {
        a = new float[][]{{a00, a01, a02},
                                {a10, a11, a12},
                                {a20, a21, a22}};
    }
    public Matrix3(){}

    public Matrix3(float[][] a) {
        this.a = a;
    }

    public float get(int row, int col){
        return a[row][col];
    }
    public void set(int row, int col, float a){
        this.a[row][col] = a;
    }

    public Matrix3 addition(final Matrix3 matrix) {
        Matrix3 result = new Matrix3();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                result.set(row, col,a[row][col] + matrix.get(row, col));
            }
        }
        return result;
    }

    public Matrix3 subtraction(final Matrix3 matrix) {
        Matrix3 result = new Matrix3();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                result.set(row, col,a[row][col] - matrix.get(row, col));
            }
        }
        return result;
    }

    public Vector3 multiplicationByVector(final Vector3 vector) {
        return new Vector3(a[0][0] * vector.getX() + a[0][1] * vector.getY() + a[0][2] * vector.getZ(),
                a[1][0] * vector.getX() + a[1][1] * vector.getY() + a[1][2] * vector.getZ(),
                a[2][0] * vector.getX() + a[2][1] * vector.getY() + a[2][2] * vector.getZ());
    }

    public void transposition() {
        float b10 = a[1][0], b20 = a[2][0], b21 = a[2][1];
        a[1][0] = a[0][1];
        a[2][0] = a[0][2];
        a[2][1] = a[1][2];
        a[0][1] = b10;
        a[0][2] = b20;
        a[1][2] = b21;
    }

    public static Matrix3 nullMatrix() {
        return new Matrix3(0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    public static Matrix3 identityMatrix() {
        return new Matrix3(1, 0, 0, 0, 1, 0, 0, 0, 1);
    }

    public float determinant() {
        return a[0][0] * a[1][1] * a[2][2] + a[0][1] * a[1][2] * a[2][0] + a[1][0] * a[2][1] * a[0][2] -
                a[0][2] * a[1][1] * a[2][0] - a[0][1] * a[1][0] * a[2][2] - a[1][2] * a[2][1] * a[0][0];
    }
    public Matrix3 inverseMatrix() throws Exception {
        if (this.determinant() > EPS) {
            Matrix3 matrices = new Matrix3((a[1][1] * a[2][2] - a[1][2] * a[2][1]) / this.determinant(),
                    -(a[1][0] * a[2][2] - a[2][0] * a[1][2]) / this.determinant(),
                    (a[1][0] * a[2][1] - a[2][0] * a[1][1]) / this.determinant(),
                    -(a[0][1] * a[2][2] - a[2][1] * a[0][2]) / this.determinant(),
                    (a[0][0] * a[2][2] - a[2][0] * a[0][2]) / this.determinant(),
                    -(a[0][0] * a[2][1] - a[2][0] * a[0][1]) / this.determinant(),
                    (a[0][1] * a[1][2] - a[1][1] * a[0][2]) / this.determinant(),
                    -(a[0][0] * a[1][2] - a[1][0] * a[0][2]) / this.determinant(),
                    (a[0][0] * a[1][1] - a[1][0] * a[0][1]) / this.determinant());
            matrices.transposition();
            return matrices;
        }   else
            throw new Exception("Division by zero!");
    }

    @Override
    public boolean equals(Object obj) {
        final float EPS = 1e-7f;
        if (obj.getClass() != this.getClass()) {
            return false;
        } else {
            Matrix3 matrices = (Matrix3) obj;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (Math.abs(a[i][j] - matrices.get(i, j)) >= EPS)
                        return false;
                }
            }
            return true;
        }
    }
}
