package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight {
    private Color iA; //rgb intensities
    private Double3 kA; //
    private Color intensity; //color intensity
    public static AmbientLight none = new AmbientLight(Color.BLACK, Double3.ZERO); //

    public AmbientLight(Color iA, Double3 kA ) {
        this.iA = new Color(iA.getColor());
        this.kA = kA;

        intensity = iA.scale(kA);
    }
//    public AmbientLight(double kA ) {
//        this.kA = kA;
//    }
    public Color getIntensity() {
        return this.intensity;
    }
}
