package com.mika.dynamic;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import com.mika.dynamic.dex.BaseDexClassLoaderHelper;
import com.mika.dynamic.entity.PluginItem;
import com.mika.dynamic.utils.DLUtils;
import com.mika.dynamic.utils.FileUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import invoke.RefInvoke;

/**
 * @Author: mika
 * @Time: 2018-12-11 14:21
 * @Description:
 */
public class PluginManager {

    private static final String Tag = PluginManager.class.getSimpleName();

    public static List<PluginItem> plugins = new ArrayList<>();

    //ContextImpl中的LoadApk对象：mPackageInfo
    private static Object mPackageInfo = null;
    private static volatile Context mBaseContext = null;
    //正在使用的Resources
//    public static volatile Resources mNowResources = null;

    public static void init(Application application) {
        mPackageInfo = RefInvoke.getFieldObject(application.getBaseContext(), "mPackageInfo");
        mBaseContext = application.getBaseContext();
//        mNowResources = application.getResources();
        try {
            AssetManager assets = application.getAssets();
            String[] paths = assets.list("");
            ArrayList<String> pluginPaths = new ArrayList<>();
            for (String path : paths) {
                Log.i(Tag, "asset path = " + path);
                if (path.endsWith(".apk")) {
                    String apkName = path;
                    String dexName = apkName.replace(".apk", ".dex");

                    FileUtils.extractAssets(mBaseContext, apkName);
                    mergeDex(apkName, dexName);
                    PluginItem pluginItem = generatePluginItem(apkName);
                    plugins.add(pluginItem);
                    pluginPaths.add(pluginItem.pluginPath);
                }
            }
//            reloadInstalledPluginResources(pluginPaths);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void mergeDex(String apkName, String dexName) {
        File dexFile = mBaseContext.getFileStreamPath(apkName);
        File optDexFile = mBaseContext.getFileStreamPath(dexName);
        BaseDexClassLoaderHelper.patchClassLoader(mBaseContext.getClassLoader(), dexFile, optDexFile);
    }

    public static PluginItem generatePluginItem(String apkName) {
        File file = mBaseContext.getFileStreamPath(apkName);
        PluginItem pluginItem = new PluginItem();
        pluginItem.pluginPath = file.getAbsolutePath();
        pluginItem.packageInfo = DLUtils.getPackageInfo(mBaseContext, pluginItem.pluginPath);
        Log.d(Tag, "generatePluginItem = " + pluginItem.toString());
        return pluginItem;
    }

    public static void reloadInstalledPluginResources(ArrayList<String> pluginPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = AssetManager.class.getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, mBaseContext.getPackageResourcePath());

            for (String path : pluginPath) {
                addAssetPath.invoke(assetManager, path);
            }

            Resources newResources = new Resources(assetManager,
                    mBaseContext.getResources().getDisplayMetrics(),
                    mBaseContext.getResources().getConfiguration());

            RefInvoke.setFieldObject(mBaseContext, "mResources", newResources);
            //这是最主要的，如果不支持插件运行时更新，只留这一个就可以了
            RefInvoke.setFieldObject(mPackageInfo, "mResources", newResources);

//            mNowResources = newResources;
            RefInvoke.setFieldObject(mBaseContext, "mTheme", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
