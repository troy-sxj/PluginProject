package com.mika.host;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.mika.dynamic.activity.DyIntent;

import dalvik.system.DexClassLoader;


/**
 * @Author: mika
 * @Time: 2018-12-17 18:37
 * @Description:
 */
public class ThatActivity extends FragmentActivity {

//    DexClassLoader classLoader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_that);
        findViewById(R.id.btnLoadPlugin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPlugin();
            }
        });

    }


    private void loadPlugin() {
        DyIntent dyIntent = new DyIntent("com.mika.plugin1.TestPluginActivity");
        startActivity(dyIntent);
    }

    private void loadJni() {

//        FileUtils.extractAssets(getBaseContext(), "plugin1-debug.apk");
//        File extractFile = this.getFileStreamPath("plugin1-debug.apk");
//        String dexpath = extractFile.getPath();
//
//        File fileRelease = getDir("dex", 0); //0 表示Context.MODE_PRIVATE
//
//        //得到由逗号分割的一组so的路径
//        String libPaths = FileUtils.unzipSpecificFile(dexpath, extractFile.getParent());
//        Log.e("aaa", libPaths);
//
//        classLoader = new DexClassLoader(dexpath,
//                fileRelease.getAbsolutePath(), libPaths, getClassLoader());
//
//        Class mLoadClassBean;
//        try {
//            mLoadClassBean = classLoader.loadClass("com.mika.plugin1.Bean");
//            Object beanObject = mLoadClassBean.newInstance();
//
//            Method getNameMethod = mLoadClassBean.getMethod("getNativeName");
//            getNameMethod.setAccessible(true);
//            String name = (String) getNameMethod.invoke(beanObject);
//
//            Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();
//
//        } catch (Exception e) {
//            Log.e("DEMO", "msg:" + e.getMessage());
//        }
    }
}
