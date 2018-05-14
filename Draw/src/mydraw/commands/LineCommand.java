package mydraw.commands;


import mydraw.DrawGUIs;

import java.awt.*;
import java.io.Serializable;

/**
 * Command to draw polylines.
 */
public class LineCommand implements Drawable, Serializable {

    private final int x;
    private final int y;

    private final DrawGUIs window;

    /**
     * Constructor.
     *
     */
    public LineCommand(int x, int y, DrawGUIs window) {
        this.x = x;
        this.y = y;
        this.window = window;
    }

    @Override
    public void draw(Graphics g) {
        g.setPaintMode();
        g.setColor(window.getColor());
        g.drawLine(window.getShapeManager().getCurrentDrawer().getLastx(), window.getShapeManager().getCurrentDrawer().getLasty(), x, y);
        window.getShapeManager().getCurrentDrawer().setLastx(x);
        window.getShapeManager().getCurrentDrawer().setLasty(y);

    }
}