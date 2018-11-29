package com.mika.host;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @Author: mika
 * @Time: 2018-11-29 11:39
 * @Description:
 */
public class SecondActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }
}
