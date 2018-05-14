package mydraw.listeners;

// if this class is active, rectangles are drawn

import mydraw.DrawGUIs;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * ShapeDrawer for rectangles.
 */
public class RectangleDrawer extends ShapeDrawer {
    final DrawGUIs window;
    int pressx, pressy;
    public int lastx = -1, lasty = -1;
    public RectangleDrawer(DrawGUIs window){
        this.window = window;
    }

    // mouse pressed => fix first corner of rectangle
    @Override
    public void mousePressed(MouseEvent e) {

        pressx = e.getX();
        pressy = e.getY();
    }

    // mouse released => fix second corner of rectangle
    // and draw the resulting shape
    @Override
    public void mouseReleased(MouseEvent e) {
        if(lastx != -1)
        {

        }
        // these commands finish the rubberband mode
        // draw the final rectangle
        window.getApp().drawRectangle(new Point(pressx, pressy),
                new Point(e.getX(), e.getY()));

    }

    // mouse released => temporarily set second corner of rectangle
    // draw the resulting shape in "rubber-band mode"
    @Override
    public void mouseDragged(MouseEvent e) {
        final Graphics g = window.getDrawingPanel().getGraphics();
        // these commands set the rubberband mode
        g.setXORMode(window.getColor());
        g.setColor(window.getDrawingPanel().getBackground());
        if (lastx != -1) {
            // first undraw previous rubber rect
            doDraw(pressx, pressy, lastx, lasty, g);
        }
        lastx = e.getX();
        lasty = e.getY();
        // draw new rubber rect
        doDraw(pressx, pressy, lastx, lasty, g);
    }

    /**
     * Draws the shape.
     *
     * @param x0 first x-coordinate
     * @param y0 first y-coordinate
     * @param x1 second x-coordinate
     * @param y1 second y-coordinate
     * @param g  Graphics object to draw on
     */
    public void doDraw(int x0, int y0, int x1, int y1, Graphics g) {
        // calculate upperleft and width/height of rectangle
        final int x = Math.min(x0, x1);
        final int y = Math.min(y0, y1);
        final int w = Math.abs(x1 - x0);
        final int h = Math.abs(y1 - y0);
        // draw rectangle
        g.drawRect(x, y, w, h);
    }

    @Override
    public int getLastx(){
        return lastx;
    }

    @Override
    public int getLasty(){
        return lasty;
    }

    @Override
    public void setLastx(int x) {
        lastx = x;
    }

    @Override
    public void setLasty(int y) {
        lasty = y;
    }
}