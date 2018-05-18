package mydraw.listeners;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import mydraw.DrawGUIs;
import mydraw.drawables.Drawable;
import mydraw.drawables.LineDrawable;

public class LineDrawer extends RectangleDrawer {

	/**
	 * Constructor.
	 *
	 * @param window
	 *            gui
	 */
	public LineDrawer(DrawGUIs window) {
		super(window);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (lastx != -1) {
			// these commands finish the rubberband mode
			final Graphics g = window.getDrawingPanel().getGraphics();
			g.setXORMode(window.getColor());
			g.setColor(window.getDrawingPanel().getBackground());
			doDraw(pressx, pressy, lastx, lasty, g);
			lastx = -1;
			lasty = -1;
		}
		// draw the final oval
		window.getApp().drawLine(new Point(pressx, pressy), new Point(e.getX(), e.getY()));
	}

	@Override
	public void doDraw(int x0, int y0, int x1, int y1, Graphics g) {
		final Point pressed = new Point(x0, y0);
		final Point released = new Point(x1, y1);
		// draw line
		final Drawable drawable = new LineDrawable(pressed, released, g.getColor());
		drawable.draw(g);
	}
}
