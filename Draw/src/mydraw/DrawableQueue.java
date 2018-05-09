package mydraw;

import java.awt.Graphics;
/**
 * The CommandFactory class.
 */
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class DrawableQueue
{
    private final List<Drawable> drawables;

    private DrawableQueue()
    {
        drawables = new LinkedList<Drawable>();
    }

    public void addDrawable(final Drawable drawable)
    {
        drawables.add(drawable);
    }

    public void executeDrawable(Graphics g)
    {
        drawables.get(0)
            .draw(g);
    }

    /* Factory pattern */
    public static DrawableQueue init()
    {
        final DrawableQueue dq = new DrawableQueue();

        // TODO 
        // commands are added here using lambdas. It is also possible to dynamically add commands without editing the code.
        //        cf.addDrawable("Light on", () -> System.out.println("Light turned on"));
        //        cf.addDrawable("Light off", () -> System.out.println("Light turned off"));

        return dq;
    }
}

// TODO
//public final class Main {
//    public static void main(final String[] arguments) {
//        final CommandFactory cf = CommandFactory.init();
//        
//        cf.executeCommand("Light on");
//        cf.listCommands();
//    }
//}