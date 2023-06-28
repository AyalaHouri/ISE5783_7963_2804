package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Random;

/**
 * The Camera class represents a camera in a 3D scene. It is responsible for generating rays and rendering an image
 * using a ray tracing algorithm.
 */
public class Camera {
    private Point p0; // starting point, camera location
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private double width, height; // view plane size
    private double distance; // distance between camera and view plane
    private int numRays = 1; // number of rays to send
    private boolean superSampling = false; // for improvements
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;

    /**
     * Constructs a Camera object with the specified camera location (p), viewing direction (vTo), and up direction (vUp).
     *
     * @param p   The starting point or location of the camera.
     * @param vTo The vector pointing towards the viewing direction.
     * @param vUp The vector pointing upwards.
     * @throws IllegalArgumentException If vTo and vUp are not orthogonal.
     */
    public Camera(Point p, Vector vTo, Vector vUp) {
        this.p0 = p;

        // Check if vTo and vUp are orthogonal
        if (vTo.dotProduct(vUp) != 0) {
            throw new IllegalArgumentException("Vup and Vto are not orthogonal");
        }

        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        // vright = cross product of vto and vup (normalized)
        this.vRight = this.vTo.crossProduct(this.vUp).normalize();
    }

    /**
     * Sets the size of the view plane.
     *
     * @param w The width of the view plane.
     * @param h The height of the view plane.
     * @return The Camera object for method chaining.
     */
    public Camera setVPSize(double w, double h) {
        this.width = w;
        this.height = h;
        return this;
    }

    /**
     * Sets the number of rays to send per pixel.
     *
     * @param num The number of rays to send.
     * @return The Camera object for method chaining.
     */
    public Camera setNumRays(int num) {
        this.numRays = num;
        return this;
    }

    /**
     * Sets the distance between the camera and the view plane.
     *
     * @param d The distance between the camera and the view plane.
     * @return The Camera object for method chaining.
     */
    public Camera setVPDistance(double d) {
        this.distance = d;
        return this;
    }

    /**
     * Sets whether super sampling should be used during rendering.
     *
     * @param superSampling Whether super sampling should be used.
     * @return The Camera object for method chaining.
     */
    public Camera setSuperSampling(boolean superSampling) {
        this.superSampling = superSampling;
        return this;
    }

    /**
     * Sets the image writer for the camera.
     *
     * @param imageWriter The ImageWriter object to be used.
     * @return The Camera object for method chaining.
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * Sets the ray tracer for the camera.
     *
     * @param rayTracer The RayTracerBase object to be used.
     * @return The Camera object for method chaining.
     */
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * Returns the starting point or location of the camera.
     *
     * @return The starting point of the camera.
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Returns the vector pointing towards the viewing direction.
     *
     * @return The vector pointing towards the viewing direction.
     */
    public Vector getvTo() {
        return vTo;
    }

    /**
     * Returns the vector pointing upwards.
     *
     * @return The vector pointing upwards.
     */
    public Vector getvUp() {
        return vUp;
    }

    /**
     * Returns the vector pointing to the right side.
     *
     * @return The vector pointing to the right side.
     */
    public Vector getvRight() {
        return vRight;
    }

    /**
     * Returns the height of the view plane.
     *
     * @return The height of the view plane.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Returns the width of the view plane.
     *
     * @return The width of the view plane.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Returns the distance between the camera and the view plane.
     *
     * @return The distance between the camera and the view plane.
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Constructs a single ray for the specified pixel coordinates.
     *
     * @param nX The number of pixels in the X-axis.
     * @param nY The number of pixels in the Y-axis.
     * @param j  The column index of the pixel.
     * @param i  The row index of the pixel.
     * @return The constructed Ray object.
     */
    public Ray constructRay(double nX, double nY, int j, int i){
        Point pIJ = middlePoint(nX, nY, j, i);

        // Ratio (pixel width & height)
        double rX = width / nX;
        double rY = height / nY;

        Vector vIJ = pIJ.subtract(p0);

        return new Ray(p0, vIJ);
    }

