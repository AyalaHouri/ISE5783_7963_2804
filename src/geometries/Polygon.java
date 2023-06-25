package geometries;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * Implements the Geometry interface.
 *
 * @author Dan
 */
public class Polygon extends Geometry {
   /** List of polygon's vertices */
   protected final List<Point> vertices;
   /** Associated plane in which the polygon lays */
   protected final Plane plane;
   private final int size;

   /**
    * Polygon constructor based on vertices list. The list must be ordered by edge
    * path. The polygon must be convex.
    *
    * @param vertices
    *            list of vertices according to their order by edge path
    * @throws IllegalArgumentException
    *             in any case of illegal combination of vertices: <br>
    *             - Less than 3 vertices <br>
    *             - Consequent vertices are in the same point <br>
    *             - The vertices are not in the same plane <br>
    *             - The order of vertices is not according to edge path <br>
    *             - Three consequent vertices lay in the same line (180&#176; angle
    *             between two consequent edges) <br>
    *             - The polygon is concave (not convex)
    */
   public Polygon(Point... vertices) {
      if (vertices.length < 3)
         throw new IllegalArgumentException("A polygon can't have less than 3 vertices");

      // Store the vertices in an immutable List
      this.vertices = List.of(vertices);
      size = vertices.length;

      // Generate the plane according to the first three vertices and associate the
      // polygon with this plane.
      // The plane holds the invariant normal (orthogonal unit) vector to the polygon
      plane = new Plane(vertices[0], vertices[1], vertices[2]);

      // If the polygon has only three vertices, it must be a Triangle and no further
      // checks are necessary
      if (size == 3)
         return;

      Vector n = plane.getNormal();
      Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
      Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

      // Cross Product of any subsequent edges will throw an IllegalArgumentException
      // because of Zero Vector if they connect three vertices that lay in the same
      // line.
      // Generate the direction of the polygon according to the angle between last and
      // first edge being less than 180 deg. It is hold by the sign of its dot product
      // with
      // the normal. If all the rest consequent edges will generate the same sign -
      // the
      // polygon is convex ("kamur" in Hebrew).
      boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
      for (var i = 1; i < vertices.length; ++i) {
         // Test that the point is in the same plane as calculated originally
         if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
            throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");

         // Test the consequent edges have
         edge1 = edge2;
         edge2 = vertices[i].subtract(vertices[i - 1]);

         if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
            throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
      }
   }
   /**
    * Finds the intersections between a ray and a polygon.
    *
    * @param ray         The ray to intersect with the polygon.
    * @param maxDistance The maximum allowed distance between the ray origin and the intersection point.
    * @return A list of GeoPoint objects representing the intersections, or null if no intersection is found.
    */
   @Override
   public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
      List<GeoPoint> planeIntersections = plane.findGeoIntersections(ray);

      if (planeIntersections == null) {
         return null;
      }

      Point P0 = ray.getP0();
      Vector v = ray.getDir();

      Point P1 = vertices.get(1);
      Point P2 = vertices.get(0);

      Vector v1 = P0.subtract(P1);
      Vector v2 = P0.subtract(P2);

      double sign = alignZero(v.dotProduct(v1.crossProduct(v2)));

      if (isZero(sign)) {
         return null;
      }

      boolean positive = sign > 0;

      // Iterate through all vertices of the polygon
      for (int i = vertices.size() - 1; i > 0; --i) {
         v1 = v2;
         v2 = P0.subtract(vertices.get(i));

         sign = alignZero(v.dotProduct(v1.crossProduct(v2)));
         if (isZero(sign)) {
            return null;
         }

         if (positive != (sign > 0)) {
            return null;
         }
      }

      Point point = planeIntersections.get(0).point;
      return List.of(new GeoPoint(this, point));
   }

   /**
    * Returns the normal vector of a plane based on a given point.
    *
    * @param point the point used to calculate the normal vector of the plane
    * @return the normal vector of the plane
    */
   @Override
   public Vector getNormal(Point point) {
      return plane.getNormal();
   }
//
//   public List<Point> findIntersections(Ray ray){
//      return findGeoIntersectionsHelper(ray,);
//   }
}

