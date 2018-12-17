package com.mika.plugin1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * @Author: mika
 * @Time: 2018-12-17 11:47
 * @Description:
 */
public class TestService extends Service {

    private static final String Tag = TestService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Tag, "onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(Tag, "onBind");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(Tag, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(Tag, "onDestroy");
        super.onDestroy();
    }
}
