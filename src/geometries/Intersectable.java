package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

/**

 The Intersectable interface represents a geometric object that can be intersected by a ray in 3D space.
 Implementing classes should provide the implementation of the findIntersections method to find the intersection points
 between the intersectable object and the given ray.
 */

/**
 * @author Ayala Houri and Shani Zegal
 */
public abstract class Intersectable {

    /**
     Finds the intersection points between this intersectable object and the given ray.
     @param ray the ray to intersect this object with
     @return a list of Point objects representing the intersection points, or an empty list if no intersections exist
     */
    /**
     * find intersection points from specific Ray
     * @param ray the ray crossing the geometric object
     * @return immutable List of intersection points
     */
    public List<Point> findIntersections(Ray ray) {
        List<GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream()
                .map(gp -> gp.point)
                . toList();

    }
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;
        public GeoPoint(Geometry geometry, Point point){
            this.geometry = geometry;
            this.point=point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint geoPoint)) return false;
            return Objects.equals(geometry, geoPoint.geometry) && Objects.equals(point, geoPoint.point);
        }

        @Override
        public int hashCode() {
            return Objects.hash(geometry, point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
        
    }

}
