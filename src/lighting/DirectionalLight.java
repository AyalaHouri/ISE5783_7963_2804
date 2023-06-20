package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The DirectionalLight class represents a directional light source in a scene.
 * Directional lights emit light from a specific direction and have an infinite distance.
 *
 * <p>The intensity and direction of the directional light can be set using
 * the available constructors.
 *
 * @since 1.0
 */
public class DirectionalLight extends Light implements LightSource {

    private final Vector direction;

    /**
     * Constructs a DirectionalLight object with the specified intensity and direction.
     *
     * @param intensity The intensity of the directional light as a Color object.
     * @param direction The direction of the directional light as a Vector object.
     */
    protected DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    /**
     * Retrieves the intensity of the light at the specified point.
     *
     * @param point The point at which to evaluate the light intensity.
     * @return The intensity of the light at the specified point as a Color object.
     * @since 1.0
     */
    @Override
    public Color getIntensity(Point point) {
        return getIntensity();
    }

    /**
     * Retrieves the direction of illumination at the specified point.
     *
     * @param point The point at which to evaluate the direction of illumination.
     * @return The direction of illumination at the specified point as a Vector object.
     * @since 1.0
     */
    @Override
    public Vector getL(Point point) {
        return direction;
    }

    /**
     * Retrieves the distance between the directional light and the specified point.
     *
     * @param point The point to calculate the distance from.
     * @return The distance between the directional light and the specified point,
     *         which is always positive infinity for a directional light.
     * @since 1.0
     */
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}
