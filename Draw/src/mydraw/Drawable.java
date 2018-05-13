package mydraw;

import java.awt.Graphics;

/**
 * Interface for drawable shape commands.
 */
public interface Drawable {

	/**
	 * Draws this shape.
	 *
	 * @param g
	 *            Graphics object to draw on
	 */
	void draw(Graphics g);
}
