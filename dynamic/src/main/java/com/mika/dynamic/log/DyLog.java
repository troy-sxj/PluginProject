package com.mika.dynamic.log;


/**
 * @Author: mika
 * @Time: 2018/12/25 11:49 AM
 * @Description:
 */
public class DyLog {

    /**
     * {@link Logger}
     **/
    private static Logger mLogger = new DefaultLogger();
    /**
     * is debug or not.
     **/
    private static boolean isDebug = false;

    /**
     * set {@link Logger}
     *
     * @param logger
     */
    public static void setLogger(Logger logger) {
        if (logger != null) {
            mLogger = logger;
        }
    }

    /**
     * enable debug mode.
     */
    public static void enableDebug(boolean enable) {
        isDebug = enable;
    }

    /**
     * @return is debug or not.
     */
    public static boolean isDebug() {
        return isDebug;
    }

    /**
     * @param msg
     */
    public static void v(String msg) {
        if (isDebug) {
            mLogger.v(msg);
        }
    }

    /**
     * @param tag
     * @param msg
     */
    public static void v(String tag, String msg) {
        if (isDebug) {
            mLogger.v(tag, msg);
        }
    }

    /**
     * @param msg
     */
    public static void d(String msg) {
        if (isDebug) {
            mLogger.d(msg);
        }
    }

    /**
     * @param tag
     * @param msg
     */
    public static void d(String tag, String msg) {
        if (isDebug) {
            mLogger.d(tag, msg);
        }
    }

    /**
     * @param msg
     */
    public static void i(String msg) {
        if (isDebug) {
            mLogger.i(msg);
        }
    }

    /**
     * @param tag
     * @param msg
     */
    public static void i(String tag, String msg) {
        if (isDebug) {
            mLogger.i(tag, msg);
        }
    }

    /**
     * @param msg
     */
    public static void w(String msg) {
        if (isDebug) {
            mLogger.w(msg);
        }
    }

    /**
     * @param tag
     * @param msg
     */
    public static void w(String tag, String msg) {
        if (isDebug) {
            mLogger.w(tag, msg);
        }
    }

    /**
     * @param tag
     * @param error
     */
    public static void w(String tag, String msg, Throwable error) {
        if (isDebug) {
            mLogger.w(tag, msg, error);
        }
    }

    /**
     * @param msg
     */
    public static void e(String msg) {
        if (isDebug) {
            mLogger.e(msg);
        }
    }

    /**
     * @param tag
     * @param msg
     */
    public static void e(String tag, String msg) {
        if (isDebug) {
            mLogger.e(tag, msg);
        }
    }

    /**
     * @param tag
     * @param error
     */
    public static void e(String tag, String msg, Throwable error) {
        if (isDebug) {
            mLogger.e(tag, msg, error);
        }
    }
}
