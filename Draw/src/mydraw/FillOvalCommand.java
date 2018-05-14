package mydraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class FillOvalCommand extends OvalCommand {

    public FillOvalCommand(Point pressed, Point released, Color color) {
        super(pressed, released, color);
    }

    @Override
    public void draw(Graphics g) {
        g.setPaintMode();
        g.setColor(color);
        g.fillOval(x, y, width, height);
    }
}
