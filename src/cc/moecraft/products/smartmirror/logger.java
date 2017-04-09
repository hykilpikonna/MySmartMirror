package cc.moecraft.products.smartmirror;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import static cc.moecraft.products.smartmirror.Main.debug;

public class logger
{
    private static Logger log = Logger.getLogger(logger.class);

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
        log.info(s);
    }

    public static void log(Level l, String s)
    {
        log.log(l, s);
    }
}
