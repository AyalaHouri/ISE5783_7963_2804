package primitives;

public class Ray {
    private final Point p0;
    private final Vector dir;

    /**
     * Constructor to initialize Double3 based object with its two parameters
     * @param p0
     * @param dir
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    /**
     * getter function
     * @return p0
     */
    public Point getP0() {
        return p0;
    }

    /**
     * getter function
     * @return dir
     */

    public Vector getDir() {
        return dir;
    }
}
