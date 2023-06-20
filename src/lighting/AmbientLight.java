package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight extends Light{
    private Color iA; //rgb intensities
    private Double3 kA; //
    public static AmbientLight none = new AmbientLight(Color.BLACK, Double3.ZERO); //
    /**
     * Constructs an ambient light with the default color (black).
     */
    public AmbientLight() {
        super(Color.BLACK);
    }

    /**
     * Constructs an ambient light with the specified intensity and attenuation.
     * The intensity and attenuation are used to calculate the final color of the ambient light.
     *
     * @param iA The intensity of the ambient light as a Color object.
     * @param kA The attenuation factors for the ambient light as a Double3 object.
     */
    public AmbientLight(Color iA, Double3 kA) { super(iA.scale(kA));}

}
