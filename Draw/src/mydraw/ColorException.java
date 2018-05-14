package mydraw;

/**
 * Exception for invalid colors.
 */
class ColorException extends Exception {

	/**
	 * Constructor.
	 */
	public ColorException() {
		super("Unknown Color");
	}
}