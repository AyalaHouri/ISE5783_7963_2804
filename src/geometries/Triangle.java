package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * The Triangle class represents a triangle in 3D space by its three points.
 * It extends the Polygon class.
 * @author Ayala Houri and Shani Zegal
 */
public class Triangle extends Polygon {

    /**
     * Constructor to initialize a Triangle object with its three points.
     *
     * @param tpoint1 The first point of the triangle.
     * @param tpoint2 The second point of the triangle.
     * @param tpoint3 The third point of the triangle.
     */
    public Triangle(Point tpoint1, Point tpoint2, Point tpoint3){
        super(tpoint1, tpoint2, tpoint3);
    }

    /**
     * Calculates the normal vector of the triangle at a given point on its surface.
     *
     * @param point A point on the surface of the triangle.
     * @return The normalized vector perpendicular to the surface of the triangle at the given point.
     */
    @Override
    public Vector getNormal(Point point) {
        return super.getNormal(point);
    }

    /**
     * Finds the intersection points between a given ray and this triangle.
     *
     * @param ray the ray to find the intersection points with
     * @return a list containing the intersection points, or null if no intersection exists
     */
    public List<Point> findIntersections(Ray ray){
        // calculate the normal vectors of the triangle's three edges
        Vector v1=vertices.get(0).subtract(ray.getP0());
        Vector v2=vertices.get(1).subtract(ray.getP0());
        Vector v3=vertices.get(2).subtract(ray.getP0());
        Vector n1=v1.crossProduct(v2).normalize();
        Vector n2=v2.crossProduct(v3).normalize();
        Vector n3=v3.crossProduct(v1).normalize();

        Vector v = ray.getDir();

        // check if the ray intersects the triangle using the normal vectors
        if(alignZero(n1.dotProduct(v))>0&&alignZero(n2.dotProduct(v))>0&&alignZero(n3.dotProduct(v))>0||
                alignZero(n1.dotProduct(v))<0&&alignZero(n2.dotProduct(v))<0&&alignZero(n3.dotProduct(v))<0)
            return this.plane.findIntersections(ray);

        // if there is no intersection, return null
        return null;
    }

}
