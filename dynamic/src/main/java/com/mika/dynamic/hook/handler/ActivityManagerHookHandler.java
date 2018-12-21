package com.mika.dynamic.hook.handler;

import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

import com.mika.dynamic.hook.HookHelper;

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

    private static String METHOD_START_ACTIVITY = "startActivity";
    private Object mBase;

    public ActivityManagerHookHandler(Object base) {
        this.mBase = base;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.d(TAG, "method: " + method.getName() + " called with args: " + Arrays.toString(args));
        if (METHOD_START_ACTIVITY.equals(method.getName())) {
            //只拦截这个方法；替换参数，设置替换原始Activity启动别的Activity偷梁换柱；找到参数里面的第一个Intent对象
            Log.e(TAG, "invoke startActivity");
            Intent raw;
            int index = 0;

            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Intent) {
                    index = i;
                    break;
                }
            }
            raw = (Intent) args[index];
            Intent newIntent = new Intent();
            String stubPackage = raw.getComponent().getPackageName();
            ComponentName componentName = new ComponentName(stubPackage, "com.mika.host.StubActivity");
            newIntent.setComponent(componentName);
            newIntent.putExtra(HookHelper.EXTRA_TARGET_INTENT, raw);
            args[index] = newIntent;
            Log.e(TAG, "hook success");
        }
        return method.invoke(mBase, args);
    }
}