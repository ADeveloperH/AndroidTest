//
// Created by Administrator on 2018/7/17 0017.
//
#include <jni.h>
#include <string.h>
#include <stdio.h>
#include "com_mobile_androidtest_SignatureUtil.h"

/**
 *这个key就是要用来加密传输内容的rsa的公钥
 */
const char* AUTH_KEY = "1111";

/**
 * 发布的app 签名,只有和本签名一致的app 才会返回 AUTH_KEY
 * 这个RELEASE_SIGN的值是上一步用java代码获取的值
 * 也可以是签名的MD5
 */
const char* RELEASE_SIGN = "308201dd30820146020101300d06092a864886f70d010105050030373116301406035504030c0d416e64726f69642044656275673110300e060355040a0c07416e64726f6964310b3009060355040613025553301e170d3138303533313033343930355a170d3438303532333033343930355a30373116301406035504030c0d416e64726f69642044656275673110300e060355040a0c07416e64726f6964310b300906035504061302555330819f300d06092a864886f70d010101050003818d0030818902818100b4b836a06cc45d750323abbb8d6dea25e4fe2c87c964f1464408028ebac052578d59dd5aefe7011e40dd455ca2fc2d270229a8f41bb6d628f2f5c9ae3605dd0154840fe8256008d80cc2f98241140dfbe22c020e9f6b1189d113dda39062d3a6ba3cda90f55e8e3b9b53575052c66f62e06eae61a2a56b264dc61b6bdd9538710203010001300d06092a864886f70d01010505000381810094fabb6fa38649665819d67a6eb3e6bf0652eb613bd4ea68f3272fcfa4a2d6d4bc563d583bd2f233e78a270c68fb47de4ea8c5b9be990de773b93060cccf2bacd23ad550559cb8c57744569204bc798b69dbd4e211a475f970fc8cdea20f799f76d6dc9dcec0da4941462be4a794fb3a82e823f622e9de621fb5d815d9f4c170";


JNIEXPORT jstring JNICALL Java_com_mobile_androidtest_SignatureUtil_checkSignature
        (JNIEnv *env, jclass jclazz, jobject contextObject){

    jclass native_class = env->GetObjectClass(contextObject);
    jmethodID pm_id = env->GetMethodID(native_class, "getPackageManager", "()Landroid/content/pm/PackageManager;");
    jobject pm_obj = env->CallObjectMethod(contextObject, pm_id);
    jclass pm_clazz = env->GetObjectClass(pm_obj);
// 得到 getPackageInfo 方法的 ID
    jmethodID package_info_id = env->GetMethodID(pm_clazz, "getPackageInfo","(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
    jclass native_classs = env->GetObjectClass(contextObject);
    jmethodID mId = env->GetMethodID(native_classs, "getPackageName", "()Ljava/lang/String;");
    jstring pkg_str = static_cast<jstring>(env->CallObjectMethod(contextObject, mId));
// 获得应用包的信息
    jobject pi_obj = env->CallObjectMethod(pm_obj, package_info_id, pkg_str, 64);
// 获得 PackageInfo 类
    jclass pi_clazz = env->GetObjectClass(pi_obj);
// 获得签名数组属性的 ID
    jfieldID signatures_fieldId = env->GetFieldID(pi_clazz, "signatures", "[Landroid/content/pm/Signature;");
    jobject signatures_obj = env->GetObjectField(pi_obj, signatures_fieldId);
    jobjectArray signaturesArray = (jobjectArray)signatures_obj;
    jsize size = env->GetArrayLength(signaturesArray);
    jobject signature_obj = env->GetObjectArrayElement(signaturesArray, 0);
    jclass signature_clazz = env->GetObjectClass(signature_obj);
    jmethodID string_id = env->GetMethodID(signature_clazz, "toCharsString", "()Ljava/lang/String;");
    jstring str = static_cast<jstring>(env->CallObjectMethod(signature_obj, string_id));
    char *c_msg = (char*)env->GetStringUTFChars(str,0);
    //return str;
    if(strcmp(c_msg,RELEASE_SIGN)==0)//签名一致  返回合法的 api key，否则返回错误
    {
        return (env)->NewStringUTF(AUTH_KEY);
    }else
    {
        return (env)->NewStringUTF("error");
    }
}
