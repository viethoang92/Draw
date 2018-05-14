package mydraw;

import mydraw.commands.CommandQueue;
import mydraw.listeners.BGColorItemListener;
import mydraw.listeners.FGColorItemListener;
import mydraw.listeners.ShapeDrawer;
import mydraw.listeners.ShapeManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.*;


/**
 * This class implements the GUI for our application
 */
public class DrawGUIs extends JFrame {
    private BufferedImage bImg;
    private Color color;
    private DrawingPanel panel;
    private final Map<String, Color> cm = new LinkedHashMap<>() {
        private static final long serialVersionUID = 1L;

        {
            put("black", Color.BLACK);
            put("green", Color.GREEN);
            put("red", Color.RED);
            put("blue", Color.BLUE);
            put("white", Color.WHITE);
            put("yellow", Color.YELLOW);
        }
    };

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
     * @param application thid gui's application
     */
    public DrawGUIs(Draw application) {
        super("Draw");
        this.app = application;
        this.color = Color.BLACK;
        this.panelHeight = frameHeight - menuBarHeight;
        displayGUI();
    }

    public void displayGUI() {

        // Create the Contentpanel.
        panel = new DrawingPanel();
        panel.setBackground(Color.WHITE);
        panel.setOpaque(true);
        panel.setPreferredSize(new Dimension(frameWidth, panelHeight));
        shapeManager = new ShapeManager(panel, this);

        // Create menu bar
        JMenuBar mb = new JMenuBar();
        mb.setOpaque(true);
        mb.setBackground(Color.lightGray);
        mb.setPreferredSize(new Dimension(frameWidth, menuBarHeight));

        // Create a menu and add it to the menu bar.
        JMenu menu = new JMenu("File");
        mb.add(menu);

        // Create a toolbar.
        final JToolBar tb = new JToolBar();
        tb.setOpaque(true);
        tb.setBackground(Color.GRAY);
        tb.setPreferredSize(new Dimension(frameWidth, buttonBarHeight));

        // selector for drawing modes
        final Choice shape_chooser = new Choice();

        for (final Entry<String, ShapeDrawer> enp : shapeManager.getDrawerSet()) {
            shape_chooser.add(enp.getKey());
        }
        shape_chooser.addItemListener(shapeManager);

        // selector for drawing colors
        final Choice color_chooser = new Choice();
        for (final Entry<String, Color> enp : cm.entrySet()) {
            color_chooser.add(enp.getKey());
        }
        color_chooser.addItemListener(new FGColorItemListener(this));

        // selector for backgroundcolors
        final Choice bgColor_chooser = new Choice();
        for (final Entry<String, Color> enp : cm.entrySet()) {
            bgColor_chooser.add(enp.getKey());
        }
        bgColor_chooser.addItemListener(new BGColorItemListener(this));


        // Create buttons
        final JButton clear = new JButton("Clear");
        final JButton auto = new JButton("Auto");
        final JButton undo = new JButton("Undo");
        final JButton redo = new JButton("Redo");


        JMenuItem quit = new JMenuItem("Quit");
        JMenuItem txtSave = new JMenuItem("Text speichern ...");
        JMenuItem txtRead = new JMenuItem("Datei lesen...");
        JMenuItem save = new JMenuItem("Save");
        menu.add(save);
        menu.add(txtSave);
        menu.add(txtRead);
        menu.add(quit);


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


        // Setup BufferedImage
        bImg = new BufferedImage(frameWidth, panelHeight,
                BufferedImage.TYPE_INT_ARGB);
        final Graphics bg = bImg.createGraphics();
        bg.setColor(Color.WHITE);
        bg.fillRect(0, 0, bImg.getWidth(), bImg.getHeight());

        // Set a LayoutManager
        this.setLayout(new BorderLayout(3, 3));
        this.setSize(frameWidth, frameHeight);
        this.setBackground(Color.WHITE);
        this.setForeground(Color.BLACK);

        // Set the menu bar and add the panel to the Content
        this.setJMenuBar(mb);
        this.getContentPane().add(tb, BorderLayout.NORTH);
        this.getContentPane()
                .add(panel, BorderLayout.CENTER);

        // Display the window.
        this.pack();
        this.setVisible(true);

        // Handle the window close request similarly
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                doCommand("quit");
            }
        });

        // Define action listener adapters that connect the buttons to the app
        clear.addActionListener(new DrawActionListener("clear"));
        quit.addActionListener(new DrawActionListener("quit"));
        auto.addActionListener(new DrawActionListener("auto"));
        save.addActionListener(new DrawActionListener("save"));
        txtSave.addActionListener(new DrawActionListener("txtSave"));
        txtRead.addActionListener(new DrawActionListener("txtRead"));
        undo.addActionListener(new DrawActionListener("undo"));
        redo.addActionListener(new DrawActionListener("redo"));
    }


    /**
     * This is the application method that processes commands sent by the GUI
     */
    public void doCommand(String command) {
        if (command.equals("clear")) { // clear the GUI window
            // It would be more modular to include this functionality in the GUI
            // class itself. But for demonstration purposes, we do it here.
            app.clear();
        } else if (command.equals("quit")) { // quit the application
            this.dispose(); // close the GUI
            System.exit(0); // and exit.
        } else if (command.equals("auto")) {
            app.autoDraw();
        } else if (command.equals("save")) {
            try {
//                app.writeImage(bImg, "mybImg.png");
                JFileChooser jfc = new JFileChooser();
                int retVal = jfc.showSaveDialog(null);
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    File f = jfc.getSelectedFile();
                    String test = f.getAbsolutePath();
                    ImageIO.write( bImg, "png", new File(test));
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        } else if (command.equals("txtSave")) {
            try {
                JFileChooser jfc = new JFileChooser();
                int retVal = jfc.showSaveDialog(null);
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    File f = jfc.getSelectedFile();
                    String name = f.getAbsolutePath();
                    app.writeText(name);
                }
            } catch (TxtIOException e) {
                e.printStackTrace();
            }
        } else if (command.equals("txtRead")) {
            JFileChooser jfc = new JFileChooser();
            int retVal = jfc.showOpenDialog(null);
            if (retVal == JFileChooser.APPROVE_OPTION) {
                File f = jfc.getSelectedFile();
                String name = f.getAbsolutePath();
                try {
                    app.readText(f.getAbsolutePath());
                } catch (TxtIOException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else if (command.equals("undo")) {
            app.undo();
        } else if (command.equals("redo")) {
            app.redo();
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
     * @param newColor new drawing color
     */
    public void setColor(Color newColor) {
        color = newColor;
    }

    /**
     * Returns the color map with all available colors.
     *
     * @return color map
     */
    public Map<String, Color> getColorMap() {
        return cm;
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
     * returns the buffered image.
     *
     * @return buffered image
     */
    public BufferedImage getBufferedImage() {
        return bImg;
    }

    public Draw getApp() {
        return app;
    }

    public ShapeManager getShapeManager() {
        return shapeManager;
    }

    // Here's a local class used for action listeners for the buttons
    class DrawActionListener implements ActionListener {
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
