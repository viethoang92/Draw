package mydraw.listeners;

// if this class is active, the mouse is interpreted as a pen

import mydraw.DrawGUIs;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * ShapeDrawer for polylines.
 */
public class ScribbleDrawer extends ShapeDrawer
{
    int lastx, lasty;

    private final List<Point> points = new ArrayList<>();
    private DrawGUIs window;

    public ScribbleDrawer(DrawGUIs window) {
        this.window = window;
    }
    @Override
    public void mousePressed(MouseEvent e) {
        lastx = e.getX();
        lasty = e.getY();
        points.add(new Point(lastx, lasty));
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        window.getApp().drawPolyLine(points);
        points.clear();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        window.getApp().drawLine(e.getX(),e.getY());
        points.add(new Point(lastx, lasty));
    }


    @Override
    public int getLastx() {
        return lastx;
    }

    @Override
    public int getLasty() {
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