package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**

 The Intersectable interface represents a geometric object that can be intersected by a ray in 3D space.
 Implementing classes should provide the implementation of the findIntersections method to find the intersection points
 between the intersectable object and the given ray.
 */

/**
 * @author Ayala Houri and Shani Zegal
 */
public interface Intersectable {

    /**
     Finds the intersection points between this intersectable object and the given ray.
     @param ray the ray to intersect this object with
     @return a list of Point objects representing the intersection points, or an empty list if no intersections exist
     */
    List<Point> findIntersections(Ray ray);
}
