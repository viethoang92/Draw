package mydraw;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.RenderedImage;
import java.io.*;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import mydraw.drawables.*;

public class Draw {
    private final DrawGUIs window;

    public Draw() {
        this.window = new DrawGUIs(this);
    }

    public static void main(String[] args) {
        new Draw();
    }

    /**
     * Returns current drawing color.
     *
     * @return current drawing color
     */
    public String getFGColor() {
        return getKey(window.getColor());
    }

    /**
     * Sets foreground color.
     *
     * @param new_color new color to set
     * @throws ColorException if color is not in Choice
     */
    public void setFGColor(String new_color) throws ColorException {
        final Color color = window.getColorMap().get(new_color.toLowerCase());
        if (color != null) {
            window.setColor(color);
        }
        else
            throw new ColorException();
    }

    /**
     * Returns the width of the window.
     *
     * @return width
     */
    public int getWidth() {
        return window.getSize().width;
    }

    /**
     * Returns the height of the window.
     *
     * @return height
     */
    public int getHeight() {
        return window.getSize().height;
    }

    /**
     * Sets the height of the window.
     *
     * @param height new height
     */
    public void setHeight(int height) {
        window.setSize(window.getSize().width, height);
    }

    /**
     * Sets the width of the window.
     *
     * @param width new width
     */
    public void setWidth(int width) {
        window.setSize(width, window.getSize().height);
    }

    /**
     * Sets background color
     *
     * @param new_color new background color
     * @throws ColorException invalid color
     */
    public void setBGColor(String new_color) throws ColorException {
        final Color color = window.getColorMap()
                .get(new_color.toLowerCase());
        if (color != null) {
            window.getDrawingPanel().setBackground(color);

        } else
            throw new ColorException();
    }

    /**
     * Returns the background color of the window.
     *
     * @return background color
     */
    public String getBGColor() {

        return getKey(window.getDrawingPanel().getBackground());
    }

    /**
     * Draws a rectangle.
     *
     * @param upper_left  top left corner
     * @param lower_right bottom right corner
     */
    public void drawRectangle(Point upper_left, Point lower_right) {
        final RectangleDrawable cmd = new RectangleDrawable(upper_left, lower_right, window, window.getColor());
        cmd.draw(window.getDrawingPanel().getGraphics());
        cmd.draw(window.getBufferedImage().createGraphics());
        DrawableQueue.add(cmd);
    }

    /**
     * Draws an oval.
     *
     * @param upper_left  top left corner
     * @param lower_right bottom right corner
     */
    public void drawOval(Point upper_left, Point lower_right) {
        final OvalDrawable cmd = new OvalDrawable(upper_left, lower_right, window, window.getColor());
        cmd.draw(window.getDrawingPanel().getGraphics());
        cmd.draw(window.getBufferedImage().createGraphics());
        DrawableQueue.add(cmd);
    }

    /**
     * Draws a polyline.
     *
     * @param points list of points
     */
    public void drawPolyLine(java.util.List<Point> points) {
        final ScribbleDrawable cmd = new ScribbleDrawable(points, window.getColor());
        cmd.draw(window.getDrawingPanel().getGraphics());
        cmd.draw(window.getBufferedImage().createGraphics());
        DrawableQueue.add(cmd);

    }

    /**
     * Draws a polyline.
     *
     * @param x x
     * @param y y
     */
    public void drawLine(int x, int y) {
        final LineDrawable cmd = new LineDrawable(x, y , window);
        cmd.draw(window.getDrawingPanel().getGraphics());
        cmd.draw(window.getBufferedImage().createGraphics());
    }
    /**
     * Draws a filled rectangle.
     *
     * @param upper_left  top left corner
     * @param lower_right bottom right corner
     */
    public void drawFilledRectangle(Point upper_left, Point lower_right) {
        final FillRectDrawable cmd = new FillRectDrawable(upper_left, lower_right, window, window.getColor());
        cmd.draw(window.getDrawingPanel().getGraphics());
        cmd.draw(window.getBufferedImage().createGraphics());
        DrawableQueue.add(cmd);
    }

