package mydraw.listeners;

import mydraw.DrawGUIs;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * ShapeDrawer for filled ovals
 */
class FillOvalDrawer extends RectangleDrawer

{
    public FillOvalDrawer(DrawGUIs window) {
        super(window);
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        final Graphics g = window.getDrawingPanel().getGraphics();
        final Graphics gb = window.getBufferedImage()
                .createGraphics();

        if (lastx != -1)
        {
            // first undraw a rubber rect
            g.setXORMode(window.getColor());
            g.setColor(window.getDrawingPanel().getBackground());
            doDraw(pressx, pressy, lastx, lasty, g);

            gb.setXORMode(window.getColor());
            gb.setColor(window.getDrawingPanel().getBackground());
            doDraw(pressx, pressy, lastx, lasty, g);
            lastx = -1;
            lasty = -1;
        }
        // these commands finish the rubberband mode
        // draw the final oval
        window.getApp().drawFilledOval(new Point(pressx, pressy),
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
        g.fillOval(x, y, w, h);
    }
}
