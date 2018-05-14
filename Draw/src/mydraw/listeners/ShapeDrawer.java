package mydraw.listeners;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Listens to Mouse actions but ignores mouse movements
 */
public abstract class ShapeDrawer extends MouseAdapter
        implements MouseMotionListener {

    @Override
    public void mouseMoved(MouseEvent e) {
        /* ignore */
    }

    public abstract int getLastx();

    public abstract int getLasty();

    public abstract void setLastx(int x);

    public abstract void setLasty(int y);

}