package com.mika.dynamic.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * @Author: mika
 * @Time: 2018-12-11 15:46
 * @Description:
 */
public class DLUtils {

    public static PackageInfo getPackageInfo(Context context, String apkFilePath){
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(apkFilePath, PackageManager.GET_ACTIVITIES);
        return packageArchiveInfo;
    }
}
