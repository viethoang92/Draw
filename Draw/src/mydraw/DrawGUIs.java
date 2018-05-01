package mydraw;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import mydraw.DrawGUIs.ShapeManager.ShapeDrawer;

/** This class implements the GUI for our application */
public class DrawGUIs extends JFrame
{
    public BufferedImage bImg;
    public Color color;
    public JPanel panel;
    public HashMap <String, Color> cm;
    
    private Draw app;
    private int frameWidth = 600;
    private int frameHeight = 500;
    private int menuBarHeight = 20;
    private int panelHeight;

    private static final long serialVersionUID = 1L;

    public DrawGUIs(Draw application)
    {
        super("Draw");
        app = application;
        color = Color.BLACK;
        cm = new HashMap <String, Color> ();
        panelHeight = frameHeight - menuBarHeight;
        initColors();
        displayGUI();
    }

    public void initColors()
    {
        cm.put("black", Color.BLACK);
        cm.put("green", Color.GREEN);
        cm.put("red", Color.RED);
        cm.put("blue", Color.BLUE);
        cm.put("white", Color.WHITE);
    }
    public void displayGUI()
    {
     // Create the Contentpanel.  
        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(frameWidth, panelHeight));
        
        // selector for drawing modes
        Choice shape_chooser = new Choice();
        shape_chooser.add("Scribble");
        shape_chooser.add("Rectangle");
        shape_chooser.add("Oval");
        shape_chooser.addItemListener(new ShapeManager(panel));

        // selector for drawing colors
        Choice color_chooser = new Choice();
        for(Entry<String, Color> enp : cm.entrySet())
        {
            String str = enp.getKey();
            color_chooser.add(str);
        }
        color_chooser.addItemListener(new ColorItemListener());

        // Create buttons
        JButton clear = new JButton("Clear");
        JButton quit = new JButton("Quit");
        JButton auto = new JButton("Auto");
        JButton save = new JButton("Save");

        // Create the menu bar.  Make it have a gray background.
        JMenuBar mb = new JMenuBar();
        mb.setOpaque(true);
        mb.setBackground(Color.GRAY);
        mb.setPreferredSize(new Dimension(frameWidth, menuBarHeight));

        // Set a LayoutManager, and add the choosers and buttons to the menubar.
        //      this.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        mb.add(new JLabel("Shape:"));
        mb.add(shape_chooser);
        mb.add(new JLabel("Color:"));
        mb.add(color_chooser);
        mb.add(clear, BorderLayout.NORTH);
        mb.add(quit, BorderLayout.NORTH);
        mb.add(auto, BorderLayout.NORTH);
        mb.add(save, BorderLayout.NORTH);

        // Setup BufferedImage
        bImg = new BufferedImage(frameWidth, panelHeight,
                BufferedImage.TYPE_INT_ARGB);
        Graphics bg = bImg.createGraphics();
        bg.setColor(Color.WHITE);
        bg.fillRect(0, 0, bImg.getWidth(), bImg.getHeight());

        // Set a LayoutManager
        this.setLayout(new BorderLayout(3, 3));
        this.setSize(frameWidth, frameHeight);
        this.setBackground(Color.WHITE);
        this.setForeground(Color.BLACK);

        //Set the menu bar and add the panel to the Content
        this.setJMenuBar(mb);
        this.getContentPane().add(panel, BorderLayout.CENTER);

        //Display the window.
        this.pack();
        this.setVisible(true);

