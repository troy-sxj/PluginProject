package com.mika.dynamic.loader;

import java.util.ArrayList;
import java.util.List;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * @Author: mika
 * @Time: 2018/12/27 2:59 PM
 * @Description:
 */
public class DyClassLoader extends PathClassLoader {

    private List<DexClassLoader> mClassLoaderList = null;


    public DyClassLoader(String dexPath, ClassLoader parent) {
        super(dexPath, parent);
        mClassLoaderList = new ArrayList<>();
    }

    public void addPluginClassLoader(DexClassLoader dexClassLoader){
        mClassLoaderList.add(dexClassLoader);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> clazz =null;
        try {
            clazz = getParent().loadClass(name);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        if(clazz != null) return clazz;
        if(mClassLoaderList != null){
            for(ClassLoader classLoader: mClassLoaderList){
                if(classLoader == null) continue;
                try {
                    clazz = classLoader.loadClass(name);
                }catch (ClassNotFoundException e){
                    e.printStackTrace();
                }
                if(clazz != null) return clazz;
            }
        }
        throw new ClassNotFoundException(name + " in loader " + this);
    }

}
