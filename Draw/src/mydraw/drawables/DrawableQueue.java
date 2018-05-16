package mydraw.drawables;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

/**
 * Class for managing drawings of the current session.
 */
public final class DrawableQueue {

	private static List<Drawable> QUEUE = new LinkedList<>();

	private static int CURRENT_INDEX = 0;

	/**
	 * Adds a drawable element to the queue.
	 *
	 * @param command
	 *            drawable element
	 */
	public static void add(Drawable command) {
		System.out.println("add " + CURRENT_INDEX);
		if (CURRENT_INDEX < QUEUE.size() - 1) {
			final List<Drawable> newQueue = new LinkedList<>();
			for (int i = 0; i < CURRENT_INDEX; i++) {
				newQueue.add(QUEUE.get(i));
			}
			QUEUE = newQueue;
		}
		CURRENT_INDEX++;
		QUEUE.add(command);
	}

	/**
	 * Redraws all elements in the queue to current state.
	 *
	 * @param g
	 *            Graphics object to draw on.
	 */
	public static void redraw(Graphics g) {
		System.out.println("redraw " + CURRENT_INDEX);
		for (int i = 0; i < CURRENT_INDEX; i++) {
			QUEUE.get(i).draw(g);
		}
	}

	/**
	 * Draws the image without the last element.
	 *
	 * @param g
	 *            Graphics object to draw on
	 */
	public static void undo(Graphics g) {
		System.out.println("undo " + CURRENT_INDEX);
		if (CURRENT_INDEX > 0) {
			CURRENT_INDEX--;
			redraw(g);
		}
	}

	/**
	 * Draws the image with the latest undone element.
	 *
	 * @param g
	 *            Graphics object to draw on
	 */
	public static void redo(Graphics g) {
		if (CURRENT_INDEX < QUEUE.size()) {
			CURRENT_INDEX++;
			redraw(g);
		}
	}

	/**
	 * Returns a copy of the queue.
	 *
	 * @return queue
	 */
	public static List<Drawable> getQueue() {
		return new LinkedList<>(QUEUE);
	}

	public static void setQueue(List<Drawable> newQueue) {
		QUEUE = newQueue;
	}
}