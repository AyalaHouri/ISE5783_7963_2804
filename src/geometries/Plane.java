package geometries;
import static primitives.Util.isZero;

import java.util.List;

import primitives.Point;
import primitives.Vector;

/**
 * This class represent a plan in 3D world by a point and normal vector
 */
public class Plane {
    Point p0;
    Vector normal;

    /**
     * Constructor to initialize Plane based object with its three points
     * if we want to calculate the normal vector we need two vectors in tha plane and calculate the cross product between them, dou to that we will calculate two vectors ib the plan by the three received points
     * @param point1
     * @param point2
     * @param point3
     */
    public Plane(Point point1,Point point2,Point point3){
        Vector V1=point1.subtract(point2);
        Vector V2=point1.subtract(point3);
        Vector V3=V1.crossProduct(V2);
        p0=point1;
        normal=V3.normalize();
    }

    /**
     * Constructor to initialize Plane based object with its two parameters 1.point 2.vector
     * @param point
     * @param vector
     */
    public Plane(Point point,Vector vector){
        p0=point;
        normal=vector.normalize();
    }

    /**
     * get function
     * @return the class normal
     */
    public Vector getNormal(){return normal;}

    /**
     *
     * get function
     * @return the class normal
     */
    public Vector getNormal(Point point){return normal;}


}
