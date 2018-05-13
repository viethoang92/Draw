package mydraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Command to draw ovals.
 */
public final class OvalCommand implements Drawable {

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
	 *            color
	 */
	public OvalCommand(Point pressed, Point released, Color color) {
		this.pressed = pressed;
		this.released = released;
		this.color = color;
	}

	@Override
	public void draw(Graphics g) {
		final int x = Math.min(pressed.x, released.x);
		final int y = Math.min(pressed.y, released.y);
		final int width = Math.abs(pressed.x - released.x);
		final int height = Math.abs(pressed.y - released.y);

		g.setColor(color);
		g.drawOval(x, y, width, height);
	}
}
