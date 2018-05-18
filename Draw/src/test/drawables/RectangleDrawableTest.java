package test.drawables;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.awt.Point;

import org.junit.jupiter.api.Test;

import mydraw.drawables.RectangleDrawable;

class RectangleDrawableTest {

	@Test
	void testToString() {
		final Point pressed = new Point(10, 10);
		final Point released = new Point(45, 80);
		final RectangleDrawable testee = new RectangleDrawable(pressed, released, Color.BLACK);

		final String expected = "rectangle:10,10:45,80:black";
		assertEquals(expected, testee.toString());

		final Point pressed2 = new Point(80, 15);
		final Point released2 = new Point(125, 30);
		final RectangleDrawable testee2 = new RectangleDrawable(pressed2, released2, Color.WHITE);

		final String expected2 = "rectangle:80,15:125,30:white";
		assertEquals(expected2, testee2.toString());
	}
}
