package mydraw.commands;

import mydraw.DrawGUIs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class FillOvalCommand extends OvalCommand {

    public FillOvalCommand(Point pressed, Point released, DrawGUIs window, Color color) {
        super(pressed, released, window, color);
    }

    @Override
    public void draw(Graphics g) {
        g.setPaintMode();
        g.setColor(color);
        g.fillOval(x, y, width, height);
    }
}
