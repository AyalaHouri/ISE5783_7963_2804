package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Represents a cylinder in 3D space, defined by a ray (the axis of the cylinder), a radius, and a height.
 * @author Ayala Houri and Shani Zegal
 */
public class Cylinder extends Tube {
    /**
     * The height of the cylinder.
     */
    final private double height;

    /**
     * Constructs a new cylinder with the specified ray, radius, and height.
     * @param ray The ray that represents the axis of the cylinder.
     * @param radius The radius of the cylinder.
     * @param height The height of the cylinder.
     */
    public Cylinder(Ray ray, double radius, double height) {
        super(radius, ray);
        this.height = height;
    }

    /**
     * Returns the normal vector to the cylinder at the specified point.
     * @param p The point to get the normal vector at.
     * @return The normal vector to the cylinder at the specified point.
     */
    @Override
    public Vector getNormal(Point p) {
        Point o = axisRay.getP0();
        Vector v = axisRay.getDir();

        // projection of P-O on the ray:
        double t;
        try {
            t = alignZero(p.subtract(o).dotProduct(v));
        } catch (IllegalArgumentException e) { // P = O
            return v;
        }

        // if the point is at a base
        if (t == 0 || isZero(height - t)) // if it's close to 0, we'll get ZERO vector exception
            return v;

        o = o.add(v.scale(t));
        return p.subtract(o).normalize();
    }

    public List<Point> findIntersections(Ray ray){
        return null;
    }
}
