package com.mobile.androidtest.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * author：hj
 * time: 2017/8/21 0021 21:14
 */


public class DiskLruCacheUtils {

    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        //如果sd卡存在并且没有被移除
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }


    /**
     * 应用程序的版本号，
     * 要传入版本号是因为如果应用升级缓存会被清除掉
     * @param context
     * @return
     */
    public static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }


    /**
     * A hashing method that changes a string (like a URL) into a hash suitable for using as a
     * disk filename.
     * 把图片URL经过MD5加密生成唯一的key值，避免了URL中可能含有非法字符问题
     */
    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        // http://stackoverflow.com/questions/332079
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 根据链接获取当前的缓存
     * @param diskLruCache
     * @param imgeUrl
     * @return
     */
    public static Bitmap getCache(DiskLruCache diskLruCache,String imgeUrl) {
        try {
            String key = hashKeyForDisk(imgeUrl);
            DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
            if (snapshot != null) {
                InputStream in = snapshot.getInputStream(0);
                return BitmapFactory.decodeStream(in);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 清除当前链接的缓存
     * @param diskLruCache
     * @param imgeUrl
     */
    public static void removeCache(DiskLruCache diskLruCache,String imgeUrl) {
        try {
            diskLruCache.remove(hashKeyForDisk(imgeUrl));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除所有缓存
     * @param diskLruCache
     */
    public static void deleteCache(DiskLruCache diskLruCache) {
        try {
            //delete()方法内部会调用close()
            diskLruCache.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取缓存的大小
     * @param diskLruCache
     * @return
     */
    public static long getCacheSize(DiskLruCache diskLruCache) {
        return diskLruCache.size();
    }
}
