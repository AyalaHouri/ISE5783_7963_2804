package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Tube extends RadialGeometry{
    final protected Ray exisRay;
    public Tube(double tempRadius,Ray ray) {
        super(tempRadius);
        exisRay=ray;
    }

    /**
     * the function gets a point and calculates the normal of the tube. We calculate the dot product between the dir vector and the vector that we get from subtracting
     * the point from the center of the tube axis. If we get zero - we return this vector normalized. If not, we move the center point to be under the "head" of the vector.
     * If the 2 points are equal - that means the point is on the tube axis and we throw an error. Otherwise, we return the vector we get from subtracting the point
     * from point new center
     * @param point
     * @return vector normal
     */
    public Vector getNormal(Point point) {
        Point center = exisRay.getP0();
        Vector dir = exisRay.getDir();

        Vector normal = point.subtract(center);

        //are the vectors 90 degrees
        double scalar = alignZero(dir.dotProduct(normal));
        if (isZero(scalar)) {
            return normal.normalize();
        }

        Point p = center.add(dir.scale(scalar));
        if (point.equals(p)) {
            throw new IllegalArgumentException("point can't be on tube axis");
        }
        normal = point.subtract(p).normalize();
        return normal;
    }
}
