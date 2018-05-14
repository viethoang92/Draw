package mydraw.listeners;

import mydraw.DrawGUIs;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class FGColorItemListener implements ItemListener {
    // user selected new color => store new color in DrawGUIs
    private final DrawGUIs window;

    public FGColorItemListener(DrawGUIs window)
    {
        this.window = window;
    }
    @Override
    public void itemStateChanged(ItemEvent e) {



        final Color newColor = window.getColorMap().get(e.getItem());
        if (newColor != null) {
            window.setColor(newColor);
        }

    }
}
