package com.mobile.androidtest.annotation;

import android.util.Log;

import java.lang.reflect.Method;

/**
 * @author huangjian
 * @create 2018/7/25 0025
 * @Description 反射机制的注解处理器
 */
public class AnnotationUtil {
    private static final String TAG = "hj";

    public static void getAnnotation(Class<?> cl) {
        if (cl != null) {
            for (Method method : cl.getDeclaredMethods()) {
                AnnotationTest annotation = method.getAnnotation(AnnotationTest.class);
                if (annotation != null) {
                    String name = annotation.name();
                    int id = annotation.id();
                    Log.d(TAG, "getAnnotation: name :" + name);
                    Log.d(TAG, "getAnnotation: id :" + id);
                }
            }
        }
    }
}
