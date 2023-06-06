package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
/**
 * implementation of RayTracerBase class
 */
public class RayTracerBasic extends RayTracerBase {

    public RayTracerBasic(Scene scene) {
        super(scene);
    }
    /**
     * Traces a ray in the scene and determines the color at the intersection point.
     *
     * @param ray The ray to trace in the scene
     * @return The color at the intersection point, or the background color if no intersection is found
     */
    @Override
    public Color traceRay(Ray ray) {
        if (scene.getGeometries().findIntersections(ray) == null) {
            return scene.getBackground();
        }

        Point p = ray.findClosestPoint(scene.getGeometries().findIntersections(ray));

        return calcColor(p);
    }


    /**
     * calculate the right color to paint the object
     * @param point the point to calculate his color (Point3D)
     * @return the right color (Color)
     */
    public Color calcColor(Point point){
        return scene.getAmbientLight().getIntensity();
    }
    /**
     * calculate the color of the receiving point
     *
     * @param geopoint the intersection point (GeoPoint)
     * @param ray      the ray cross the point (Ray)
     * @return the color (Color)
     */
    private Color calcColor(GeoPoint geopoint, Ray ray) {
        return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(_scene._ambientLight.getIntensity());
    }

}
