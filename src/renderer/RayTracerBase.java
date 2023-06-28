package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * an abstract class responsible for the Ray tracer
 */
public abstract class RayTracerBase {

    protected Scene scene;

    /**
     *  c-tor receive scene and set him in the variable
     * @param scene scene (Scene)
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * if the ray intersect  the geometry, paint in the right color
     *
     * @param ray the ray we check if he intersect with the geometry (Ray)
     * @return the right color to paint the pixel (Color)
     */
    public abstract Color traceRay(Ray ray);


}
