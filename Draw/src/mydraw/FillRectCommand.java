package mydraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

/**
 * Command to draw a filled rectangle.
 */
public class FillRectCommand extends RectangleCommand {

    public FillRectCommand(Point pressed, Point released, Color color) {
        super(pressed, released, color);
    }

    @Override
    public void draw(Graphics g) {
        g.setPaintMode();
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }
}
