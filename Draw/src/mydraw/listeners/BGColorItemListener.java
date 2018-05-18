package mydraw.listeners;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import mydraw.ColorManager;
import mydraw.DrawGUIs;
import mydraw.drawables.Drawable;
import mydraw.drawables.DrawableQueue;
import mydraw.drawables.FillRectDrawable;

public class BGColorItemListener implements ItemListener {
	// user selected new color => store new color in DrawGUIs
	private final DrawGUIs window;

	/**
	 * Constructor.
	 *
	 * @param window
	 *            gui
	 */
	public BGColorItemListener(DrawGUIs window) {
		this.window = window;
		final Drawable cmd = new FillRectDrawable(new Point(0, 0),
				new Point(window.getDrawingPanel().getWidth(), window.getDrawingPanel().getHeight()), Color.WHITE);
		DrawableQueue.add(cmd);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		final Color newColor = ColorManager.getColor((String) e.getItem());
		window.getDrawingPanel().setBackground(newColor);

		DrawableQueue.remove(0);
		final Drawable cmd = new FillRectDrawable(new Point(0, 0),
				new Point(window.getDrawingPanel().getWidth(), window.getDrawingPanel().getHeight()),
				window.getDrawingPanel().getBackground());
		DrawableQueue.add(0, cmd);
		DrawableQueue.redraw(window.getDrawingPanel().getGraphics());
	}
}
