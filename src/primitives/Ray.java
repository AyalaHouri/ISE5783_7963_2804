package primitives;

/**
 * The Ray class represents a ray in three-dimensional space with a starting point and a direction.
 * @author Ayala Houri and Shani Zegal
 */
public class Ray {
    private final Point p0; // The starting point of the ray
    private final Vector dir; // The direction of the ray

    /**
     * Constructs a new Ray object with the specified starting point and direction.
     *
     * @param p0 the starting point of the ray
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

}
