package mydraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

/**
 * Command to draw ovals.
 */
public class OvalCommand extends RectangleCommand{

    public OvalCommand(Point pressed, Point released, Color color) {
        super(pressed, released, color);
    }

    @Override
    public void draw(Graphics g) {
        g.setPaintMode();
        g.setColor(color);
        g.drawOval(x, y, width, height);
    }
}
