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
    @Override
    public List<Point> findIntersections(Ray ray){
        return null;
    }
    /**
     * Helper method to find the intersections between a ray and the geometry of the tube.
     *
     * @param ray          The ray for intersection tests
     * @param maxDistance  The maximum distance of valid intersections
     * @return A list of GeoPoint objects representing the intersections between the ray and the tube,
     *         or null if there are no valid intersections
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Vector vAxis = axisRay.getDir();
        Vector v = ray.getDir();
        Point p0 = ray.getP0();

        // At^2+Bt+C=0
        double a = 0;
        double b = 0;
        double c = 0;

        double vVa = alignZero(v.dotProduct(vAxis));
        Vector vVaVa;
        Vector vMinusVVaVa;
        if (vVa == 0) // the ray is orthogonal to the axis
            vMinusVVaVa = v;
        else {
            vVaVa = vAxis.scale(vVa);
            try {
                vMinusVVaVa = v.subtract(vVaVa);
            } catch (IllegalArgumentException e1) { // the rays is parallel to axis
                return null;
            }
        }
        // A = (v-(v*va)*va)^2
        a = vMinusVVaVa.lengthSquared();

        Vector deltaP = null;
        try {
            deltaP = p0.subtract(axisRay.getP0());
        } catch (IllegalArgumentException e1) { // the ray begins at axis P0
            if (vVa == 0 && alignZero(radius - maxDistance) <= 0) { // the ray is orthogonal to Axis
                return List.of(new GeoPoint(this, ray.getPoint(radius)));
            }
            double t = alignZero(Math.sqrt(radius * radius / vMinusVVaVa.lengthSquared()));
            return alignZero(t - maxDistance) >= 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t)));
        }

        double dPVAxis = alignZero(deltaP.dotProduct(vAxis));
        Vector dPVaVa;
        Vector dPMinusdPVaVa;
        if (dPVAxis == 0)
            dPMinusdPVaVa = deltaP;
        else {
            dPVaVa = vAxis.scale(dPVAxis);
            try {
                dPMinusdPVaVa = deltaP.subtract(dPVaVa);
            } catch (IllegalArgumentException e1) {
                double t = alignZero(Math.sqrt(radius * radius / a));
                return alignZero(t - maxDistance) >= 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t)));
            }
        }

        // B = 2(v - (v*va)*va) * (dp - (dp*va)*va))
        b = 2 * alignZero(vMinusVVaVa.dotProduct(dPMinusdPVaVa));
        c = dPMinusdPVaVa.lengthSquared() - radius * radius;

        // A*t^2 + B*t + C = 0 - lets resolve it
        double discr = alignZero(b * b - 4 * a * c);
        if (discr <= 0) return null; // the ray is outside or tangent to the tube

        double doubleA = 2 * a;
        double tm = alignZero(-b / doubleA);
        double th = Math.sqrt(discr) / doubleA;
        if (isZero(th)) return null; // the ray is tangent to the tube

        double t1 = alignZero(tm + th);
        if (t1 <= 0) // t1 is behind the head
            return null; // since th must be positive (sqrt), t2 must be non-positive as t1

        double t2 = alignZero(tm - th);

        // if both t1 and t2 are positive
        if (t2 > 0 && alignZero(t2 - maxDistance) < 0)
            return List.of(new GeoPoint(this, ray.getPoint(t1)),new GeoPoint(this, ray.getPoint(t2)));
        else if (alignZero(t1 - maxDistance) < 0)// t2 is behind the head
            return List.of(new GeoPoint(this, ray.getPoint(t1)));
        return null;
    }
}
