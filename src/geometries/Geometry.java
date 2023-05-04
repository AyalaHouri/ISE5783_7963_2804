package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * An interface representing a geometry shape in 3D space.
 * @author Ayala Houri and Shani Zegal
 */
public interface Geometry extends Intersectable {
    /**
     * Returns the normal vector to the geometry at the specified point.
     * @param point The point to get the normal vector at.
     * @return The normal vector to the geometry at the specified point.
     */
    public abstract Vector getNormal(Point point);
}