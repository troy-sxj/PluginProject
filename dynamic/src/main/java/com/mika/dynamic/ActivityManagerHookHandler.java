package com.mika.dynamic;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Author: mika
 * @Time: 2018-11-16 17:02
 * @Description:
 */
public class ActivityManagerHookHandler implements InvocationHandler {

    private static final String TAG = ActivityManagerHookHandler.class.getSimpleName();
    private Object mBase;

    public ActivityManagerHookHandler(Object base) {
        this.mBase = base;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.d(TAG, "method: " + method.getName() + " called with args: " + Arrays.toString(args));
        return method.invoke(mBase, args);
    }
}
