package com.mika.dynamic;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.mika.dynamic.dex.BaseDexClassLoaderHelper;
import com.mika.dynamic.entity.PluginItem;
import com.mika.dynamic.utils.DLUtils;
import com.mika.dynamic.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: mika
 * @Time: 2018-12-11 14:21
 * @Description:
 */
public class PluginManager {

    private static final String Tag = PluginManager.class.getSimpleName();

    private static boolean isDebug = false;
    public static List<PluginItem> plugins = new ArrayList<>();
    private static volatile Context mBaseContext = null;

    public static void init(Application application) {
        mBaseContext = application.getBaseContext();
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

}
