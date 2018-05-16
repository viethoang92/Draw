package mydraw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link Draw}.
 */
final class DrawTest {

    // TODO add tests for new functions
    private final Draw window;

    DrawTest() {
        window = new Draw();
    }

    @BeforeEach
    public void setUp() throws ColorException {
        window.setFGColor("black");
        window.setBGColor("white");
        window.setWidth(500);
        window.setHeight(400);
    }

    @Test
    public void testGetBGColor(){
        assertEquals("white", window.getBGColor());
    }

    @Test
    public void testGetFGColor(){
        assertEquals("black", window.getFGColor());
    }

    @Test
    public void testGetWidth(){
        assertEquals(500, window.getWidth());
    }

    @Test
    public void testGetHeight()  {
        assertEquals(400, window.getHeight());
    }

    @Test
    public void testSetBGColor() throws ColorException {
        window.setBGColor("black");
        assertEquals("black", window.getBGColor());
    }

    @Test
    public void testSetFGColor() throws ColorException {
        window.setFGColor("blue");
        assertEquals("blue", window.getFGColor());
    }

    @Test
    public void testSetWidth() {
        window.setWidth(700);
        assertEquals(700, window.getWidth());
    }

    @Test
    public void testSetHeight() {
        window.setHeight(700);
        assertEquals(700, window.getHeight());
    }

    @Test
    public void testSetFGColorException() {
        Assertions.assertThrows(ColorException.class, () -> {
            window.setFGColor("bla");
        });
    }

    @Test
    public void testSetBGColorException() {
        Assertions.assertThrows(ColorException.class, () -> {
            window.setBGColor("bla");
        });
    }

    @Test
    public void testDrawing() throws IOException {
        window.autoDraw();
        Image image1 = window.getDrawing();
        window.writeImage(image1, "test.png");
        Image img = window.readImage("test.png");
        Image image2 = window.readImage("refbImg.png");
        assertTrue(compareImages((BufferedImage) image1, (BufferedImage) img));
        assertTrue(compareImages((BufferedImage) image2, (BufferedImage) img));
        assertTrue(compareImages((BufferedImage) image1, (BufferedImage) image2));
    }

    private boolean compareImages(BufferedImage image1, BufferedImage image2) {
        if (image1.getWidth() != image2.getWidth()
                || image1.getHeight() != image2.getHeight()) {
            return false;
        }

        for (int x = 0; x < image1.getWidth(); x++) {
            for (int y = 0; y < image1.getHeight(); y++) {
                if (image1.getRGB(x, y) != image2.getRGB(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    
    @Test
    public void testUndoRedo() throws IOException, ColorException
    {
        window.setBGColor("blue");
        window.clear();
        window.drawOval(new Point(50, 50), new Point(200, 200));
        Image undoTestImg = window.getDrawing();
        window.writeImage(undoTestImg, "undoTestImg.png");
        
        window.setFGColor("red");
        window.drawRectangle(new Point(100, 100), new Point(300, 300));
        window.setFGColor("Green");
        final List<Point> list = Arrays.asList(new Point(50, 50), new Point(100, 150), new Point(80, 80));
        window.drawPolyLine(list);
        Image redoTestImg = window.getDrawing();
        window.writeImage(redoTestImg, "redoTestImg.png");
        
        window.undo();
        window.undo();
        Image undoImg = window.getDrawing();
        assertTrue(compareImages((BufferedImage) undoImg, (BufferedImage) undoTestImg));
        
        window.redo();
        window.redo();
        Image redoImg = window.getDrawing();
        assertTrue(compareImages((BufferedImage) redoImg, (BufferedImage) redoTestImg));
        
        
    }

}
