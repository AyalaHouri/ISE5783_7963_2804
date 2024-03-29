package scene;

import lighting.AmbientLight;
import geometries.Geometries;
import primitives.Color;
import  lighting.LightSource;

import java.util.LinkedList;
import java.util.List;

public class Scene {

    private final String name;
    public final Color background;
    public final Geometries geometries;
    private AmbientLight ambientLight;
    public List<LightSource> lights = new LinkedList<LightSource>();

    public Scene(SceneBuilder builder) {
        name = builder.name;
        background = builder.background;
        ambientLight = builder.ambientLight;
        geometries = builder.geometries;
    }

    public String getName() {
        return name;
    }

    public Color getBackground() {
        return background;
    }

    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public static class SceneBuilder {

        private final String name;
        private Color background = Color.BLACK;
        private AmbientLight ambientLight = AmbientLight.none;
        private Geometries geometries = new Geometries();

        public SceneBuilder(String name) {
            this.name = name;
        }

        public SceneBuilder setBackground(Color background) {
            this.background = background;
            return this;
        }

        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }

        public SceneBuilder setGeometries(Geometries geometries) {
            this.geometries = geometries;
            return this;
        }

        public Scene build() {
            return new Scene(this);
        }

    }
    public Geometries getGeometries() {
        return geometries;
    }
    public List<LightSource> getLights() {
        return lights;
    }
    public Scene setLightSourceList(List<LightSource> lightSourceList) {
        lights = lightSourceList;
        return this;
    }
}
