package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * this class represent a Triangle in 3D world by three points
 */
public class Triangle extends Polygon {
//    final private Point point1;
//    final private Point point2;
//    final private Point point3;

    /**
     * Constructor to initialize triangle based object with its three points
     * @param tpoint1
     * @param tpoint2
     * @param tpoint3
     */
    public Triangle(Point tpoint1,Point tpoint2,Point tpoint3 ){
        super(tpoint1,tpoint2,tpoint3);
//        point1=tpoint1;
//        point2=tpoint2;
//        point3=tpoint3;
    }

    @Override
    public Vector getNormal(Point point) {
        return super.getNormal(point);
    }
}
