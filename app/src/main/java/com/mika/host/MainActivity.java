package com.mika.host;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mika.dynamic.HookHelper;
import com.mika.dynamic.utils.FileUtils;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;
import invoke.RefInvoke;


public class MainActivity extends Activity implements View.OnClickListener {

    private final static String Tag = MainActivity.class.getSimpleName();
    private Button btn1, btn2, btn3;


    private String pluginName = "plugin1.apk";

    @Override
    protected void attachBaseContext(Context newBase) {
        try {
//            HookHelper.attachBaseContext();

        }catch (Exception e){
            e.printStackTrace();
        }
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadPluginDex(pluginName);

        btn1 = (Button) findViewById(R.id.btnCopyPlugin);
        btn2 = (Button) findViewById(R.id.btnLoadDex);
        btn3 = (Button) findViewById(R.id.btnLoadClass);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCopyPlugin:
                FileUtils.extractAssets(getBaseContext(), pluginName);
                break;
            case R.id.btnLoadDex:
                loadPluginDex(pluginName);
                break;
            case R.id.btnLoadClass:
                loadBean();
                break;
        }
    }


    private void jumpTestActivity() {
        getApplicationContext().startActivity(new Intent(this, SecondActivity.class));
    }


    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void jumpTestService() {
        RefInvoke.getFieldObject(BaseDexClassLoader.class, this.getClassLoader(), "pathList");
    }

    private void jumpTestReceiver() {

    }

    private void testFile(){
        FileUtils.testStorage(getBaseContext());
    }

    private DexClassLoader dexClassLoader;

    private void loadPluginDex(String pluginName) {
        File extraFile = this.getFileStreamPath(pluginName);
        String dexPath = extraFile.getPath();
        File fileRelease = this.getDir("dex", 0);
        dexClassLoader = new DexClassLoader(dexPath, fileRelease.getAbsolutePath(), null, this.getClassLoader());
    }

    private void loadBean() {
        try {
            Class aClass = dexClassLoader.loadClass("com.mika.plugin1.Bean");
            Object beanObj = aClass.newInstance();
            Method getName = aClass.getDeclaredMethod("getName");
            getName.setAccessible(true);
            String invoke = (String) getName.invoke(beanObj);
            Toast.makeText(this, invoke, Toast.LENGTH_SHORT).show();
//            Log.e(Tag, "loadBean : " + invoke);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }catch (InvocationTargetException e){
            e.printStackTrace();
        }
    }
}

