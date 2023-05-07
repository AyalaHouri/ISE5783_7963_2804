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
public class Geometries implements Intersectable {
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
     * Finds the intersection points between a given ray and the geometry objects in this Geometries object.
     *
     * @param ray the ray to find the intersection points with
     * @return a list containing the intersection points, or null if no intersection exists
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> result = null;

        // iterate over all the intersectable objects in this Geometries object
        for (Intersectable item : intersectables) {
            // find the intersection points between the ray and the current intersectable object
            List<Point> intersectionPoints = item.findIntersections(ray);

            if (intersectionPoints != null) {
                // if there are intersection points, add them to the result list
                if (result == null) {
                    result = new LinkedList<>();
                }

                result.addAll(intersectionPoints);
            }
        }

        return result;
    }
}
