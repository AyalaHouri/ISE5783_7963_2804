package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Point;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for renderer.ImageWriter class
 * @author Ayala Houri and Shani Zegal
 */
class ImageWriterTests {
    /**
     * Test method for {@link ImageWriter#writeToImage()} (Point)}
     */
    @Test
    public void writeToImageTest() {
        ImageWriter image1 = new ImageWriter("Test_image", 800, 500);

        for (int i = 0; i < 800; i++) {
            for (int j = 0; j < 500; j++) {
                // nX/16 = 50; nY/10 = 50
                if ((i % 50 == 0) || (j % 50 == 0))
                    image1.writePixel(i, j, new Color(0,233,45));
                else
                    image1.writePixel(i, j, new Color(4, 6, 140));
            }
        }
        image1.writeToImage();
    }
}