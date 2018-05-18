package mydraw.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;

import mydraw.DrawGUIs;

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
	private final LineDrawer lineDrawer;
	private ShapeDrawer currentDrawer;

	private final Map<String, ShapeDrawer> map = new LinkedHashMap<>();

	/**
	 * Constructor.
	 *
	 * @param itsPanel
	 *            drawing panel
	 * @param itsGUI
	 *            the gui window
	 */
	public ShapeManager(JPanel itsPanel, DrawGUIs itsGUI) {
		window = itsGUI;
		this.panel = itsPanel;
		scribbleDrawer = new ScribbleDrawer(window);
		rectDrawer = new RectangleDrawer(window);
		ovalDrawer = new OvalDrawer(window);
		fillRectDrawer = new FillRectDrawer(window);
		fillOvalDrawer = new FillOvalDrawer(window);
		lineDrawer = new LineDrawer(window);
		// default: scribble mode
		this.currentDrawer = scribbleDrawer;
		// activate scribble drawer
		panel.addMouseListener(currentDrawer);
		panel.addMouseMotionListener(currentDrawer);

		map.put("Scribble", scribbleDrawer);
		map.put("Rectangle", rectDrawer);
		map.put("Oval", ovalDrawer);
		map.put("FilledRectangle", fillRectDrawer);
		map.put("FilledOval", fillOvalDrawer);
		map.put("Line", lineDrawer);
	}

	// reset the shape drawer
	/**
	 * Sets current drawer.
	 *
	 * @param drawer
	 *            new drawer
	 */
	private void setCurrentDrawer(ShapeDrawer drawer) {
		if (currentDrawer == drawer)
			return;

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
	public void itemStateChanged(ItemEvent e) {
		final ShapeDrawer newDrawer = map.get(e.getItem());
		if (newDrawer != null)
			setCurrentDrawer(newDrawer);
	}

	/**
	 * Returns a set with all available drawer names.
	 *
	 * @return set of drawer names
	 */
	public Set<String> getDrawerSet() {
		return map.keySet();
	}
}
