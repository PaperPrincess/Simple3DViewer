package com.vsu.cgcourse.render_engine;

import com.vsu.cgcourse.math.Matrix4;
import com.vsu.cgcourse.math.Vector3;
import com.vsu.cgcourse.model.Mesh;
import javafx.scene.canvas.GraphicsContext;

import javax.vecmath.Point2f;
import java.util.ArrayList;

import static com.vsu.cgcourse.render_engine.GraphicConveyor.multiplyMatrix4ByVector3;
import static com.vsu.cgcourse.render_engine.GraphicConveyor.vertexToPoint;

public class RenderEngine {
    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Mesh mesh,
            final int width,
            final int height) throws Exception {
        Matrix4 viewMatrix = camera.getViewMatrix();
        Matrix4 projectionMatrix = camera.getProjectionMatrix();

        Matrix4 modelViewProjectionMatrix = GraphicConveyor.rotateScaleTranslate(
                mesh.value.getX(), mesh.value.getY(), mesh.value.getZ(),
               mesh.value.getAngleX(), mesh.value.getAngleY(), mesh.value.getAngleZ(),
               mesh.value.getTx(), mesh.value.getTy(), mesh.value.getTz());
        modelViewProjectionMatrix.mul(viewMatrix);
        modelViewProjectionMatrix.mul(projectionMatrix);
        modelViewProjectionMatrix.transposition();

//        Matrix4 modelViewProjectionMatrix = GraphicConveyor.rotateScaleTranslate(
//                mesh.value.getX(), mesh.value.getY(), mesh.value.getZ(),
//                mesh.value.getAngleX(), mesh.value.getAngleY(), mesh.value.getAngleZ(),
//                mesh.value.getTx(), mesh.value.getTy(), mesh.value.getTz());


        final int nPolygons = mesh.polygons.polygonVertexIndices.size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = mesh.polygons.polygonVertexIndices.get(polygonInd).size();
            ArrayList<Point2f> resultPoints = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                Vector3 vertex = mesh.vertices.get(mesh.polygons.polygonVertexIndices.get(polygonInd).get(vertexInPolygonInd));
                Point2f resultPoint = vertexToPoint(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex), width, height);
                resultPoints.add(resultPoint);
            }

            for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                graphicsContext.strokeLine(
                        resultPoints.get(vertexInPolygonInd - 1).x,
                        resultPoints.get(vertexInPolygonInd - 1).y,
                        resultPoints.get(vertexInPolygonInd).x,
                        resultPoints.get(vertexInPolygonInd).y);
            }

            if (nVerticesInPolygon > 0)
                graphicsContext.strokeLine(
                        resultPoints.get(nVerticesInPolygon - 1).x,
                        resultPoints.get(nVerticesInPolygon - 1).y,
                        resultPoints.get(0).x,
                        resultPoints.get(0).y);
        }
    }
}