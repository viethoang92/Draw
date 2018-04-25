package mydraw;
//This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)

import java.awt.Choice;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

//++
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sun.glass.ui.Window;

/** The application class. Processes high-level commands sent by GUI */
public class Draw {
	/** main entry point. Just create an instance of this application class */
	public static void main(String[] args) {
		new Draw();
	}

	/** Application constructor: create an instance of our GUI class */
	public Draw() {
		window = new DrawGUIs(this);
	}

	protected DrawGUIs window; // chg
	/** This is the application method that processes commands sent by the GUI */
	public void doCommand(String command) {
		if (command.equals("clear")) { // clear the GUI window
			// It would be more modular to include this functionality in the GUI
			// class itself. But for demonstration purposes, we do it here.
			Graphics g = window.getGraphics();
			g.setColor(window.getBackground());
			g.fillRect(0, 0, window.getSize().width, window.getSize().height);
		} else if (command.equals("quit")) { // quit the application
			window.dispose(); // close the GUI
			System.exit(0); // and exit.
		} else if (command.equals("auto")) {
			autoDraw();
		}
	}

	/**
<<<<<<< HEAD
	 * Returns current foreground color.
	 * 
	 * @return current foreground color
=======
	 * Gibt die aktuelle Zeichenfarbe zur�ck.
	 * 
	 * @return aktuelle Zeichenfarbe
	 * @throws ColorException ungültige Farbe
>>>>>>> 9b4eec82e78cb257bf14bf59fc3b350457faa479
	 */
	public String getFGColor() throws ColorException {
		String color;
		switch (window.getForeground().toString()) {
		case "java.awt.Color[r=255,g=0,b=0]":
			color = "red";
			break;
		case "java.awt.Color[r=0,g=0,b=0]":
			color = "black";
			break;
		case "java.awt.Color[r=0,g=255,b=0]":
			color = "green";
			break;
		case "java.awt.Color[r=0,g=0,b=255]":
			color = "blue";
			break;
		default:
			throw new ColorException();
		}
		return color;
	}

	/**
	 * Sets foreground color.
	 * 
	 * @param new_color new color to set
	 * @throws ColorException if color is not in Choice
	 */
	public void setFGColor(String new_color) throws ColorException {
		Color color = null;
		switch (new_color.toLowerCase()) {
		case "black":
			color = Color.BLACK;
			break;
		case "green":
			color = Color.GREEN;
			break;
		case "red":
			color = Color.RED;
			break;
		case "blue":
			color = Color.BLUE;
			break;
		default:
			throw new ColorException();
		}
		window.setForeground(color);
	}

	/**
<<<<<<< HEAD
	 * Returns the window width.
=======
	 * Gibt die Breite des Fensters zur�ck.
>>>>>>> 9b4eec82e78cb257bf14bf59fc3b350457faa479
	 * 
	 * @return width of window
	 */
	public int getWidth() {
		return window.getSize().width;
	}

	/**
<<<<<<< HEAD
	 * Returns the window height.
	 * 
	 * @return height of window
=======
	 * Gibt die H�he des Fensters zur�ck.
	 * 
	 * @return H�he des Fensters
>>>>>>> 9b4eec82e78cb257bf14bf59fc3b350457faa479
	 */
	public int getHeight() {
		return window.getSize().height;
	}

	/**
	 * Sets the window width.
	 * 
	 * @param width width of window to set
	 */
	public void setWidth(int width) {
		window.setSize(width, getHeight());
	}

	/**
<<<<<<< HEAD
	 * Sets the window height.
	 * 
	 * @param height height of the window to set
=======
	 * Setzt die H�he des Fensters.
	 * 
	 * @param height
	 *            neue H�he des Fensters
>>>>>>> 9b4eec82e78cb257bf14bf59fc3b350457faa479
	 */
	public void setHeight(int height) {
		window.setSize(getWidth(), height);
	}

