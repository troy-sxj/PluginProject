package com.mika.dynamic.dex;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;

import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;
import invoke.RefInvoke;


/**
 * @Author: mika
 * @Time: 2018-12-07 14:42
 * @Description:
 */
public class BaseDexClassLoaderHelper {

    public static void patchClassLoader(ClassLoader cl, File apkFile, File optDexFile) {
        try {
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
            //替换dex数组
            RefInvoke.setFieldObject(pathListObj, "dexElements", newElements);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
