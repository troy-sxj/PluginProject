package com.mika.dynamic;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;

import com.mika.dynamic.dex.BaseDexClassLoaderHelper;
import com.mika.dynamic.log.DyLog;
import com.mika.dynamic.model.PluginInfo;
import com.mika.dynamic.utils.DLUtils;
import com.mika.dynamic.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: mika
 * @Time: 2018/12/25 11:49 AM
 * @Description:
 */
public class DyPlugin {
    private static final String Tag = DyPlugin.class.getSimpleName();
    private static final int LOAD_MAX_NUM = 3;  //最大支持的插件数量
    private static boolean isDebug = false;
    private static final Object SYNC_OBJ = new Object();
    private static volatile Context mBaseContext = null;


    public static List<PluginInfo> mPluginList = new ArrayList<>();

    /**
     * 初始化
     *
     * @param application
     * @param debug
     */
    public static void attachApplication(Application application, boolean debug) {
        mBaseContext = application;
        isDebug = debug;
    }

    /**
     * 加载插件
     *
     * @param pluginName <p>
     *                   1. 判断插件是否已加载
     *                   2. 判断是否超出支持的插件数量
     *                   3. 将插件拷贝到当前应用目录下
     *                   </p>
     */
    public static void loadPlugin(String pluginName) {
        if (null == mBaseContext) {
            throw new RuntimeException("DyPlugin must init, before load Plugin");
        }
        if (TextUtils.isEmpty(pluginName)) {
            throw new RuntimeException("Plugin name can not be null. ");
        }
        synchronized (SYNC_OBJ) {
            //1. 判断插件是否已加载，避免重复加载
            for (PluginInfo pluginInfo : mPluginList) {
                if (pluginInfo.pluginName.equals(pluginName)) {
                    DyLog.i(Tag, "plugin : " + pluginName + " has loaded. ");
                    return;
                }
            }
            //2. 判断是否超出支持的插件数量
            if (mPluginList.size() > LOAD_MAX_NUM) {
                DyLog.e(Tag, "load plugin failed, max plugin num = " + LOAD_MAX_NUM + " , current plugin num is " + mPluginList.size());
                return;
            }
            //3. 加载plugin
            loadPluginFromAsset(pluginName);
        }
    }

    /**
     * 从asset中加载
     *
     * @param pluginName
     */
    private static void loadPluginFromAsset(String pluginName) {
        AssetManager assets = mBaseContext.getAssets();
        try {
            String[] pathList = assets.list("");
            for (String path : pathList) {
                DyLog.i(Tag, "asset file path = " + path);
                if (path.endsWith(DyConstant.PLUGIN_SUFFIX) && path.equals(pluginName)) {
                    String apkName = path;
                    String dexName = path.replace(".apk", ".dex");

                    FileUtils.extractAssets(mBaseContext, apkName);
                    mergeDex(apkName, dexName);
                    PluginInfo pluginInfo = generatePluginItem(apkName);
                    mPluginList.add(pluginInfo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 合并dex，将插件的dex合并到默认classLoader的dexList中
     *
     * @param apkName
     * @param dexName
     */
    private static void mergeDex(String apkName, String dexName) {
        File dexFile = mBaseContext.getFileStreamPath(apkName);
        File optDexFile = mBaseContext.getFileStreamPath(dexName);
        BaseDexClassLoaderHelper.patchClassLoader(mBaseContext.getClassLoader(), dexFile, optDexFile);
    }

    /**
     * 生成插件packInfo
     *
     * @param apkName
     * @return
     */
    private static PluginInfo generatePluginItem(String apkName) {
        File file = mBaseContext.getFileStreamPath(apkName);
        PluginInfo pluginInfo = new PluginInfo();
        pluginInfo.pluginName = apkName;
        pluginInfo.pluginPath = file.getAbsolutePath();
        pluginInfo.packageInfo = DLUtils.getPackageInfo(mBaseContext, pluginInfo.pluginPath);
        Log.d(Tag, "generatePluginItem = " + pluginInfo.toString());
        return pluginInfo;
    }

    public static PluginInfo queryActivity(String className) {
        if (TextUtils.isEmpty(className)) {
            throw new RuntimeException("DyPlugin( queryActivity error) : className can not be null. ");
        }
        for (PluginInfo pluginInfo : mPluginList) {
            boolean isContain = pluginInfo.containComponent(className);
            if (isContain) return pluginInfo;
        }
        return null;
    }
}
