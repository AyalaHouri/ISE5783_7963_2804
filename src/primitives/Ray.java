package primitives;
import geometries.Intersectable.GeoPoint;
import java.util.List;
import java.util.Objects;

/**
 * The Ray class represents a ray in three-dimensional space with a starting point and a direction.
 * It provides methods for accessing the starting point, direction, and calculating points along the ray.
 * The class also includes a method for finding the closest point from a list of points to the ray.
 */
public class Ray {
    private final Point p0; // The starting point of the ray
    private final Vector dir; // The direction of the ray

    /**
     * Constructs a new Ray object with the specified starting point and direction.
     *
     * @param p0  the starting point of the ray
     * @param dir the direction of the ray
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize(); // Normalizes the direction vector
    }

    /**
     * Returns the starting point of the ray.
     *
     * @return the starting point of the ray
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Returns the direction of the ray.
     *
     * @return the direction of the ray
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * Returns the point on this ray at a given distance from its starting point.
     *
     * @param t the distance along the ray to calculate the point at
     * @return the point on the ray at the given distance
     */
    public Point getPoint(double t) {
        // calculate the point on the ray using the parameter t
        Point p = this.p0.add(this.dir.scale(t));

        // return the calculated point
        return p;
    }


    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * Returns the GeoPoint from the given list that is closest to the reference point of this object.
     *
     * @param geoPointList The list of GeoPoint objects to search for the closest point
     * @return The GeoPoint object with the closest point to the reference point,
     *         or null if the list is empty
     */

    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPointList) {
        if (geoPointList.isEmpty()) {
            return null;
        }

        double minDistance = geoPointList.get(0).point.distance(p0);
        int indexOfElementWithClosestPoint = 0;
        int iterationIndex = 0;

        for (GeoPoint item : geoPointList) {
            if (item.point.distance(p0) < minDistance) {
                minDistance = item.point.distance(p0);
                indexOfElementWithClosestPoint = iterationIndex;
            }
            iterationIndex++;
        }

        return geoPointList.get(indexOfElementWithClosestPoint);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }
    /**
     * find the closest point to the ray
     *
     * @param geoPointList list of geoPoints
     *                     (they all the same geometry but different intersection points, if there have more then one intersection point)
     * @return the geometry with his closest intersection point to the ray (GeoPoint)
     */
    public GeoPoint getClosestGeoPoint(List<GeoPoint> geoPointList) {

        if (geoPointList.isEmpty()) return null;

        double minDistance = geoPointList.get(0).point.distance(p0);

        //indexes to find the element with the closest point.
        int indexOfElementWithClosestPoint = 0;
        int iterationIndex = 0;

        for (GeoPoint item : geoPointList) {
            if (item.point.distance(p0) < minDistance) {
                minDistance = item.point.distance(p0);
                indexOfElementWithClosestPoint = iterationIndex;
            }
            iterationIndex++;
        }

        return geoPointList.get(indexOfElementWithClosestPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p0, dir);
    }

}
