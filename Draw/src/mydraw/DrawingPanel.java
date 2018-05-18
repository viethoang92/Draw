package mydraw;

import java.awt.Graphics;

import javax.swing.JPanel;

import mydraw.drawables.DrawableQueue;

/**
 * Drawing panel class to override paintComponent method.
 */
final class DrawingPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.println("paint component redraw");
		DrawableQueue.redraw(g);
	}
}
