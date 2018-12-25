package com.mika.plugin1;

/**
 * @Author: mika
 * @Time: 2018/12/25 4:21 PM
 * @Description:
 */
public class JniUtils {

    static {
        System.loadLibrary("hello");
    }

    public native String getString();
}
