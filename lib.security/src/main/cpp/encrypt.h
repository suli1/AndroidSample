//
// Created by 苏力 on 2017/7/20.
//

#ifndef ANDROIDSAMPLE_ENCRYPT_H
#define ANDROIDSAMPLE_ENCRYPT_H


#include <stddef.h>

/**
 * md5 128bits end withs '\0'
 * @param src input data
 * @param slen input data length
 * @param output encode by HEX end widths '\0'
 */
void md5(const unsigned char *src, size_t slen, unsigned char output[33]);

/**
 * SHA1 160bits
 * @param src input data
 * @param slen input data length
 * @param output encode by HEX end widths '\0'
 */
void sha1(const unsigned char *src, size_t slen, unsigned char output[41]);

/**
 *
 * Encoder flag bit to omit all line terminators (i.e., the output
 * @param dst
 * @param olen
 * @param src
 * @param slen
 * @return
 */
int base64_encode(unsigned char **dst, size_t *olen, const unsigned char *src, size_t slen);

/**
 * base64 NO_WRAP
 * Decode base64-encoded data
 * @param dst
 * @param olen
 * @param src
 * @param slen
 * @return
 */
int base64_decode(unsigned char **dst, size_t *olen, const unsigned char *src, size_t slen);

#define AES_BLOCK_SIZE 16

/**
 * RSA/ECB/PKCS1Padding
 * @param output input data max Len 117
 * @param outputLen
 * @param input
 * @param inputLen
 * @param key
 * @param keyLen
 * @return 0 on success
 */
int encrypt_rsa_public(unsigned char **output, int *outputLen, const unsigned char *input,
                       const int inputLen, const unsigned char *key, const int keyLen);

/**
 * AES/CBC/PKCS5Padding
 * @param output
 * @param outputLen
 * @param input
 * @param inputLen
 * @param key
 * @param keyLen
 * @return 0 on success
 */
int encrypt_aes_cbc(unsigned char **output, int *outputLen, const unsigned char *input,
                    const int inputLen, const unsigned char *key, const unsigned int keyLen);

/**
 * AES CBC PkCS5Paddnig
 * @param output
 * @param outputLen
 * @param input
 * @param inputLen
 * @param key
 * @param keyLen
 * @return 0 on success
 */
int decrypt_aes_cbc(unsigned char **output, int *outputLen, const unsigned char *input,
                    const int inputLen, const unsigned char *key, const unsigned int keyLen);

#endif //ANDROIDSAMPLE_ENCRYPT_H
