package com.mika.plugin.hook;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Author: mika
 * @Time: 2018-11-15 16:12
 * @Description:
 */
public class HookHandler implements InvocationHandler {

    private static final String TAG = HookHandler.class.getSimpleName();

    private Object mBase;

    public HookHandler(Object base) {
        this.mBase = base;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.d(TAG, "before hook");
        Log.d(TAG, "method: " + method.getName() + ", called with args: " + Arrays.toString(args));
        return method.invoke(mBase, args);
    }
}
