package com.mika.plugin1;

import android.os.Bundle;
import android.util.Log;

import com.mika.dynamic.base.DyBaseActivity;

/**
 * @Author: mika
 * @Time: 2018-12-17 15:09
 * @Description:
 */
public class TestActivity extends DyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Log.d("plugin activity ", "Test Activity onCreate");
    }
}
