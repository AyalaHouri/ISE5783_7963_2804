package renderer;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import primitives.Material;
import static primitives.Util.alignZero;

/**
 * implementation of RayTracerBase class
 */
public class RayTracerBasic extends RayTracerBase {

    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * if the ray intersect  the geometry, paint in the right color
     *
     * @param ray the ray we check if he intersect with the geometry (Ray)
     * @return the right color to paint the pixel (Color)
     */
    @Override
    public Color traceRay(Ray ray) {

        if (scene.geometries.findGeoIntersections(ray) == null)
            return scene.background;

        GeoPoint p = ray.getClosestGeoPoint(scene.geometries.findGeoIntersections(ray));

        return calcColor(p, ray);
    }

    /**
     * calculate the color on specific point on the object
     *
     * @param geoPoint The object with the closest intersection point with the object
     * @param ray      The intersect ray
     * @return The color on the point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        Color ambientLightIntensity = scene.getAmbientLight().getIntensity();
        Color EmissionColor = geoPoint.geometry.getEmission();
        return ambientLightIntensity.add(EmissionColor).add(calcLocalEffects(geoPoint, ray));
    }

    /**
     * calculate the right color to paint the object
     *
     * @param point the point to calculate his color (Point3D)
     * @return the right color (Color)
     */
    public Color calcColor(Point point) {
        return scene.getAmbientLight().getIntensity();
    }

    /**
     * calculate the diffuse on the object
     *
     * @param kD             Diffuse attenuation factor
     * @param l              The normalized vector from the position point to attached point
     * @param n              The normal vector to the object
     * @param lightIntensity The light intensity
     * @return The diffuse on the object (Color)
     */
    private Color calcDiffusive(double kD, Color lightIntensity, double nl) {
        Color diffuse = lightIntensity.scale(kD * Math.abs(nl));
        return diffuse;
    }

    /**
     * calculate the specular on the object
     *
     * @param kS             Specular attenuation factor
     * @param l              The normalized vector from the position point to attached point
     * @param n              The normal vector to the object
     * @param v              The direction vector from the camera
     * @param nShininess     Shininess attenuation factor
     * @param lightIntensity The light intensity
     * @return
     */
    private Color calcSpecular(double kS, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity, double nl) {
        Vector r = l.subtract(n.scale(nl).scale(2));
        Color specular = lightIntensity.scale(kS * Math.pow(Math.max(0, v.scale(-1).dotProduct(r)), nShininess));
        return specular;
    }
    
    /**
     * Calculates the local effects of lighting at the intersection point.
     * Takes into account diffuse and specular reflection components based on the material properties of the intersected geometry.
     *
     * @param intersection The intersection point between the ray and the geometry.
     * @param ray          The ray that intersects the geometry.
     * @return The color resulting from the local lighting effects.
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Vector v = ray.getDir();
        Vector n = intersection.geometry.getNormal(intersection.point);

        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;

        Material material = intersection.geometry.getMaterial();

        int nShininess = material.nShininess;
        double kd = material.kD, ks = material.kS;
        Color color = Color.BLACK;

        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);

            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) {
                // sign(nl) == sign(nv)
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffusive(kd, lightIntensity, nl), calcSpecular(ks, l, n, v, nShininess, lightIntensity, nl));
            }
        }
        return color;
    }

}