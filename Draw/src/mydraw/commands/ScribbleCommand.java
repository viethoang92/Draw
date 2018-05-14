package mydraw.commands;

import mydraw.DrawGUIs;

import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Command to draw polylines.
 */
public class ScribbleCommand implements Drawable, Serializable {

	private final List<Point> points;

	private final DrawGUIs window;

	/**
	 * Constructor.
	 *
	 * @param points
	 *            points of the polyline.
	 */
	public ScribbleCommand(List<Point> points, DrawGUIs window) {
		this.points = new ArrayList<>();
		this.points.addAll(points);
		this.window = window;
	}

	@Override
	public void draw(Graphics g) {
		final int[] x = new int[points.size()];
		final int[] y = new int[points.size()];
		for (int i = 0; i < points.size(); i++) {
			final Point p = points.get(i);
			x[i] = p.x;
			y[i] = p.y;
		}
		g.setPaintMode();
		g.setColor(window.getColor());
		g.drawPolyline(x, y, points.size());
	}
}
