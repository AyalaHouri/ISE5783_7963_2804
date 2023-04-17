package primitives;

public class Vector extends Point{
    /**
     * Constructor to build Vector with Double3 coordinate
     * @param x-the first coordinate
     * @param y-the second coordinate
     * @param z-the third coordinate
     */
    public Vector(double x, double y, double z) {
        super(x,y,z);
        if(xyz.equals(Double3.ZERO)){
            throw new IllegalArgumentException("Zero vector is not allowed");
        }

    }

    /**
     * Constructor to build Vector with Double3 coordinate
     * @param coordinate-the coordinates
     */
     Vector(Double3 coordinate){
        this(coordinate.d1,coordinate.d2,coordinate.d3);
    }

    /**
     * a grnerate function, checks if the received parameter is equales to the class vector
     * @param o-parameter
     * @return boolean value
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector vector)) return false;
        return xyz.equals(vector.xyz);
    }
    @Override
    public String toString() {
        return "Vector{" +
                "xyz=" + xyz+
        '}'+super.toString();
    }

    /**
     *recived a vector and add it to the class vector ((a,b,c)+(e,f,g)=(a+e,b+f,c+g))
     * @param vector which we want to add to our vector
     * @return the addable vector
     */
    public Vector add(Vector vector){
       return new Vector(xyz.add(vector.xyz));
    }

    /**
     * the function doubled the vector with the received number
     * @param scalar
     * @return the doubled vector
     */
    public Vector scale(double scalar){
        return new Vector(xyz.scale(scalar));
    }

    /**
     * received a vector and doubled (it's a cross product doubling) it with the class vector
     * @param vector
     * @return the calculated vector
     */
    public Vector crossProduct(Vector vector){
        return new Vector(xyz.d2*vector.xyz.d3-xyz.d3*vector.xyz.d2,
                -(xyz.d1*vector.xyz.d3-xyz.d3*vector.xyz.d1),
                xyz.d1*vector.xyz.d2-xyz.d2*vector.xyz.d1 );
    }

    /**
     * calculate the Squared length of the class vector
     * @return the Squared length
     */
    public double lengthSquared(){
        return (xyz.d1*xyz.d1+xyz.d2*xyz.d2+xyz.d3*xyz.d3);
    }

    /**
     * calculate the length of the class vector
     * @return the length
     */
    public double length(){
        return Math.sqrt(lengthSquared());
    }

    /**
     * calculate the normal vector of the vector
     * @return the normalized vector
     */
    public Vector normalize(){
         return new Vector(xyz.reduce(length()));
    }

    /**
     *the function calculate the dot product doubling between the received vector and the class vector
     * @param vector
     * @return
     */
    public double dotProduct(Vector vector) {
        return (vector.xyz.d1 * xyz.d1 + vector.xyz.d2 * xyz.d2 + vector.xyz.d3 * xyz.d3);
    }
}
