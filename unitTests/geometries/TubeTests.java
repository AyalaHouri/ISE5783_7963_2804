package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Tube class
 * @author Ayala Houri and Shani Zegal
 */
public class TubeTests {

    /** Test method for {@link geometries.Tube#getNormal(primitives.Point)}. */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Point p1 = new Point(0,0,1);
        Point p2 = new Point(0,0,2);
        Tube tube = new Tube(5, new Ray(p1,new Vector(1,2,3)));
        // ensure there are no exceptions
        assertDoesNotThrow(() -> tube.getNormal(p2), "");
        // generate the test result
        Vector result = tube.getNormal(p2);
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Tube's normal is not a unit vector");

        // =============== Boundary Values Tests ==================
        // TC11: Addition of point to axis ray gives 90-degree angle
//        Ray axisRay = new Ray(new Point(0,0,0),new Vector(0,0,1));
//        Tube t = new Tube(1, axisRay);
//        Point p = new Point(1,0,0);
//        assertEquals(p, t.getNormal(p), "Point is \"across\" from axis ray");
    }
}