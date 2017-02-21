#include "aes256.h"
#include "base64.h"

#ifndef LOGE
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,"jni---get---key",__VA_ARGS__)
#endif

jstring encryptAES(JNIEnv *, const uint8_t * /* plainText */, const uint8_t * /* key */,
                   const uint8_t * /* iv */);

jstring decryptAES(JNIEnv *, const uint8_t * /* encryptText */, const uint8_t * /* key */,
                   const uint8_t * /* iv */);

jstring charToJstring(JNIEnv *envPtr, char *src);

jstring JNICALL Java_com_suli_sample_MainActivity_encryptImportantInfo(
        JNIEnv *env, jobject thiz, jstring text, jstring key) {
    const char *keyChar = (*env)->GetStringUTFChars(env, key, JNI_FALSE);
    const char *textChar = (*env)->GetStringUTFChars(env, text, JNI_FALSE);

    jstring result = encryptAES(env, textChar, keyChar, keyChar);

    (*env)->ReleaseStringUTFChars(env, key, keyChar);
    (*env)->ReleaseStringUTFChars(env, text, textChar);

    return result;
}

jstring JNICALL Java_com_suli_sample_MainActivity_decryptImportantInfo(
        JNIEnv *env, jobject thiz, jstring text, jstring key) {
    const char *keyChar = (*env)->GetStringUTFChars(env, key, JNI_FALSE);
    const char *textChar = (*env)->GetStringUTFChars(env, text, JNI_FALSE);

    jstring result = decryptAES(env, textChar, keyChar, keyChar);

    (*env)->ReleaseStringUTFChars(env, key, keyChar);
    (*env)->ReleaseStringUTFChars(env, text, textChar);

    return result;
}

jstring encryptAES(JNIEnv *envPtr, const uint8_t *text, const uint8_t *key, const uint8_t *iv) {
    // 初始化加密参数
    aes256_context ctx;
    aes256_init(&ctx, key);


    // 分组填充加密
    int i;
    int mwSize = strlen(text);
    int remainder = mwSize % 16;
    jstring entryptString;
    if (mwSize < 16) {    // 小于16字节，填充16字节，后面填充几个几 比方说10个字节 就要补齐6个6 11个字节就补齐5个5
        uint8_t input[16];
        for (i = 0; i < 16; i++) {
            if (i < mwSize) {
                input[i] = text[i];
            } else {
                input[i] = 16 - mwSize;
            }
        }
        //加密
        uint8_t output[16];
        aes256_encrypt_cbc(&ctx, input, iv, output);
        //base64加密后然后jstring格式输出
        char *enc = base64_encode(output, sizeof(output));
        entryptString = charToJstring(envPtr, enc);

        free(enc);
    } else {    //如果是16的倍数，填充16字节，后面填充0x10
        int group = mwSize / 16;
        int size = 16 * (group + 1);
        uint8_t input[size];
        for (i = 0; i < size; i++) {
            if (i < mwSize) {
                input[i] = text[i];
            } else {
                if (remainder == 0) {
                    input[i] = 0x10;
                } else {    //如果不足16位 少多少位就补几个几  如：少4为就补4个4 以此类推
                    int dif = size - mwSize;
                    input[i] = dif;
                }
            }
        }
        //加密
        uint8_t output[size];
        aes256_encrypt_cbc(&ctx, input, iv, output);
        //base64加密后然后jstring格式输出
        char *enc = base64_encode(output, sizeof(output));
        entryptString = charToJstring(envPtr, enc);

        free(enc);
    }

    return entryptString;
}


jstring decryptAES(JNIEnv *envPtr, const uint8_t *text, const uint8_t *key, const uint8_t *iv) {
    // init aes context
    aes256_context ctx;
    aes256_init(&ctx, key);

    // base64 decode
    char *dec = base64_decode(text, strlen(text));

    // aes decrypt
    uint8_t  output[16];
    aes256_decrypt_cbc(&ctx, iv, dec, output);

    // TODO 去掉填充的字节

    return charToJstring(envPtr, output);
}

jstring charToJstring(JNIEnv *envPtr, char *src) {
    JNIEnv env = *envPtr;

    jsize len = strlen(src);
    jclass clsstring = env->FindClass(envPtr, "java/lang/String");
    jstring strencode = env->NewStringUTF(envPtr, "UTF-8");
    jmethodID mid = env->GetMethodID(envPtr, clsstring, "<init>",
                                     "([BLjava/lang/String;)V");
    jbyteArray barr = env->NewByteArray(envPtr, len);
    env->SetByteArrayRegion(envPtr, barr, 0, len, (jbyte *) src);

    return (jstring) env->NewObject(envPtr, clsstring, mid, barr, strencode);
}
