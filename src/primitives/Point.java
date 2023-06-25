package primitives;

import java.lang.Math;
import java.util.Objects;

/**
 * Represents a point in a 3D space.
 * @author Ayala Houri and Shani Zegal
 */
public class Point {
    public static final Point ZERO = new Point(Double3.ZERO);
    final Double3 xyz;

    /**
     * Constructs a new Point object with the given coordinates.
     *
     * @param x The x coordinate of the point.
     * @param y The y coordinate of the point.
     * @param z The z coordinate of the point.
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    /**
     * Constructs a new Point object with the given coordinate.
     *
     * @param coordinate The coordinate of the point as a Double3 object.
     */
    Point(Double3 coordinate){
        xyz = new Double3(coordinate.d1, coordinate.d2, coordinate.d3);
    }

    /**
     * Checks whether this Point object is equal to the specified object.
     *
     * @param o The object to compare to this Point object.
     * @return True if the object is equal to this Point object, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return xyz.equals(point.xyz);
    }


    /**
     * Returns a hash code for this Point object.
     *
     * @return A hash code value for this Point object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(xyz);
    }

    /**
     * Returns a string representation of this Point object.
     *
     * @return A string representation of this Point object.
     */
    @Override
    public String toString() {
        return "Point{" + xyz;
    }

    /**
     * Calculates the squared distance between this point and the specified point.
     *
     * @param point The point to calculate the distance to.
     * @return The squared distance between this point and the specified point.
     */
    public double distanceSquared(Point point){
        return ((point.xyz.d1- xyz.d1)*(point.xyz.d1- xyz.d1)+(point.xyz.d2- xyz.d2)*(point.xyz.d2- xyz.d2)+(point.xyz.d3- xyz.d3)*(point.xyz.d3- xyz.d3));
    }

    /**
     * Calculates the distance between this point and the specified point.
     *
     * @param point The point to calculate the distance to.
     * @return The distance between this point and the specified point.
     */
    public double distance(Point point){
        return Math.sqrt(distanceSquared(point));
    }

    /**
     * Moves this point in the direction of the specified vector.
     *
     * @param vector The vector indicating the direction and distance to move the point.
     * @return A new Point object representing the moved point.
     */
    public Point add(Vector vector){
        return new Point(xyz.add(vector.xyz));
    }

    /**
     * Calculates the vector between this point and the specified point.
     *
     * @param point The point to calculate the vector to.
     * @return A new Vector object representing the vector between this point and the specified point.
     */
    public Vector subtract(Point point){
        return (new Vector(xyz.subtract(point.xyz)));
    }

    public double getX() {
        return xyz.getX();
    }
    public double getY() {
        return xyz.getY();
    }
    public double getZ() {
        return xyz.getZ();
    }
}
