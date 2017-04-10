package cc.moecraft.products.smartmirror;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import static cc.moecraft.products.smartmirror.Main.debug;

public class logger
{
    private static Logger log = Logger.getLogger(logger.class);

    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static void Debug(String s)
    {
        if (debug)
        {
            pureLog("[" + YELLOW + "DEBUG" + WHITE + "][" + CYAN +
                    Thread.currentThread().getStackTrace()[2].getClassName() + "." +
                    Thread.currentThread().getStackTrace()[2].getMethodName() + ":" +
                    Thread.currentThread().getStackTrace()[2].getLineNumber() + WHITE + "] " + GREEN + stringFormat(s));
        }
    }

    public static void Debug(Object object, String message)
    {
        if (debug)
        {
            pureLog("[" + YELLOW + "DEBUG" + WHITE + "][" + CYAN +
                    Thread.currentThread().getStackTrace()[2].getClassName() + "." +
                    Thread.currentThread().getStackTrace()[2].getMethodName() + ":" +
                    Thread.currentThread().getStackTrace()[2].getLineNumber() + WHITE + "][" + CYAN +
                    object.getClass().getSimpleName() + WHITE + "] " + GREEN + stringFormat(message));
        }
    }

    public static void log(String s)
    {
        log(Level.INFO, s);
    }

    public static void log(Level l, String s)
    {
        log.log(l, WHITE + "[" + CYAN + "LOG" + WHITE + "] " + stringFormat(s));
    }

    private static void pureLog(String s)
    {
        log.info(s);
    }

    public static String stringFormat(String s)
    {
        String output = "";
        for (int i = 0; i < s.length(); i++)
        {
            switch (s.charAt(i))
            {
                case '[':
                    output += WHITE + "[" + CYAN;
                    break;
                case ']':
                    output += WHITE + "]" + GREEN;
                    break;
                default:
                    output += s.charAt(i);
            }
        }
        return output;
    }
}