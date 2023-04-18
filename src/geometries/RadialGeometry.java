package geometries;

/**
 * This is an abstract class to represent all the geometries that have a radius.
 * It implements the Geometry interface.
 * @author Ayala Houri and Shani Zegal
 */
public abstract class RadialGeometry implements Geometry {

    /** The radius of the radial geometry */
    final double radius;

    /**
     * Constructor to initialize the radius of the radial geometry.
     *
     * @param tempRadius The radius of the geometry.
     */
    public RadialGeometry(double tempRadius) {
        radius = tempRadius;
    }
}
