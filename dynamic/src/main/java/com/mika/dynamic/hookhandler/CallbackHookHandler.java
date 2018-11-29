package com.mika.dynamic.hookhandler;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.mika.dynamic.HookHelper;

import invoke.RefInvoke;

/**
 * @Author: mika
 * @Time: 2018-11-29 15:11
 * @Description: <p>
 * Hook ActivityThread中mH的mCallback对象
 * </p>
 */
public class CallbackHookHandler implements Handler.Callback {

    private Handler mHandler;

    public CallbackHookHandler(Handler handler) {
        this.mHandler = handler;
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case 100:   //LAUNCH_ACTIVITY
                handleLaunchActivity(message);
                break;
        }
        mHandler.handleMessage(message);
        return true;
    }

    private void handleLaunchActivity(Message message) {
        Object obj = message.obj;
        Log.d(CallbackHookHandler.class.getSimpleName(), obj.toString());

        Intent intent = (Intent) RefInvoke.getFieldObject(obj, "intent");
        Intent targetIntent = intent.getParcelableExtra(HookHelper.EXTRA_TARGET_INTENT);
        intent.setComponent(targetIntent.getComponent());
    }
}
