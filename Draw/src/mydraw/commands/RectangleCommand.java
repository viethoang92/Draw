package mydraw.commands;

import mydraw.DrawGUIs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

/**
 * Command to draw rectangles.
 */
public class RectangleCommand implements Drawable, Serializable {
    final DrawGUIs window;
    final Color color;
    final Point pressed;
    final Point released;
    final int x;
    final int y;
    final int width;
    final int height;

    /**
     * Constructor.
     *
     * @param pressed  point where mouse was pressed
     * @param released point where mouse was released
     */
    public RectangleCommand(Point pressed, Point released, DrawGUIs window, Color color) {
        this.pressed = pressed;
        this.released = released;
        this.window = window;
        this.color = color;
        x = Math.min(pressed.x, released.x);
        y = Math.min(pressed.y, released.y);
        width = Math.abs(pressed.x - released.x);
        height = Math.abs(pressed.y - released.y);
    }

    @Override
    public void draw(Graphics g) {

        if (window.getShapeManager().getCurrentDrawer().getLastx() != -1) {
            // first undraw a rubber rect
            g.setXORMode(color);
            g.setColor(window.getDrawingPanel().getBackground());
            g.drawRect(x, y, width, height);

            window.getShapeManager().getCurrentDrawer().setLastx(-1);
            window.getShapeManager().getCurrentDrawer().setLasty(-1);


        }

            g.setPaintMode();
            g.setColor(color);
            g.drawRect(x, y, width, height);


    }
}
