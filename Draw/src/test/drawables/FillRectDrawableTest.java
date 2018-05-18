package test.drawables;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.awt.Point;

import org.junit.jupiter.api.Test;

import mydraw.drawables.FillRectDrawable;

class FillRectDrawableTest {

	@Test
	void testToString() {
		final Point pressed = new Point(20, 20);
		final Point released = new Point(70, 80);
		final FillRectDrawable testee = new FillRectDrawable(pressed, released, Color.BLUE);

		final String expected = "filledRectangle:20,20:70,80:blue";
		assertEquals(expected, testee.toString());

		final Point pressed2 = new Point(65, 125);
		final Point released2 = new Point(100, 100);
		final FillRectDrawable testee2 = new FillRectDrawable(pressed2, released2, Color.YELLOW);

		final String expected2 = "filledRectangle:65,125:100,100:yellow";
		assertEquals(expected2, testee2.toString());
	}

}
