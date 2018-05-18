package mydraw.drawables;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import mydraw.ColorManager;

/**
 * Command to draw rectangles.
 */
public class RectangleDrawable implements Drawable {
	final Color color;
	final Point pressed;
	final Point released;

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
	public RectangleDrawable(Point pressed, Point released, Color color) {
		this.pressed = pressed;
		this.released = released;
		this.color = color;
	}

	@Override
	public void draw(Graphics g) {
		final int x = Math.min(pressed.x, released.x);
		final int y = Math.min(pressed.y, released.y);
		final int w = Math.abs(pressed.x - released.x);
		final int h = Math.abs(pressed.y - released.y);
		g.setColor(color);
		g.drawRect(x, y, w, h);
	}

	@Override
	public String toString() {
		final String name = "rectangle";
		final String pressed = this.pressed.x + "," + this.pressed.y;
		final String released = this.released.x + "," + this.released.y;
		final String color = ColorManager.getColor(this.color);
		return name + ':' + pressed + ':' + released + ':' + color;
	}

}
