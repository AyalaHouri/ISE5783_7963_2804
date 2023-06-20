package lighting;
/**
 * The abstract Light class represents a generic light source.
 * Subclasses of Light can define specific types of lights, such as lamps or LEDs.
 *
 * <p>This class provides a basic template for implementing light-related functionality.
 * Concrete subclasses must extend this class and provide their own implementations.
 *
 * @since 1.0
 */
import primitives.Color;

public abstract class Light {
    private Color intensity;
    protected Light(Color intensity){
        this.intensity=intensity;
    }

    /**
     * Retrieves the intensity of the light.
     *
     * @return The intensity of the light as a Color object.
     * @since 1.0
     */
    public Color getIntensity() {
        return intensity;
    }


}

