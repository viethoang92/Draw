package mydraw;

/**
 * Exception for invalid colors.
 */
public class ColorException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public ColorException() {
		super("Unknown Color");
	}
}