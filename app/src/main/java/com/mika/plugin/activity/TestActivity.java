package com.mika.plugin.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @Author: mika
 * @Time: 2018-11-14 16:50
 * @Description:
 */
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PackageManager packageManager = getPackageManager();
    }
}
