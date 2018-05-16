package mydraw.listeners;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import mydraw.ColorManager;
import mydraw.DrawGUIs;

public class FGColorItemListener implements ItemListener {
	// user selected new color => store new color in DrawGUIs
	private final DrawGUIs window;

	public FGColorItemListener(DrawGUIs window) {
		this.window = window;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		final Color newColor = ColorManager.getColor((String) e.getItem());
		if (newColor != null) {
			window.setColor(newColor);
		}

	}
}
