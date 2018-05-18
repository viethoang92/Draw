package mydraw;

import java.io.IOException;

/**
 * Exception for failures during writing and reading of drawable files.
 */
class TxtIOException extends IOException {
	private static final long serialVersionUID = 1L;

	public TxtIOException() {
		super("A problem with the text input or text output occurred.");
	}
}