    /**
     * Constructs multiple rays for the specified pixel coordinates.
     *
     * @param nX The number of pixels in the X-axis.
     * @param nY The number of pixels in the Y-axis.
     * @param j  The column index of the pixel.
     * @param i  The row index of the pixel.
     * @return A list of constructed Ray objects.
     */
    public List<Ray> constructRays(double nX, double nY, int j, int i){
        Point pIJ = middlePoint(nX, nY, j, i);

        // Ratio (pixel width & height)
        double rX = width / nX;
        double rY = height / nY;


        Random rand = new Random();
        List<Ray> rays = new LinkedList();
        for (int k = 0; k < numRays; k++) {
            Point point = pIJ.add(vRight.scale(rand.nextDouble(-rX/2,rX/2)))
                    .add(vUp.scale(rand.nextDouble(-rY/2,rY/2)));
            Vector vector = point.subtract(p0);
            Ray ray = new Ray(p0,vector);
            rays.add(ray);
        }

        return rays;
    }

    /**
     * Computes the middle point on the view plane for the specified pixel coordinates.
     *
     * @param nX The number of pixels in the X-axis.
     * @param nY The number of pixels in the Y-axis.
     * @param j  The column index of the pixel.
     * @param i  The row index of the pixel.
     * @return The middle point on the view plane.
     */
    public Point middlePoint(double nX, double nY, int j, int i){
        //Image center
        Point pC = p0.add(vTo.scale(distance));

        // Ratio (pixel width & height)
        double rX = width / nX;
        double rY = height / nY;

        //Pixel [i,j] center
        double yI = -1 * (i - (nY - 1) / 2d) * rY;
        double xJ = (j - (nX - 1) / 2d) * rX;

        // in the beginning pIJ is the center pixel, and if we need to move up and down or right and left
        Point pIJ = pC;
        if (xJ != 0) pIJ = pIJ.add(vRight.scale(xJ));
        if (yI != 0) pIJ = pIJ.add(vUp.scale(yI));

        return pIJ;
    }

    /**
     * Constructs the adaptive supersampling for a specific pixel.
     *
     * @param nX the number of pixels in the X-axis
     * @param nY the number of pixels in the Y-axis
     * @param j the column index of the pixel
     * @param i the row index of the pixel
     * @return the ColorRay representing the computed color of the pixel
     */
    public Color constructAdaptiveSuperSampling(int nX, int nY, int j, int i) {
        //Calculate the size of each pixel
        double rX = width / nX;
        double rY = height / nY;

        Point mid = middlePoint(nX, nY,j,i);
        return AdaptiveSuperSampling(mid,rX,rY,this.numRays);
    }

