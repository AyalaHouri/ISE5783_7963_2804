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
        p0=point1;
        if(point1.equals(point2) || point1.equals(point3) || point2.equals(point3))
            throw new IllegalArgumentException("Two or more points are the same");

        Vector v1=point1.subtract(point2);  //vector from point2 to point1
        Vector v2=point1.subtract(point3);  //vector from point3 to point1

        if(v1.normalize().equals(v2.normalize()))
            throw new IllegalArgumentException("The vectors have a linear dependency");

        Vector v3=v1.crossProduct(v2);  //cross product to get the perpendicular vector

        normal=v3.normalize();
    }

    /**
     * Constructor to initialize Plane based object with its two parameters 1.point 2.vector
     * @param point
     * @param vector
     */
    public Plane(Point point,Vector vector){
        p0=point;
        if(!(isZero(vector.length()-1d))){
            normal=vector.normalize();
        }
        else
            this.normal = vector;
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
    public Vector getNormal(Point point){return getNormal();}


}
