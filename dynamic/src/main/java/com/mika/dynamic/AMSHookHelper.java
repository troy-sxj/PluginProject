//package com.mika.dynamic;
//
//import com.mika.dynamic.hookhandler.ActivityManagerHookHandler;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Proxy;
//
//import invoke.RefInvoke;
//
///**
// * @Author: mika
// * @Time: 2018-11-29 15:58
// * @Description:
// */
//public class AMSHookHelper {
//
//
//
//    public static void hookAMN() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException{
//        Object gDefault = RefInvoke.getStaticFieldObject("android.app.ActivityManagerNative", "gDefault");
//        Object mInstance = RefInvoke.getFieldObject("android.util.Singleton", gDefault, "mInstance");
//        Class<?> iActivityManager = Class.forName("android.app.IActivityManager");
//        Object proxyInstance = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{iActivityManager}, new ActivityManagerHookHandler(mInstance));
//        RefInvoke.setFieldObject("android.util.Singleton", gDefault,"mInstance", proxyInstance);
//    }
//}
