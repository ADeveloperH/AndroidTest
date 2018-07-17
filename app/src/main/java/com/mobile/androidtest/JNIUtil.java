package com.mobile.androidtest;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

/**
 * @author huangjian
 * @create 2018/7/17 0017
 * @Description
 */
public class JNIUtil {

    static {
        //装载JNI库文件，也可以在使用的地方进行装载
        //必须与app下的build.gradle中moduleName相同
        System.loadLibrary("hellojnilib");
    }

    public static native String getNativeStr();

    /**
     * 获取app签名信息
     * @param context
     * @return
     */
    public static String getSignature(Context context) {
        try {
            /** 通过包管理器获得指定包名包含签名的包信息 **/
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            /******* 通过返回的包信息获得签名数组 *******/
            Signature[] signatures = packageInfo.signatures;
            /******* 循环遍历签名数组拼接应用签名 *******/
            return signatures[0].toCharsString();
            /************** 得到应用签名 **************/
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
