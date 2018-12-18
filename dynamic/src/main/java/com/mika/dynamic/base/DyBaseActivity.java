package com.mika.dynamic.base;

import android.app.Activity;
import android.content.res.Resources;

import com.mika.dynamic.PluginManager;

/**
 * @Author: mika
 * @Time: 2018-12-11 18:23
 * @Description:
 */
public class DyBaseActivity extends Activity {

    @Override
    public Resources getResources() {
//        if(PluginManager.mNowResources == null){
//            return PluginManager.mNowResources;
//        }
        return super.getResources();
    }
}
