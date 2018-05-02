package mydraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

public class Draw
{
	protected DrawGUIs window;


	public Draw()
	{
		window = new DrawGUIs(this);
	}

	public static void main(String[] args)
	{
		new Draw();
	}

	/**
	 * Returns current drawing color.
	 *
	 * @return current drawing color
	 */
	public String getFGColor()
	{
		return getKey(window.color);
	}

	/**
	 * Sets foreground color.
	 *
	 * @param new_color
	 *            new color to set
	 * @throws ColorException
	 *             if color is not in Choice
	 */
	public void setFGColor(String new_color) throws ColorException
	{
		Color color = window.cm.get(new_color.toLowerCase());
		if (color != null)
			window.color = color;
		else
			throw new ColorException();
	}



	public int getWidth()
	{
		return window.getSize().width;
	}

	public int getHeight()
	{
		return window.getSize().height;
	}

	public void setHeight(int height)
	{
		window.setSize(window.getSize().width, height);
	}

	public void setWidth(int width)
	{
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
	public void setBGColor(String new_color) throws ColorException
	{
		Color color = window.cm.get(new_color.toLowerCase());
		if (color != null)
			window.setBackground(color);
		else
			throw new ColorException();

		Graphics g = window.panel.getGraphics();
		g.setColor(window.getBackground());

		Graphics gb = window.bImg.createGraphics();
		gb.setColor(window.getBackground());
	}

	/**
	 * Returns the background color of the window.
	 *
	 * @return background color
	 */
	public String getBGColor() {

		return getKey(window.getBackground());
	}





	/**
	 * Draws a rectangle.
	 *
	 * @param upper_left
	 *            top left corner
	 * @param lower_right
	 *            bottom right corner
	 */
	public void drawRectangle(Point upper_left, Point lower_right)
	{
		int width = lower_right.x - upper_left.x;
		int height = lower_right.y - upper_left.y;

		Graphics g = window.panel.getGraphics();
		g.setColor(window.color);
		g.drawRect(upper_left.x, upper_left.y, width, height);

		Graphics gb = window.bImg.createGraphics();
		gb.setColor(window.color);
		gb.drawRect(upper_left.x, upper_left.y, width, height);
	}

	/**
	 * Draws an oval.
	 *
	 * @param upper_left
	 *            top left corner
	 * @param lower_right
	 *            bottom right corner
	 */
	public void drawOval(Point upper_left, Point lower_right)
	{
		int width = lower_right.x - upper_left.x;
		int height = lower_right.y - upper_left.y;

		Graphics g = window.panel.getGraphics();
		g.setColor(window.color);
		g.drawOval(upper_left.x, upper_left.y, width, height);

		Graphics gb = window.bImg.createGraphics();
		gb.setColor(window.color);
		gb.drawOval(upper_left.x, upper_left.y, width, height);
	}

	/**
	 * Draws a polyline.
	 *
	 * @param points
	 *            list of points
	 */
	public void drawPolyLine(java.util.List<Point> points) {
		int[] x = new int[points.size()];
		int[] y = new int[points.size()];
		for (int i = 0; i < points.size(); i++) {
			Point p = points.get(i);
			x[i] = p.x;
			y[i] = p.y;
		}
		Graphics g = window.panel.getGraphics();
		g.setColor(window.color);
		g.drawPolyline(x, y, points.size());

		Graphics gb = window.bImg.getGraphics();
		gb.setColor(window.color);
		gb.drawPolyline(x, y, points.size());
	}

	/**
	 * Returns the current drawing.
	 *
	 * @return drawing
	 */
	public Image getDrawing() {
		return window.bImg;
	}

	/**
	 * Clears drawing panel.
	 */
	public void clear()
	{
		Graphics g = window.panel.getGraphics();
		g.setColor(window.getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());

		Graphics gb = window.bImg.getGraphics();
		gb.setColor(window.getBackground());
		gb.fillRect(0, 0, window.bImg.getWidth(), window.bImg.getHeight());
	}

	public void autoDraw()
	{

		try
		{
			setBGColor("blue");
			clear();
			drawOval(new Point(50, 50), new Point(200, 200));
			setFGColor("red");
			drawRectangle(new Point(100, 100), new Point(300, 300));
			setFGColor("Green");
			List<Point> list = Arrays.asList(new Point(50, 50), new Point(100, 150), new Point(80, 80));
			drawPolyLine(list);
		}
		catch (ColorException e)
		{
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
	 */
	public void writeImage(Image img, String filename) throws IOException
	{
		ImageIO.write((RenderedImage) img, "PNG",
				new File(filename));
	}

	/**
	 * Reads an image.
	 *
	 * @param filename
	 *            filename
	 * @return Image image
	 * @throws IOException
	 */
	public Image readImage(String filename) throws IOException {
		return ImageIO.read(new File(filename));
	}

	private String getKey(Color color)
	{
		for (String key : window.cm.keySet())
		{
			if (window.cm.get(key).equals(color)) return key;
		}
		return null;
	}
}