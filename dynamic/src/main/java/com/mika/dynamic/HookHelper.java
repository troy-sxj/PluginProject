package com.mika.dynamic;

import android.content.Context;
import android.util.Log;

import java.lang.annotation.Target;
import java.lang.reflect.Proxy;
import java.sql.Ref;

import invoke.RefInvoke;

/**
 * @Author: mika
 * @Time: 2018-11-16 16:59
 * @Description:
 */
public final class HookHelper {

    private static final String TAG = HookHelper.class.getSimpleName();

    public static void hookActivityManager() {
        //获取ActivityManagerNative中的gDefault对象
        Object gDefault = RefInvoke.getStaticFieldObject("android.app.ActivityManagerNative", "gDefault");
        //获取gDefault中的mInstance实例
        Object mInstance = RefInvoke.getFieldObject("android.util.Singleton", gDefault,"mInstance");
        try {
            //gDefault中的mInstance是一个泛型对象：IActivityManager
            Class<?> iActivityManager = Class.forName("android.app.IActivityManager");
            //通过泛型反射，生成一个mInstance的代理
            Object proxyInstance = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{iActivityManager}, new ActivityManagerHookHandler(mInstance));
            //替换gDefault中的mInstance替换为我们的代理对象proxyInstance
            RefInvoke.setFieldObject("android.util.Singleton", gDefault, "mInstance", proxyInstance);
        } catch (Exception e) {
            Log.e(TAG, "hookActivityManager exception: "+ e.getMessage());
            e.printStackTrace();
        }
    }

    public static void hookPackageManager(Context context){
        //获取ActivityThread中的静态实例sCurrentActivityThread
        Object sCurrentActivityThread = RefInvoke.getStaticFieldObject("android.app.ActivityThread", "sCurrentActivityThread");
        //获取sCurrentActivityThread中的IPackageManager实例对象：sPackageManager
        Object sPackageManager = RefInvoke.getFieldObject("android.app.ActivityThread", sCurrentActivityThread, "sPackageManager");
        try{
            Class<?> iPackageManager = Class.forName("android.content.pm.IPackageManager");
            Object proxyInstance = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{iPackageManager}, new PackageManagerHookHandler(sPackageManager));
            //替换ActivityThread中的PackageManager
            RefInvoke.setFieldObject(sPackageManager, "sPackageManager", proxyInstance);

            //替换ApplicationPackageManager里面的mPM对象
            RefInvoke.setFieldObject(context.getPackageManager(), "mPM", proxyInstance);

        }catch (Exception e){
            Log.e(TAG, "hookPackageManager exception: "+ e.getMessage());
            e.printStackTrace();
        }
    }
}
