package renderer;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import primitives.Material;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * implementation of RayTracerBase class
 */
public class RayTracerBasic extends RayTracerBase {

    private static final double EPS = 0.1;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final Double3 INITIAL_K = Double3.ONE;


    public RayTracerBasic(Scene scene) {
        super(scene);
    }


    @Override
    public Color traceRay(Ray ray) {

        if (scene.geometries.findGeoIntersections(ray) == null)
            return scene.background;

        GeoPoint p = ray.getClosestGeoPoint(scene.geometries.findGeoIntersections(ray));

        return calcColor(p, ray);
    }
    /**
     * Calculates the color of a point in a 3D scene considering global effects such as reflection and transparency.
     *
     * @param gp        The GeoPoint representing the point in 3D space.
     * @param material  The Material object representing the material properties of the surface at the given point.
     * @param n         The surface normal at the given point.
     * @param v         The view direction.
     * @param nv        The dot product of n and v.
     * @param level     The recursion level for calculating global effects.
     * @param k         The attenuation factor for global effects.
     * @return The calculated color at the given point.
     */
    private Color calcGlobalEffects(GeoPoint gp, Material material, Vector n, Vector v, double nv, int level, Double3 k) {
        Color color = Color.BLACK;
        // kkr - reflection
        Double3 kkr = material.getKr().product(k);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
            color = color.add(
                    calcGlobalEffect(constructReflectedRay(gp.point, v, n), level - 1, material.getKr(), kkr).scale(material.getKr()));
        }
        // kkt - transparency
        Double3 kkt = material.getKt().product(k);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(gp.point, v, n), level - 1, material.getKt(), kkt).scale(material.getKt()));
        }
        return color;
    }

    /**
     * Constructs a refracted ray based on the given parameters.
     *
     * @param point The starting point of the ray.
     * @param v     The view direction of the ray.
     * @param n     The surface normal.
     * @return A new Ray representing the refracted ray.
     */
    private Ray constructRefractedRay(Point point, Vector v, Vector n) {
        return new Ray(point, n, v);
    }

    /**
     * Constructs a reflected ray based on the given parameters.
     *
     * @param pointGeo The starting point of the ray.
     * @param v        The view direction of the ray.
     * @param n        The surface normal.
     * @return A new Ray representing the reflected ray.
     */
    private Ray constructReflectedRay(Point pointGeo, Vector v, Vector n) {
        // r = v - 2 * (v.n) * n
        double vn = v.dotProduct(n);

        if (vn == 0) {
            return null; // No reflection if the dot product is zero
        }

        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(pointGeo, n, r);
    }


    /**
     * Calculates the global effect (reflection or refraction) for a given ray and level of recursion.
     *
     * @param ray   The ray for which to calculate the global effect.
     * @param level The recursion level for calculating the global effect.
     * @param kx    The attenuation factor for the global effect.
     * @param kkx   The product of the material reflection or transmission coefficient and the attenuation factor.
     * @return The calculated color for the global effect.
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.getBackground() : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }


    /**
     * calculate the color on specific point on the object
     *
     * @param geoPoint The object with the closest intersection point with the object
     * @param ray      The intersect ray
     * @return The color on the point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Vector v = ray.getDir();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);

        // check that ray is not parallel to geometry
        double nv = alignZero(n.dotProduct(v));


        Material material = geoPoint.geometry.getMaterial();
        Color color = calcLocalEffects(geoPoint, material, n, v, nv, k);
        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, material,n,v,nv, level, k));
    }


    /**
     * Calculates the color at a given intersection point in the scene.
     *
     * @param geopoint The intersection point in the scene.
     * @param ray      The ray that intersected the point.
     * @return The calculated color at the intersection point.
     */
    private Color calcColor(GeoPoint geopoint, Ray ray) {
        return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.getAmbientLight().getIntensity());
    }


    /**
     * Calculates the diffusive component of the shading for a given diffuse reflection coefficient, dot product of
     * the surface normal and light direction, and light intensity.
     *
     * @param kD        The diffuse reflection coefficient.
     * @param nl        The dot product of the surface normal and light direction.
     * @param intensity The light intensity.
     * @return The calculated diffusive component.
     */
    private Double3 calcDiffusive(Double3 kD, double nl, Color intensity) {
        double abs_nl = Math.abs(nl);
        Double3 amount = kD.scale(abs_nl);
        return amount;
    }


    /**
     * calculate the specular on the object
     *
     * @param kS             Specular attenuation factor
     * @param l              The normalized vector from the position point to attached point
     * @param n              The normal vector to the object
     * @param v              The direction vector from the camera
     * @param shininess     Shininess attenuation factor
     * @param intensity The light intensity
     * @return
     */
    private Double3 calcSpecular(Double3 kS, Vector n, Vector l, double nl,Vector v,int shininess,Color intensity) {
        Vector r = l.add(n.scale(-2 * nl)); // nl must not be zero!
        double minusVR = -alignZero(r.dotProduct(v));
        if (minusVR <= 0)
            return new Double3(0);
        Double3 amount =kS.scale(Math.pow(minusVR, shininess));
        return amount;
    }

    /**
     * The method checks whether there is any object shading the light source from a
     * point
     *
     * @param gp the point with its geometry
     * @param lightSource light source
     * @param l  direction from light to the point
     * @param n normal vector from the surface towards the geometry
     *
     * @return accumulated transparency attenuation factor
     */
    private Double3 transparency(LightSource lightSource, Vector l, Vector n, GeoPoint gp) {
        // Pay attention to your method of distance screening
        Vector lightDirection = l.scale(-1); // from point to light source
        Point point = gp.point;
        Ray lightRay = new Ray(point, n, lightDirection);

        double maxdistance = lightSource.getDistance(point);
        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(lightRay, maxdistance);

        if (intersections == null)
            return Double3.ONE;

        Double3 ktr = Double3.ONE;
//        loop over intersections and for each intersection which is closer to the
//        point than the light source multiply ktr by 𝒌𝑻 of its geometry.
//        Performance:
//        if you get close to 0 –it’s time to get out( return 0)
        for (var geo : intersections) {
            ktr = ktr.product(geo.geometry.getMaterial().getKt());
            if (ktr.lowerThan(MIN_CALC_COLOR_K)) {
                return Double3.ZERO;
            }
        }
        return ktr;
    }

    /**
     * Calculates the local effects of lighting at the intersection point.
     * Takes into account diffuse and specular reflection components based on the material properties of the intersected geometry.
     *
     * @param gp  geopoint of the intersection
     * @param v ray direction
     * @return resulting color with diffuse and specular
     * @author Eliezer Ginsburger
     */
    private Color calcLocalEffects(GeoPoint gp, Material material, Vector n, Vector v, double nv, Double3 k) {
        Color color = gp.geometry.getEmission();

        Point point = gp.point;

        for (LightSource lightSource : scene.getLights()) {
            Vector l = lightSource.getL(point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sign(nv)
                Double3 ktr = transparency(lightSource, l, n, gp);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(point).scale(ktr);
                    color = color.add(
                            iL.scale(calcDiffusive(material.getKd(), nl,iL)),
                            iL.scale(calcSpecular(material.getKs(), n, l, nl, v,material.getShininess(),iL)));
                }
            }
        }
        return color;
    }

    /**
     * Finds the closest intersection point between the given ray and the geometries in the scene.
     *
     * @param ray The ray for which to find the closest intersection.
     * @return The closest intersection point, or null if no intersection is found.
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(ray);
        if (intersections == null) {
            return null;
        }
        return ray.findClosestGeoPoint(intersections);
    }


}