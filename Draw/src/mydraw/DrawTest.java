package mydraw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DrawTest
{
    private Draw window;

    public DrawTest()
    {
        window = new Draw();
    }

    @BeforeEach
    public void setUp() throws ColorException
    {
        window.setFGColor("black");
        window.setBGColor("white");
        window.setWidth(500);
        window.setHeight(400);
    }

    @Test
    public void testGetBGColor() throws ColorException
    {
        assertEquals("white", window.getBGColor());
    }

    @Test
    public void testGetFGColor() throws ColorException
    {
        assertEquals("black", window.getFGColor());
    }

    @Test
    public void testGetWidth() throws ColorException
    {
        System.out.println(window.getWidth());
        assertEquals(500, window.getWidth());
    }

    @Test
    public void testGetHeight() throws ColorException
    {
        assertEquals(400, window.getHeight());
    }

    @Test
    public void testSetBGColor() throws ColorException
    {
        window.setBGColor("black");
        assertEquals("black", window.getBGColor());
    }

    @Test
    public void testSetFGColor() throws ColorException
    {
        window.setFGColor("blue");
        assertEquals("blue", window.getFGColor());
    }

    @Test
    public void testSetWidth() throws ColorException
    {
        window.setWidth(700);
        assertEquals(700, window.getWidth());
    }

    @Test
    public void testSetHeight() throws ColorException
    {
        window.setHeight(700);
        assertEquals(700, window.getHeight());
    }

    @Test
    public void testSetFGColorException() throws ColorException
    {
        Assertions.assertThrows(ColorException.class, () -> {
            window.setFGColor("bla");
        });
    }

    @Test
    public void testSetBGColorException() throws ColorException
    {

        Assertions.assertThrows(ColorException.class, () -> {
            window.setBGColor("bla");
        });
    }

    @Test
    public void testDrawing() throws IOException
    {
        window.autoDraw();
        Image image1 = window.getDrawing();
        window.writeImage(image1, "test");
        Image img = window.readImage("test");
        Image image2 = window.readImage("refbImg");
        assertTrue(compareImages((BufferedImage) image1, (BufferedImage) img));
        assertTrue(compareImages((BufferedImage) image2, (BufferedImage) img));
        assertTrue(compareImages((BufferedImage) image1, (BufferedImage) image2));
    }

    private boolean compareImages(BufferedImage image1, BufferedImage image2)
    {
        if (image1.getWidth() != image2.getWidth()
                || image1.getHeight() != image2.getHeight())
        {
            return false;
        }

        for (int x = 0; x < image1.getWidth(); x++)
        {
            for (int y = 0; y < image1.getHeight(); y++)
            {
                if (image1.getRGB(x, y) != image2.getRGB(x, y))
                {

                    return false;
                }
            }
        }

        return true;
    }

}
