package mydraw;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JPanel;

//this class determines how mouse events are to be interpreted,
// depending on the shape mode currently set
/**
 * Manages all available drawing shapes.
 */
class ShapeManager implements ItemListener {

    private final JPanel panel;
    private final DrawGUIs window;
    private final ScribbleDrawer scribbleDrawer = new ScribbleDrawer();
    private final RectangleDrawer rectDrawer = new RectangleDrawer();
    private final OvalDrawer ovalDrawer = new OvalDrawer();
    private final FillRectDrawer fillRectDrawer = new FillRectDrawer();
    private final FillOvalDrawer fillOvalDrawer = new FillOvalDrawer();
    private ShapeDrawer currentDrawer;

    private final Map<String,ShapeDrawer> sm = new LinkedHashMap<String, ShapeDrawer>()
    {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        {
            put("Scribble", scribbleDrawer);
            put("Rectangle", rectDrawer);
            put("Oval", ovalDrawer);
            put("FilledRectangle", fillRectDrawer);
            put("FilledOval", fillOvalDrawer);
        }
    };

	/**
	 * Constructor.
	 *
	 * @param itsPanel
	 *            drawing panel
	 * @param itsGUI
	 *            this application's gui
	 */
	public ShapeManager(JPanel itsPanel, DrawGUIs itsGUI) {
		this.window = itsGUI;
		this.panel = itsPanel;
		// default: scribble mode
		this.currentDrawer = scribbleDrawer;
		// activate scribble drawer
		panel.addMouseListener(currentDrawer);
		panel.addMouseMotionListener(currentDrawer);
	}

    /**
     * Return the key set of the ShapeDrawers.
     *
     * @return key set
     */
    abstract class ShapeDrawer extends MouseAdapter
            implements MouseMotionListener
    {
        @Override
        public void mouseMoved(MouseEvent e)
        {
            /* ignore */ }
    }

    // if this class is active, the mouse is interpreted as a pen
    /**
     * ShapeDrawer for polylines.
     */
    class ScribbleDrawer extends ShapeDrawer
    {
        private int lastx, lasty;

        private final List<Point> points = new ArrayList<>();

		@Override
		public void mousePressed(MouseEvent e) {
			lastx = e.getX();
			lasty = e.getY();
			points.add(new Point(lastx, lasty));
		}

        @Override
        public void mouseReleased(MouseEvent e)
        {
            final Graphics g = panel.getGraphics();
            final ScribbleCommand cmd = new ScribbleCommand(points,
                    window.getColor());
            CommandQueue.add(cmd);
            points.clear();
        }

		@Override
		public void mouseDragged(MouseEvent e) {
			final Graphics g = panel.getGraphics();
			final int x = e.getX(), y = e.getY();
			g.setColor(window.getColor());
			g.setPaintMode();
			g.drawLine(lastx, lasty, x, y);

            final Graphics gb = window.getBufferedImage()
                .createGraphics();
            gb.setColor(window.getColor());
            gb.setPaintMode();
            gb.drawLine(lastx, lasty, x, y);
            lastx = x;
            lasty = y;

            points.add(new Point(lastx, lasty));
        }
    }

	// if this class is active, rectangles are drawn
	/**
	 * ShapeDrawer for rectangles.
	 */
	class RectangleDrawer extends ShapeDrawer {
		int pressx, pressy;
		int lastx = -1, lasty = -1;

		// mouse pressed => fix first corner of rectangle
		@Override
		public void mousePressed(MouseEvent e) {
			pressx = e.getX();
			pressy = e.getY();
		}

        // mouse released => fix second corner of rectangle
        // and draw the resulting shape
        @Override
        public void mouseReleased(MouseEvent e)
        {
            final Graphics g = panel.getGraphics();
            final Graphics gb = window.getBufferedImage()
                .createGraphics();
            final Drawable cmd = new RectangleCommand(new Point(pressx, pressy),
                    new Point(e.getX(), e.getY()), window.getColor());

            if (lastx != -1)
            {
                // first undraw a rubber rect
                g.setXORMode(window.getColor());
                g.setColor(panel.getBackground());
                doDraw(pressx, pressy, lastx, lasty, g);

                gb.setXORMode(window.getColor());
                gb.setColor(panel.getBackground());
                doDraw(pressx, pressy, lastx, lasty, gb);
                lastx = -1;
                lasty = -1;
            }
            // these commands finish the rubberband mode
            // draw the final rectangle
            cmd.draw(g);
            cmd.draw(gb);
            CommandQueue.add(cmd);
        }

		// mouse released => temporarily set second corner of rectangle
		// draw the resulting shape in "rubber-band mode"
		@Override
		public void mouseDragged(MouseEvent e) {
			final Graphics g = panel.getGraphics();
			// these commands set the rubberband mode
			g.setXORMode(window.getColor());
			g.setColor(panel.getBackground());
			if (lastx != -1) {
				// first undraw previous rubber rect
				doDraw(pressx, pressy, lastx, lasty, g);
			}
			lastx = e.getX();
			lasty = e.getY();
			// draw new rubber rect
			doDraw(pressx, pressy, lastx, lasty, g);
		}

