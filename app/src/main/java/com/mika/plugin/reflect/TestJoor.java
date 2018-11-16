package com.mika.plugin.reflect;


import android.util.Log;

import com.mika.reflect.Reflect;

/**
 * @Author: mika
 * @Time: 2018-11-15 11:20
 * @Description:
 */
public class TestJoor {

    public  static void testClassByString(){
        Reflect r1 = Reflect.on(Object.class);
        Reflect r2 = Reflect.on("java.lang.Object");
        Reflect r3 = Reflect.on("java.lang.Object",ClassLoader.getSystemClassLoader());

        Object o1 = Reflect.on(Object.class).get();
        Object o2 = Reflect.on("java.lang.Object").get();


        String j1 = Reflect.on((Object) "abc").get();
        int j2 = Reflect.on(1).get();

        Class<?> type = Reflect.on("android.widget.Button").type();
        Log.e("aaa", j1 + " , "  +j2 + ", "+type.getSuperclass());
    }
}