	/**
	 * Sets background color
	 * 
<<<<<<< HEAD
	 * @param new_color new background color to be set
	 * @throws ColorException if new color is not in the set
=======
	 * @param new_color
	 *            neue Hintergrundfarbe
	 * @throws ColorException
	 *             ung�ltige Farbe
>>>>>>> 9b4eec82e78cb257bf14bf59fc3b350457faa479
	 */
	public void setBGColor(String new_color) throws ColorException {
		Color color = null;
		switch (new_color.toLowerCase()) {
		case "black":
			color = Color.black;
			break;
		case "green":
			color = Color.green;
			break;
		case "red":
			color = Color.red;
			break;
		case "blue":
			color = Color.blue;
			break;
		case "white":
			color = Color.white;
			break;
		default:
			throw new ColorException();
		}
		window.getContentPane().setBackground(color);
		window.setBackground(color);
	}

	/**
	 * Gibt die Hintergrundfarbe des Fensters zur�ck.
	 * 
	 * @return Hintergrundfarbe des Fensters
	 * @throws ColorException unbekannte Farbe
	 */
	public String getBGColor() throws ColorException {

		String color;
		switch (window.getBackground().toString()) {
		case "java.awt.Color[r=255,g=0,b=0]":
			color = "red";
			break;
		case "java.awt.Color[r=0,g=0,b=0]":
			color = "black";
			break;
		case "java.awt.Color[r=0,g=255,b=0]":
			color = "green";
			break;
		case "java.awt.Color[r=0,g=0,b=255]":
			color = "blue";
			break;
		case "java.awt.Color[r=255,g=255,b=255]":
			color = "white";
			break;
		default:
			throw new ColorException();
		}
		return color; 
	}

	/**
	 * Zeichnet ein Recheck.
	 * 
	 * @param upper_left
	 *            linke, obere Ecke
	 * @param lower_right
	 *            rechte, untere Ecke
	 */
	public void drawRectangle(Point upper_left, Point lower_right) {
		int width = lower_right.x - upper_left.x;
		int height = lower_right.y - upper_left.y;
		window.getGraphics().drawRect(upper_left.x, upper_left.y, width, height);
	}

	/**
	 * Zeichnet ein Oval.
	 * 
	 * @param upper_left
	 *            linke, obere Ecke
	 * @param lower_right
	 *            rechte, untere Ecke
	 */
	public void drawOval(Point upper_left, Point lower_right) {
		int width = lower_right.x - upper_left.x;
		int height = lower_right.y - upper_left.y;
		window.getGraphics().drawOval(upper_left.x, upper_left.y, width, height);
	}

	/**
	 * Zeichnet eine Polylinie
	 * 
	 * @param points
	 *            Liste der Punkte
	 */
	public void drawPolyLine(java.util.List<Point> points) {
		int[] x = new int[points.size()];
		int[] y = new int[points.size()];
		for (int i = 0; i < points.size(); i++) {
			Point p = points.get(i);
			x[i] = p.x;
			y[i] = p.y;
		}
		window.getGraphics().drawPolyline(x, y, points.size());
	}

	/**
	 * Gibt die Zeichnung zur�ck.
	 * 
	 * @return Zeichnung
	 */
	public Image getDrawing() {
		return window.createImage(getWidth(), getHeight());
	}

