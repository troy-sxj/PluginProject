package com.mika.host;

import android.app.Application;
import android.content.Context;

import com.mika.dynamic.PluginManager;

/**
 * @Author: mika
 * @Time: 2018-12-11 18:27
 * @Description:
 */
public class MyApp extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
            PluginManager.init(this);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
