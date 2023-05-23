package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

public class Camera {
    private Point p0; //starting point
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private double width, height; //view plane size
    private double distance; //distance between camera and view plane

    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;

    public Point getP0() {
        return p0;
    }
    public Vector getvTo() {
        return vTo;
    }
    public Vector getvUp() {
        return vUp;
    }
    public Vector getvRight() {
        return vRight;
    }
    public double getHeight() {
        return height;
    }
    public double getWidth() {
        return width;
    }
    public double getDistance() {
        return distance;
    }
    public Camera(Point p,Vector vTo,Vector vUp){
        this.p0=p;
        //are vto and vup are orthogonal?
        if(vTo.dotProduct(vUp)!=0){
           throw new IllegalArgumentException("Vup and Vto are not orthogonal");
        }

        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        //vright = cross product of vto and vup (normalized)
        this.vRight = this.vTo.crossProduct(this.vUp).normalize();
      }

    public Camera setVPSize(double w, double h) {
        this.width = w;
        this.height = h;
        return this;
    }

    public Camera setVPDistance(double d) {
        this.distance = d;
        return this;
    }

    /**
     * setter for imageWriter
     *
     * @param imageWriter
     * @return the image writer for the camera
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * setter for rayTracer
     * @param rayTracer
     * @return the ray tracer for the camera
     */
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    public Ray constructRay(int nX, int nY, int j, int i){
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

        Vector vIJ = pIJ.subtract(p0);

        return new Ray(p0, vIJ);
    }
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

            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    castRay(nX, nY, i, j);
                }
            }
        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
        }
        return this;
    }
    private void castRay(int nX, int nY, int col, int row) {
        Ray ray = constructRay(nX, nY, row, col);
        Color pixelColor = rayTracer.traceRay(ray);
        imageWriter.writePixel(row, col, pixelColor);
    }
    public void printGrid(int interval, Color color) {
        imageWriter.printGrid(interval, color);
    }

    public Camera writeToImage() {
        imageWriter.writeToImage();
        return this;
    }


}
