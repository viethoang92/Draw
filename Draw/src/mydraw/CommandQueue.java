package mydraw;

import javax.swing.plaf.ColorUIResource;
import java.awt.Graphics;
import java.sql.SQLOutput;
import java.util.LinkedList;
import java.util.List;

/**
 * Class for managing drawings of the current session.
 */
public final class CommandQueue {

	private static final List<Drawable> QUEUE = new LinkedList<>();

	private static int CURRENT_INDEX = 0;

	/**
	 * Adds a drawable element to the queue.
	 *
	 * @param command drawable element
	 */
	public static void add(Drawable command) {
		System.out.println("add " + CURRENT_INDEX);
		CURRENT_INDEX++;
		for (int i = CURRENT_INDEX; i < QUEUE.size(); i++) {
			QUEUE.remove(i);
		}
		QUEUE.add(command);

		System.out.println("current index: " + CURRENT_INDEX);
		System.out.println("queue size: " + QUEUE.size() + "\n");

	}

	/**
	 * Redraws all elements in the queue to current state.
	 *
	 * @param g Graphics object to draw on.
	 */
	public static void redraw(Graphics g) {
		System.out.println("redraw " + CURRENT_INDEX);
		for (int i = 0; i < CURRENT_INDEX; i++) {
			QUEUE.get(i).draw(g);
		}

		System.out.println("current index: " + CURRENT_INDEX);
		System.out.println("queue size: " + QUEUE.size()+ "\n");
	}

	/**
	 * Draws the image without the last element.
	 *
	 * @param g Graphics object to draw on
	 */
	public static void undo(Graphics g) {
		System.out.println("undo " + CURRENT_INDEX);
		if (CURRENT_INDEX > 0) {
			CURRENT_INDEX--;
			redraw(g);
		}
		System.out.println("current index: " + CURRENT_INDEX);
		System.out.println("queue size: " + QUEUE.size()+ "\n");
	}

	/**
	 * Draws the image with the latest undone element.
	 *
	 * @param g Graphics object to draw on
	 */
	public static void redo(Graphics g) {
		if (CURRENT_INDEX < QUEUE.size()) {
			CURRENT_INDEX++;
			redraw(g);
		}
		System.out.println("current index: " + CURRENT_INDEX);
		System.out.println("queue size: " + QUEUE.size()+ "\n");
	}


	public static List<Drawable> getQueue() {
		return QUEUE;
	}
}