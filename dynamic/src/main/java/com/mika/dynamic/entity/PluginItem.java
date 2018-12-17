package com.mika.dynamic.entity;

import android.content.pm.PackageInfo;

/**
 * @Author: mika
 * @Time: 2018-12-11 15:04
 * @Description:
 */
public class PluginItem {

    public PackageInfo packageInfo;
    public String pluginPath;

    public PluginItem() {
    }

    public PluginItem(PackageInfo packageInfo, String pluginPath) {
        this.packageInfo = packageInfo;
        this.pluginPath = pluginPath;
    }

    @Override
    public String toString() {
        return "PluginItem{" +
                "packageInfo=" + packageInfo +
                ", pluginPath='" + pluginPath + '\'' +
                '}';
    }
}
