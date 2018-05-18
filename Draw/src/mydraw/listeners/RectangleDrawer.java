package mydraw.listeners;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

// if this class is active, rectangles are drawn

import mydraw.DrawGUIs;
import mydraw.drawables.Drawable;
import mydraw.drawables.RectangleDrawable;

/**
 * ShapeDrawer for rectangles.
 */
public class RectangleDrawer extends ShapeDrawer {
	final DrawGUIs window;
	int pressx, pressy;
	protected int lastx = -1;
	protected int lasty = -1;

	/**
	 * Constructor.
	 *
	 * @param window
	 *            gui
	 */
	public RectangleDrawer(DrawGUIs window) {
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
		if (lastx != -1) {
			// these commands finish the rubberband mode
			final Graphics g = window.getDrawingPanel().getGraphics();
			g.setXORMode(window.getColor());
			g.setColor(window.getDrawingPanel().getBackground());
			doDraw(pressx, pressy, lastx, lasty, g);
			lastx = -1;
			lasty = -1;
		}
		// draw the final rectangle
		window.getApp().drawRectangle(new Point(pressx, pressy), new Point(e.getX(), e.getY()));

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
	 * @param x0
	 *            first x-coordinate
	 * @param y0
	 *            first y-coordinate
	 * @param x1
	 *            second x-coordinate
	 * @param y1
	 *            second y-coordinate
	 * @param g
	 *            Graphics object to draw on
	 */
	public void doDraw(int x0, int y0, int x1, int y1, Graphics g) {
		// calculate upperleft and width/height of rectangle
		final Point pressed = new Point(x0, y0);
		final Point released = new Point(x1, y1);
		// draw rectangle
		final Drawable drawable = new RectangleDrawable(pressed, released, g.getColor());
		drawable.draw(g);
	}

}