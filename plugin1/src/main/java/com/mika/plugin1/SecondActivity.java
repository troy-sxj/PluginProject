package com.mika.plugin1;

import android.os.Bundle;

import com.mika.dynamic.activity.BasePluginActivity;

/**
 * @Author: mika
 * @Time: 2018/12/27 5:28 PM
 * @Description:
 */
public class SecondActivity extends BasePluginActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }
}
