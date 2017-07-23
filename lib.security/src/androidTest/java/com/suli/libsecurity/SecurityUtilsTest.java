package com.suli.libsecurity;

import android.support.test.runner.AndroidJUnit4;
import com.suli.lib.security.Constant;
import com.suli.lib.security.SecurityUtils;
import com.suli.lib.utils.EncodeUtils;
import com.suli.lib.utils.EncryptUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by suli690 on 2017/7/20.
 * {@link SecurityUtils} 单元测试
 */

@RunWith(AndroidJUnit4.class) public class SecurityUtilsTest {
  private final String data = "18664519382";

  @Test public void testHash() {
    String md5Java = EncryptUtils.encryptMD5ToString(data);
    String md5Native = SecurityUtils.md5(data);
    Assert.assertEquals(md5Java, md5Native);

    String sha1Java = EncryptUtils.encryptSHA1ToString(data);
    String sha1Native = SecurityUtils.sha1(data);
    Assert.assertEquals(sha1Java, sha1Native);
  }

  @Test public void testBase64() {
    String base64EncodeNative = SecurityUtils.base64EncodeToString(data);
    String base64EncodeJava = EncodeUtils.base64Encode2String(data.getBytes());
    Assert.assertEquals(base64EncodeJava, base64EncodeNative);

    String base64DecodeNative = SecurityUtils.base64DecodeToString(base64EncodeNative);
    String base64DecodeJava = new String(EncodeUtils.base64Decode(base64EncodeNative));
    Assert.assertEquals(data, base64DecodeNative);
    Assert.assertEquals(base64DecodeJava, base64DecodeNative);
  }

  @Test public void testAES() {
    String key = "12345678qwertyui"; // 16位
    String encrypt = SecurityUtils.encryptAESBase64(data, key);
    //String encrypt = EncryptUtils.encryptAES2Base64(data, key);
    //String decrypt = EncryptUtils.decryptBase64AES(encrypt, key);
    String decrypt = SecurityUtils.decryptAESBase64(encrypt, key);
    Assert.assertEquals(data, decrypt);
  }

  @Test public void testRSA() {
    String encrypt =
        SecurityUtils.encryptBase64RsaByPublicKey(data, Constant.NORMAL_RSA_PUBLIC_KEY);
    String decrypt =
        EncryptUtils.decryptBase64RSAByPrivateKey(encrypt, Constant.NORMAL_RSA_PRIVATE_KEY_JAVA);
    Assert.assertEquals(data, decrypt);
  }
}
