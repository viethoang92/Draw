package mydraw;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

public class CommandQueue {

	private static final List<Drawable> QUEUE = new LinkedList<>();

	private static int CURRENT_INDEX = 0;

	public static void add(Drawable command) {
		CURRENT_INDEX++;
		for (int i = CURRENT_INDEX; i < QUEUE.size(); i++) {
			QUEUE.remove(i);
		}
		QUEUE.add(command);
	}

	public static void redraw(Graphics g) {
		for (int i = 0; i < CURRENT_INDEX; i++) {
			QUEUE.get(i).draw(g);
		}
	}

	public static void undo(Graphics dPanel, Graphics bImg) {
		System.out.println(CURRENT_INDEX);
		if (CURRENT_INDEX > 0) {
			CURRENT_INDEX--;
			redraw(dPanel);
			redraw(bImg);
		}
	}

	public static void redo(Graphics dPanel, Graphics bImg) {
		if (!(CURRENT_INDEX == QUEUE.size() - 1)) {
			CURRENT_INDEX++;
			redraw(dPanel);
			redraw(bImg);
		}
	}
}
