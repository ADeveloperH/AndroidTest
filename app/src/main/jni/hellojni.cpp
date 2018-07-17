//
// Created by Administrator on 2018/7/17 0017.
//
#include "com_mobile_androidtest_JNIUtil.h"
JNIEXPORT jstring JNICALL Java_com_mobile_androidtest_JNIUtil_getNativeStr(JNIEnv *env, jclass thiz){
    return (env)->NewStringUTF("Hello JNI");
}