package mainModule;

import geometries.Sphere;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import static java.awt.Color.BLUE;
import static java.awt.Color.RED;

public class mainTest {
    private final Scene scene = new Scene.SceneBuilder("Test scene").build();
    @Test
    public void shapes() {
        //to change location change point coordinates (x is left,right)
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(150, 150)
                .setVPDistance(1000);

        scene.getGeometries().add( //
                new Sphere(new Point(0, 0, -50), 50d)
                        .setEmission(new Color(BLUE)) //
                        .setMaterial(new Material()
                                .setKd(0.4)
                                .setKs(0.3)
                                .setShininess(100)
                                .setKt(0.3)),
                new Sphere(new Point(0, 0, -50), 25d)
                        .setEmission(new Color(RED)) //
                        .setMaterial(new Material()
                                .setKd(0.5)
                                .setKs(0.5)
                                .setShininess(100)));
        scene.getLights().add( //
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
                        .setKl(0.0004)
                        .setKq(0.0000006));

        camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

}
