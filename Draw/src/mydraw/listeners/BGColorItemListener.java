package mydraw.listeners;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import mydraw.ColorManager;
import mydraw.DrawGUIs;

public class BGColorItemListener implements ItemListener {
	// user selected new color => store new color in DrawGUIs
	private final DrawGUIs window;

	public BGColorItemListener(DrawGUIs window) {
		this.window = window;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// try {
		// app.setBGColor(app.getKey(cm.get(e.getItem())));
		// } catch (ColorException e1) {
		// e1.printStackTrace();
		// }
		final Color newColor = ColorManager.getColor((String) e.getItem());
		window.getDrawingPanel().setBackground(newColor);
		// List<Drawable> newQueue = new LinkedList<>();
		// List<Drawable> currentQueue = new LinkedList<>();
		// currentQueue = CommandQueue.getQueue();
		// currentQueue.remove(0);
		window.getApp().clear();

		// CommandQueue.redraw(window.getDrawingPanel().getGraphics());
		// CommandQueue.redraw(window.getBufferedImage().createGraphics());

		// window.getDrawingPanel().updateUI();
	}
}
