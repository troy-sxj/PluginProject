package com.mika.plugin1;

import android.os.Bundle;

import com.mika.dynamic.BasePluginActivity;

/**
 * @Author: mika
 * @Time: 2018-12-17 19:10
 * @Description:
 */
public class TestPluginActivity extends BasePluginActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_plugin);
    }
}