	/**
	 * Setzt die Zeichenfl�che zur�ck.
	 */
	public void clear() {
		Graphics g = window.getGraphics();
		g.setColor(window.getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	/**
	 * Zeichnet ein voreingestelltes Bild.
	 */
	public void autoDraw() {
		try {
			
			setBGColor("black");			
			setFGColor("Red");
			drawRectangle(new Point(100, 100), new Point(300, 300));
			setFGColor("Blue");			
			drawOval(new Point(50, 50), new Point(200, 200));
			setFGColor("Green");
			List<Point> list = Arrays.asList(new Point(50, 50), new Point(100, 150), new Point(80, 80));
			drawPolyLine(list);
		} catch (ColorException e) {
			JOptionPane.showMessageDialog(window, e.getMessage());
		}
	}

	/**
	 * Speichert ein Bild als Datei ab.
	 * 
	 * @param img
	 *            abzuspeicherndes Bild
	 * @param filename
	 *            Dateiname
	 * @throws IOException
	 */
	public void writeImage(Image img, String filename) throws IOException {
		MyBMPFile.write(filename, img);
	}

	/**
	 * Liest eine Bilddatei ein.
	 * 
	 * @param filename
	 *            Dateiname
	 * @return Image
	 * @throws IOException
	 */
	public Image readImage(String filename) throws IOException {
		return MyBMPFile.read(filename);
	}
}

/** This class implements the GUI for our application */
class DrawGUIs extends JFrame {

	private static final long serialVersionUID = 1L;

	Draw app; // A reference to the application, to send commands to.
	Color color;

	/**
	 * The GUI constructor does all the work of creating the GUI and setting up
	 * event listeners. Note the use of local and anonymous classes.
	 */
	public DrawGUIs(Draw application) {
		super("Draw"); // Create the window
		app = application; // Remember the application reference
		color = Color.black; // the current drawing color


		// selector for drawing modes
		Choice shape_chooser = new Choice();
		shape_chooser.add("Scribble");
		shape_chooser.add("Rectangle");
		shape_chooser.add("Oval");

		// selector for drawing colors
		Choice color_chooser = new Choice();
		color_chooser.add("Black");
		color_chooser.add("Green");
		color_chooser.add("Red");
		color_chooser.add("Blue");

		// Create two buttons
		JButton clear = new JButton("Clear");
		JButton quit = new JButton("Quit");
		JButton auto = new JButton("Auto");

		// Set a LayoutManager, and add the choosers and buttons to the window.
		this.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
		this.add(new JLabel("Shape:"));
		this.add(shape_chooser);
		this.add(new JLabel("Color:"));
		this.add(color_chooser);
		this.add(clear);
		this.add(quit);
		this.add(auto);

		// Here's a local class used for action listeners for the buttons
		class DrawActionListener implements ActionListener {
			private String command;

			public DrawActionListener(String cmd) {
				command = cmd;
			}

			public void actionPerformed(ActionEvent e) {
				app.doCommand(command);
			}
		}

		// Define action listener adapters that connect the buttons to the app
		clear.addActionListener(new DrawActionListener("clear"));
		quit.addActionListener(new DrawActionListener("quit"));
		auto.addActionListener(new DrawActionListener("auto"));

		// this class determines how mouse events are to be interpreted,
		// depending on the shape mode currently set
		class ShapeManager implements ItemListener {
			DrawGUIs gui;

			abstract class ShapeDrawer extends MouseAdapter implements MouseMotionListener {
				public void mouseMoved(MouseEvent e) {
					/* ignore */ }
			}

			// if this class is active, the mouse is interpreted as a pen
			class ScribbleDrawer extends ShapeDrawer {
				int lastx, lasty;

				public void mousePressed(MouseEvent e) {
					lastx = e.getX();
					lasty = e.getY();
				}

				public void mouseDragged(MouseEvent e) {
					Graphics g = gui.getGraphics();
					int x = e.getX(), y = e.getY();
					g.setColor(gui.color);
					g.setPaintMode();
					g.drawLine(lastx, lasty, x, y);
					lastx = x;
					lasty = y;
				}
			}

			// if this class is active, rectangles are drawn
			class RectangleDrawer extends ShapeDrawer {
				int pressx, pressy;
				int lastx = -1, lasty = -1;

				// mouse pressed => fix first corner of rectangle
				public void mousePressed(MouseEvent e) {
					pressx = e.getX();
					pressy = e.getY();
				}

				// mouse released => fix second corner of rectangle
				// and draw the resulting shape
				public void mouseReleased(MouseEvent e) {
					Graphics g = gui.getGraphics();
					if (lastx != -1) {
						// first undraw a rubber rect
						g.setXORMode(gui.color);
						g.setColor(gui.getBackground());
						doDraw(pressx, pressy, lastx, lasty, g);
						lastx = -1;
						lasty = -1;
					}
					// these commands finish the rubberband mode
					g.setPaintMode();
					g.setColor(gui.color);
					// draw the finel rectangle
					doDraw(pressx, pressy, e.getX(), e.getY(), g);
				}

				// mouse released => temporarily set second corner of rectangle
				// draw the resulting shape in "rubber-band mode"
				public void mouseDragged(MouseEvent e) {
					Graphics g = gui.getGraphics();
					// these commands set the rubberband mode
					g.setXORMode(gui.color);
					g.setColor(gui.getBackground());
					if (lastx != -1) {
						// first undraw previous rubber rect
						doDraw(pressx, pressy, lastx, lasty, g);

					}
					lastx = e.getX();
					lasty = e.getY();
					// draw new rubber rect
					doDraw(pressx, pressy, lastx, lasty, g);
				}

				public void doDraw(int x0, int y0, int x1, int y1, Graphics g) {
					// calculate upperleft and width/height of rectangle
					int x = Math.min(x0, x1);
					int y = Math.min(y0, y1);
					int w = Math.abs(x1 - x0);
					int h = Math.abs(y1 - y0);
					// draw rectangle
					g.drawRect(x, y, w, h);
				}
			}

			// if this class is active, ovals are drawn
			class OvalDrawer extends RectangleDrawer {
				public void doDraw(int x0, int y0, int x1, int y1, Graphics g) {
					int x = Math.min(x0, x1);
					int y = Math.min(y0, y1);
					int w = Math.abs(x1 - x0);
					int h = Math.abs(y1 - y0);
					// draw oval instead of rectangle
					g.drawOval(x, y, w, h);
				}
			}

			ScribbleDrawer scribbleDrawer = new ScribbleDrawer();
			RectangleDrawer rectDrawer = new RectangleDrawer();
			OvalDrawer ovalDrawer = new OvalDrawer();
			ShapeDrawer currentDrawer;

			public ShapeManager(DrawGUIs itsGui) {
				gui = itsGui;
				// default: scribble mode
				currentDrawer = scribbleDrawer;
				// activate scribble drawer
				gui.addMouseListener(currentDrawer);
				gui.addMouseMotionListener(currentDrawer);
			}

			// reset the shape drawer
			public void setCurrentDrawer(ShapeDrawer l) {
				if (currentDrawer == l)
					return;

				// deactivate previous drawer
				gui.removeMouseListener(currentDrawer);
				gui.removeMouseMotionListener(currentDrawer);
				// activate new drawer
				currentDrawer = l;
				gui.addMouseListener(currentDrawer);
				gui.addMouseMotionListener(currentDrawer);
			}

			// user selected new shape => reset the shape mode
			public void itemStateChanged(ItemEvent e) {
				if (e.getItem().equals("Scribble")) {
					setCurrentDrawer(scribbleDrawer);
				} else if (e.getItem().equals("Rectangle")) {
					setCurrentDrawer(rectDrawer);
				} else if (e.getItem().equals("Oval")) {
					setCurrentDrawer(ovalDrawer);
				}
			}
		}

		shape_chooser.addItemListener(new ShapeManager(this));

		class ColorItemListener implements ItemListener {

			// user selected new color => store new color in DrawGUIs
			public void itemStateChanged(ItemEvent e) {
				if (e.getItem().equals("Black")) {
					color = Color.black;
				} else if (e.getItem().equals("Green")) {
					color = Color.green;
				} else if (e.getItem().equals("Red")) {
					color = Color.red;
				} else if (e.getItem().equals("Blue")) {
					color = Color.blue;
				}
			}
		}

		color_chooser.addItemListener(new ColorItemListener());

		// Handle the window close request similarly
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				app.doCommand("quit");
			}
		});

		// Finally, set the size of the window, and pop it up
		this.setSize(500, 400);
		this.setBackground(Color.white);
		// this.show(); //chg
		this.setVisible(true); // ++
	}

}