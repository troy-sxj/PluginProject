package com.mika.host;

import android.app.Application;
import android.content.Context;

import com.mika.dynamic.DyPlugin;
import com.mika.dynamic.utils.FileUtils;

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
            DyPlugin.getInstance(base).init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DyPlugin.loadPlugin("plugin1-debug.apk");
        DyPlugin.loadPlugin("plugin2-debug.apk");
    }

    /**
     * 模拟动态加载Plugin
     *
     * @param base
     * @TODO 1. 异步文件拷贝
     * 2. 同步拷贝状态（是否拷贝成功，成功后注册该插件为可用状态）
     */
    private void loadPlugin(Context base) {
        FileUtils.extractAssets(base, "plugin1-debug.apk");
    }

}
