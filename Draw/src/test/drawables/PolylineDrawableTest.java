package test.drawables;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.awt.Point;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import mydraw.drawables.PolylineDrawable;

class PolylineDrawableTest {

	@Test
	void testToString() {
		final List<Point> points = Arrays.asList(new Point(10, 10), new Point(35, 50), new Point(80, 90));
		final PolylineDrawable testee = new PolylineDrawable(points, Color.YELLOW);

		final String expected = "polyline:10,10;35,50;80,90:yellow";
		assertEquals(expected, testee.toString());

		final List<Point> points2 = Arrays.asList(new Point(38, 12), new Point(50, 120), new Point(0, 0));
		final PolylineDrawable testee2 = new PolylineDrawable(points2, Color.BLUE);

		final String expected2 = "polyline:38,12;50,120;0,0:blue";
		assertEquals(expected2, testee2.toString());
	}
}