    /**
     * Draws a filled oval.
     *
     * @param upper_left  top left corner
     * @param lower_right bottom right corner
     */
    public void drawFilledOval(Point upper_left, Point lower_right) {
        final FillOvalDrawable cmd = new FillOvalDrawable(upper_left, lower_right, window, window.getColor());
        cmd.draw(window.getDrawingPanel().getGraphics());
        cmd.draw(window.getBufferedImage().createGraphics());
        DrawableQueue.add(cmd);
    }

    /**
     * Returns the current drawing.
     *
     * @return drawing
     */
    public Image getDrawing() {
        return window.getBufferedImage();
    }

    /**
     * Clears drawing panel by drawing a filled rectangle
     */
    public void clear() {
        final FillRectDrawable cmd = new FillRectDrawable(new Point(0, 0), new Point(window.getDrawingPanel().getWidth(), window.getDrawingPanel().getHeight()),
                window, window.getDrawingPanel().getBackground());
        cmd.draw(window.getDrawingPanel().getGraphics());
        cmd.draw(window.getBufferedImage().createGraphics());
        DrawableQueue.add(cmd);
    }

    /**
     * Draws a predefined image.
     */
    public void autoDraw() {
        // TODO add new functionality
        try {
            setBGColor("blue");
            clear();
            drawOval(new Point(50, 50), new Point(200, 200));
            setFGColor("red");
            drawRectangle(new Point(100, 100), new Point(300, 300));
            setFGColor("Green");
            final List<Point> list = Arrays.asList(new Point(50, 50), new Point(100, 150), new Point(80, 80));
            drawPolyLine(list);
            
        } catch (final ColorException e) {
            e.printStackTrace();
        }

    }

    /**
     * Exports an image.
     *
     * @param img      image
     * @param filename filename
     * @throws IOException if an input or output expression occurred
     */
    public void writeImage(Image img, String filename) throws IOException {
        ImageIO.write((RenderedImage) img
                , "PNG", new File(filename));
    }

    /**
     * Reads an image.
     *
     * @param filename filename
     * @return Image image
     * @throws IOException if an input or output expression occurred
     */
    public Image readImage(String filename) throws IOException {
        return ImageIO.read(new File(filename));
    }

    /**
     * Saves a CommandQueue as a .dat file
     *
     * @param fileName filename
     * @throws TxtIOException if an text input or text output expression occurred
     */
    public void writeText(String fileName) throws TxtIOException {

        try (FileOutputStream fout = new FileOutputStream(fileName)) {
            try (ObjectOutputStream oos = new ObjectOutputStream(fout)) {
                oos.writeObject(DrawableQueue.getQueue());
            } catch (IOException e) {
                throw new TxtIOException();
            }
        } catch (IOException e) {
            throw new TxtIOException();
        }
    }

    /**
     * Loads a CommandQueue in .dat format.
     *
     * @param fileName filename
     * @throws TxtIOException if an text input exception occurred
     */
    public void readText(String fileName) throws TxtIOException {
        try (FileInputStream fi = new FileInputStream(new File(fileName))) {
            try(ObjectInputStream oi = new ObjectInputStream(fi)){
                DrawableQueue.setQueue((List<Drawable>) oi.readObject());
            } catch (ClassNotFoundException e) {
                throw new TxtIOException();
            }
        } catch (IOException e) {
            throw new TxtIOException();
        }
    }

    /**
     * Removes the last drawn element.
     */
    public void undo() {
        DrawableQueue.undo(window.getDrawingPanel().getGraphics());
        window.getDrawingPanel().updateUI();
    }

    /**
     * Inserts the last undone element.
     */
    public void redo() {
        DrawableQueue.redo(window.getDrawingPanel().getGraphics());
        window.getDrawingPanel().updateUI();
    }

    private String getKey(Color color) {
        for (final String key : window.getColorMap().keySet()) {
            if (window.getColorMap().get(key).equals(color))
                return key;
        }
        return null;
    }
}