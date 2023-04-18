package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * The Sphere class represents a sphere in 3D space by its center point and radius.
 * It extends the RadialGeometry class.
 * @author Ayala Houri and Shani Zegal
 */
public class Sphere extends RadialGeometry{

    /** The center point of the sphere */
    final Point center;

    /**
     * Constructor to initialize a Sphere object with its center point and radius.
     *
     * @param tcenter The center point of the sphere.
     * @param tradius The radius of the sphere.
     */
    public Sphere(Point tcenter, double tradius){
        super(tradius);
        center = tcenter;
    }

    /**
     * Calculates the normal vector of the sphere at a given point on its surface.
     *
     * @param point A point on the surface of the sphere.
     * @return The normalized vector perpendicular to the surface of the sphere at the given point.
     */
    public Vector getNormal(Point point){
        return point.subtract(center).normalize();
    }
}
