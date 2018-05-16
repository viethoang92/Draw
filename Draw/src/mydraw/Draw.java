package mydraw;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import mydraw.drawables.Drawable;
import mydraw.drawables.DrawableQueue;
import mydraw.drawables.FillOvalDrawable;
import mydraw.drawables.FillRectDrawable;
import mydraw.drawables.LineDrawable;
import mydraw.drawables.OvalDrawable;
import mydraw.drawables.PolylineDrawable;
import mydraw.drawables.RectangleDrawable;

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
		return ColorManager.getColor(window.getColor());
	}

	/**
	 * Sets foreground color.
	 *
	 * @param new_color
	 *            new color to set
	 * @throws ColorException
	 *             if color is not in Choice
	 */
	public void setFGColor(String new_color) throws ColorException {
		final Color color = ColorManager.getColor(new_color.toLowerCase());
		if (color != null) {
			window.setColor(color);
		} else
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
	 * @param height
	 *            new height
	 */
	public void setHeight(int height) {
		window.setSize(window.getSize().width, height);
	}

	/**
	 * Sets the width of the window.
	 *
	 * @param width
	 *            new width
	 */
	public void setWidth(int width) {
		window.setSize(width, window.getSize().height);
	}

	/**
	 * Sets background color
	 *
	 * @param new_color
	 *            new background color
	 * @throws ColorException
	 *             invalid color
	 */
	public void setBGColor(String new_color) throws ColorException {
		final Color color = ColorManager.getColor(new_color.toLowerCase());
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

		return ColorManager.getColor(window.getDrawingPanel().getBackground());
	}

	/**
	 * Draws a rectangle.
	 *
	 * @param upper_left
	 *            top left corner
	 * @param lower_right
	 *            bottom right corner
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
	 * @param upper_left
	 *            top left corner
	 * @param lower_right
	 *            bottom right corner
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
	 * @param points
	 *            list of points
	 */
	public void drawPolyLine(java.util.List<Point> points) {
		final PolylineDrawable cmd = new PolylineDrawable(points, window, window.getColor());
		cmd.draw(window.getDrawingPanel().getGraphics());
		cmd.draw(window.getBufferedImage().createGraphics());
		DrawableQueue.add(cmd);

	}

	/**
	 * Draws a polyline.
	 *
	 * @param x
	 *            x
	 * @param y
	 *            y
	 */
	public void drawLine(int x, int y) {
		final LineDrawable cmd = new LineDrawable(x, y, window);
		cmd.draw(window.getDrawingPanel().getGraphics());
		cmd.draw(window.getBufferedImage().createGraphics());
	}

	/**
	 * Draws a filled rectangle.
	 *
	 * @param upper_left
	 *            top left corner
	 * @param lower_right
	 *            bottom right corner
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
	 * @param upper_left
	 *            top left corner
	 * @param lower_right
	 *            bottom right corner
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
		final FillRectDrawable cmd = new FillRectDrawable(new Point(0, 0),
				new Point(window.getDrawingPanel().getWidth(), window.getDrawingPanel().getHeight()), window,
				window.getDrawingPanel().getBackground());
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
	 * @param img
	 *            image
	 * @param filename
	 *            filename
	 * @throws IOException
	 *             if an input or output expression occurred
	 */
	public void writeImage(Image img, String filename) throws IOException {
		ImageIO.write((RenderedImage) img, "PNG", new File(filename));
	}

	/**
	 * Reads an image.
	 *
	 * @param filename
	 *            filename
	 * @return Image image
	 * @throws IOException
	 *             if an input or output expression occurred
	 */
	public Image readImage(String filename) throws IOException {
		return ImageIO.read(new File(filename));
	}

	public void writeText(String name) throws TxtIOException {
		final File file = new File(name);
		try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

			for (final Drawable drawable : DrawableQueue.getQueue()) {
				writer.write(drawable.toString());
				writer.newLine();
			}
			writer.flush();
		} catch (final IOException e) {
			// TODO
			e.printStackTrace();
		}
	}

	public void readText(String name) throws TxtIOException {
		final File file = new File(name);
		try (final BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line = null;
			final List<Drawable> drawables = new ArrayList<>();
			while ((line = reader.readLine()) != null) {
				final String[] components = line.split(":");
				final Drawable drawable;
				switch (components[0]) {
				case "polyline":
					drawable = createDrawablePolyline(components);
					break;
				case "rectangle":
					drawable = createDrawableRectangle(components);
					break;
				case "oval":
					drawable = createDrawableOval(components);
					break;
				case "filledRectangle":
					drawable = createDrawableFilledRect(components);
					break;
				default:
					throw new TxtIOException();
				}
				drawables.add(drawable);
			}
			clear();
			for (final Drawable drawable : drawables) {
				drawable.draw(window.getDrawingPanel().getGraphics());
				DrawableQueue.add(drawable);
			}
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Drawable createDrawableFilledRect(String[] components) {
		final Point pressed = createPoint(components[1]);
		final Point released = createPoint(components[2]);
		final Color color = ColorManager.getColor(components[3]);
		return new FillRectDrawable(pressed, released, window, color);
	}

	private Drawable createDrawableOval(String[] components) {
		final Point pressed = createPoint(components[1]);
		final Point released = createPoint(components[2]);
		final Color color = ColorManager.getColor(components[3]);
		return new OvalDrawable(pressed, released, window, color);
	}

	private Drawable createDrawableRectangle(String[] components) {
		final Point pressed = createPoint(components[1]);
		final Point released = createPoint(components[2]);
		final Color color = ColorManager.getColor(components[3]);
		return new RectangleDrawable(pressed, released, window, color);
	}

	private PolylineDrawable createDrawablePolyline(String[] components) {
		final String[] pointsStr = components[1].split(";");
		final List<Point> points = new ArrayList<>();
		for (final String point : pointsStr) {
			points.add(createPoint(point));
		}
		final Color color = ColorManager.getColor(components[2]);
		return new PolylineDrawable(points, window, color);
	}

	private Point createPoint(String pointStr) {
		final String[] coords = pointStr.split(",");
		final int x = Integer.parseInt(coords[0]);
		final int y = Integer.parseInt(coords[1]);
		return new Point(x, y);
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
}