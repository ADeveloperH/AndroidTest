package com.mobile.androidtest;

/**
 * @author huangjian
 * @create 2018/7/17 0017
 * @Description
 */
public class SignatureUtil {
    static {
        //装载JNI库文件，也可以在使用的地方进行装载
        //必须与app下的build.gradle中moduleName相同
        System.loadLibrary("hellojnilib");
    }
    public static native String checkSignature(Object obj);

}
