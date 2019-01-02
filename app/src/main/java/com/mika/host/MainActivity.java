package com.mika.host;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mika.dynamic.utils.FileUtils;
import com.mika.host.base.BaseActivity;

import java.lang.reflect.Method;

import dalvik.system.BaseDexClassLoader;
import invoke.RefInvoke;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private final static String Tag = MainActivity.class.getSimpleName();
    private Button btn1, btn2, btn3, btn4;

    @Override
    protected void attachBaseContext(Context newBase) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadPluginDex(pluginName);

        btn1 = findViewById(R.id.btnCopyPlugin);
        btn2 = findViewById(R.id.btnLoadDex);
        btn3 = findViewById(R.id.btnLoadClass);
        btn4 = findViewById(R.id.btnLoadRes);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCopyPlugin:
                break;
            case R.id.btnLoadDex:
                loadPluginDex(pluginName);
                break;
            case R.id.btnLoadClass:
                loadBean();
                break;
            case R.id.btnLoadRes:
                loadRes();
                break;
        }
    }


    private void jumpTestActivity() {
    }


    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void jumpTestService() {
        RefInvoke.getFieldObject(BaseDexClassLoader.class, this.getClassLoader(), "pathList");
    }

    private void jumpTestReceiver() {

    }

    private void testFile() {
        FileUtils.testStorage(getBaseContext());
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadRes() {
//        try {
////            loadResource();
//            Class<?> aClass = dexClassLoader.loadClass("com.mika.plugin1.DynamicString");
//            Object dynamic = aClass.newInstance();
//            String stringForResId = ((IDynamic) dynamic).getStringForResId(this);
//            Log.e(Tag, stringForResId);
////            Toast.makeText(this, stringForResId, Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }


}

