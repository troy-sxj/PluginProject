package com.mika.dynamic.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import invoke.RefInvoke;

/**
 * @Author: mika
 * @Time: 2018-12-17 17:32
 * @Description:
 */
public abstract class BaseHostActivity extends FragmentActivity {

    private AssetManager mAssetManager;
    private Resources mResources;
    private Resources.Theme mTheme;
    private ClassLoader mClassLoader;

    protected String mDexPath;
    protected String mClass;
    protected IRemoteActivity iRemoteActivity;

    /**
     * 如果dex合并过，不需要再次加载ClassLoader
     */
    protected void loadClassLoader() {
//        File dexOutputDir = this.getDir("dex", Context.MODE_PRIVATE);
//        final String dexOutputPath = dexOutputDir.getAbsolutePath();
//        mClassLoader = new DexClassLoader(mDexPath,
//                dexOutputPath, null, getClassLoader());
    }

    protected void loadResources() {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, mDexPath);
            mAssetManager = assetManager;

            Resources superRes = super.getResources();
            mResources = new Resources(mAssetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
            //TODO 添加主题目前有问题
//            mTheme = mResources.newTheme();
//            mTheme.setTo(super.getTheme());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void launchTargetActivity() {
        try {
            Class<?> loadClass = getClassLoader().loadClass(mClass);
            Constructor<?> loadConstructor = loadClass.getConstructor(new Class[]{});
            Object instance = loadConstructor.newInstance(new Object[]{});
            iRemoteActivity = (IRemoteActivity) instance;
            iRemoteActivity.setProxy(this, mDexPath);
            Bundle bundle = new Bundle();
            //TODO 传递Bundle值
            iRemoteActivity.onCreate(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resources getResources() {
        return mResources == null ? super.getResources() : mResources;
    }

    @Override
    public AssetManager getAssets() {
        return mAssetManager == null ? super.getAssets() : mAssetManager;
    }

    @Override
    public Resources.Theme getTheme() {
        return super.getTheme();
//        return mTheme == null ? super.getTheme() : mTheme;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(iRemoteActivity != null) {
            iRemoteActivity.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if(iRemoteActivity != null){
            iRemoteActivity.onConfigurationChanged(newConfig);
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(iRemoteActivity != null) {
            iRemoteActivity.onStart();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(iRemoteActivity != null) {
            iRemoteActivity.onRestart();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(iRemoteActivity != null) {
            iRemoteActivity.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(iRemoteActivity != null) {
            iRemoteActivity.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(iRemoteActivity != null) {
            iRemoteActivity.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(iRemoteActivity != null) {
            iRemoteActivity.onDestroy();
        }
    }
}
