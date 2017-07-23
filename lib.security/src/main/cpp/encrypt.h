//
// Created by 苏力 on 2017/7/20.
//

#ifndef ANDROIDSAMPLE_ENCRYPT_H
#define ANDROIDSAMPLE_ENCRYPT_H


#include <stddef.h>

void md5(const unsigned char *src, size_t slen, unsigned char output[16]);

void sha1(const unsigned char *src, size_t slen, unsigned char output[20]);

int base64_encode(unsigned char **dst, size_t *olen, const unsigned char *src, size_t slen);

int base64_decode(unsigned char **dst, size_t *olen, const unsigned char *src, size_t slen);

#define AES_BLOCK_SIZE 16

int encrypt_rsa_public(unsigned char **output, int *outputLen, const unsigned char *input,
                       const int inputLen, const unsigned char *key, const int keyLen);

int encrypt_aes_cbc(unsigned char **output, int *outputLen, const unsigned char *input,
                    const int inputLen, const unsigned char *key, const int keyLen);

int decrypt_aes_cbc(unsigned char **output, int *outputLen, const unsigned char *input,
                    const int inputLen, const unsigned char *key, const int keyLen);

#endif //ANDROIDSAMPLE_ENCRYPT_H