		/**
		 * Draws the shape.
		 *
		 * @param x0
		 *            first x-coordinate
		 * @param y0
		 *            first y-coordinate
		 * @param x1
		 *            second x-coordinate
		 * @param y1
		 *            second y-coordinate
		 * @param g
		 *            Graphics object to draw on
		 */
		public void doDraw(int x0, int y0, int x1, int y1, Graphics g) {
			// calculate upperleft and width/height of rectangle
			final int x = Math.min(x0, x1);
			final int y = Math.min(y0, y1);
			final int w = Math.abs(x1 - x0);
			final int h = Math.abs(y1 - y0);
			// draw rectangle
			g.drawRect(x, y, w, h);
		}
	}

    // if this class is active, ovals are drawn
    /**
     * ShapeDrawer for ovals.
     */
    class OvalDrawer extends RectangleDrawer
    {

        @Override
        public void mouseReleased(MouseEvent e)
        {
            final Graphics g = panel.getGraphics();
            final Graphics gb = window.getBufferedImage()
                .createGraphics();
            final Drawable cmd = new OvalCommand(new Point(pressx, pressy),
                    new Point(e.getX(), e.getY()), window.getColor());

            if (lastx != -1)
            {
                // first undraw a rubber rect
                g.setXORMode(window.getColor());
                g.setColor(panel.getBackground());
                doDraw(pressx, pressy, lastx, lasty, g);

                gb.setXORMode(window.getColor());
                gb.setColor(panel.getBackground());
                doDraw(pressx, pressy, lastx, lasty, g);
                lastx = -1;
                lasty = -1;
            }
            // these commands finish the rubberband mode
            // draw the final rectangle
            cmd.draw(g);
            cmd.draw(gb);
            CommandQueue.add(cmd);
        }

        @Override
        public void doDraw(int x0, int y0, int x1, int y1, Graphics g)
        {
            final int x = Math.min(x0, x1);
            final int y = Math.min(y0, y1);
            final int w = Math.abs(x1 - x0);
            final int h = Math.abs(y1 - y0);
            // draw oval instead of rectangle
            g.drawOval(x, y, w, h);
        }
    }

    class FillRectDrawer extends RectangleDrawer
    {
        @Override
        public void mouseReleased(MouseEvent e)
        {
            final Graphics g = panel.getGraphics();
            final Graphics gb = window.getBufferedImage()
                .createGraphics();
            final Drawable cmd = new FillRectCommand(new Point(pressx, pressy),
                    new Point(lastx, lasty), window.getColor());

            if (lastx != -1)
            {
                // first undraw a rubber rect
                g.setXORMode(window.getColor());
                g.setColor(panel.getBackground());
                doDraw(pressx, pressy, lastx, lasty, g);

                gb.setXORMode(window.getColor());
                gb.setColor(panel.getBackground());
                doDraw(pressx, pressy, lastx, lasty, gb);
                lastx = -1;
                lasty = -1;
            }
            // these commands finish the rubberband mode
            // draw the final rectangle
            cmd.draw(g);
            cmd.draw(gb);
            CommandQueue.add(cmd);
        }
    }

    /**
     * ShapeDrawer for filled ovals
     */
    class FillOvalDrawer extends OvalDrawer

    {
        @Override
        public void mouseReleased(MouseEvent e)
        {
            final Graphics g = panel.getGraphics();
            final Graphics gb = window.getBufferedImage()
                .createGraphics();
            final Drawable cmd = new FillOvalCommand(new Point(pressx, pressy),
                    new Point(e.getX(), e.getY()), window.getColor());

            if (lastx != -1)
            {
                // first undraw a rubber rect
                g.setXORMode(window.getColor());
                g.setColor(panel.getBackground());
                doDraw(pressx, pressy, lastx, lasty, g);

                gb.setXORMode(window.getColor());
                gb.setColor(panel.getBackground());
                doDraw(pressx, pressy, lastx, lasty, g);
                lastx = -1;
                lasty = -1;
            }
            // these commands finish the rubberband mode
            // draw the final rectangle
            cmd.draw(g);
            cmd.draw(gb);
            CommandQueue.add(cmd);
        }

        @Override
        public void doDraw(int x0, int y0, int x1, int y1, Graphics g)
        {
            final int x = Math.min(x0, x1);
            final int y = Math.min(y0, y1);
            final int w = Math.abs(x1 - x0);
            final int h = Math.abs(y1 - y0);
            // draw oval instead of rectangle
            g.fillOval(x, y, w, h);
        }
    }
    // reset the shape drawer
    /**
     * Sets current drawer.
     *
     * @param drawer
     *            new drawer
     */
    public void setCurrentDrawer(ShapeDrawer drawer)
    {
        if (currentDrawer == drawer) return;

        // deactivate previous drawer
        panel.removeMouseListener(currentDrawer);
        panel.removeMouseMotionListener(currentDrawer);
        // activate new drawer
        currentDrawer = drawer;
        panel.addMouseListener(currentDrawer);
        panel.addMouseMotionListener(currentDrawer);
    }

    // user selected new shape => reset the shape mode
    @Override
    public void itemStateChanged(ItemEvent e)
    {
        final ShapeDrawer newDrawer = sm.get(e.getItem());
        if (newDrawer != null) setCurrentDrawer(newDrawer);
    }
    
    
    public Set<Entry<String, ShapeDrawer>> getDrawerSet()
    {
        return sm.entrySet();
    }
}
