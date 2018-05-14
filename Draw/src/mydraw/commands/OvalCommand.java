package mydraw.commands;

import mydraw.DrawGUIs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Command to draw ovals.
 */
public class OvalCommand extends RectangleCommand{

    public OvalCommand(Point pressed, Point released, DrawGUIs window, Color color) {
        super(pressed, released, window, color);
    }

    @Override
    public void draw(Graphics g) {
        g.setPaintMode();
        g.setColor(color);
        g.drawOval(x, y, width, height);
    }
}
