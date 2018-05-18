package mydraw.drawables;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import mydraw.ColorManager;

/**
 * Command to draw polylines.
 */
public class LineDrawable implements Drawable {

	private final Point pressed;
	private final Point released;

	private final Color color;

	/**
	 * Constructor.
	 *
	 * @param pressed
	 *            point where mouse was pressed
	 * @param released
	 *            point where mouse was released
	 * @param color
	 *            color of this shape
	 */
	public LineDrawable(Point pressed, Point released, Color color) {
		this.pressed = pressed;
		this.released = released;
		this.color = color;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.drawLine(pressed.x, pressed.y, released.x, released.y);
	}

	@Override
	public String toString() {
		final String name = "line";
		final String pressed = this.pressed.x + "," + this.pressed.y;
		final String released = this.released.x + "," + this.released.y;
		final String color = ColorManager.getColor(this.color);
		return name + ':' + pressed + ':' + released + ':' + color;
	}
}