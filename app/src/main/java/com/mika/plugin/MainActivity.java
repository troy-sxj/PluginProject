package com.mika.plugin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mika.plugin.hook.HookHelper;
import com.mika.plugin.reflect.TestClassCtor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String Tag = MainActivity.class.getSimpleName();
    private Button btn1, btn2, btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HookHelper.hookActivityManager();
        HookHelper.hookPackageManager(getBaseContext());
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:

                break;
            case R.id.button2:
                jumpTestService();
                break;
            case R.id.button3:
                jumpTestReceiver();
                break;
        }
    }


    private void jumpTestActivity() {
        testReflectConstructor();
    }


    private void jumpTestService() {

    }

    private void jumpTestReceiver() {

    }


    private void testReflectClass() {
        try {
            Class<?> aClass = Class.forName("java.lang.String");
            Class<?> aClass1 = Class.forName("android.widget.Button");
            Class<?> superclass = aClass1.getSuperclass();
            Class class4 = String.class;
            Class class5 = java.lang.String.class;
            Class class6 = MainActivity.class;
            Class class7 = int.class;
            Class class8 = int[].class;
            Class class9 = Boolean.TYPE;
            Class class10 = Byte.TYPE;
            Class class11 = Character.TYPE;
            System.out.println();
            System.out.println(aClass.toString());
            System.out.println(aClass1.toString());
            System.out.println(superclass.toString());
            System.out.println(class4.toString());
            System.out.println(class5.toString());
            System.out.println(class6.toString());
            System.out.println(class7.toString());
            System.out.println(class8.toString());
            System.out.println(class9.toString());
            System.out.println(class10.toString());
            System.out.println(class11.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void testReflectConstructor() {
        TestClassCtor testClassCtor = new TestClassCtor();
        Class temp = testClassCtor.getClass();
        String className = temp.getName();
//        Log.e(Tag, temp.getName());
        try {
            Constructor[] declaredConstructors = temp.getDeclaredConstructors();
            for (int i = 0; i < declaredConstructors.length; i++) {
                int modifiers = declaredConstructors[i].getModifiers();
                Log.e(Tag, Modifier.toString(modifiers) + " " + className + "(");

                Class[] parameterTypes = declaredConstructors[i].getParameterTypes();
                for (int j = 0; j < parameterTypes.length; j++) {
                    Log.e(Tag, parameterTypes[j].getName());
//                    if (parameterTypes.length > j + 1) {
//                        Log.e(Tag, ", ");
//                    }
                }
                Log.e(Tag, ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testReflectInstance() {
        try {
            Class[] argsClass = {int.class, String.class};
            Class[] argsClass1 = {int.class, double.class};
            Class aClass = Class.forName("com.mika.plugin.reflect.TestClassCtor");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(argsClass);
            Object aa = declaredConstructor.newInstance(1, "aa");

            Constructor declaredConstructor1 = aClass.getDeclaredConstructor(argsClass1);
            declaredConstructor1.setAccessible(true);
            declaredConstructor1.newInstance(1, 1.0d);


            Class[] class4 = {String.class};
            Method doSomething = aClass.getDeclaredMethod("doSomething", String.class);
            doSomething.setAccessible(true);
            Object[] agrs = {"miak"};
            doSomething.invoke(aa, agrs);


            Field name = aClass.getDeclaredField("name");
            name.setAccessible(true);
            Object o = name.get(aa);
            name.set(o, "ccc");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void testReflectPrivate() {
        try {
            Class<?> aClass = Class.forName("com.mika.plugin.reflect.TestClassCtor");
            Object classCtor = aClass.newInstance();


            Field name = aClass.getDeclaredField("name");
            name.setAccessible(true);
            name.set(classCtor, "aaaa");

            Log.e(Tag, (String) name.get(classCtor));

            Method getName = aClass.getDeclaredMethod("getName");
            Log.e(Tag, (String) getName.invoke(classCtor));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }


    private void testGeneric() {
        try {
            Class<?> singletonInstance = Class.forName("com.mika.plugin.reflect.Singleton");
            Field fieldInstance = singletonInstance.getDeclaredField("mInstance");
            fieldInstance.setAccessible(true);


            Class<?> aClass = Class.forName("com.mika.plugin.reflect.Animator");
            Field getDefault = aClass.getDeclaredField("getDefault");
            getDefault.setAccessible(true);
            Object getDefaultObj = getDefault.get(null);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}

