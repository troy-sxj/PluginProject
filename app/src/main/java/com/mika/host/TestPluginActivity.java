package com.mika.host;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

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


    }

    public void startPluginService() {

    }
}
