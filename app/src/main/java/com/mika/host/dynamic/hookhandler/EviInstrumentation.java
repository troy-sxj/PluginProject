package com.mika.host.dynamic.hookhandler;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.mika.host.dynamic.HookHelper;

import invoke.RefInvoke;

/**
 * @Author: mika
 * @Time: 2018-11-29 11:27
 * @Description:
 */
public class EviInstrumentation extends Instrumentation {

    private static final String TAG = "EviInstrumentation";

    private Instrumentation mBase;

    public EviInstrumentation(Instrumentation base) {
        this.mBase = base;
    }

    public Activity newActivity(ClassLoader cl, String className,
                                Intent intent)
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        Log.e(TAG, "newActivity");
        Intent rawIntent = intent.getParcelableExtra(HookHelper.EXTRA_TARGET_INTENT);
        if (rawIntent == null) {
            return mBase.newActivity(cl, className, intent);
        }
        String newClassName = rawIntent.getComponent().getClassName();
        return mBase.newActivity(cl, newClassName, rawIntent);
    }

    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {
        Class[] paramClazz = {Context.class, IBinder.class, IBinder.class, Activity.class, Intent.class, int.class, Bundle.class};
        Object[] paramObj = {who, contextThread, token, target, intent, requestCode, options};
        return (ActivityResult) RefInvoke.invokeInstanceMethod(mBase, "execStartActivity", paramClazz, paramObj);
    }

}
