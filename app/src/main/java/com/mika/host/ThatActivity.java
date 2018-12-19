package com.mika.host;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.mika.dynamic.DynamicConstant;
import com.mika.dynamic.PluginManager;
import com.mika.dynamic.entity.PluginItem;


/**
 * @Author: mika
 * @Time: 2018-12-17 18:37
 * @Description:
 */
public class ThatActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_that);

        findViewById(R.id.btnLoadPlugin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPlugin();
            }
        });
    }

    private void loadPlugin() {
        try {
            Intent intent = new Intent(DynamicConstant.PROXY_VIEW_ACTION);
            PluginItem pluginItem = PluginManager.plugins.get(0);
            String className = pluginItem.packageInfo.packageName + ".TestPluginActivity";
            intent.putExtra(DynamicConstant.EXTRA_DEX_PATH, pluginItem.pluginPath);
            intent.putExtra(DynamicConstant.EXTRA_DEX_CLASS, className);
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
