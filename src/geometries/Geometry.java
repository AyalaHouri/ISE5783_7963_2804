package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * an interface class,this class is the source and the father of all geometries shapes
 */
public interface Geometry {
    /**
     * received a point in the plane and return the normal vector
     * @param point
     * @return the normal vector
     */
    public abstract Vector getNormal(Point point);

}
