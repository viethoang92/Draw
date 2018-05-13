package mydraw;

import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Drawing panel class to override paintComponent method.
 */
public final class DrawingPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		CommandQueue.redraw(g);
	}
}
