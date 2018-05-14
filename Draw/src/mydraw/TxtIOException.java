package mydraw;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TxtIOException extends IOException
{
    private static final long serialVersionUID = 1L;

    public TxtIOException() {
        super("couldn't read or write txt.");
    }
}
