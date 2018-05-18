package mydraw.listeners;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

// if this class is active, the mouse is interpreted as a pen

import mydraw.DrawGUIs;
import mydraw.drawables.LineDrawable;

/**
 * ShapeDrawer for polylines.
 */
public class ScribbleDrawer extends ShapeDrawer {
	private int lastx;
	private int lasty;

	private final List<Point> points = new ArrayList<>();
	private final DrawGUIs window;

	/**
	 * Constructor.
	 *
	 * @param window
	 *            gui
	 */
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
	public void mouseReleased(MouseEvent e) {
		window.getApp().drawPolyLine(points);
		points.clear();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		final Point pressed = new Point(lastx, lasty);
		final Point released = new Point(e.getX(), e.getY());
		final LineDrawable cmd = new LineDrawable(pressed, released, window.getColor());
		cmd.draw(window.getDrawingPanel().getGraphics());
		lastx = e.getX();
		lasty = e.getY();
		points.add(new Point(lastx, lasty));
	}
}