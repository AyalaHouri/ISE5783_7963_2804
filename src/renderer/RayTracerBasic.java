package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

/**
 * implementation of RayTracerBase class
 */
public class RayTracerBasic extends RayTracerBase {

    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * if the ray intersect the geometry, paint in the right color
     * @param ray the ray we check if it intersects with the geometry (Ray)
     * @return the right color to paint the pixel (Color)
     */
    @Override
    public Color traceRay(Ray ray) {

        if(scene.getGeometries().findIntersections(ray)==null)
            return scene.getBackground();

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
}
