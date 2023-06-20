package geometries;
import static primitives.Util. *;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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

    /**
     * Find intersection points of the ray with the sphere
     *
     * @param ray the ray that intersects the sphere
     * @return List of intersection points, or null if there are no intersections
     */

    /**
     * Finds the intersections between a ray and a sphere.
     *
     * @param ray         The ray to intersect with the sphere.
     * @param maxDistance The maximum allowed distance between the ray origin and the intersection point.
     * @return A list of GeoPoint objects representing the intersections, or null if no intersection is found.
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point P0 = ray.getP0();
        Vector v = ray.getDir();

        if (P0.equals(center)) {
            if (alignZero(radius - maxDistance) > 0) {
                return null;
            }
            return List.of(new GeoPoint(this, center.add(v.scale(radius))));
        }

        Vector U = center.subtract(P0);

        double tm = alignZero(v.dotProduct(U));
        double d = alignZero(Math.sqrt(U.lengthSquared() - tm * tm));

        // no intersections: the ray direction is above the sphere
        if (d >= radius) {
            return null;
        }

        double th = alignZero(Math.sqrt(radius * radius - d * d));

        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        if (t1 <= 0 && t2 <= 0) {
            return null;
        }

        if (t1 > 0 && t2 > 0 && alignZero(t1 - maxDistance) <= 0 && alignZero(t2 - maxDistance) <= 0) {
            Point P1 = ray.getPoint(t1);
            Point P2 = ray.getPoint(t2);
            return List.of(new GeoPoint(this, P1), new GeoPoint(this, P2));
        }
        if (t1 > 0 && alignZero(t1 - maxDistance) <= 0) {
            Point P1 = ray.getPoint(t1);
            return List.of(new GeoPoint(this, P1));
        }
        if (t2 > 0 && alignZero(t2 - maxDistance) <= 0) {
            Point P2 = ray.getPoint(t2);
            return List.of(new GeoPoint(this, P2));
        }
        return null;
    }


}
