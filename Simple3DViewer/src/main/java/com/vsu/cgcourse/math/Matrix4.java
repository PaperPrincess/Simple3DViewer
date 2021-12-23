package com.vsu.cgcourse.math;

public class Matrix4 implements Cloneable {

    private float[][] a = new float[4][4];
    private final float EPS = 1e-7f;

    public Matrix4() {
    }

    public Matrix4(float a00, float a01, float a02, float a03,
                   float a10, float a11, float a12, float a13,
                   float a20, float a21, float a22, float a23,
                   float a30, float a31, float a32, float a33) {
        a = new float[][]{{a00, a01, a02, a03},
                {a10, a11, a12, a13},
                {a20, a21, a22, a23},
                {a30, a31, a32, a33}};
    }
    public Matrix4(float[] matrix4){

    }
    public Matrix4(float[][] a) {
        this.a = a;
    }

    public Matrix4(Matrix4 matrix4) {
        for (int i = 0; i < 4; i++) {
            a[i] = matrix4.a[i].clone();
        }
    }

    public float get(int row, int col) {
        return a[row][col];
    }

    public void set(int row, int col, float a) {
        this.a[row][col] = a;
    }

    public Matrix4 addition(final Matrix4 matrices) {
        Matrix4 matrix4 = new Matrix4();
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                matrix4.set(row, col, a[row][col] + matrices.get(row, col));
            }
        }
        return matrix4;
    }

    public Matrix4 subtraction(final Matrix4 matrices) {
        Matrix4 matrix4 = new Matrix4();
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                matrix4.set(row, col, a[row][col] - matrices.get(row, col));
            }
        }
        return matrix4;
    }

    public Vector4 multiplicationByVector(final Vector4 vector) {
        return new Vector4(a[0][0] * vector.getX() + a[0][1] * vector.getY() +
                a[0][2] * vector.getZ() + a[0][3] * vector.getL(),
                a[1][0] * vector.getX() + a[1][1] * vector.getY() +
                        a[1][2] * vector.getZ() + a[1][3] * vector.getL(),
                a[2][0] * vector.getX() + a[2][1] * vector.getY() +
                        a[2][2] * vector.getZ() + a[2][3] * vector.getL(),
                a[3][0] * vector.getX() + a[3][1] * vector.getY() +
                        a[3][2] * vector.getZ() + a[3][3] * vector.getL());
    }


    public void transposition() {
        float b10 = a[1][0], b20 = a[2][0], b21 = a[2][1], b30 = a[3][0], b31 = a[3][1], b32 = a[3][2];
        a[1][0] = a[0][1];
        a[2][0] = a[0][2];
        a[2][1] = a[1][2];
        a[3][0] = a[0][3];
        a[3][1] = a[1][3];
        a[3][2] = a[2][3];
        a[0][1] = b10;
        a[0][2] = b20;
        a[0][3] = b30;
        a[1][2] = b21;
        a[1][3] = b31;
        a[2][3] = b32;
    }

    public static Matrix4 nullMatrix() {
        return new Matrix4(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0);
    }

    public static Matrix4 identityMatrix() {
        return new Matrix4(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0,
                0, 0, 0, 1);
    }

    public float determinant() {
        float det = 0;
        for (int col = 0; col < 4; col++) {
            det += Math.pow(-1, col) * a[0][col] * createMatrix3(this, 0, col).determinant();
        }
        return det;
    }

    public Matrix4 inverseMatrix() throws Exception {
        if (this.determinant() > EPS) {
            Matrix4 matrix = new Matrix4();
            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {
                    matrix.set(col, row, (float) (Math.pow(-1, row + col) *
                            createMatrix3(this, row, col).determinant() / this.determinant()));
                }
            }
            return matrix;
        } else
            throw new Exception("Division by zero!");
    }

    public Matrix3 createMatrix3(Matrix4 matrix4, int r4, int c4) {
        Matrix3 matrix3 = new Matrix3();
        int r3 = 0;
        for (int row = 0; row < 4; row++) {
            if (row != r4) {
                int c3 = 0;
                for (int col = 0; col < 4; col++) {
                    if (col != c4) {
                        matrix3.set(r3, c3, matrix4.get(row, col));
                        c3++;
                    }
                }
                r3++;
            }
        }
        return matrix3;
    }

    @Override
    public boolean equals(Object obj) {
        final float EPS = 1e-7f;
        if (obj.getClass() != this.getClass()) {
            return false;
        } else {
            Matrix4 matrices = (Matrix4) obj;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (Math.abs(a[i][j] - matrices.get(i, j)) >= EPS)
                        return false;
                }
            }
            return true;
        }
    }

    @Override
    public Matrix4 clone() throws CloneNotSupportedException {
        return (Matrix4) super.clone();
    }

    public final void mul(Matrix4 var1) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.set(i, j, this.get(i, 0) * var1.get(0, j) +
                        this.get(i, 1) * var1.get(1, j) +
                        this.get(i, 2) * var1.get(2, j) +
                        this.get(i, 3) * var1.get(3, j));
            }
        }
    }
}
