package com.mika.plugin.reflect;

import android.util.Log;

/**
 * @Author: mika
 * @Time: 2018-11-14 18:50
 * @Description:
 */
public class TestClassCtor {

    private final String Tag = TestClassCtor.class.getSimpleName();

    private String name;

    private static String ccc;

    public TestClassCtor() {
        Log.e(Tag, "TestClassCtor()");
        name = "mika";
    }

    public TestClassCtor(int a) {
        Log.e(Tag, "TestClassCtor(int a)");
    }

    public TestClassCtor(int a, String b) {
        Log.e(Tag, "TestClassCtor(int a, String b)");
        name = b;
    }

    private TestClassCtor(int a, double d) {
        Log.e(Tag, "TestClassCtor(int a, double d)");

    }

    private String doSomething(String d) {
        Log.e(Tag, "doSomething: " + d);
        return d;
    }

    public String getName() {
        return name;
    }

    public String getStatic() {
        return ccc;
    }
}
