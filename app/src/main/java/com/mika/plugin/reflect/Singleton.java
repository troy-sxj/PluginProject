package com.mika.plugin.reflect;

/**
 * @Author: mika
 * @Time: 2018-11-15 10:51
 * @Description:
 */
public abstract class Singleton<T> {

    private T mInstance;

    protected abstract T create();

    public final T get(){
        if(mInstance == null){
            mInstance = create();
        }
        return mInstance;
    }
}
