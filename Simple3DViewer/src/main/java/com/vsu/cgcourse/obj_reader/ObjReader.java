package com.vsu.cgcourse.obj_reader;

import com.vsu.cgcourse.math.Vector2;
import com.vsu.cgcourse.math.Vector3;
import com.vsu.cgcourse.model.Mesh;
import com.vsu.cgcourse.model.Polygons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ObjReader {
    private static final String OBJ_VERTEX_TOKEN = "v";
    private static final String OBJ_TEXTURE_TOKEN = "vt";
    private static final String OBJ_NORMAL_TOKEN = "vn";
    private static final String OBJ_FACE_TOKEN = "f";
    private int count;

    public ObjReader() {
    }

    public static Mesh read(String fileContent) {
        Mesh result = new Mesh();
        int lineInd = 0;
        Scanner scanner = new Scanner(fileContent);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            ArrayList<String> wordsInLine = new ArrayList(Arrays.asList(line.split("\\s+")));
            if (!wordsInLine.isEmpty()) {
                String token = (String) wordsInLine.get(0);
                wordsInLine.remove(0);
                ++lineInd;
                byte var8 = -1;
                switch (token.hashCode()) {
                    case 102:
                        if (token.equals("f")) {
                            var8 = 3;
                        }
                        break;
                    case 118:
                        if (token.equals("v")) {
                            var8 = 0;
                        }
                        break;
                    case 3768:
                        if (token.equals("vn")) {
                            var8 = 2;
                        }
                        break;
                    case 3774:
                        if (token.equals("vt")) {
                            var8 = 1;
                        }
                }

                switch (var8) {
                    case 0:
                        result.getVertices().add(parseVertex(wordsInLine, lineInd));
                        break;
                    case 1:
                        result.getTextureVertices().add(parseTextureVertex(wordsInLine, lineInd));
                        break;
                    case 2:
                        result.getNormals().add(parseNormal(wordsInLine, lineInd));
                        break;
                    case 3:
                        parseFace(wordsInLine, result.getPolygons(), lineInd);
                }
            }
        }
        scanner.close();
        result.recheckModel();
        return result;
    }

    protected static void parseFace(
            final ArrayList<String> wordsInLineWithoutToken,
            Polygons polygons,
            int lineInd) {
        ArrayList<Integer> onePolygonVertexIndices = new ArrayList<Integer>();
        ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<Integer>();
        ArrayList<Integer> onePolygonNormalIndices = new ArrayList<Integer>();

        for (String s : wordsInLineWithoutToken) {
            parseFaceWord(s, onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices, lineInd);
        }

        polygons.polygonVertexIndices.add(onePolygonVertexIndices);
        polygons.polygonTextureVertexIndices.add(onePolygonTextureVertexIndices);
        polygons.polygonNormalIndices.add(onePolygonNormalIndices);
    }

    protected static void parseFaceWord(
            String wordInLine,
            ArrayList<Integer> onePolygonVertexIndices,
            ArrayList<Integer> onePolygonTextureVertexIndices,
            ArrayList<Integer> onePolygonNormalIndices,
            int lineInd) {
        try {
            String[] wordIndices = wordInLine.split("/");
            switch (wordIndices.length) {
                case 1 -> onePolygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
                case 2 -> {
                    onePolygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
                    onePolygonTextureVertexIndices.add(Integer.parseInt(wordIndices[1]) - 1);
                }
                case 3 -> {
                    onePolygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
                    onePolygonNormalIndices.add(Integer.parseInt(wordIndices[2]) - 1);
                    if (!wordIndices[1].equals("")) {
                        onePolygonTextureVertexIndices.add(Integer.parseInt(wordIndices[1]) - 1);
                    }
                }
                default -> throw new ObjReaderException("Invalid element size.", lineInd);
            }

        } catch (NumberFormatException e) {
            throw new ObjReaderException("Failed to parse int value.", lineInd);

        } catch (IndexOutOfBoundsException e) {
            throw new ObjReaderException("Too few arguments.", lineInd);
        }
    }

    protected static Vector3 parseVertex(ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        try {
            if (wordsInLineWithoutToken.size() > 3) {
                throw new ObjReaderException("Too many vertex arguments.", lineInd);
            } else {
                return new Vector3(new float[]{Float.parseFloat((String) wordsInLineWithoutToken.get(0)), Float.parseFloat((String) wordsInLineWithoutToken.get(1)), Float.parseFloat((String) wordsInLineWithoutToken.get(2))});
            }

        } catch (IndexOutOfBoundsException e) {
            throw new ObjReaderException("Too few vertex arguments.", lineInd);
        }
    }

    protected static Vector2 parseTextureVertex(ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        try {
            if (wordsInLineWithoutToken.size() != 2 && Float.parseFloat((String) wordsInLineWithoutToken.get(2)) != 0.0F) {
                throw new ObjReaderException("Too many texture vertex arguments.", lineInd);
            } else {
                return new Vector2(new float[]{Float.parseFloat((String) wordsInLineWithoutToken.get(0)), Float.parseFloat((String) wordsInLineWithoutToken.get(1))});
            }

        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            System.out.println("Too few texture vertex arguments." + lineInd);
            // throw new ObjReaderException("Too few texture vertex arguments.", lineInd);
        }
        return null;
    }

    protected static Vector3 parseNormal(ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        try {
            if (wordsInLineWithoutToken.size() > 3) {
                throw new ObjReaderException("Too many normal arguments.", lineInd);
            } else {
                return new Vector3(new float[]{Float.parseFloat((String) wordsInLineWithoutToken.get(0)), Float.parseFloat((String) wordsInLineWithoutToken.get(1)), Float.parseFloat((String) wordsInLineWithoutToken.get(2))});
            }

        } catch (IndexOutOfBoundsException var4) {
            throw new ObjReaderException("Too few normal arguments.", lineInd);
        }
    }
}
