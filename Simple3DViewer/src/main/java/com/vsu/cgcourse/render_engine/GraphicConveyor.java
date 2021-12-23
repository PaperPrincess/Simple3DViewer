package com.vsu.cgcourse.render_engine;

import com.vsu.cgcourse.math.Matrix4;
import com.vsu.cgcourse.math.Vector3;

import javax.vecmath.Point2f;

public class GraphicConveyor {

    public static Matrix4 rotateScaleTranslate(float x, float y, float z, float angleX, float angleY, float angleZ, float tx, float ty, float tz) {
        Matrix4 matrix4 = Matrix4.identityMatrix();
        matrix4.mul(WorldCoordinates.scale(x, y, z));
        matrix4.mul(WorldCoordinates.rotationX(angleX));
        matrix4.mul(WorldCoordinates.rotationY(angleY));
        matrix4.mul(WorldCoordinates.rotationZ(angleZ));
        matrix4.mul(WorldCoordinates.translation(tx, ty, tz));
        return matrix4;
    }

    public static Matrix4 lookAt(Vector3 eye, Vector3 target) throws Exception {
        return lookAt(eye, target, new Vector3(0F, 1.0F, 0F));
    }

    public static Matrix4 lookAt(Vector3 eye, Vector3 target, Vector3 up) throws Exception {
        Vector3 resultZ = target.sub(eye);
        Vector3 resultX = up.cross(resultZ);
        Vector3 resultY = resultZ.cross(resultX);

        resultX.normalize();
        resultY.normalize();
        resultZ.normalize();

        return new Matrix4(
                resultX.getX(), resultY.getX(), resultZ.getX(), 0,
                resultX.getY(), resultY.getY(), resultZ.getY(), 0,
                resultX.getZ(), resultY.getZ(), resultZ.getZ(), 0,
                -resultX.dot(eye), -resultY.dot(eye), -resultZ.dot(eye), 1);
    }

    public static Matrix4 perspective(
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        Matrix4 result = new Matrix4();
        float tangentMinusOnDegree = (float) (1.0F / (Math.tan(fov * 0.5F)));
        result.set(0, 0, tangentMinusOnDegree / aspectRatio);
        result.set(1, 1, tangentMinusOnDegree);
        result.set(2, 2, (farPlane + nearPlane) / (farPlane - nearPlane));
        result.set(2, 3, 1.0F);
        result.set(3, 2, 2 * (nearPlane * farPlane) / (nearPlane - farPlane));
        return result;
    }


    public static Vector3 multiplyMatrix4ByVector3(final Matrix4 matrix, final Vector3 vertex) {
        final float x = (vertex.getX() * matrix.get(0, 0)) + (vertex.getY() * matrix.get(0,1)) + (vertex.getZ() * matrix.get(0,2)) + matrix.get(0,3);
        final float y = (vertex.getX() * matrix.get(1, 0)) + (vertex.getY() * matrix.get(1,1)) + (vertex.getZ() * matrix.get(1,2)) + matrix.get(1, 3);
        final float z = (vertex.getX() * matrix.get(2, 0)) + (vertex.getY() * matrix.get(2,1)) + (vertex.getZ() * matrix.get(2,2)) + matrix.get(2,3);
        final float w = (vertex.getX() * matrix.get(3,0)) + (vertex.getY() * matrix.get(3, 1)) + (vertex.getZ() * matrix.get(3,2)) + matrix.get(3,3);
        return new Vector3(x / w, y / w, z / w);
    }

    public static Point2f vertexToPoint(final Vector3 vertex, final int width, final int height) {
        return new Point2f(vertex.getX() * width + width / 2.0F, -vertex.getY() * height + height / 2.0F);
    }

}
