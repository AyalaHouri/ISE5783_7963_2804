package lighting;

import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;

/**
 * The PointLight class represents a point light source in a scene.
 * Point lights emit light from a specific position in all directions.
 *
 * <p>The intensity and position of the point light can be set using
 * the available constructors and setter methods.
 *
 * @since 1.0
 */
public class PointLight extends Light implements LightSource {
    private final Point position;
    private Double3 Kc = Double3.ONE;
    private Double3 Kl = Double3.ZERO;
    private Double3 Kq = Double3.ZERO;

    /**
     * Constructs a PointLight object with the specified intensity and position.
     *
     * @param intensity The intensity of the point light as a Color object.
     * @param position  The position of the point light as a Point object.
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
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
        Color Ic = getIntensity();
        double distance = point.distance(position);
        double distanceSquared = point.distanceSquared(position);

        Double3 factor = (Kc.add(Kl.scale(distance)).add(Kq.scale(distanceSquared)));

        return Ic.reduce(factor);
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
        return point.subtract(position).normalize();
    }

    /**
     * Retrieves the distance between the point light and the specified point.
     *
     * @param point The point to calculate the distance from.
     * @return The distance between the point light and the specified point.
     * @since 1.0
     */
    public double getDistance(Point point) {
        return point.distance(this.position);
    }

    public PointLight setKc(Double3 kc) {
        this.Kc = kc;
        return this;
    }

    public PointLight setKc(double kc) {
        this.Kc = new Double3(kc);
        return this;
    }


    public PointLight setKl(Double3 kl) {
        this.Kl = kl;
        return this;
    }

    public PointLight setKl(double kl) {
        this.Kl = new Double3(kl);
        return this;
    }


    public PointLight setKq(Double3 kq) {
        this.Kq = kq;
        return this;
    }

    public PointLight setKq(double kq) {
        this.Kq = new Double3(kq);
        return this;
    }
}