package test.drawables;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.awt.Point;

import org.junit.jupiter.api.Test;

import mydraw.drawables.OvalDrawable;

class OvalDrawableTest {

	@Test
	void testToString() {
		final Point pressed = new Point(10, 10);
		final Point released = new Point(45, 80);
		final OvalDrawable testee = new OvalDrawable(pressed, released, Color.BLACK);

		final String expected = "oval:10,10:45,80:black";
		assertEquals(expected, testee.toString());

		final Point pressed2 = new Point(200, 150);
		final Point released2 = new Point(30, 65);
		final OvalDrawable testee2 = new OvalDrawable(pressed2, released2, Color.GREEN);

		final String expected2 = "oval:200,150:30,65:green";
		assertEquals(expected2, testee2.toString());
	}
}
