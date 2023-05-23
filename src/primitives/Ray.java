package primitives;

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

    /**
     * Finds the closest point from a list of points to the ray.
     *
     * @param point3DList the list of points to find the closest point from
     * @return the closest point to the ray, or null if the list is empty
     */
    public Point findClosestPoint(List<Point> point3DList) {
        if (point3DList.isEmpty())
            return null;

        double minDistance = point3DList.get(0).distance(getP0());
        Point closest = point3DList.get(0);

        for (Point item : point3DList) {
            if (item.distance(getP0()) < minDistance) {
                closest = item;
                minDistance = item.distance(getP0());
            }
        }

        return closest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p0, dir);
    }
}
