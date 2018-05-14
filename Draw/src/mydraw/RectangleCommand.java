package mydraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Command to draw rectangles.
 */
public class RectangleCommand implements Drawable
{

    final Point pressed;

    final Point released;

    final Color color;

    final int x;
    final int y;
    final int width;
    final int height;

    /**
     * Constructor.
     *
     * @param pressed
     *            point where mouse was pressed
     * @param released
     *            point where mouse was released
     * @param color
     *            color
     */
    public RectangleCommand(Point pressed, Point released, Color color)
    {
        this.pressed = pressed;
        this.released = released;
        this.color = color;

        x = Math.min(pressed.x, released.x);
        y = Math.min(pressed.y, released.y);
        width = Math.abs(pressed.x - released.x);
        height = Math.abs(pressed.y - released.y);
    }

    @Override
    public void draw(Graphics g)
    {
        g.setPaintMode();
        g.setColor(color);
        g.drawRect(x, y, width, height);

    }
}
