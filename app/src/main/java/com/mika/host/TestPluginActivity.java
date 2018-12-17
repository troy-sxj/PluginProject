package com.mika.host;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mika.dynamic.PluginManager;

/**
 * @Author: mika
 * @Time: 2018-12-17 11:45
 * @Description:
 */
public class TestPluginActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_plugin);
        findViewById(R.id.btnStartActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPluginActivity();
            }
        });
        findViewById(R.id.btnStartService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPluginService();
            }
        });
    }


    public void startPluginActivity() {
        try {
            Intent intent = new Intent();
            String activityName = PluginManager.plugins.get(0).packageInfo.packageName + ".TestActivity";
            intent.setComponent(new ComponentName(this, Class.forName(activityName)));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void startPluginService() {
        try {
            Intent intent = new Intent();
            String serviceName = PluginManager.plugins.get(0).packageInfo.packageName + ".TestService";
            intent.setComponent(new ComponentName(this, Class.forName(serviceName)));
            startService(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
