package mydraw;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import mydraw.drawables.Drawable;
import mydraw.drawables.DrawableQueue;
import mydraw.drawables.FillRectDrawable;
import mydraw.listeners.BGColorItemListener;
import mydraw.listeners.FGColorItemListener;
import mydraw.listeners.ShapeManager;

/**
 * This class implements the GUI for our application
 */
public class DrawGUIs extends JFrame {
	// TODO add read image button
	// TODO translate everything to english
	private Color color;
	private DrawingPanel panel;

	private final Draw app;
	private ShapeManager shapeManager;
	private final int frameWidth = 800;
	private final int frameHeight = 500;
	private final int menuBarHeight = 20;
	private final int buttonBarHeight = 20;
	private final int panelHeight;

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 *
	 * @param application
	 *            thid gui's application
	 */
	public DrawGUIs(Draw application) {
		super("Draw");
		this.app = application;
		this.color = Color.BLACK;
		this.panelHeight = frameHeight - menuBarHeight;
		displayGUI();
	}

	private void displayGUI() {
		configureWindow();
		// Create the Contentpanel.
		configureDrawingPanel();
		// Create menu bar
		configureMenuBar();
		// Create a toolbar.
		configureToolBar();
		// Display the window.
		makeVisible();
	}

	private void configureWindow() {
		// Set a LayoutManager
		this.setLayout(new BorderLayout(3, 3));
		this.setSize(frameWidth, frameHeight);
		this.setBackground(Color.WHITE);
		this.setForeground(Color.BLACK);
		this.setPreferredSize(new Dimension(frameWidth, frameHeight));
		// Handle the window close request similarly
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				doCommand("quit");
			}
		});
	}

	private void makeVisible() {
		this.pack();
		this.setVisible(true);
	}

	private void configureToolBar() {
		final JToolBar tb = new JToolBar();
		tb.setOpaque(true);
		tb.setBackground(Color.GRAY);
		tb.setPreferredSize(new Dimension(frameWidth, buttonBarHeight));

		// selector for drawing modes
		final Choice shape_chooser = new Choice();

		for (final String drawer : shapeManager.getDrawerSet()) {
			shape_chooser.add(drawer);
		}
		shape_chooser.addItemListener(shapeManager);

		// selector for drawing colors
		final Choice color_chooser = new Choice();
		for (final String color : ColorManager.getColorSet()) {
			color_chooser.add(color);
		}
		color_chooser.addItemListener(new FGColorItemListener(this));

		// selector for backgroundcolors
		final Choice bgColor_chooser = new Choice();
		for (final String color : ColorManager.getColorSet()) {
			bgColor_chooser.add(color);
		}
		bgColor_chooser.addItemListener(new BGColorItemListener(this));
		bgColor_chooser.select("white");

		// Create buttons
		final JButton clear = new JButton("Clear");
		final JButton auto = new JButton("Auto");
		final JButton undo = new JButton("Undo");
		final JButton redo = new JButton("Redo");

		// Set a LayoutManager, and add the choosers and buttons to the button bar.
		// this.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
		tb.add(new JLabel("Shape:"));
		tb.add(shape_chooser);
		tb.add(new JLabel("Color:"));
		tb.add(color_chooser);
		tb.add(new JLabel("Background:"));
		tb.add(bgColor_chooser);
		tb.add(clear, BorderLayout.SOUTH);
		tb.add(auto, BorderLayout.NORTH);
		tb.add(undo, BorderLayout.NORTH);
		tb.add(redo, BorderLayout.NORTH);
		// Define action listener adapters that connect the buttons to the app
		clear.addActionListener(new DrawActionListener("clear"));
		auto.addActionListener(new DrawActionListener("auto"));
		undo.addActionListener(new DrawActionListener("undo"));
		redo.addActionListener(new DrawActionListener("redo"));
		this.getContentPane().add(tb, BorderLayout.NORTH);
	}

	private void configureMenuBar() {
		final JMenuBar mb = new JMenuBar();
		mb.setOpaque(true);
		mb.setBackground(Color.lightGray);
		mb.setPreferredSize(new Dimension(frameWidth, menuBarHeight));
		this.setJMenuBar(mb);

		// Create a menu and add it to the menu bar.
		final JMenu menu = new JMenu("File");
		mb.add(menu);

		// Create menu items and add them to the menu
		final JMenuItem save = new JMenuItem("Save");
		final JMenuItem txtSave = new JMenuItem("Text speichern ...");
		final JMenuItem txtRead = new JMenuItem("Datei lesen...");
		final JMenuItem quit = new JMenuItem("Quit");
		menu.add(save);
		menu.add(txtSave);
		menu.add(txtRead);
		menu.add(quit);

		// Add action listeners to the menu items
		save.addActionListener(new DrawActionListener("save"));
		txtSave.addActionListener(new DrawActionListener("txtSave"));
		txtRead.addActionListener(new DrawActionListener("txtRead"));
		quit.addActionListener(new DrawActionListener("quit"));
	}

	private void configureDrawingPanel() {
		panel = new DrawingPanel();
		panel.setBackground(Color.WHITE);
		panel.setOpaque(true);
		panel.setPreferredSize(new Dimension(frameWidth, panelHeight));
		shapeManager = new ShapeManager(panel, this);
		this.getContentPane().add(panel, BorderLayout.CENTER);
	}

	/**
	 * This is the application method that processes commands sent by the GUI
	 */
	private void doCommand(String command) {
		if (command.equals("clear")) {
			// clear the GUI window
			app.clear();
		} else if (command.equals("quit")) {
			// quit the application
			this.dispose(); // close the GUI
			System.exit(0); // and exit.
		} else if (command.equals("auto")) {
			app.autoDraw();
		} else if (command.equals("save")) {
			doSaveImage();
		} else if (command.equals("txtSave")) {
			doSaveText();
		} else if (command.equals("txtRead")) {
			doReadText();
		} else if (command.equals("undo")) {
			app.undo();
		} else if (command.equals("redo")) {
			app.redo();
		}
	}

	private void doSaveImage() {
		try {
			final JFileChooser jfc = new JFileChooser();
			final int retVal = jfc.showSaveDialog(this);
			if (retVal == JFileChooser.APPROVE_OPTION) {
				final File f = jfc.getSelectedFile();
				final String test = f.getAbsolutePath();
				app.writeImage(getBufferedImage(), test);
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	private void doSaveText() {
		try {
			final JFileChooser jfc = new JFileChooser();
			final int retVal = jfc.showSaveDialog(null);
			if (retVal == JFileChooser.APPROVE_OPTION) {
				final File f = jfc.getSelectedFile();
				final String name = f.getAbsolutePath();
				app.writeText(name);
			}
		} catch (final TxtIOException e) {
			e.printStackTrace();
		}
	}

	private void doReadText() {
		final JFileChooser jfc = new JFileChooser();
		final int retVal = jfc.showOpenDialog(null);
		if (retVal == JFileChooser.APPROVE_OPTION) {
			final File f = jfc.getSelectedFile();
			final String name = f.getAbsolutePath();
			try {
				app.readText(name);

			} catch (final TxtIOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns the current drawing color.
	 *
	 * @return drawing color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets the new drawing color.
	 *
	 * @param newColor
	 *            new drawing color
	 */
	public void setColor(Color newColor) {
		color = newColor;
	}

	/**
	 * Returns the drawing panel.
	 *
	 * @return panel
	 */
	public JPanel getDrawingPanel() {
		return panel;
	}

	/**
	 * Returns the buffered image.
	 *
	 * @return buffered image
	 */
	public BufferedImage getBufferedImage() {
		final BufferedImage result = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
		final Graphics g = result.createGraphics();
		final Drawable clear = new FillRectDrawable(new Point(0, 0), new Point(panel.getWidth(), panel.getHeight()),
				panel.getBackground());
		clear.draw(g);
		DrawableQueue.redraw(g);
		return result;
	}

	/**
	 * Returns this gui's application.
	 *
	 * @return application
	 */
	public Draw getApp() {
		return app;
	}

	// Here's a local class used for action listeners for the buttons
	private class DrawActionListener implements ActionListener {
		private final String command;

		public DrawActionListener(String cmd) {
			command = cmd;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			doCommand(command);
		}
	}
}
