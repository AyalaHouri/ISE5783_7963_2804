package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a collection of intersectable geometry objects.
 * @author Ayala Houri and Shani Zegal
 */
public class Geometries extends Intersectable {
    // a list of intersectable objects
    private List<Intersectable> intersectables;

    /**
     * Constructs a new, empty Geometries object.
     */
    public Geometries() {
        intersectables = new LinkedList<>();
    }

    /**
     * Constructs a new Geometries object from an array of Intersectable objects.
     *
     * @param geometries the array of Intersectable objects to add to this Geometries object
     */
    public Geometries(Intersectable... geometries) {
        intersectables = List.of(geometries);
    }

    /**
     * Adds one or more Intersectable objects to this Geometries object.
     *
     * @param geometries the Intersectable objects to add to this Geometries object
     */
    public void add(Intersectable... geometries) {
        intersectables.addAll(Arrays.asList(geometries));
    }

    /**
     * Finds the intersections between a ray and a collection of intersectable objects.
     *
     * @param ray         The ray to intersect with the objects.
     * @param maxDistance The maximum allowed distance between the ray origin and the intersection point.
     * @return A list of GeoPoint objects representing the intersections, or null if no intersection is found.
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> result = null;
        for (Intersectable item : intersectables) {
            List<GeoPoint> itemList = item.findGeoIntersectionsHelper(ray, maxDistance);
            if (itemList != null) {
                if (result == null) {
                    result = new LinkedList<>();
                }
                result.addAll(itemList);
            }
        }
        return result;
    }

}
