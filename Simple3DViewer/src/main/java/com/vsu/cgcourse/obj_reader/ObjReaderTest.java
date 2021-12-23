package com.vsu.cgcourse.obj_reader;

import com.vsu.cgcourse.math.Vector3;
import org.junit.Assert;
import org.junit.Test;

import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.Arrays;

 public class ObjReaderTest {
    @Test
    public void testParseVertex01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3 result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3 expectedResult = new Vector3(1.01f, 1.02f, 1.03f);
        Assert.assertEquals(result, expectedResult);
    }

    @Test
    public void testParseVertex03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("ab", "o", "ba"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseVertex04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few vertex arguments.";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }
}