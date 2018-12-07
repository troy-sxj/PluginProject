package com.mika.host.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import com.mika.dynamic.utils.FileUtils;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * @Author: mika
 * @Time: 2018-12-06 18:59
 * @Description:
 */
public class BaseActivity extends Activity {

    private AssetManager mAssetManager;
    private Resources mResources;
    private Resources.Theme mTheme;
    protected DexClassLoader dexClassLoader;
    private String dexPath;
    protected String pluginName = "plugin1.apk";

    @Override
    protected void attachBaseContext(Context newBase) {
        try{
            FileUtils.extractAssets(getBaseContext(), pluginName);
        }catch (Exception e){
            e.printStackTrace();
        }

        super.attachBaseContext(newBase);
    }

    @Override
    public AssetManager getAssets() {
        if(mAssetManager == null) {
            return super.getAssets();
        }
        return mAssetManager;
    }

    @Override
    public Resources getResources() {
        if(mResources == null) {
            return super.getResources();
        }
        return mResources;
    }

    @Override
    public Resources.Theme getTheme() {
        if(mTheme == null) {
            return super.getTheme();
        }
        return mTheme;
    }

    protected void loadPluginDex(String pluginName) {
        File extraFile = this.getFileStreamPath(pluginName);

        dexPath = extraFile.getPath();
        File fileRelease = this.getDir("dex", 0);
        dexClassLoader = new DexClassLoader(dexPath, fileRelease.getAbsolutePath(), null, this.getClassLoader());
    }

    protected void loadResource() {
        try {
            if(mAssetManager == null) {
                AssetManager assetManager = AssetManager.class.newInstance();
                Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
                addAssetPath.invoke(assetManager, dexPath);
                mAssetManager = assetManager;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(mResources == null) {
            mResources = new Resources(mAssetManager, super.getResources().getDisplayMetrics(), super.getResources().getConfiguration());
        }
        if(mTheme == null) {
            mTheme = mResources.newTheme();
            mTheme.setTo(super.getTheme());
        }
    }



}
