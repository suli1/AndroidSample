//
// Created by 苏力 on 2017/7/20.
//


#include <mbedtls/pk.h>
#include <malloc.h>
#include <stdlib.h>
#include <mbedtls/error.h>
#include <mbedtls/aes.h>
#include "Log.h"

static int myrand(void *rng_state, unsigned char *output, size_t len) {
    size_t i;

//    if (rng_state != NULL)
//        rng_state = NULL;
    for (i = 0; i < len; ++i)
        output[i] = rand();
    return (0);
}

void printError(int errorCode) {
    char buffer[256];
    memset(buffer, 0, sizeof(buffer));
    mbedtls_strerror(errorCode, buffer, 256);
    LOGE("%s", buffer);
}

int encryptRsaByPk(unsigned char **output, int *outputLen, const unsigned char *input,
                   const int inputLen, const unsigned char *key, const int keyLen) {
    LOGD("input len: %d, rsa key len:%d", inputLen, keyLen);

    mbedtls_pk_context pk;
    mbedtls_rsa_context *rsa;

    mbedtls_pk_init(&pk);

    int ret;
    ret = mbedtls_pk_parse_public_key(&pk, key, (size_t) keyLen);
    if (ret != 0) {
        LOGE("RSA public key parse error");
        printError(ret);
        return -1;
    }

    rsa = mbedtls_pk_rsa(pk);

    *outputLen = mbedtls_pk_get_len(&pk);
    *output = (unsigned char *) malloc(*outputLen);
    memset(*output, 0, *outputLen);
    ret = mbedtls_rsa_pkcs1_encrypt(rsa, myrand, NULL, MBEDTLS_RSA_PUBLIC, inputLen, input,
                                    *output);
    if (ret != 0) {
        LOGE("RSA public encrypt error");
        printError(ret);
    }

    mbedtls_pk_free(&pk);
    mbedtls_rsa_free(rsa);

    return ret;
}

int encryptAesCbc(unsigned char **output, int *outputLen, const unsigned char *input,
                  const int inputLen, const unsigned char *key, const int keyLen) {
    mbedtls_aes_context ctx;
    mbedtls_aes_init(&ctx);
    mbedtls_aes_setkey_enc(&ctx, key, keyLen * 8);
    unsigned char iv[16];
    memset(iv, 0, sizeof(iv));
    *outputLen = inputLen + inputLen % 16;
    *output = (unsigned char *) malloc(*outputLen);
    int ret = mbedtls_aes_crypt_cbc(&ctx, MBEDTLS_AES_ENCRYPT, (size_t) inputLen, iv, input,
                                    *output);
    if (ret != 0) {
        LOGE("AES encrypt error!");
        printError(ret);
    }

    mbedtls_aes_free(&ctx);

    return ret;
}
