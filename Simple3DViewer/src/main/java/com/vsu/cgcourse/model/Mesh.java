package com.vsu.cgcourse.model;

import com.vsu.cgcourse.math.Vector2;
import com.vsu.cgcourse.math.Vector3;
import com.vsu.cgcourse.obj_reader.ObjReaderException;

import java.util.ArrayList;
import java.util.Iterator;

public class Mesh {
    public ArrayList<Vector3> vertices = new ArrayList<>();
    public ArrayList<Vector2> textureVertices = new ArrayList<>();
    public ArrayList<Vector3> normals = new ArrayList<>();
    public Polygons polygons = new Polygons();

    public String name;
    public Value value = new Value();

    public Mesh() {}

    public ArrayList<Vector3> getVertices() {
        return this.vertices;
    }

    public ArrayList<Vector2> getTextureVertices() {
        return this.textureVertices;
    }

    public ArrayList<Vector3> getNormals() {
        return this.normals;
    }

    public Polygons getPolygons() {
        return this.polygons;
    }

    public void setPolygons(Polygons polygons) {
        this.polygons = polygons;
    }
    public void recheckModel() {
        for(int index = 0; index < this.polygons.getPolygonNormalIndices().size(); ++index) {
            this.polygons.recheckOnCorrect(index);
            this.recheckOnRightIndices(index);
        }
    }

    private void recheckOnRightIndices(int index) {
        Iterator var2 = ((ArrayList)this.polygons.getPolygonVertexIndices().get(index)).iterator();

        Integer vn;
        do {
            if (!var2.hasNext()) {
                var2 = ((ArrayList)this.polygons.getPolygonTextureVertexIndices().get(index)).iterator();

                do {
                    if (!var2.hasNext()) {
                        var2 = ((ArrayList)this.polygons.getPolygonNormalIndices().get(index)).iterator();

                        do {
                            if (!var2.hasNext()) {
                                return;
                            }

                            vn = (Integer)var2.next();
                        } while(vn <= this.normals.size() - 1);

                        vn = vn + 1;
                        throw new ObjReaderException("Error of getting wrong NormalIndex: " + vn + ". Maximum: " + this.normals.size());
                    }

                    vn = (Integer)var2.next();
                } while(vn <= this.textureVertices.size() - 1);

                vn = vn + 1;
                throw new ObjReaderException("Error of getting wrong TextureIndex: " + vn + ". Maximum: " + this.textureVertices.size());
            }

            vn = (Integer)var2.next();
        } while(vn <= this.vertices.size() - 1);

        vn = vn + 1;
        throw new ObjReaderException("Error of getting wrong VertexIndex: " + vn + ". Maximum: " + this.vertices.size());
    }
}

