package primitives;
import java.lang.Math;
import java.util.Objects;

/**
 * this class will serve all the geometry shapes
 * Ayala Houri && Shani Zegal
 */
public class Point {
     final Double3 xyz;

    /**
     * Constructor to initialize point based object with its double
     * @param x
     * @param y
     * @param z
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }
    Point(Double3 coordinate){
        xyz = new Double3(coordinate.d1,coordinate.d2,coordinate.d3);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point point)) return false;
        return xyz.equals(point.xyz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xyz);
    }

    @Override
    public String toString() {
        return "Point{" +xyz;
    }

    /**
     *calculate squared distance from the received point to the class point
     * @param point received point
     * @return the squared distance between the received point to the class point
     */
    public double distanceSquared(Point point){
        return ((point.xyz.d1- xyz.d1)*(point.xyz.d1- xyz.d1)+(point.xyz.d2- xyz.d2)*(point.xyz.d2- xyz.d2)+(point.xyz.d3- xyz.d3)*(point.xyz.d3- xyz.d3));
    }

    /**
     *calculate distance from the received point to the class point
     * @param point received point
     * @return the distance between the received point to the class point
     */
    public double distance(Point point){
        return Math.sqrt(distanceSquared(point));
    }

    /**
     * the function move the point to the vector direction
     * @param vector which we want to move the point by it
     * @return the moved point
     */
    public Point add(Vector vector){
        return new Point(xyz.add(vector.xyz));
    }

    /**
     * received a point and subtract it with the class point
     * @param point
     * @return the calculated vector
     */
    public Vector subtract(Point point){
        return (new Vector(xyz.subtract(point.xyz)));
    }
    }
