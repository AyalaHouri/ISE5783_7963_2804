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

    private Color calcGlobalEffects(GeoPoint gp,Material material, Vector n, Vector v, double nv, int level, Double3 k) {
        Color color = Color.BLACK;
        //kkr - reflection
        Double3 kkr = material.getKr().product(k);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K))
            color = color.add(
                    calcGlobalEffect(constructReflectedRay(gp.point, v, n), level-1, material.getKr(), kkr).scale(material.getKr()));
        //kkt - transparency
        Double3 kkt = material.getKt().product(k);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K))
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(gp.point, v, n), level-1, material.getKt(), kkt).scale(material.getKt()));
        return color;
    }

    private Ray constructRefractedRay(Point point, Vector v, Vector n) {
        return new Ray(point, n, v);
    }
    private Ray constructReflectedRay(Point pointGeo, Vector v, Vector n) {
        //r = v - 2.(v.n).n
        double vn = v.dotProduct(n);

        if (vn == 0) {
            return null;
        }

        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(pointGeo, n, r);
    }

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
//        Color color = geoPoint.geometry.getEmission();

        Vector v = ray.getDir();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);

        // check that ray is not parallel to geometry
        double nv = alignZero(n.dotProduct(v));

//        if (isZero(nv)) {
//            return color;
//        }
        Material material = geoPoint.geometry.getMaterial();
//        color = color.add(calcLocalEffects(geoPoint, material, n, v, nv, k));
        Color color = calcLocalEffects(geoPoint, material, n, v, nv, k);
        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, material,n,v,nv, level, k));
    }


    private Color calcColor(GeoPoint geopoint, Ray ray) {
        return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.getAmbientLight().getIntensity());
    }


    private Double3 calcDiffusive(Double3 kD, double nl,  Color intensity) {
        double abs_nl = Math.abs(nl);
        Double3 amount = kD.scale(abs_nl);
//        return intensity.scale(amount);
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
//            return Color.BLACK; // view from direction opposite to r vector
            return new Double3(0);
        Double3 amount =kS.scale(Math.pow(minusVR, shininess));
//        return intensity.scale(amount);
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
//                if (unshaded(gp, lightSource, l, n,nv)) {
                    Color iL = lightSource.getIntensity(point).scale(ktr);
                    color = color.add(
                            iL.scale(calcDiffusive(material.getKd(), nl,iL)),
                            iL.scale(calcSpecular(material.getKs(), n, l, nl, v,material.getShininess(),iL)));
                }
            }
        }
        return color;
    }

    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(ray);
        if (intersections == null) {
            return null;
        }
        return ray.findClosestGeoPoint(intersections);
    }

//    /**
//     * Calculates the local effects of lighting at the intersection point.
//     * Takes into account diffuse and specular reflection components based on the material properties of the intersected geometry.
//     *
//     * @param intersection The intersection point between the ray and the geometry.
//     * @param ray          The ray that intersects the geometry.
//     * @return The color resulting from the local lighting effects.
//     */
//    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
//        Vector v = ray.getDir();
//        Vector n = intersection.geometry.getNormal(intersection.point);
//
//        double nv = alignZero(n.dotProduct(v));
//        if (nv == 0) return Color.BLACK;
//
//        Material material = intersection.geometry.getMaterial();
//
//        int nShininess = material.nShininess;
//        double kd = material.getKd(), ks = material.getKs();
//        Color color = Color.BLACK;
//
//        for (LightSource lightSource : scene.lights) {
//            Vector l = lightSource.getL(intersection.point);
//
//            double nl = alignZero(n.dotProduct(l));
//            if (nl * nv > 0) {
//                // sign(nl) == sign(nv)
//                Color lightIntensity = lightSource.getIntensity(intersection.point);
//                color = color.add(calcDiffusive(kd, lightIntensity, nl), calcSpecular(ks, l, n, v, nShininess, lightIntensity, nl));
//            }
//        }
//        return color;
//    }

}