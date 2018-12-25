//
// Created by mika on 2018/12/25.
//
#include <jni.h>
#include "com_mika_plugin1_JniUtils.h"

JNIEXPORT jstring JNICALL Java_com_mika_plugin1_JniUtils_getString(JNIEnv *env, jobject obj){
    return env->NewStringUTF("Hello C");
}
