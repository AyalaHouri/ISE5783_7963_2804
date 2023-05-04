package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Represents a tube in 3D space, defined by a center axis ray and a radius.
 * @author Ayala Houri and Shani Zegal
 */
public class Tube extends RadialGeometry {

    /** The axis ray of the tube. */
    final Ray axisRay;

    /**
     * Constructs a new tube object with the specified radius and axis ray.
     *
     * @param radius the radius of the tube.
     * @param axisRay the axis ray of the tube.
     */
    public Tube(double radius, Ray axisRay) {
        super(radius);
        this.axisRay = axisRay;
    }

    /**
     * Computes the normal vector to the tube at the specified point.
     *
     * @param point the point to compute the normal vector at.
     * @return the normal vector to the tube at the specified point.
     * @throws IllegalArgumentException if the specified point is on the tube's axis.
     */
    @Override
    public Vector getNormal(Point point) {
        Point center = axisRay.getP0();
        Vector dir = axisRay.getDir();

        Vector normal = point.subtract(center);

        // Calculate the scalar projection of the normal vector onto the axis direction vector.
        double scalar = alignZero(dir.dotProduct(normal));

        // If the scalar projection is zero, the point is on the tube's surface and the normal is the
        // normalized normal vector.
        if (isZero(scalar)) {
            return normal.normalize();
        }

        // Calculate the point on the axis ray closest to the specified point.
        Point p = center.add(dir.scale(scalar));

        // If the specified point is on the axis ray, throw an exception since there is no defined
        // normal vector at that point.
        if (point.equals(p)) {
            throw new IllegalArgumentException("Point cannot be on tube axis");
        }

        // Compute the normal vector as the normalized difference between the specified point and the
        // closest point on the axis ray.
        normal = point.subtract(p).normalize();
        return normal;
    }

    public List<Point> findIntersections(Ray ray){
        return null;
    }
}
