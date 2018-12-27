package com.mika.dynamic.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * @Author: mika
 * @Time: 2018-12-17 17:11
 * @Description:
 */
public interface IRemoteActivity {

    void onCreate(Bundle savedInstanceState);

    void setProxy(FragmentActivity that, String dexPath);

    void onStart();

    void onStop();

    void onRestart();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onConfigurationChanged(Configuration newConfig);

    void onResume();

    void onPause();

    void onDestroy();

    int getLaunchMode();

}
