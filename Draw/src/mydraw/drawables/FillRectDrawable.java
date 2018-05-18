package mydraw.drawables;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import mydraw.ColorManager;

/**
 * Command to draw a filled rectangle.
 */
public class FillRectDrawable extends RectangleDrawable {

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
	public FillRectDrawable(Point pressed, Point released, Color color) {
		super(pressed, released, color);
	}

	@Override
	public void draw(Graphics g) {
		final int x = Math.min(pressed.x, released.x);
		final int y = Math.min(pressed.y, released.y);
		final int w = Math.abs(pressed.x - released.x);
		final int h = Math.abs(pressed.y - released.y);
		g.setColor(color);
		g.fillRect(x, y, w, h);
	}

	@Override
	public String toString() {
		final String name = "filledRectangle";
		final String pressed = this.pressed.x + "," + this.pressed.y;
		final String released = this.released.x + "," + this.released.y;
		final String color = ColorManager.getColor(this.color);
		return name + ':' + pressed + ':' + released + ':' + color;
	}
}
