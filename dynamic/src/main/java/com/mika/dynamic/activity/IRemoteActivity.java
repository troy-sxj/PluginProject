package com.mika.dynamic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * @Author: mika
 * @Time: 2018-12-17 17:11
 * @Description:
 */
public interface IRemoteActivity {

    public void onCreate(Bundle savedInstanceState);

    public void setProxy(FragmentActivity that, String dexPath);

    public void onStart();

    public void onStop();

    public void onRestart();

    public void onActivityResult(int requestCode, int resultCode, Intent data);

    public void onResume();

    public void onPause();

    public void onDestroy();

    public int getLaunchMode();

}
