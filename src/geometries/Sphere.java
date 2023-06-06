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
    @Override
    public List<Point> findIntersections(Ray ray){

        // if p0 on the center, calculate with line parametric representation
        // the direction vector normalized
        if (center.equals(ray.getP0())) {
            //Point newPoint = ray.getP0().add(ray.getDir().scale(radius));
            Point newPoint = ray.getPoint(radius); //using the new method
            return List.of(newPoint);
        }

        // calculate the vector from the ray's starting point to the center of the sphere
        Vector u = center.subtract(ray.getP0());

        // calculate the distance between the ray's starting point and the closest point to the center of the sphere
        double Tm = ray.getDir().dotProduct(u);

        // calculate the distance between the closest point to the center of the sphere and the actual center of the sphere
        double d = Math.sqrt(u.lengthSquared() - Tm * Tm);

        // if the distance between the closest point to the center of the sphere and the actual center of the sphere is greater than the radius,
        // there are no intersections
        if(d >= radius)
            return null;

        // calculate the height of the triangle formed by the radius of the sphere and the distance between the closest point to the center of the sphere and the actual center of the sphere
        double Th = alignZero(radius * radius - d * d);

        // if the height of the triangle is less than or equal to zero, the ray is tangent to the sphere, so there is only one intersection point
        if(Th <= 0)
            return null;

        // calculate the height of the triangle formed by the radius of the sphere and the distance between the closest point to the center of the sphere and the actual center of the sphere
        Th = Math.sqrt(Th);

        // calculate the two possible intersection points
        double t1 = alignZero(Tm + Th);
        double t2 = alignZero(Tm - Th);

        // if both intersection points are in front of the camera, return both intersection points
        if (t1 > 0 && t2 > 0) {
            Point p1 = ray.getPoint(t1);
            Point p2 = ray.getPoint(t2);
            return List.of(p1,p2);
        }

        // if only one intersection point is in front of the camera, return that intersection point
        if (t1 > 0) {
            return List.of(ray.getPoint(t1));
        }

        if (t2 > 0) {
            return List.of(ray.getPoint(t2));
        }

        // if there are no intersection points, return null
        return null;
    }
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
