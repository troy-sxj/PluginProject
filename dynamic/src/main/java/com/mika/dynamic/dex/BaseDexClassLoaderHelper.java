package com.mika.dynamic.dex;

import android.text.TextUtils;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;
import invoke.RefInvoke;


/**
 * @Author: mika
 * @Time: 2018-12-07 14:42
 * @Description:
 */
public class BaseDexClassLoaderHelper {

    public static final String LIB_SEPARATOR = ",";

    /**
     * 合并dex，添加nativeLibPath
     *
     * @param cl
     * @param apkFile
     * @param optDexFile
     * @param nativeLibPath
     */
    public static void patchClassLoader(ClassLoader cl, File apkFile, File optDexFile, String nativeLibPath) {
        try {
            /**
             * 1. 合并dex
             */
            //获取BaseDexClassLoader： DexPathList pathList
            Object pathListObj = RefInvoke.getFieldObject(DexClassLoader.class.getSuperclass(), cl, "pathList");
            //获取DexPathList： Element[] dexElements
            Object[] dexElements = (Object[]) RefInvoke.getFieldObject(pathListObj, "dexElements");
            //Element 类型
            Class<?> elementClass = dexElements.getClass().getComponentType(); //getComponentType可以获取一个数组的class对象
            //创建一个数组，用来替换原始的数组
            Object[] newElements = (Object[]) Array.newInstance(elementClass, dexElements.length + 1);
            //构造插件Element(File dir, boolean isDirectory, File zip, DexFile dexFile)这个构造函数
            Class[] p1 = {DexFile.class, File.class};
            Object[] v1 = {DexFile.loadDex(apkFile.getCanonicalPath(), optDexFile.getAbsolutePath(), 0), apkFile};
            Object object = RefInvoke.createObject(elementClass, p1, v1);
            Object[] toAddElementArray = {object};
            //把原始的elements复制进去
            System.arraycopy(dexElements, 0, newElements, 0, dexElements.length);
            System.arraycopy(toAddElementArray, 0, newElements, dexElements.length, toAddElementArray.length);
            //替换dexElements数组
            RefInvoke.setFieldObject(pathListObj, "dexElements", newElements);

            //
            /**
             * 2. 将插件中so文件路径设置到ClassLoader中
             * <p>
             *     下面的操作只针对api 28，其他api待适配
             * </p>
             */
            //获取host项目中的libs
            Object[] originalLibElements = (Object[]) RefInvoke.getFieldObject(pathListObj, "nativeLibraryPathElements");
            //生成NativeLibraryElement类
            Class<?> nativeLibraryElementClazz = originalLibElements.getClass().getComponentType();
            //获取所有插件的so文件路径
            List<File> libFiles = new ArrayList<>();
            if (!TextUtils.isEmpty(nativeLibPath)) {
                if (nativeLibPath.contains(LIB_SEPARATOR)) {
                    String[] split = nativeLibPath.split(LIB_SEPARATOR);
                    for (String str : split) {
                        libFiles.add(new File(str));
                    }
                } else {
                    libFiles.add(new File(nativeLibPath));
                }
            }

            Object[] pluginLibsElements = (Object[]) Array.newInstance(nativeLibraryElementClazz, libFiles.size());
            Object nativeLibElement;
            for (int i = 0; i < libFiles.size(); i++) {
                nativeLibElement = RefInvoke.createObject(nativeLibraryElementClazz, File.class, libFiles.get(i));
                pluginLibsElements[i] = nativeLibElement;
            }
            Object[] finalLibsArray = (Object[]) Array.newInstance(nativeLibraryElementClazz, originalLibElements.length + pluginLibsElements.length);
            System.arraycopy(originalLibElements, 0, finalLibsArray, 0, originalLibElements.length);
            System.arraycopy(pluginLibsElements, 0, finalLibsArray, originalLibElements.length, pluginLibsElements.length);
            //替换NativeLibraryPathElements数组
            RefInvoke.setFieldObject(pathListObj, "nativeLibraryPathElements", finalLibsArray);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
