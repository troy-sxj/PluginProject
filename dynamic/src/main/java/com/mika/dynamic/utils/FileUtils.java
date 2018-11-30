package com.mika.dynamic.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: mika
 * @Time: 2018-11-30 10:48
 * @Description:
 */
public class FileUtils {

    public static final String Tag = FileUtils.class.getSimpleName();

    /**
     * 判断是否有外部存储
     *
     * @return
     */
    public static boolean hasExternalStorage() {
        String state = Environment.getExternalStorageState();
        if (state != null && Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static void testStorage(Context base) {
        File filesDir = base.getFilesDir();
        File externalStoragePublicDirectory = base.getExternalFilesDir("");
        Log.e(Tag, "内部应用存储： " + filesDir.getAbsolutePath());
        Log.e(Tag, "外部应用存储： " + externalStoragePublicDirectory.getAbsolutePath());


        long dataUsableSpace = Environment.getDataDirectory().getUsableSpace();
        long storageUsableSpace = Environment.getExternalStorageDirectory().getUsableSpace();
        Log.e(Tag, "内存(" + Environment.getDataDirectory().toString() + ")可用： " + android.text.format.Formatter.formatFileSize(base, dataUsableSpace));
        Log.e(Tag, "存储(" + Environment.getExternalStorageDirectory().toString() + ")可用： " + android.text.format.Formatter.formatFileSize(base, storageUsableSpace));
    }

    public static long getUsableSpace(File filePath) {
        if (filePath == null) return -1;
        return filePath.getUsableSpace();
        //如果需要兼容api 9 以下
        //final StatFs stats = new StatFs(path.getPath());
        //return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
    }

    public static void extractAssets(Context base, String assetName) {
        AssetManager assetManager = base.getAssets();
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            is = assetManager.open(assetName);
            File fileStreamPath = base.getFileStreamPath(assetName);
            fos = new FileOutputStream(fileStreamPath);
            byte[] buffer = new byte[1024];
            int count = 0;
            while (((count = is.read(buffer)) > 0)) {
                fos.write(buffer, 0, count);
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeSilently(is);
            closeSilently(fos);
        }
    }

    private static void closeSilently(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
        } catch (Throwable e) {
            // ignore
        }
    }
}
