package com.mika.dynamic.log;

import android.util.Log;

/**
 * default logger.
 *
 * @author 12075179
 * @date 2016/11/14
 */
public class DefaultLogger implements Logger {

    private static final String TAG = "DefaultLog";

    @Override
    public boolean isDebug() {
        return true;
    }

    @Override
    public void v(String msg) {
        v(TAG, msg);
    }

    @Override
    public void v(String tag, String msg) {
        Log.v(tag, msg);
    }

    @Override
    public void d(String msg) {
        d(TAG, msg);
    }

    @Override
    public void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    @Override
    public void i(String msg) {
        i(TAG, msg);
    }

    @Override
    public void i(String tag, String msg) {
        Log.i(tag, msg);
    }

    @Override
    public void w(String msg) {
        w(TAG, msg);
    }

    @Override
    public void w(String tag, String msg) {
        Log.w(tag, msg);
    }

    @Override
    public void w(String tag, String msg, Throwable error) {
        Log.w(tag, msg, error);
    }

    @Override
    public void e(String msg) {
        e(TAG, msg);
    }

    @Override
    public void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    @Override
    public void e(String tag, String msg, Throwable error) {
        Log.e(tag, msg, error);
    }
}