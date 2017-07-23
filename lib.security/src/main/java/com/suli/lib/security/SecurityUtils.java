package com.suli.lib.security;

import com.suli.lib.utils.EncodeUtils;

/**
 * Created by suli690 on 2017/7/20.
 */

public class SecurityUtils {

  static {
    System.loadLibrary("security");
  }

  public static String base64EncodeToString(String data) {
    return new String(base64Encode(data.getBytes()));
  }

  public static String base64DecodeToString(String data) {
    return new String(base64Decode(data.getBytes()));
  }

  public static String decryptAESBase64(String data, String key) {
    return new String(decryptAES(EncodeUtils.base64Decode(data), key));
  }

  public static String encryptAESBase64(String data, String key) {
    return EncodeUtils.base64Encode2String(encryptAES(data.getBytes(), key));
  }

  public static String encryptBase64RsaByPublicKey(String data, String key) {
    return EncodeUtils.base64Encode2String(encryptRsaByPublicKey(data.getBytes(), key));
  }

  public static native String md5(String data);

  public static native String sha1(String data);

  public static native byte[] base64Encode(byte[] data);

  public static native byte[] base64Decode(byte[] data);

  private static native byte[] encryptAES(byte[] data, String key);

  private static native byte[] decryptAES(byte[] data, String key);

  private static native byte[] encryptRsaByPublicKey(byte[] data, String key);
}
