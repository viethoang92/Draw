package mydraw.listeners;

import mydraw.DrawGUIs;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JPanel;

//this class determines how mouse events are to be interpreted,
// depending on the shape mode currently set
/**
 * Manages all available drawing shapes.
 */
public class ShapeManager implements ItemListener {

    private final JPanel panel;
    private static DrawGUIs window;
    private final ScribbleDrawer scribbleDrawer;
    private final RectangleDrawer rectDrawer;
    private final OvalDrawer ovalDrawer;
    private final FillRectDrawer fillRectDrawer;
    private final FillOvalDrawer fillOvalDrawer;
    private ShapeDrawer currentDrawer;

    private final Map<String,ShapeDrawer> sm = new LinkedHashMap<>();

	/**
	 * Constructor.
	 *  @param itsPanel
	 *            drawing panel
	 * @param itsGUI
     */
	public ShapeManager(JPanel itsPanel, DrawGUIs itsGUI) {
		this.window = itsGUI;
		this.panel = itsPanel;
        scribbleDrawer = new ScribbleDrawer(window);
        rectDrawer = new RectangleDrawer(window);
        ovalDrawer = new OvalDrawer(window);
        fillRectDrawer = new FillRectDrawer(window);
        fillOvalDrawer = new FillOvalDrawer(window);
        // default: scribble mode
        this.currentDrawer = scribbleDrawer;
        // activate scribble drawer
        panel.addMouseListener(currentDrawer);
        panel.addMouseMotionListener(currentDrawer);


        sm.put("Scribble", scribbleDrawer);
        sm.put("Rectangle", rectDrawer);
        sm.put("Oval", ovalDrawer);
        sm.put("FilledRectangle", fillRectDrawer);
        sm.put("FilledOval", fillOvalDrawer);
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

    public ShapeDrawer getCurrentDrawer() {
        return currentDrawer;
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
