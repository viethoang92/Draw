package mydraw;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

public class CommandQueue {

	private static final List<Drawable> QUEUE = new LinkedList<>();

	private static int CURRENT_IDX = 0;

	public static void add(Drawable command) {
		QUEUE.add(command);
	}

	public static void workOffRequests(Graphics g) {
		for (final Drawable cmd : QUEUE) {
			cmd.draw(g);
		}
		CURRENT_IDX = QUEUE.size();
	}

	public static void undo(Graphics g) {
		CURRENT_IDX--;
		for (int i = 0; i < CURRENT_IDX; i++) {
			QUEUE.get(i).draw(g);
		}
	}
}
