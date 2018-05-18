package test.drawables;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.awt.Point;

import org.junit.jupiter.api.Test;

import mydraw.drawables.FillOvalDrawable;

class FillOvalDrawableTest {

	@Test
	void testToString() {
		final Point pressed = new Point(10, 10);
		final Point released = new Point(45, 80);
		final FillOvalDrawable testee = new FillOvalDrawable(pressed, released, Color.BLACK);

		final String expected = "filledOval:10,10:45,80:black";
		assertEquals(expected, testee.toString());

		final Point pressed2 = new Point(75, 12);
		final Point released2 = new Point(300, 150);
		final FillOvalDrawable testee2 = new FillOvalDrawable(pressed2, released2, Color.BLUE);

		final String expected2 = "filledOval:75,12:300,150:blue";
		assertEquals(expected2, testee2.toString());
	}

}
