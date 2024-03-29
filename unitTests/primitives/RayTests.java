package primitives;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for primitives.Ray class
 * @author Ayala Houri and Shani Zegal
 */
class RayTests {

    /**
     * Tests the {@link Ray#findClosestPoint(List)} method.
     */
    @Test
    void findClosestPoint() {

        List<Point> point3DList = new LinkedList<>();

        Point p1 = new Point(1, 1, 1);
        Point p2 = new Point(2, 2, 2);
        Point p3 = new Point(3, 3, 3);

        point3DList.add(p1);
        point3DList.add(p2);
        point3DList.add(p3);

        Vector dirVector = new Vector(0, -0.5, 0);

        // ============ Equivalence Partitions Tests ==============
        //TC01: The closest point is in the middle of the list
        Ray ray1 = new Ray(new Point(2, 2.5, 2), dirVector);
        assertEquals(p2, ray1.findClosestPoint(point3DList), "The point in the middle!!");

        // =============== Boundary Values Tests ==================
        //TC10: The closest point is the first point in the list
        Ray ray2 = new Ray(new Point(1, 1.25, 1), dirVector);
        assertEquals(p1, ray2.findClosestPoint(point3DList), "The point is the first one!!");

        //TC11: The closest point is the last point in the list
        Ray ray3 = new Ray(new Point(3, 3.5, 3), dirVector);
        assertEquals(p3, ray3.findClosestPoint(point3DList), "The point is the last one!!");

        //TC12: The list is null
        point3DList.clear();
        assertNull(ray3.findClosestPoint(point3DList), "The list is empty!!");
    }
}