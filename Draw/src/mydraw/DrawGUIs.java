package mydraw;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import mydraw.ShapeManager.FillRectDrawer;
import mydraw.ShapeManager.ShapeDrawer;

/** This class implements the GUI for our application */
public class DrawGUIs extends JFrame
{
    private BufferedImage bImg;
    private Color color;
    private JPanel panel;
    private final Map<String, Color> cm = new LinkedHashMap<String, Color>()
    {
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
    private final int frameWidth = 600;
    private final int frameHeight = 500;
    private final int menuBarHeight = 40;
    private final int panelHeight;

    private static final long serialVersionUID = 1L;

    public DrawGUIs(Draw application)
    {
        super("Draw");
        app = application;
        color = Color.BLACK;
        panelHeight = frameHeight - menuBarHeight;
        displayGUI();
    }

    public void displayGUI()
    {
        // Create the Contentpanel.
        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(frameWidth, panelHeight));

        // selector for drawing modes
        final Choice shape_chooser = new Choice();
        ShapeManager shapeManager = new ShapeManager(panel, this);
        for (final Entry<String, ShapeDrawer> enp : shapeManager.getDrawers())
        {
            shape_chooser.add(enp.getKey());
            System.out.println(enp.getKey());
        }
        shape_chooser.addItemListener(shapeManager);

        // selector for drawing colors
        final Choice color_chooser = new Choice();
        for (final Entry<String, Color> enp : cm.entrySet())
        {
            color_chooser.add(enp.getKey());
        }
        color_chooser.addItemListener(new ColorItemListener());
        
        // selector for backgroundcolors
        final Choice bgColor_chooser = new Choice();
        for (final Entry<String, Color> enp : cm.entrySet())
        {
            bgColor_chooser.add(enp.getKey());
        }
        bgColor_chooser.addItemListener(new BgColorItemListener());
        

        // Create buttons
        final JButton clear = new JButton("Clear");
        final JButton quit = new JButton("Quit");
        final JButton auto = new JButton("Auto");
        final JButton save = new JButton("Save");

        // Create the menu bar. Make it have a gray background.
        final JPanel bb = new JPanel();
        bb.setOpaque(true);
        bb.setBackground(Color.GRAY);
        bb.setPreferredSize(new Dimension(frameWidth, menuBarHeight));
        

        // Set a LayoutManager, and add the choosers and buttons to the menubar.
        // this.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        bb.add(new JLabel("Shape:"));
        bb.add(shape_chooser);
        bb.add(new JLabel("Color:"));
        bb.add(color_chooser);
        bb.add(new JLabel("Background:"));
        bb.add(bgColor_chooser);
        bb.add(clear, BorderLayout.NORTH);
        bb.add(quit, BorderLayout.NORTH);
        bb.add(auto, BorderLayout.NORTH);
        bb.add(save, BorderLayout.NORTH);

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
        this.getContentPane().add(bb, BorderLayout.NORTH);
        this.getContentPane()
            .add(panel, BorderLayout.CENTER);

        // Display the window.
        this.pack();
        this.setVisible(true);

        // Handle the window close request similarly
        this.addWindowListener(new WindowAdapter()
        {
            @Override
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

    public void paintComponent(Graphics g)
    {
        super.paintComponents(g);
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
//                app.writeImage(bImg, "mybImg.png");
                JFileChooser jfc = new JFileChooser();
                int retVal = jfc.showSaveDialog(null);
                if(retVal==JFileChooser.APPROVE_OPTION){
                    File f = jfc.getSelectedFile();
                    String test = f.getAbsolutePath();
                    ImageIO.write((BufferedImage)bImg,"png",new File(test));
                 }
            }
            catch (final IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color newColor)
    {
        color = newColor;
    }

    public Map<String, Color> getColorMap()
    {
        return cm;
    }

    public JPanel getDrawingPanel()
    {
        return panel;
    }

    public BufferedImage getBufferedImage()
    {
        return bImg;
    }

    // Here's a local class used for action listeners for the buttons
    class DrawActionListener implements ActionListener
    {
        private final String command;

        public DrawActionListener(String cmd)
        {
            command = cmd;
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            doCommand(command);
        }
    }

    class ColorItemListener implements ItemListener
    {
        // user selected new color => store new color in DrawGUIs
        @Override
        public void itemStateChanged(ItemEvent e)
        {
            final Color newColor = cm.get(e.getItem());
            if (newColor != null)
            {
                color = cm.get(e.getItem());
            }
        }
    }
    class BgColorItemListener implements ItemListener
    {
        // user selected new color => store new color in DrawGUIs
        @Override
        public void itemStateChanged(ItemEvent e)
        {
            final Color newColor = cm.get(e.getItem());
            if (newColor != null)
            {
                panel.setBackground(newColor);
            }
        }
    }
}
