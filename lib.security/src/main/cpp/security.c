//
// Created by 苏力 on 2017/7/19.
//

#include <jni.h>
#include <malloc.h>
#include "Log.h"
#include "encrypt.h"

#define TARGET_CLASS "com/suli/lib/security/SecurityUtils"

jbyteArray handleCryptoTemplates(JNIEnv *env,
                                 int (*fun)(unsigned char *, int *, const unsigned char *,
                                            const int, const unsigned char *, const int),
                                 jbyteArray input_, jstring key_) {
    jbyte *input = (*env)->GetByteArrayElements(env, input_, 0);
    jsize inputLen = (*env)->GetArrayLength(env, input_);

    const char *key = (*env)->GetStringUTFChars(env, key_, 0);
    jsize keyLen = (*env)->GetStringUTFLength(env, key_);

    LOGD("keyLen:%d", keyLen);

    jbyteArray result;
    unsigned char *output = NULL;
    int outputLen = 0;
    if (fun(&output, &outputLen, (unsigned char *) input, inputLen,
            (unsigned char *) key, keyLen) == 0) {
        result = (*env)->NewByteArray(env, (jsize) outputLen);
        (*env)->SetByteArrayRegion(env, result, 0, (jsize) outputLen, (jbyte *) output);
    } else {
        result = (*env)->NewByteArray(env, 0);
    }

    if (output != NULL) {
        free(output);
    }
    (*env)->ReleaseByteArrayElements(env, input_, input, 0);
    (*env)->ReleaseStringUTFChars(env, key_, key);

    return result;
}

jbyteArray encryptAES(JNIEnv *env, jobject instantce, jbyteArray input_, jstring key_) {
    return handleCryptoTemplates(env, encryptAesCbc, input_, key_);
}

jbyteArray encryptRsaByPublicKey(JNIEnv *env, jobject inistance, jbyteArray input_, jstring key_) {
    return handleCryptoTemplates(env, encryptRsaByPk, input_, key_);
}


static const JNINativeMethod gMethods[] = {
        {"encryptAES",            "([BLjava/lang/String;)[B", (void *) encryptAES},
        {"encryptRsaByPublicKey", "([BLjava/lang/String;)[B", (void *) encryptRsaByPublicKey},
};

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = NULL;
    if ((*vm)->GetEnv(vm, (void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        return -1;
    }

    jclass clazz = (*env)->FindClass(env, TARGET_CLASS);
    if (!clazz) {
        return -1;
    }

    if ((*env)->RegisterNatives(env, clazz, gMethods,
                                sizeof(gMethods) / sizeof(gMethods[0])) != JNI_OK) {
        return -1;
    }
    LOGI("JNI_OnLoad complete!");

    return JNI_VERSION_1_4;
}

JNIEXPORT void JNICALL JNI_OnUnload(JavaVM *vm, void *reserved) {
    LOGI("JNI_OnUnload");
    JNIEnv *env = NULL;
    if ((*vm)->GetEnv(vm, (void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        return;
    }
}
