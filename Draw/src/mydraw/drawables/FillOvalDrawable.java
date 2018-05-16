package mydraw.drawables;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import mydraw.ColorManager;
import mydraw.DrawGUIs;

public class FillOvalDrawable extends OvalDrawable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public FillOvalDrawable(Point pressed, Point released, DrawGUIs window, Color color) {
		super(pressed, released, window, color);
	}

	@Override
	public void draw(Graphics g) {
		if (window.getShapeManager().getCurrentDrawer().getLastx() != -1) {
			// first undraw a rubber rect
			g.setXORMode(color);
			g.setColor(window.getDrawingPanel().getBackground());
			g.drawOval(x, y, width, height);

			window.getShapeManager().getCurrentDrawer().setLastx(-1);
			window.getShapeManager().getCurrentDrawer().setLasty(-1);

		}
		g.setPaintMode();
		g.setColor(color);
		g.fillOval(x, y, width, height);
	}

	@Override
	public String toString() {
		final String name = "filledOval";
		final String pressed = this.pressed.x + "," + this.pressed.y;
		final String released = this.released.x + "," + this.released.y;
		final String color = ColorManager.getColor(this.color);
		return name + ':' + pressed + ':' + released + ':' + color;
	}
}
