package mydraw;

public class TxtIOException extends Exception
{
    private static final long serialVersionUID = 1L;

    public TxtIOException() {
        super("couldn't read or write txt.");
    }
}
