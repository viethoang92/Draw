package mydraw;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

public class CommandQueue {

	private static final List<Drawable> QUEUE = new LinkedList<>();

	private static int CURRENT_INDEX = 0;

	public static void add(Drawable command) {
		QUEUE.add(command);
		CURRENT_INDEX++;
	}

	public static void redraw(Graphics g) {
		for (final Drawable cmd : QUEUE) {
			cmd.draw(g);
		}
		CURRENT_INDEX = QUEUE.size();
	}

	public static void undo(Graphics g) {
		CURRENT_INDEX--;
		for (int i = 0; i < CURRENT_INDEX; i++) {
			QUEUE.get(i).draw(g);
		}
	}
}