    /**
     * Performs adaptive supersampling to compute the color of a pixel.
     *
     * @param rayIntersect the point of intersection between the ray and the scene
     * @param rX the pixel width
     * @param rY the pixel height
     * @param rayNum the number of rays to cast for supersampling
     * @return the ColorRay representing the computed color of the pixel
     */
    public Color AdaptiveSuperSampling(Point rayIntersect, double rX, double rY ,int rayNum) {

        // Cast rays for the four corners of the pixel
        Color topLeft = castRay(rX, rY, -1, -1);
        Color topRight = castRay( rX, rY, 1, -1);
        Color bottomLeft = castRay( rX, rY, -1, 1);
        Color bottomRight = castRay(rX, rY, 1, 1);

        // Check if all four colors are similar or the rayNum has reached the limit
        if ((topLeft.equals(topRight)
                && topLeft.equals(bottomLeft)
                && topLeft.equals(bottomRight))
                || rayNum <=0 ) {

            // Return the color if all four colors are similar or rayNum limit reached
            return topLeft;
        } else {
            // Recursively divide the pixel and perform adaptive supersampling
            double newRx = rX / 2;
            double newRy = rX / 2;

            // Compute the four subpixel points within the pixel
            Point A = rayIntersect
                    .add(this.vUp.scale((rY/2)*-1)
                            .add(this.vRight.scale((rX/2)*-1)));

            Point B = rayIntersect
                    .add(this.vUp.scale((rY/2)*1)
                            .add(this.vRight.scale((rX/2)*-1)));

            Point C = rayIntersect
                    .add(this.vUp.scale((rY/2)*-1)
                            .add(this.vRight.scale((rX/2)*1)));

            Point D = rayIntersect
                    .add(this.vUp.scale((rY/2)*1)
                            .add(this.vRight.scale((rX/2)*1)));

            // Recursively compute the color of the subpixels
            Color topLeftSubpixel = AdaptiveSuperSampling(A, newRx, newRy, rayNum / 4);
            Color topRightSubpixel = AdaptiveSuperSampling(B, newRx, newRy, rayNum / 4);
            Color bottomLeftSubpixel = AdaptiveSuperSampling(C, newRx, newRy, rayNum / 4);
            Color bottomRightSubpixel = AdaptiveSuperSampling(D, newRx, newRy, rayNum / 4);

            // Compute the average color of the subpixels
            Color averageColor = topLeftSubpixel
                    .add(topRightSubpixel)
                    .add(bottomLeftSubpixel)
                    .add(bottomRightSubpixel)
                    .reduce(4);

            // Return the Color representing the average color
            return averageColor;
        }
    }


    /**
     * Renders the scene using the ray tracing algorithm and saves the result to an image file.
     *
     * @throws MissingResourceException If the image writer or ray tracer is not set.
     */
    public Camera renderImage() {
        try {
            if (imageWriter == null) {
                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
            }
            if (rayTracer == null) {
                throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
            }
            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();

            //go over all pixels
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    //if multiple rays
                    if(this.numRays>1) {
                        castRays(nX, nY, i, j);
                    }
                    //only one ray
                    else {
                        Color color = castRay(nX, nY, i, j);
                        imageWriter.writePixel(j, i, color);
                    }
                }
            }
        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
        }
        return this;
    }


    /**
     * Casts a single ray and returns the color of the pixel it intersects with.
     *
     * @param nX   The normalized x-coordinate of the ray origin.
     * @param nY   The normalized y-coordinate of the ray origin.
     * @param col  The column index of the pixel.
     * @param row  The row index of the pixel.
     * @return The color of the pixel the ray intersects with.
     */
    private Color castRay(double nX, double nY, int col, int row) {
        Ray ray = constructRay(nX, nY, row, col);
        Color pixelColor = rayTracer.traceRay(ray);
        return pixelColor;
    }


    /**
     * Casts rays and calculates the color of the pixel by either using adaptive super-sampling or regular sampling.
     *
     * @param nX   The normalized x-coordinate of the pixel.
     * @param nY   The normalized y-coordinate of the pixel.
     * @param col  The column index of the pixel.
     * @param row  The row index of the pixel.
     */
    private void castRays(int nX, int nY, int col, int row) {
        List<Ray> rays = constructRays(nX, nY, row, col);
        Color pixelColor = Color.BLACK;
        if (this.superSampling) {
            pixelColor = constructAdaptiveSuperSampling(nX, nY, col, row);
        } else {
            for (Ray ray : rays) {
                pixelColor = pixelColor.add(rayTracer.traceRay(ray));
            }
            pixelColor = pixelColor.reduce(rays.size());
        }
        imageWriter.writePixel(row, col, pixelColor);
    }

    /**
     * Prints a grid on the image with the specified interval and color.
     *
     * @param interval  The interval at which the grid lines should be printed.
     * @param color     The color of the grid lines.
     */
    public void printGrid(int interval, Color color) {
        imageWriter.printGrid(interval, color);
    }

    /**
     * Writes the rendered image to a file.
     *
     * @return The Camera object.
     */
    public Camera writeToImage() {
        imageWriter.writeToImage();
        return this;
    }
}
