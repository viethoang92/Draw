package mydraw.drawables;


import mydraw.DrawGUIs;

import java.awt.*;
import java.io.Serializable;

/**
 * Command to draw polylines.
 */
public class LineDrawable implements Drawable, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final int x;
    private final int y;

    private final DrawGUIs window;

    /**
     * Constructor.
     *
     */
    public LineDrawable(int x, int y, DrawGUIs window) {
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