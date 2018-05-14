package mydraw;


import java.io.IOException;

class TxtIOException extends IOException
{
    private static final long serialVersionUID = 1L;

    public TxtIOException() {
        super("couldn't read or write txt.");
    }
}
