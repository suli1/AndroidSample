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
    mbedtls_pk_context pk;
    mbedtls_rsa_context *rsa;

    mbedtls_pk_init(&pk);

    int ret;
    ret = mbedtls_pk_parse_public_key(&pk, key, (size_t) keyLen + 1);
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
    if (keyLen != 16) {
        return -1;
    }

    mbedtls_aes_context ctx;
    mbedtls_aes_init(&ctx);
    mbedtls_aes_setkey_enc(&ctx, key, keyLen * sizeof(unsigned char));
    unsigned char iv[16];
    memcpy(iv, key, sizeof(iv));

    int padding;
    if (inputLen % 16 == 0) {
        padding = 16;
    } else {
        padding = 16 - inputLen % 16;
    }
    *output = (unsigned char *) malloc(*outputLen);

    int ret;
    for (int i = 0; i < inputLen + padding; i += 16) {
        unsigned char tmp[16];
        memcpy(tmp, input + i, 16);
        int left = inputLen + padding - i;
        if (left <= 16) {
            memset(tmp + (16 - padding), padding, padding);
        }
        ret = mbedtls_aes_crypt_cbc(&ctx, MBEDTLS_AES_ENCRYPT, 16, iv, tmp,
                                    *output + i);
        if (ret != 0) {
            LOGE("AES encrypt error!");
            printError(ret);
            break;
        }
    }

    mbedtls_aes_free(&ctx);

    return ret;
}

int decryptAesCbc(unsigned char **output, int *outputLen, const unsigned char *input,
                  const int inputLen, const unsigned char *key, const int keyLen) {
    if (keyLen != 16 || inputLen % 16) {
        return -1;
    }

    mbedtls_aes_context ctx;
    mbedtls_aes_init(&ctx);
    mbedtls_aes_setkey_enc(&ctx, key, keyLen * sizeof(unsigned char));
    unsigned char iv[16];
    memcpy(iv, key, sizeof(iv));

    *output = (unsigned char *) malloc(inputLen);

    int ret;
    for (int i = 0; i < inputLen; i += 16) {
        char tmp[16];

        ret = mbedtls_aes_crypt_cbc(&ctx, MBEDTLS_AES_DECRYPT, 16, iv, tmp,
                                    *output + i);
        if (ret != 0) {
            LOGE("AES encrypt error!");
            printError(ret);
            break;
        }

        int left = inputLen - i;
        if (left == 16) {
            int padding = *(*output + inputLen - 1);
            *outputLen = inputLen - padding;
        }
    }

    mbedtls_aes_free(&ctx);

    return ret;
}
