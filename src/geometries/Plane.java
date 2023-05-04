package geometries;

import static primitives.Util.isZero;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * This class represents a plane in 3D space by a point and normal vector.
 * @author Ayala Houri and Shani Zegal
 */
public class Plane {
    Point p0;
    Vector normal;

    /**
     * Constructs a Plane object based on three points.
     *
     * @param point1 The first point on the plane.
     * @param point2 The second point on the plane.
     * @param point3 The third point on the plane.
     * @throws IllegalArgumentException If two or more points are the same, or if
     *                                  the two vectors in the plane have a linear
     *                                  dependency.
     */
    public Plane(Point point1, Point point2, Point point3) throws IllegalArgumentException {
        p0 = point1;
        if (point1.equals(point2) || point1.equals(point3) || point2.equals(point3))
            throw new IllegalArgumentException("Two or more points are the same");

        Vector v1 = point1.subtract(point2); // vector from point2 to point1
        Vector v2 = point1.subtract(point3); // vector from point3 to point1

        if (v1.normalize().equals(v2.normalize()))
            throw new IllegalArgumentException("The vectors have a linear dependency");

        Vector v3 = v1.crossProduct(v2); // cross product to get the perpendicular vector

        normal = v3.normalize();
    }

    /**
     * Constructs a Plane object based on a point and a vector.
     *
     * @param point  A point on the plane.
     * @param vector The normal vector of the plane.
     */
    public Plane(Point point, Vector vector) {
        p0 = point;
        if (!(isZero(vector.length() - 1d))) {
            normal = vector.normalize();
        } else
            this.normal = vector;
    }

    /**
     * Returns the normal vector of the plane.
     *
     * @return The normal vector of the plane.
     */
    public Vector getNormal() {
        return normal;
    }

    /**
     * Returns the normal vector of the plane at a given point (which is ignored).
     * This is equivalent to calling the {@link #getNormal()} method.
     *
     * @param point The point at which to get the normal vector (ignored).
     * @return The normal vector of the plane.
     */
    public Vector getNormal(Point point) {
        return getNormal();
    }

    List<Point> findIntsersections(Ray ray){
        return null;
    }
}
