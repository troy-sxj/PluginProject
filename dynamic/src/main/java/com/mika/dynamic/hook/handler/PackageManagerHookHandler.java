package com.mika.dynamic.hook.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author: mika
 * @Time: 2018-11-16 17:29
 * @Description:
 */
public class PackageManagerHookHandler implements InvocationHandler {

    private static final String TAG = PackageManagerHookHandler.class.getSimpleName();

    private Object mBase;

    public PackageManagerHookHandler(Object base) {
        this.mBase = base;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
