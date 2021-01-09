// test.cpp
#include <jni.h>
#include <stdio.h>

#ifdef __cplusplus
extern "C" {
#endif

jstring Java_cn_shui_testndl_MainActivity_get(JNIEnv *env, jobject thiz) {
    printf("invoke get in c++\n");
    return env->NewStringUTF("Hello from JNIin libjni-test.so !");
}

void Java_cn_shui_testndk_MainActivity_set(JNIEnv *env, jobject thiz, jstring string) {
    printf("incoke set from C++\n");
    char* str = (char*) env->GetStringUTFChars(string, NULL);
    printf("%s\n", str);
    env->ReleaseStringUTFChars(string, str);
}

#ifdef __cplusplus
}
#endif
