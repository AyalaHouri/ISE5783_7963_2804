package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
    /**
     * Test method for {@link geometries.Triangle#findIntersections(Ray)}
     */
    @Test
    void findIntersections() {
        Triangle t = new Triangle(new Point(0, 1, 0), new Point(0, 5, 0), new Point(0, 3, 5));

        // ============ Equivalence Partitions Tests ==============
        // TC01: The intersection point is in the triangle
        assertEquals(List.of(new Point(0, 3, 1)),
                t.findIntersections(new Ray(new Point(1, 3, 0), new Vector(-1, 0, 1))),
                "The point supposed to be in the triangle");

        // TC02: The intersection point is outside the triangle, against edge
        assertNull(t.findIntersections(new Ray(new Point(1, 0, 0), new Vector(-1, 0, 1))),
                "The point supposed to be outside the triangle, against edge");

        // TC03: The intersection point is outside the triangle, against vertex
        assertNull(t.findIntersections(new Ray(new Point(1, 0, 0), new Vector(-1, 0.1, -0.1))),
                "The point supposed to be outside the triangle, against vertex");

        // =============== Boundary Values Tests ==================
        // TC10: The point is on edge
        assertNull(t.findIntersections(new Ray(new Point(1, 3, 0), new Vector(-1, 0, 0))),
                "The point supposed to be on edge");

        // TC11: The point is in vertex
        assertNull(t.findIntersections(new Ray(new Point(1, 1, 0), new Vector(-1, 0, 0))),
                "The point supposed to be in vertex");

        // TC12: The point is on edge's continuation
        assertNull(t.findIntersections(new Ray(new Point(1, 0, 0), new Vector(-1, 0.1, 0))),
                "The point supposed to be on edge's continuation");
    }


}