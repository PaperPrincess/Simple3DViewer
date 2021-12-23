package com.vsu.cgcourse.model;

import com.vsu.cgcourse.obj_reader.ObjReaderException;

import java.util.ArrayList;

public class Polygons {
    public ArrayList<ArrayList<Integer>> polygonVertexIndices = new ArrayList();
    public ArrayList<ArrayList<Integer>> polygonTextureVertexIndices = new ArrayList();
    public ArrayList<ArrayList<Integer>> polygonNormalIndices = new ArrayList();

    public Polygons() {
    }

    public ArrayList<ArrayList<Integer>> getPolygonVertexIndices() {
        return this.polygonVertexIndices;
    }

    public ArrayList<ArrayList<Integer>> getPolygonTextureVertexIndices() {
        return this.polygonTextureVertexIndices;
    }

    public ArrayList<ArrayList<Integer>> getPolygonNormalIndices() {
        return this.polygonNormalIndices;
    }

    public void recheckOnCorrect(int index) {
        this.checkOnSize(index);
        this.checkOnCorrectPolygonFill(index);
    }

    private void checkOnSize(int index) {
        if (!((ArrayList)this.polygonVertexIndices.get(index)).isEmpty() && !((ArrayList)this.polygonTextureVertexIndices.get(index)).isEmpty() && ((ArrayList)this.polygonVertexIndices.get(index)).size() != ((ArrayList)this.polygonTextureVertexIndices.get(index)).size()) {
            throw new ObjReaderException("Different size between VertexIndices and TextureVertexIndices");
        } else if (!((ArrayList)this.polygonVertexIndices.get(index)).isEmpty() && !((ArrayList)this.polygonNormalIndices.get(index)).isEmpty() && ((ArrayList)this.polygonVertexIndices.get(index)).size() != ((ArrayList)this.polygonNormalIndices.get(index)).size()) {
            throw new ObjReaderException("Different size between VertexIndices and NormalIndices");
        }
    }

    private void checkOnCorrectPolygonFill(int index) {
        if (index != 0) {
            if (((ArrayList)this.polygonTextureVertexIndices.get(0)).isEmpty() && !((ArrayList)this.polygonTextureVertexIndices.get(index)).isEmpty()) {
                throw new ObjReaderException("Unexpected TextureVertexIndices");
            }

            if (!((ArrayList)this.polygonTextureVertexIndices.get(0)).isEmpty() && ((ArrayList)this.polygonTextureVertexIndices.get(index)).isEmpty()) {
                throw new ObjReaderException("Can't find TextureVertexIndices");
            }

            if (((ArrayList)this.polygonNormalIndices.get(0)).isEmpty() && !((ArrayList)this.polygonNormalIndices.get(index)).isEmpty()) {
                throw new ObjReaderException("Unexpected NormalIndices");
            }

            if (!((ArrayList)this.polygonNormalIndices.get(0)).isEmpty() && ((ArrayList)this.polygonNormalIndices.get(index)).isEmpty()) {
                throw new ObjReaderException("Can't find NormalIndices");
            }
        }

    }
}
