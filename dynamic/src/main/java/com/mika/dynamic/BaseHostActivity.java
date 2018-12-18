package com.mika.dynamic;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import invoke.RefInvoke;

/**
 * @Author: mika
 * @Time: 2018-12-17 17:32
 * @Description:
 */
public abstract class BaseHostActivity extends Activity {

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
            mTheme = mResources.newTheme();
            mTheme.setTo(super.getTheme());
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
            testLayoutId();
            iRemoteActivity.onCreate(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testLayoutId() {
        try {
            Class<?> layoutClazz = getClassLoader().loadClass("com.mika.plugin1.R$layout");
            int layoutId = (int) RefInvoke.getStaticFieldObject(layoutClazz, "activity_test_plugin");
            Log.d("aaa", layoutId + "");
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
        return mTheme == null ? super.getTheme() : mTheme;
    }
}
