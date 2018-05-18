package mydraw;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Manager for available colors in this application.
 */
public class ColorManager {
	private static final Map<String, Color> MAP = new LinkedHashMap<String, Color>() {
		private static final long serialVersionUID = 1L;

		{
			put("black", Color.BLACK);
			put("green", Color.GREEN);
			put("red", Color.RED);
			put("blue", Color.BLUE);
			put("white", Color.WHITE);
			put("yellow", Color.YELLOW);
		}
	};

	/**
	 * Returns a Color object for its name.
	 *
	 * @param color
	 *            name of the color
	 * @return color object if name is valid, null else
	 */
	public static Color getColor(String color) {
		return MAP.get(color);
	}

	/**
	 * Returns the name of the color.
	 *
	 * @param color
	 *            color object
	 * @return name of the color if color is valid, null else
	 */
	public static String getColor(Color color) {
		for (final String key : MAP.keySet()) {
			if (MAP.get(key).equals(color))
				return key;
		}
		return null;
	}

	/**
	 * Returns the a set of all available color names.
	 *
	 * @return set of color names
	 */
	public static Set<String> getColorSet() {
		return MAP.keySet();
	}
}
