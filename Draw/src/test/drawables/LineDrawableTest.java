package test.drawables;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.awt.Point;

import org.junit.jupiter.api.Test;

import mydraw.drawables.LineDrawable;

class LineDrawableTest {

	@Test
	void testToString() {
		final Point pressed = new Point(10, 10);
		final Point released = new Point(45, 80);
		final LineDrawable testee = new LineDrawable(pressed, released, Color.GREEN);

		final String expected = "line:10,10:45,80:green";
		assertEquals(expected, testee.toString());

		final Point pressed2 = new Point(40, 90);
		final Point released2 = new Point(5, 90);
		final LineDrawable testee2 = new LineDrawable(pressed2, released2, Color.BLACK);

		final String expected2 = "line:40,90:5,90:black";
		assertEquals(expected2, testee2.toString());
	}
}
