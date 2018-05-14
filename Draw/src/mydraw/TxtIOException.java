package mydraw;


import java.io.IOException;

class TxtIOException extends IOException
{
    private static final long serialVersionUID = 1L;

    public TxtIOException() {
        super("A problem with the text input or text output occurred.");
    }
}
