package mydraw.drawables;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import mydraw.ColorManager;

/**
 * Command to draw polylines.
 */
public class PolylineDrawable implements Drawable {

	private final List<Point> points;

	private final Color color;

	/**
	 * Constructor.
	 *
	 * @param points
	 *            points of the polyline.
	 * @param color
	 *            color of this shape
	 */
	public PolylineDrawable(List<Point> points, Color color) {
		this.color = color;
		this.points = new ArrayList<>();
		this.points.addAll(points);
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
		g.setColor(color);
		g.drawPolyline(x, y, points.size());
	}

	@Override
	public String toString() {
		final String name = "polyline";
		final String color = ColorManager.getColor(this.color);
		String points = "";
		for (final Point p : this.points) {
			points += ";" + p.x + "," + p.y;
		}
		final String result = name + ':' + points.substring(1) + ':' + color;
		return result;
	}
}
