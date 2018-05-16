package mydraw.drawables;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

import mydraw.DrawGUIs;

/**
 * Command to draw rectangles.
 */
public class RectangleDrawable implements Drawable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final DrawGUIs window;
	final Color color;
	final Point pressed;
	final Point released;
	final int x;
	final int y;
	final int width;
	final int height;

	/**
	 * Constructor.
	 *
	 * @param pressed
	 *            point where mouse was pressed
	 * @param released
	 *            point where mouse was released
	 */
	public RectangleDrawable(Point pressed, Point released, DrawGUIs window, Color color) {
		this.pressed = pressed;
		this.released = released;
		this.window = window;
		this.color = color;
		x = Math.min(pressed.x, released.x);
		y = Math.min(pressed.y, released.y);
		width = Math.abs(pressed.x - released.x);
		height = Math.abs(pressed.y - released.y);
	}

	@Override
	public void draw(Graphics g) {

		if (window.getShapeManager().getCurrentDrawer().getLastx() != -1) {
			// first undraw a rubber rect
			g.setXORMode(color);
			g.setColor(window.getDrawingPanel().getBackground());
			g.drawRect(x, y, width, height);

			window.getShapeManager().getCurrentDrawer().setLastx(-1);
			window.getShapeManager().getCurrentDrawer().setLasty(-1);

		}

		g.setPaintMode();
		g.setColor(color);
		g.drawRect(x, y, width, height);

	}

	@Override
	public String toString() {
		final String name = "rectangle";
		final String pressed = this.pressed.x + "," + this.pressed.y;
		final String released = this.released.x + "," + this.released.y;
		final String color = this.color.toString();
		return name + ':' + pressed + ':' + released + ':' + color;
	}
}
