package cc.moecraft.products.smartmirror;

import java.util.logging.Level;

import static cc.moecraft.products.smartmirror.Main.debug;

/**
 * Created by Kilpikonna on 2017/4/8 0008.
 */
public class logger
{
    public static void Debug(String s)
    {
        if (debug)
        {
            log("[DEBUG(" +
                    Thread.currentThread().getStackTrace()[2].getClassName() + "." +
                    Thread.currentThread().getStackTrace()[2].getMethodName() + ":" +
                    Thread.currentThread().getStackTrace()[2].getLineNumber() + ")] " + s);
        }
    }

    public static void Debug(Object object, String message)
    {
        if (debug)
        {
            log("[DEBUG(" +
                    Thread.currentThread().getStackTrace()[2].getClassName() + "." +
                    Thread.currentThread().getStackTrace()[2].getMethodName() + ":" +
                    Thread.currentThread().getStackTrace()[2].getLineNumber() + ")] " +
                    object.getClass().getSimpleName() + ": " + message);
        }
    }

    public static void log(String s)
    {
        log(Level.INFO, s);
    }

    public static void log(Level l, String s)
    {
        logger.log(l, s);
    }
}
