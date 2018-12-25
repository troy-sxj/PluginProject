package com.mika.dynamic.model;

import android.content.pm.PackageInfo;
import android.content.pm.PackageItemInfo;

/**
 * @Author: mika
 * @Time: 2018/12/25 11:55 AM
 * @Description:
 */
public class PluginInfo {

    public static final int COMPONENT_ACTIVITY = 1;

    public String pluginName;
    public PackageInfo packageInfo;
    public String pluginPath;

    public PluginInfo() {
    }

    public PluginInfo(PackageInfo packageInfo, String pluginPath) {
        this.packageInfo = packageInfo;
        this.pluginPath = pluginPath;
    }


    public boolean containComponent(String componentName){
        return containComponent(componentName, COMPONENT_ACTIVITY);
    }

    public boolean containComponent(String componentName, int type){
        boolean isContain = false;
        switch (type){
            case COMPONENT_ACTIVITY:
                isContain = checkContain(packageInfo.activities, componentName);
                break;

        }
        return isContain;
    }

    private boolean checkContain(PackageItemInfo[] infoArray, String activityClassName) {
        if (infoArray == null || infoArray.length == 0) {
            return false;
        }
        String name;
        for (int i = 0; i < infoArray.length; i++) {
            name = infoArray[i].name;
            if (name.startsWith(".")) {
                name = infoArray[i].packageName + name;
            }
            if (name.equals(activityClassName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "PluginItem{" +
                "packageInfo=" + packageInfo +
                ", pluginPath='" + pluginPath + '\'' +
                '}';
    }
}
