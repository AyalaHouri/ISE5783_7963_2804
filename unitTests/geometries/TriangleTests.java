package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries.Triangle class
 * @author Ayala Houri and Shani Zegal
 */
class TriangleTests {

    /** Test method for {@link geometries.Triangle#getNormal(primitives.Point)}. */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Point p1 = new Point(0, 0, 1);
        Point p2 = new Point(1, 0, 0);
        Point p3 = new Point(0, 1, 0);

        Triangle triangle = new Triangle(p1, p2, p3 );
        // ensure there are no exceptions
        assertDoesNotThrow(() -> triangle.getNormal(p1), "");
        // generate the test result
        Vector result = triangle.getNormal(p1);
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Triangle's normal is not a unit vector");

        Point[] pts = {p1,p2,p3};
        for (int i = 0; i < 2; ++i)
            assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : i - 1]))),
                    "Triangle's normal is not orthogonal to one of the edges");
    }
}