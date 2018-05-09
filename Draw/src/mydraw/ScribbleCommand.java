package mydraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

public class ScribbleCommand implements Drawable {

	private final List<Point> points;

	private final Color color;

	public ScribbleCommand(List<Point> points, Color color) {
		this.points = points;
		this.color = color;
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
}
