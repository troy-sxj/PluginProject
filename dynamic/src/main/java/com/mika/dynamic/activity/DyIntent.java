package com.mika.dynamic.activity;

import android.content.Intent;

import com.mika.dynamic.DyConstant;
import com.mika.dynamic.DyPlugin;
import com.mika.dynamic.model.PluginInfo;

/**
 * @Author: mika
 * @Time: 2018/12/25 2:42 PM
 * @Description:
 */
public class DyIntent extends Intent {

    private String pluginActivityClassName;


    public DyIntent(String pluginClassName) {
        this.pluginActivityClassName = pluginClassName;
        preStartPluginActivity();
    }

    protected void preStartPluginActivity() {
        PluginInfo pluginInfo = DyPlugin.queryActivity(pluginActivityClassName);
        if (pluginInfo != null) {
            setAction(DyConstant.PROXY_VIEW_ACTION);
            putExtra(DyConstant.EXTRA_DEX_PATH, pluginInfo.pluginPath);
            putExtra(DyConstant.EXTRA_DEX_CLASS, pluginActivityClassName);
        }
    }


}
