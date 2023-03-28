package geometries;

/**
 * This is an abstract class to represent all the geometries that have a radius
 */
public abstract class RadialGeometry implements Geometry {
    final double radius;

    /**
     * constructor to initialize radius
     * @param tempRadius
     */
    public RadialGeometry(double tempRadius){
        radius=tempRadius;
    }
}
