package com.mika.dynamic.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

/**
 * @Author: mika
 * @Time: 2018-12-17 17:11
 * @Description:
 */
public class BasePluginActivity extends FragmentActivity implements IRemoteActivity {

    private static final String Tag = BasePluginActivity.class.getSimpleName();

    protected FragmentActivity that;
    private String dexPath;

    private int launchMode = LaunchMode.STANDARD;

    public void setLaunchMode(int launchMode) {
        this.launchMode = launchMode;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(Tag, "onCreate");
        hookFragmentCallbacks();
    }

    @Override
    public void setProxy(FragmentActivity that, String dexPath) {
        this.that = that;
        this.dexPath = dexPath;
    }

    @Override
    public void onStart() {
        Log.d(Tag, "onStart");
    }

    @Override
    public void onStop() {
        Log.d(Tag, "onStop");
    }

    @Override
    public void onRestart() {
        Log.d(Tag, "onRestart");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(Tag, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode + ", intent=" + data.toString());
    }

    @Override
    public void onResume() {
        Log.d(Tag, "onResume");
    }

    @Override
    public void onPause() {
        Log.d(Tag, "onPause");
    }

    @Override
    public void onDestroy() {
        Log.d(Tag, "onDestroy");
    }

    @Override
    public int getLaunchMode() {
        return this.launchMode;
    }

    @Override
    public View findViewById(int id) {
        checkThat();
        return that.findViewById(id);
    }

    @Override
    public void setContentView(int layoutResID) {
        checkThat();
        that.setContentView(layoutResID);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        checkThat();
        that.startActivityForResult(intent, requestCode);
    }

    @Override
    public Resources getResources() {
        checkThat();
        return that.getResources();
    }

    @Override
    public void finish() {
        checkThat();
        that.finish();
    }

    @Override
    public AssetManager getAssets() {
        checkThat();
        return that.getAssets();
    }

    @Override
    public Intent getIntent() {
        checkThat();
        return that.getIntent();
    }

    private void checkThat() {
        if (null == that) {
            throw new RuntimeException("BasePluginActivity: use plugin must setProxy");
        }
    }

    private void hookFragmentCallbacks(){

    }

    @Override
    public FragmentManager getSupportFragmentManager() {
        checkThat();
        Log.d(Tag, "------------- getSupportFragmentManager");
        return that.getSupportFragmentManager();
    }
}
