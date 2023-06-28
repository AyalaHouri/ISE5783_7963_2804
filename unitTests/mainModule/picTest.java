package mainModule;

import geometries.*;
import lighting.AmbientLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import static java.awt.Color.*;

public class picTest {
    Scene scene = new Scene.SceneBuilder("Test scene").build();
    Camera camera = new Camera(
            new Point(40,-700,30),
            new Vector(0,1,0),
            new Vector(0,0,1))
            .setVPSize(200, 200)
            .setVPDistance(800)
            .setRayTracer(new RayTracerBasic(scene))
            .setSuperSampling(true)
            .setNumRays(100);


    /**
     * Produce a picture of a two triangles lighted by a spot light with a Sphere
     * producing a shading
     */
    @Test
    public void golf() {
        Material material = new Material().setKd(0.5).setKs(0.2).setShininess(10).setKr(new Double3(0.1));

        scene.geometries.add(
                //grass
                new Plane(
                        new Point(-500,-700,-40),
                        new Vector(0,0,1))
                        .setEmission(new Color(10, 110, 20)) //
                        .setMaterial(new Material().setKd(0.1).setKs(0.2).setShininess(1)));
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

        scene.geometries.add(
                //box floor
                new Polygon(
                        new Point(55,60,-39.5),
                        new Point(55,40,-39.5),
                        new Point(75,40,-39.5),
                        new Point(75,60,-39.5)) //
                        .setEmission(new Color(150, 75, 0))
                        .setMaterial(new Material().setKd(0.1).setKs(0.2).setShininess(1)));
        scene.geometries.add(
                //letf side
                new Polygon(
                        new Point(55,60,-39.5),
                        new Point(55,40,-39.5),
                        new Point(55,40,-19.5),
                        new Point(55,60,-19.5)) //
                        .setEmission(new Color(150, 75, 0))
                        .setMaterial(new Material().setKd(0.1).setKs(0.2).setShininess(1)));
        scene.geometries.add(
                //right side
                new Polygon(
                        new Point(75,60,-39.5),
                        new Point(75,40,-39.5),
                        new Point(75,40,-19.5),
                        new Point(75,60,-19.5)) //
                        .setEmission(new Color(150, 75, 0))
                        .setMaterial(new Material().setKd(0.1).setKs(0.2).setShininess(1)));
        scene.geometries.add(
                //back
                new Polygon(
                        new Point(55,60,-39.5),
                        new Point(75,60,-39.5),
                        new Point(75,60,-19.5),
                        new Point(55,60,-19.5)) //
                        .setEmission(new Color(150, 75, 0))
                        .setMaterial(new Material().setKd(0.1).setKs(0.2).setShininess(1)));
        scene.geometries.add(
                //front
                new Polygon(
                        new Point(55,40,-39.5),
                        new Point(75,40,-39.5),
                        new Point(75,40,-19.5),
                        new Point(55,40,-19.5)) //
                        .setEmission(new Color(150, 75, 0))
                        .setMaterial(new Material().setKd(0.1).setKs(0.2).setShininess(1).setKr(0.5)));
        scene.geometries.add(
                new Sphere(new Point(60, 50, -20), 5) //
                        .setEmission(new Color(600, 0, 0).reduce(3))
                        .setMaterial(new Material().setKd(0.5).setKs(0.2)
                                .setShininess(10).setKr(0.5)));
        scene.geometries.add(
                new Sphere(new Point(66, 43, -20), 3) //
                        .setEmission(new Color(0, 0, 600).reduce(3))
                        .setMaterial(new Material().setKd(0.5).setKs(0.2)
                                .setShininess(10).setKr(0.5)));
        scene.geometries.add(
                new Sphere(new Point(72, 43, -20), 3) //
                        .setEmission(new Color(400, 0, 600).reduce(3))
                        .setMaterial(new Material().setKd(0.5).setKs(0.2)
                                .setShininess(10).setKr(0.5)));
        scene.geometries.add(
                new Sphere(new Point(69, 50, -18), 5) //
                        .setEmission(new Color(0, 600, 0).reduce(3))
                        .setMaterial(new Material().setKd(0.5).setKs(0.2)
                                .setShininess(10).setKr(0.5)));



        scene.geometries.add(
                //golf ball
                    new Sphere(new Point(110, 300, -35), 7) //
                            .setEmission(new Color(600, 500, 400).reduce(3))
                            .setMaterial(material));
        //red ball
        scene.geometries.add(
                new Sphere(new Point(-37, 100, -20), 20) //
                        .setEmission(new Color(600, 0, 0).reduce(3))
                        .setMaterial(new Material().setKd(0.5).setKs(0.2)
                                .setShininess(10).setKr(0.5)));
        //blue ball
        scene.geometries.add(
                new Sphere(new Point(-3, 100, -25), 15) //
                        .setEmission(new Color(0, 0, 600).reduce(3))
                        .setMaterial(new Material().setKd(0.5).setKs(0.2)
                                .setShininess(10).setKr(0.5)));
        //green ball
        scene.geometries.add(
                new Sphere(new Point(-15, 50, -30), 10) //
                        .setEmission(new Color(0, 600, 0).reduce(3))
                        .setMaterial(new Material().setKd(0.5).setKs(0.2)
                                .setShininess(10).setKr(0.5)));
        //see-through ball
        scene.geometries.add(
                new Sphere(new Point(-15, 50, -25), 50) //
                        .setEmission(new Color(BLACK).reduce(3))
                        .setMaterial(new Material().setKd(0.5).setKs(0.2)
                                .setShininess(10).setKr(0.5).setKt(0.999)));


        scene.geometries.add(
                //golf pole
                new Tube(1.5,new Ray(new Point(120, 300, 30), new Vector(0, 0, 1))) //
                        .setEmission(new Color(BLACK))
                         .setMaterial(new Material()
                                .setKs(0.4).setKr(0.5)));
//        scene.geometries.add(
//                //golf pole
//                new Cylinder(new Ray(new Point(120, 300, 30), new Vector(0, 0, 1)),1.5,0.001) //
//                        .setEmission(new Color(BLACK))
//                        .setMaterial(new Material()
//                                .setKs(0.4)));



        scene.geometries.add(
                //flag
                new Triangle(new Point(120,300,70),
                        new Point(120,300,50),
                        new Point(90,300,60)) //
                        .setEmission(new Color(RED))
                        .setMaterial(new Material()
                                .setKs(0.1)));
        // mirror
        scene.geometries.add(
                new Plane(
                        new Point(0, 1000, 0),    // Point on the plane
                        new Vector(0, 1, 0)       // Normal vector of the plane
                )
                        .setEmission(new Color(GRAY).reduce(1.3))
                        .setMaterial(new Material().setKs(0.08).setKd(0.005).setKr(0.7).setShininess(4000))
        );
        // Sky
        scene.geometries.add(
                new Plane(
                        new Point(0, -100000, 0),    // Point on the plane
                        new Vector(0, 1, 0)       // Normal vector of the plane
                )
                        .setEmission(new Color(BLUE).add(new Color(WHITE).reduce(3.4)))
                        .setMaterial(new Material().setKs(0.00001).setKd(0.000005))
        );
        scene.geometries.add(
                new Sphere(new Point(-100, 100, 300), 10) //
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.5).setKs(0.002)
                                .setShininess(10).setKt(0.999)));

        scene.lights.add( //
                new PointLight(new Color(WHITE).add(new Color(YELLOW).reduce(1)), new Point(-100, 100, 300)));

        camera.setImageWriter(new ImageWriter("GolfCourtMP2", 600, 600)) //
                .renderImage();
        camera.writeToImage();
    }
}
