package mydraw.listeners;

// if this class is active, ovals are drawn

import mydraw.DrawGUIs;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * ShapeDrawer for ovals.
 */
class OvalDrawer extends RectangleDrawer
{

    public OvalDrawer(DrawGUIs window) {
        super(window);
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        // these commands finish the rubberband mode
        // draw the final oval
        window.getApp().drawOval(new Point(pressx, pressy),
                new Point(e.getX(), e.getY()));
    }

    @Override
    public void doDraw(int x0, int y0, int x1, int y1, Graphics g)
    {
        final int x = Math.min(x0, x1);
        final int y = Math.min(y0, y1);
        final int w = Math.abs(x1 - x0);
        final int h = Math.abs(y1 - y0);
        // draw oval instead of rectangle
        g.drawOval(x, y, w, h);
    }
}
