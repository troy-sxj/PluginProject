package com.mika.dynamic.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import com.mika.dynamic.loader.BaseDexClassLoaderHelper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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

    public static String unzipSpecificFile(String zipFile, String targetDir) {
        StringBuilder stringBuilder = new StringBuilder();

        int BUFFER = 4096; // 这里缓冲区我们使用4KB，
        String strEntry; // 保存每个zip的条目名称
        ZipInputStream zis = null;
        List<String> soPathList = new ArrayList<>();
        try {
            BufferedOutputStream dest = null; // 缓冲输出流
            FileInputStream fis = new FileInputStream(zipFile);
            zis = new ZipInputStream(new BufferedInputStream(fis));
            ZipEntry entry; // 每个zip条目的实例

            while ((entry = zis.getNextEntry()) != null) {
                try {
                    //Log.i("Unzip: ", "=" + entry);
                    int count;
                    byte data[] = new byte[BUFFER];
                    strEntry = entry.getName();
                    if (!strEntry.endsWith(".so")) {
                        continue;
                    }
                    Log.e("xxxxx", strEntry);
                    File entryFile = new File(targetDir + strEntry);
                    File entryDir = new File(entryFile.getParent());
                    if (!entryDir.exists()) {
                        entryDir.mkdirs();
                    }
                    FileOutputStream fos = new FileOutputStream(entryFile);
                    dest = new BufferedOutputStream(fos, BUFFER);
                    while ((count = zis.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, count);
                    }
                    dest.flush();
                    String tempPath = entryDir.getAbsolutePath();
                    if (!soPathList.contains(tempPath)) {
                        soPathList.add(tempPath);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    if (null != dest)
                        dest.close();
                }
            }
            for (String path : soPathList) {
                stringBuilder.append(path).append(BaseDexClassLoaderHelper.LIB_SEPARATOR);
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != zis)
                    zis.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
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
