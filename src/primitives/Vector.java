package primitives;

/**
 * A class representing a vector in 3D space.
 * @author Ayala Houri and Shani Zegal
 */
public class Vector extends Point{

    /**
     * Constructor to build a Vector with three Double coordinates.
     * @param x The first coordinate.
     * @param y The second coordinate.
     * @param z The third coordinate.
     * @throws IllegalArgumentException if the coordinates are all zero.
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if(xyz.equals(Double3.ZERO)){
            throw new IllegalArgumentException("Zero vector is not allowed");
        }
    }

    /**
     * Constructor to build a Vector with a Double3 coordinate.
     * @param coordinate The coordinates.
     */
    Vector(Double3 coordinate){
        this(coordinate.d1, coordinate.d2, coordinate.d3);
    }

    /**
     * Checks if the received parameter is equal to the class vector.
     * @param o The object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector vector)) return false;
        return xyz.equals(vector.xyz);
    }

    /**
     * Returns a string representation of the vector.
     * @return A string representation of the vector.
     */
    @Override
    public String toString() {
        return "Vector{" +
                "xyz=" + xyz+
                '}'+super.toString();
    }

    /**
     * Adds a vector to the class vector.
     * @param vector The vector to add.
     * @return The resulting vector.
     */
    public Vector add(Vector vector){
        return new Vector(xyz.add(vector.xyz));
    }

    /**
     * Scales the vector by a scalar.
     * @param scalar The scalar to multiply the vector by.
     * @return The scaled vector.
     */
    public Vector scale(double scalar){
        return new Vector(xyz.scale(scalar));
    }

    /**
     * Calculates the cross product between the class vector and another vector.
     * @param vector The other vector.
     * @return The resulting vector.
     */
    public Vector crossProduct(Vector vector){
        return new Vector(xyz.d2 * vector.xyz.d3 - xyz.d3 * vector.xyz.d2,
                -(xyz.d1 * vector.xyz.d3 - xyz.d3 * vector.xyz.d1),
                xyz.d1 * vector.xyz.d2 - xyz.d2 * vector.xyz.d1 );
    }

    /**
     * Calculates the squared length of the vector.
     * @return The squared length of the vector.
     */
    public double lengthSquared(){
        return (xyz.d1 * xyz.d1 + xyz.d2 * xyz.d2 + xyz.d3 * xyz.d3);
    }

    /**
     * Calculates the length of the vector.
     * @return The length of the vector.
     */
    public double length(){
        return Math.sqrt(lengthSquared());
    }

    /**
     * Calculates the normalized vector.
     * @return The normalized vector.
     */
    public Vector normalize(){
        return new Vector(xyz.reduce(length()));
    }

    /**
     * Calculates the dot product between the class vector and another vector.
     * @param vector The other vector.
     * @return The dot product of the two vectors.
     */
    public double dotProduct(Vector vector) {
        return (vector.xyz.d1 * xyz.d1 + vector.xyz.d2 * xyz.d2 + vector.xyz.d3 * xyz.d3);
    }
}
