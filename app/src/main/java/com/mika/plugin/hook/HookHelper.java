package com.mika.plugin.hook;

import android.content.Context;
import android.content.pm.PackageManager;

import java.lang.reflect.Proxy;

import invoke.RefInvoke;

/**
 * @Author: mika
 * @Time: 2018-11-15 16:11
 * @Description:
 */
public class HookHelper {

    public static void hookActivityManager() {
        try {
            //1. 获取ActivityManagerNative中的静态单例 gDefault
            Object gDefault = RefInvoke.getFieldObject("android.app.ActivityManagerNative", null, "gDefault");
            //2. gDefault是一个Singleton对象，取出其中的mInstance对象， 类型是IActivityManager
            Object rawIActivityManager = RefInvoke.getFieldObject("android.util.Singleton", gDefault, "mInstance");
            //3. 替换mInstance对象，是我们的HookHandler工作
            Class<?> iActivityManager = Class.forName("android.app.IActivityManager");
            Object proxyInstance = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{iActivityManager}, new HookHandler(rawIActivityManager));
            RefInvoke.setFieldObject("android.util.Singleton", gDefault, "mInstance", proxyInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void hookPackageManager(Context context){
        try{
            //1. 获取ActivityThread中 静态sCurrentActivityThread对象
            Object sCurrentActivityThread = RefInvoke.getFieldObject("android.app.ActivityThread", null, "sCurrentActivityThread");
            //2. 获取ActivityThread中 原始的sPackageManager
            Object sPackageManager = RefInvoke.getFieldObject("android.app.ActivityThread", sCurrentActivityThread, "sPackageManager");
            //3. 使用代理对象替换sPackageManager
            Class<?> iPackageManager = Class.forName("android.content.pm.IPackageManager");
            Object proxyInstance = Proxy.newProxyInstance(iPackageManager.getClassLoader(), new Class[]{iPackageManager}, new HookHandler(sPackageManager));
            RefInvoke.setFieldObject("android.app.ActivityThread", sPackageManager, "sPackageManager", proxyInstance);
            PackageManager packageManager = context.getPackageManager();
            RefInvoke.setFieldObject(packageManager, "mPm", proxyInstance);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
