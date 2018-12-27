package com.mika.dynamic;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;

import com.mika.dynamic.loader.BaseDexClassLoaderHelper;
import com.mika.dynamic.loader.DyClassLoader;
import com.mika.dynamic.log.DyLog;
import com.mika.dynamic.model.PluginInfo;
import com.mika.dynamic.utils.DLUtils;
import com.mika.dynamic.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.DexClassLoader;
import invoke.RefInvoke;

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
    private static Object mPackageInfo = null;
    private static volatile ClassLoader mBaseClassLoader;

    private static volatile DyClassLoader dyClassLoader;
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
        mBaseClassLoader = mBaseContext.getClassLoader();
        mPackageInfo = RefInvoke.getFieldObject(application.getBaseContext(), "mPackageInfo");

        dyClassLoader = new DyClassLoader(mBaseContext.getPackageCodePath(), mBaseContext.getClassLoader());
        resetClassLoader();
    }

    private static void resetClassLoader(){
        RefInvoke.setFieldObject(mPackageInfo, "mClassLoader", dyClassLoader);
        Thread.currentThread().setContextClassLoader(dyClassLoader);
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

                    /**
                     * 思路1： 合并dex，合并so
                     * <p>
                     *     存在的问题：
                     *      dex合并正常，so文件合并无效，问题未知
                     * </p>
                     */
//                    String dexName = path.replace(".apk", ".dex");
//
//                    FileUtils.extractAssets(mBaseContext, apkName);
//                    File extractFile = mBaseContext.getFileStreamPath(apkName);
//                    String nativeLibPath = FileUtils.unzipSpecificFile(extractFile.getPath(), extractFile.getParent());
//                    mergeDex(apkName, dexName, nativeLibPath);

                    /**
                     * 思路2：为每个插件生成单独的classLoader，并加入到主项目的classLoader中
                     */
                    //将asset下的插件apk文件复制到app目录下
                    FileUtils.extractAssets(mBaseContext, apkName);
                    File extractFile = mBaseContext.getFileStreamPath(apkName);
                    //获取插件apk的so文件，解压到指定目录
                    String libPath = FileUtils.unzipSpecificFile(extractFile.getPath(), extractFile.getParent());
                    //生成plugin的详细信息
                    PluginInfo pluginInfo = generatePluginItem(apkName);
                    mPluginList.add(pluginInfo);
                    //指定plugin的dex文件写入路径
                    File dexOutputDir = mBaseContext.getDir("dex", Context.MODE_PRIVATE);
                    //为plugin生成classLoader
                    DexClassLoader pluginClassLoader = new DexClassLoader(pluginInfo.pluginPath, dexOutputDir.getAbsolutePath(), libPath, mBaseClassLoader);
                    //将plugin的classLoader添加到主项目的classLoader
                    dyClassLoader.addPluginClassLoader(pluginClassLoader);
                    //重新设置项目的classLoader
                    resetClassLoader();
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
    private static void mergeDex(String apkName, String dexName, String nativeLibPath) {
        File dexFile = mBaseContext.getFileStreamPath(apkName);
        File optDexFile = mBaseContext.getFileStreamPath(dexName);
        BaseDexClassLoaderHelper.patchClassLoader(mBaseContext.getClassLoader(), dexFile, optDexFile, nativeLibPath);
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
