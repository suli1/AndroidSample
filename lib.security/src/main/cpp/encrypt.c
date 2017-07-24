//
// Created by 苏力 on 2017/7/20.
//


#include <mbedtls/pk.h>
#include <malloc.h>
#include <stdlib.h>
#include <mbedtls/error.h>
#include <mbedtls/aes.h>
#include <mbedtls/base64.h>
#include <mbedtls/md5.h>
#include <mbedtls/sha1.h>
#include <string.h>
#include "Log.h"
#include "encrypt.h"


void md5(const unsigned char *src, size_t slen, unsigned char output[33]) {
    unsigned char out[16];
    mbedtls_md5(src, slen, out);
    for (int i = 0; i < 16; i++) {
        char ch[2];
        sprintf(ch, "%02X", out[i]);
        memcpy(output + (i * 2), ch, 2);
    }
    output[32] = '\0';
}

void sha1(const unsigned char *src, size_t slen, unsigned char output[41]) {
    unsigned char out[20];
    mbedtls_sha1(src, slen, out);
    for (int i = 0; i < 20; i++) {
        char ch[2];
        sprintf(ch, "%02X", out[i]);
        memcpy(output + (i * 2), ch, 2);
    }
    output[40] = '\0';
}


int base64_encode(unsigned char **dst, size_t *olen, const unsigned char *src, size_t slen) {
    mbedtls_base64_encode(NULL, 0, olen, src, slen);
    *dst = (unsigned char *) malloc(*olen);
    return mbedtls_base64_encode(*dst, *olen, olen, src, slen);
}

int base64_decode(unsigned char **dst, size_t *olen, const unsigned char *src, size_t slen) {
    mbedtls_base64_decode(NULL, 0, olen, src, slen);
    *dst = (unsigned char *) malloc(*olen);
    return mbedtls_base64_decode(*dst, *olen, olen, src, slen);
}


static int myrand(void *rng_state, unsigned char *output, size_t len) {
    size_t i;

//    if (rng_state != NULL)
//        rng_state = NULL;
    for (i = 0; i < len; ++i)
        output[i] = rand();
    return (0);
}

void print_error(int errorCode) {
    char buffer[256];
    memset(buffer, 0, sizeof(buffer));
    mbedtls_strerror(errorCode, buffer, 256);
    LOGE("%s", buffer);
}

int encrypt_rsa_public(unsigned char **output, int *outputLen, const unsigned char *input,
                       const int inputLen, const unsigned char *key, const int keyLen) {
    mbedtls_pk_context pk;
    mbedtls_rsa_context *rsa;

    mbedtls_pk_init(&pk);

    int ret;
    ret = mbedtls_pk_parse_public_key(&pk, key, (size_t) keyLen + 1);
    if (ret != 0) {
        LOGE("RSA public key parse error");
        print_error(ret);
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
        print_error(ret);
    }

    mbedtls_pk_free(&pk);
    mbedtls_rsa_free(rsa);

    return ret;
}

int encrypt_aes_cbc(unsigned char **output, int *outputLen, const unsigned char *input,
                    const int inputLen, const unsigned char *key, const unsigned int keyLen) {
    if (keyLen != 16) {
        return -1;
    }

    mbedtls_aes_context ctx;
    mbedtls_aes_init(&ctx);
    mbedtls_aes_setkey_enc(&ctx, key, keyLen * 8);

    unsigned char iv[16];
    memcpy(iv, key, sizeof(iv));

    int padding = AES_BLOCK_SIZE - inputLen % AES_BLOCK_SIZE;
    *outputLen = inputLen + padding;

    unsigned char *src = (unsigned char *) malloc(*outputLen);
    memcpy(src, input, inputLen);
    memset(src + inputLen, padding, padding);

    *output = (unsigned char *) malloc(*outputLen);
    memset(*output, 0, *outputLen);

    int ret = mbedtls_aes_crypt_cbc(&ctx, MBEDTLS_AES_ENCRYPT, *outputLen, iv, src,
                                    *output);
    if (ret != 0) {
        LOGE("AES encrypt error!");
        print_error(ret);
    }

    free(src);
    mbedtls_aes_free(&ctx);

    return ret;
}

int decrypt_aes_cbc(unsigned char **output, int *outputLen, const unsigned char *input,
                    const int inputLen, const unsigned char *key, const unsigned int keyLen) {
    if ((keyLen != 16) || (inputLen % AES_BLOCK_SIZE != 0)) {
        return -1;
    }

    mbedtls_aes_context ctx;
    mbedtls_aes_init(&ctx);
    mbedtls_aes_setkey_dec(&ctx, key, keyLen * 8);

    unsigned char iv[16];
    memcpy(iv, key, sizeof(iv));

    *output = (unsigned char *) malloc(inputLen);
    memset(*output, 0, inputLen);

    int ret = mbedtls_aes_crypt_cbc(&ctx, MBEDTLS_AES_DECRYPT, inputLen, iv, input,
                                    *output);
    if (ret != 0) {
        LOGE("AES encrypt error!");
        print_error(ret);
    }
    int padding = *(*output + (inputLen - 1));
    *outputLen = inputLen - padding;

    mbedtls_aes_free(&ctx);

    return ret;
}
