package mydraw;
//This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)

//Copyright (c) 1997 by David Flanagan
//This example is provided WITHOUT ANY WARRANTY either expressed or implied.
//You may study, use, modify, and distribute it for non-commercial purposes.
//For any commercial use, see http://www.davidflanagan.com/javaexamples

// *** modified by PTP 03/2018
// *** minimal changes from AWT to Swing -> replace elements/classes
// *** behavior is similiar but not equal ! Why?

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*; //++

/** The application class. Processes high-level commands sent by GUI */
/*
 * Zeigt Fenster an. F�hrt Befehle der Buttons aus Kooperationspartner: DrawGUIs
 */
public class Draw {
	/** main entry point. Just create an instance of this application class */
	public static void main(String[] args) {
		new Draw();
	}

	/** Application constructor: create an instance of our GUI class */
	public Draw() {
		window = new DrawGUIs(this);
	}

	protected JFrame window; // chg

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
		}
	}
}

/** This class implements the GUI for our application */
/*
 * Definiert das Fenster und ist f�r die Gestaltung zust�ndig.
 * Kooperationspartner: JFrame, Draw, DrawActionListener, ShapeManager,
 * ColorItemListener, WindowAdapter
 */
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

		// Set a LayoutManager, and add the choosers and buttons to the window.
		this.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
		this.add(new JLabel("Shape:"));
		this.add(shape_chooser);
		this.add(new JLabel("Color:"));
		this.add(color_chooser);
		this.add(clear);
		this.add(quit);

		// Here's a local class used for action listeners for the buttons
		/*
		 * Listener f�r die Buttons. Kooperationspartner: ActionListener
		 */
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

		// this class determines how mouse events are to be interpreted,
		// depending on the shape mode currently set
		/*
		 * Interpretiert die Mausaktionen entsprechend der ausgew�hlten Zeichenformen.
		 * Kooperationspartner: ShapeDrawer, ItemListener, DrawGUIs, ScribbleDrawer,
		 * RectangleDrawer, OvalDrawer
		 */
		class ShapeManager implements ItemListener {
			DrawGUIs gui;

			abstract class ShapeDrawer extends MouseAdapter implements MouseMotionListener {
				public void mouseMoved(MouseEvent e) {
					/* ignore */ }
			}

			// if this class is active, the mouse is interpreted as a pen
			/*
			 * Interpretiert die Mausaktionen als Stift. Kooperationspartner: ShapeDrawer
			 */
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

		/*
		 * Setzt die Farbe der neuen Zeichnungen entsprechend der Auswahl.
		 * Kooperationspartner: ItemListener
		 */
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

	/**
	 * Gibt die aktuelle Zeichenfarbe zur�ck.
	 * 
	 * @return aktuelle Zeichenfarbe
	 */
	public String getFGColor() {
		return color.toString();
	}

	/**
	 * Setzt die aktuelle Zeichenfarbe.
	 * 
	 * @param new_color
	 *            neue Zeichenfarbe
	 * @throws ColorException
	 *             ung�ltige Farbe
	 */
	public void setFGColor(String new_color) throws ColorException {
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
		default:
			throw new ColorException();
		}
	}

	/**
	 * Gibt die Breite des Fensters zur�ck.
	 * 
	 * @return Breite des Fensters
	 */
	public int getWidth() {
		return getSize().width;
	}

	/**
	 * Gibt die H�he des Fensters zur�ck.
	 * 
	 * @return H�he des Fensters
	 */
	public int getHeight() {
		return getSize().height;
	}

	/**
	 * Setzt die Breite des Fensters.
	 * 
	 * @param width
	 *            neue Breite des Fensters
	 */
	public void setWidth(int width) {
		setSize(width, getHeight());
	}

	/**
	 * Setzt die H�he des Fensters.
	 * 
	 * @param height
	 *            neue H�he des Fensters
	 */
	public void setHeight(int height) {
		setSize(getWidth(), height);
	}

	/**
	 * Setzt die Hintergrundfarbe des Fensters.
	 * 
	 * @param new_color
	 *            neue Hintergrundfarbe
	 * @throws ColorException
	 *             ung�ltige Farbe
	 */
	public void setBGColor(String new_color) throws ColorException {
		Color color;
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
		setBackground(color);
	}

	/**
	 * Gibt die Hintergrundfarbe des Fensters zur�ck.
	 * 
	 * @return Hintergrundfarbe des Fensters
	 */
	public String getBGColor() {
		return getBackground().toString();
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
		Graphics g = getGraphics();
		int width = lower_right.x - upper_left.x;
		int height = lower_right.y - upper_left.y;
		g.drawRect(upper_left.x, upper_left.y, width, height);
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
		Graphics g = getGraphics();
		int width = lower_right.x - upper_left.x;
		int height = lower_right.y - upper_left.y;
		g.drawOval(upper_left.x, upper_left.y, width, height);
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
		Graphics g = getGraphics();
		g.drawPolyline(x, y, points.size());
	}

	/**
	 * Gibt die Zeichnung zur�ck.
	 * 
	 * @return Zeichnung
	 */
	public Image getDrawing() {
		return createImage(getWidth(), getHeight());
	}

	/**
	 * Setzt die Zeichenfl�che zur�ck.
	 */
	public void clear() {
		Graphics g = getGraphics();
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	/**
	 * Zeichnet ein voreingestelltes Bild.
	 */
	public void autoDraw() {
		// TODO autoDraw
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
		// TODO writeImage
	}
}