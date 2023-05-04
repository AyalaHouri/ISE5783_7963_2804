package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * The Triangle class represents a triangle in 3D space by its three points.
 * It extends the Polygon class.
 * @author Ayala Houri and Shani Zegal
 */
public class Triangle extends Polygon {

    /**
     * Constructor to initialize a Triangle object with its three points.
     *
     * @param tpoint1 The first point of the triangle.
     * @param tpoint2 The second point of the triangle.
     * @param tpoint3 The third point of the triangle.
     */
    public Triangle(Point tpoint1, Point tpoint2, Point tpoint3){
        super(tpoint1, tpoint2, tpoint3);
    }

    /**
     * Calculates the normal vector of the triangle at a given point on its surface.
     *
     * @param point A point on the surface of the triangle.
     * @return The normalized vector perpendicular to the surface of the triangle at the given point.
     */
    @Override
    public Vector getNormal(Point point) {
        return super.getNormal(point);
    }

    public List<Point> findIntersections(Ray ray){
        return null;
    }
}
