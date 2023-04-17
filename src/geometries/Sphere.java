package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * the class represents a sphere in 3D world by a center point and a radius
 */
public class Sphere extends RadialGeometry{
    final Point center;

    /**
     * Constructor to initialize Sphere based object with its point and radius
     * @param point
     * @param tradius
     */
    public Sphere(Point tcenter,double tradius){
        super(tradius);
        center = tcenter;
    }

    /**
     * the function calculates the normal vector of the sphere by the received point
     * @param point
     * @return the normalized vector
     */
    public Vector getNormal(Point point){
        return point.subtract(center).normalize();
    }
}
