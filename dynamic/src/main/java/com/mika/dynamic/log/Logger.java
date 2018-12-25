package com.mika.dynamic.log;

/**
 * Logger.
 *
 * @author 12075179
 * @date 2017/1/5
 */
public interface Logger {

    /**
     * @return is debug or not.
     */
    boolean isDebug();

    /**
     * @param msg
     */
    void v(String msg);

    /**
     * @param tag
     * @param msg
     */
    void v(String tag, String msg);

    /**
     * @param msg
     */
    void d(String msg);

    /**
     * @param tag
     * @param msg
     */
    void d(String tag, String msg);

    /**
     * @param msg
     */
    void i(String msg);

    /**
     * @param tag
     * @param msg
     */
    void i(String tag, String msg);

    /**
     * @param msg
     */
    void w(String msg);

    /**
     * @param tag
     * @param msg
     */
    void w(String tag, String msg);

    /**
     * @param tag
     * @param error
     */
    void w(String tag, String msg, Throwable error);

    /**
     * @param msg
     */
    void e(String msg);

    /**
     * @param tag
     * @param msg
     */
    void e(String tag, String msg);

    /**
     * @param tag
     * @param error
     */
    void e(String tag, String msg, Throwable error);
}
