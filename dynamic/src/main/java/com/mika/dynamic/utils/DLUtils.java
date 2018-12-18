package com.mika.dynamic.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * @Author: mika
 * @Time: 2018-12-11 15:46
 * @Description:
 */
public class DLUtils {

    public static PackageInfo getPackageInfo(Context context, String apkFilePath){
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(apkFilePath, PackageManager.GET_ACTIVITIES);
        ApplicationInfo applicationInfo = packageArchiveInfo.applicationInfo;
        Log.d("aaa", "theme id: " + applicationInfo.theme );
        return packageArchiveInfo;
    }

}
