package com.suli.lib.security;

import com.suli.lib.utils.EncodeUtils;

/**
 * Created by suli690 on 2017/7/20.
 */

public class SecurityUtils {

  static {
    System.loadLibrary("security");
  }

  public static String encryptAES(String data, String key) {
    return EncodeUtils.base64Encode2String(encryptAES(data.getBytes(), key.getBytes()));
  }

  public static String encryptBase64RsaByPublicKey(String data, String key) {
    return EncodeUtils.base64Encode2String(encryptRsaByPublicKey(data.getBytes(), key));
  }

  private static native byte[] encryptAES(byte[] data, byte[] key);

  private static native byte[] encryptRsaByPublicKey(byte[] data, String key);
}
