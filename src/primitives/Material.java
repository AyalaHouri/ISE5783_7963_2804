package primitives;

/**
 * The Material class represents the material of an object.
 * It specifies the attenuation factors and shininess of the material.
 * <p>
 * The attenuation factors control the reflection properties of the material,
 * and the shininess factor determines how shiny the material appears.
 * </p>
 * <p>
 * This class is used in conjunction with rendering and lighting calculations.
 * </p>
 *
 * @author Aviel Buta
 * @author Yakir Yohanan
 */
public class Material {

    /**
     * The diffuse attenuation factor.
     * Determines the scattering of light rays from the surface.
     */
    public double kD = 0;

    /**
     * The specular attenuation factor.
     * Represents the reflectance of the light source on the surface.
     */
    public double kS = 0;

    /**
     * The shininess factor of the material.
     * Determines the shininess appearance of the material.
     */
    public int nShininess = 0;

    /**
     * Sets the diffuse attenuation factor.
     *
     * @param kD The diffuse attenuation factor to set.
     * @return This Material object.
     */
    public Material setkD(double kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Sets the specular attenuation factor.
     *
     * @param kS The specular attenuation factor to set.
     * @return This Material object.
     */
    public Material setkS(double kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Sets the shininess factor of the material.
     *
     * @param nShininess The shininess factor to set.
     * @return This Material object.
     */
    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
