//
// Created by 苏力 on 2017/7/20.
//

#ifndef ANDROIDSAMPLE_ENCRYPT_H
#define ANDROIDSAMPLE_ENCRYPT_H


#include <stddef.h>

#define AES_BLOCK_SIZE 16

int encryptRsaByPk(unsigned char **output, int *outputLen, const unsigned char *input,
                   const int inputLen, const unsigned char *key, const int keyLen);

int encryptAesCbc(unsigned char **output, int *outputLen, const unsigned char *input,
                  const int inputLen, const unsigned char *key, const int keyLen);

int decryptAesCbc(unsigned char **output, int *outputLen, const unsigned char *input,
                  const int inputLen, const unsigned char *key, const int keyLen);

#endif //ANDROIDSAMPLE_ENCRYPT_H
