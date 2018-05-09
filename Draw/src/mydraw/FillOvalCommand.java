package mydraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class FillOvalCommand implements Drawable
{
    private final Point pressed;

    private final Point released;

    private final Color color;

    public FillOvalCommand(Point pressed, Point released, Color color) {
        this.pressed = pressed;
        this.released = released;
        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        final int x = Math.min(pressed.x, released.x);
        final int y = Math.min(pressed.y, released.y);
        final int width = Math.abs(pressed.x - released.x);
        final int height = Math.abs(pressed.y - released.y);

        g.setPaintMode();
        g.setColor(color);
        g.fillOval(x, y, width, height);
    }
}
