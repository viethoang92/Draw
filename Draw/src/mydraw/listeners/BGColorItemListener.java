package mydraw.listeners;

import mydraw.DrawGUIs;
import mydraw.drawables.Drawable;
import mydraw.drawables.DrawableQueue;
import mydraw.drawables.FillRectDrawable;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedList;
import java.util.List;

public class BGColorItemListener implements ItemListener
{
    // user selected new color => store new color in DrawGUIs
    private final DrawGUIs window;

    public BGColorItemListener(DrawGUIs window)
    {
        this.window = window;
    }

    @Override
    public void itemStateChanged(ItemEvent e)
    {
        Color newColor = window.getColorMap()
            .get(e.getItem());
        window.getDrawingPanel()
            .setBackground(newColor);

        DrawableQueue.remove(0);
        final FillRectDrawable cmd = new FillRectDrawable(new Point(0, 0),
                new Point(window.getDrawingPanel()
                    .getWidth(),
                        window.getDrawingPanel()
                            .getHeight()),
                window, window.getDrawingPanel()
                    .getBackground());
        DrawableQueue.add(0, cmd);
        DrawableQueue.redraw(window.getDrawingPanel()
            .getGraphics());
        DrawableQueue.redraw(window.getBufferedImage()
            .createGraphics());
    }
}
