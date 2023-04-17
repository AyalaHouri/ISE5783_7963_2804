package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries.Sphere class
 * @author Ayala Houri and Shani Zegal
 */
public class SphereTests {

    /** Test method for {@link geometries.Sphere#getNormal(primitives.Point)}. */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad
        Point p1 = new Point(0,0,1);
        Point p2 = new Point(0,0,2);
        Sphere sphere = new Sphere(p1,5);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> sphere.getNormal(p2), "");
        // generate the test result
        Vector result = sphere.getNormal(p2);
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Sphere's normal is not a unit vector");

    }
}