        // Handle the window close request similarly
        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                doCommand("quit");
            }
        });

        // Define action listener adapters that connect the buttons to the app
        clear.addActionListener(new DrawActionListener("clear"));
        quit.addActionListener(new DrawActionListener("quit"));
        auto.addActionListener(new DrawActionListener("auto"));
        save.addActionListener(new DrawActionListener("save"));
    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponents(g);
        g.drawImage(bImg, 0, 0, null);
    }

    /** This is the application method that processes commands sent by the GUI */
    public void doCommand(String command)
    {
        if (command.equals("clear"))
        { // clear the GUI window
          // It would be more modular to include this functionality in the GUI
          // class itself. But for demonstration purposes, we do it here.
            app.clear();
        }
        else if (command.equals("quit"))
        { // quit the application
            this.dispose(); // close the GUI
            System.exit(0); // and exit.
        }

        else if (command.equals("auto"))
        {
            app.autoDraw();
        }
        else if (command.equals("save"))
        {
            try
            {
                app.writeImage(bImg, "mybImg");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    //Here's a local class used for action listeners for the buttons
    class DrawActionListener implements ActionListener
    {
        private String command;

        public DrawActionListener(String cmd)
        {
            command = cmd;
        }

        public void actionPerformed(ActionEvent e)
        {
            doCommand(command);
        }
    }

    // this class determines how mouse events are to be interpreted,
    // depending on the shape mode currently set
    class ShapeManager implements ItemListener
    {
       JPanel panel;

        ScribbleDrawer scribbleDrawer = new ScribbleDrawer();
        RectangleDrawer rectDrawer = new RectangleDrawer();
        OvalDrawer ovalDrawer = new OvalDrawer();
        ShapeDrawer currentDrawer;

        public ShapeManager(JPanel itsPanel)
        {
            panel = itsPanel;
            // default: scribble mode
            currentDrawer = scribbleDrawer;
            // activate scribble drawer
            panel.addMouseListener(currentDrawer);
            panel.addMouseMotionListener(currentDrawer);
        }

        abstract class ShapeDrawer extends MouseAdapter
                implements MouseMotionListener
        {
            public void mouseMoved(MouseEvent e)
            {
                /* ignore */ }
        }

        // if this class is active, the mouse is interpreted as a pen
        class ScribbleDrawer extends ShapeDrawer
        {
            int lastx, lasty;

            public void mousePressed(MouseEvent e)
            {
                lastx = e.getX();
                lasty = e.getY();
            }

            public void mouseDragged(MouseEvent e)
            {
                Graphics g = panel.getGraphics();
                int x = e.getX(), y = e.getY();
                g.setColor(color);
                g.setPaintMode();
                g.drawLine(lastx, lasty, x, y);
                
                Graphics gb = bImg.createGraphics();
                gb.setColor(color);
                gb.setPaintMode();
                gb.drawLine(lastx, lasty, x, y);
                lastx = x;
                lasty = y;
            }
        }

        // if this class is active, rectangles are drawn
        class RectangleDrawer extends ShapeDrawer
        {
            int pressx, pressy;
            int lastx = -1, lasty = -1;

            // mouse pressed => fix first corner of rectangle
            public void mousePressed(MouseEvent e)
            {
                pressx = e.getX();
                pressy = e.getY();
            }

            // mouse released => fix second corner of rectangle
            // and draw the resulting shape
            public void mouseReleased(MouseEvent e)
            {
                Graphics g = panel.getGraphics();
                Graphics gb = bImg.createGraphics();
                if (lastx != -1)
                {
                    // first undraw a rubber rect
                    g.setXORMode(color);
                    g.setColor(panel.getBackground());
                    doDraw(pressx, pressy, lastx, lasty, g);
                    
                    gb.setXORMode(color);
                    gb.setColor(panel.getBackground());
                    doDraw(pressx, pressy, lastx, lasty, gb);
                    lastx = -1;
                    lasty = -1;
                }
                // these commands finish the rubberband mode
                g.setPaintMode();
                g.setColor(color);
                // draw the final rectangle
                doDraw(pressx, pressy, e.getX(), e.getY(), g);
                
                gb.setPaintMode();
                gb.setColor(color);
             // draw the final rectangle
                doDraw(pressx, pressy, e.getX(), e.getY(), gb);
            }

            // mouse released => temporarily set second corner of rectangle
            // draw the resulting shape in "rubber-band mode"
            public void mouseDragged(MouseEvent e)
            {
                Graphics g = panel.getGraphics();
                // these commands set the rubberband mode
                g.setXORMode(color);
                g.setColor(panel.getBackground());
                if (lastx != -1)
                {
                    // first undraw previous rubber rect
                    doDraw(pressx, pressy, lastx, lasty, g);
                }
                lastx = e.getX();
                lasty = e.getY();
                // draw new rubber rect
                doDraw(pressx, pressy, lastx, lasty, g);
            }

            public void doDraw(int x0, int y0, int x1, int y1, Graphics g)
            {
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
        class OvalDrawer extends RectangleDrawer
        {
            public void doDraw(int x0, int y0, int x1, int y1, Graphics g)
            {
                int x = Math.min(x0, x1);
                int y = Math.min(y0, y1);
                int w = Math.abs(x1 - x0);
                int h = Math.abs(y1 - y0);
                // draw oval instead of rectangle
                g.drawOval(x, y, w, h);
            }
        }

        // reset the shape drawer
        public void setCurrentDrawer(ShapeDrawer l)
        {
            if (currentDrawer == l) return;

            // deactivate previous drawer
            panel.removeMouseListener(currentDrawer);
            panel.removeMouseMotionListener(currentDrawer);
            // activate new drawer
            currentDrawer = l;
            panel.addMouseListener(currentDrawer);
            panel.addMouseMotionListener(currentDrawer);
        }

        // user selected new shape => reset the shape mode
        public void itemStateChanged(ItemEvent e)
        {
            if (e.getItem()
                .equals("Scribble"))
            {
                setCurrentDrawer(scribbleDrawer);
            }
            else if (e.getItem()
                .equals("Rectangle"))
            {
                setCurrentDrawer(rectDrawer);
            }
            else if (e.getItem()
                .equals("Oval"))
            {
                setCurrentDrawer(ovalDrawer);
            }
        }
    }

    class ColorItemListener implements ItemListener
    {
        // user selected new color => store new color in DrawGUIs
        public void itemStateChanged(ItemEvent e)
        {
            color = cm.get(e.getItem());
        }
    }
}