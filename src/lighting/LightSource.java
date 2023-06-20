package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The LightSource interface represents a light source in a scene.
 * Light sources provide information about their intensity and the direction
 * of illumination at a given point in the scene.
 *
 * <p>Classes implementing this interface should provide implementations for
 * the {@link #getIntensity(Point)} and {@link #getL(Point)} methods.
 *
 * @since 1.0
 */
public interface LightSource {

    /**
     * Retrieves the intensity of the light at the specified point.
     *
     * @param p The point at which to evaluate the light intensity.
     * @return The intensity of the light at the specified point as a Color object.
     * @since 1.0
     */
    public Color getIntensity(Point p);

    /**
     * Retrieves the direction of illumination at the specified point.
     *
     * @param p The point at which to evaluate the direction of illumination.
     * @return The direction of illumination at the specified point as a Vector object.
     * @since 1.0
     */
    public Vector getL(Point p);
}
