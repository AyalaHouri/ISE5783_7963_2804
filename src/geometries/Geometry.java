package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * An interface representing a geometry shape in 3D space.
 * @author Ayala Houri and Shani Zegal
 */
public abstract class Geometry extends Intersectable {

    protected Color emission = Color.BLACK;
    public Color getEmission() {
        return emission;
    }

    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Returns the normal vector to the geometry at the specified point.
     * @param point The point to get the normal vector at.
     * @return The normal vector to the geometry at the specified point.
     */
    public abstract Vector getNormal(Point point);
}