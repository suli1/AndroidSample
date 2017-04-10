package com.suli.libutils;

import android.support.test.runner.AndroidJUnit4;
import com.suli.lib.utils.EncodeUtils;
import com.suli.lib.utils.EncryptUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by suli on 2017/3/22.
 * {@link EncryptUtils} 单元测试
 */
@RunWith(AndroidJUnit4.class) public class EncryptUtilsTest {

  @Test public void md2Test() {

  }

  @Test public void md5Test() {

  }

  @Test public void sha1Test() {

  }

  @Test public void sha224Test() {

  }

  @Test public void sha256Test() {

  }

  @Test public void sha384Test() {

  }

  @Test public void sha512Test() {

  }

  @Test public void hmacMd5Test() {

  }

  @Test public void hmacSha1Test() {

  }

  @Test public void hmacSha224Test() {

  }

  @Test public void hmacSha256Test() {

  }

  @Test public void hmacSha384Test() {

  }

  @Test public void hmacSha512Test() {

  }

  @Test public void desTest() {

  }

  @Test public void _3desTest() {

  }

  @Test public void aseTest() {
    final String MSG_ENCRYPT =
        "ICG7h2s5BV0pP3aseoNqjyaU62g39ta4xuEbW+tLlfp+ZsuuabyEJozCpFZBmkHq+goGrjPTijJ6KjZ96BuUwsPYtfzh5Xiakk5XnqpO9xMBWzhKBmW4w/e+6aIlN/2KOPL2MDFVWFhsVQgUhl3mdgojiZZ5UYfijZrZob1Xsls/dFHaE0K8DBDDs0uqP6FOJdLVfo/dVz18m5AeQx8r50/8bPFV2vq+W/mkSrRTADA8HLEdeP7SlXF1C2CXK5MR+o/3R1mr4tvyuTx5Zp98nRKDa55gMIvfJt4G5vBOU2DGwfJJLscchlJtgYsnuZiD9hmq6aXTiTGAOy+XXeqKA6sa9r2zgye4hlvvMZwaw+o=";
    final String MSG =
        "accountId=15586906&curVer=5.0.1&mobile=3UWLdbzJxnRftOXzUrL0CEieNy5Zr3ktUsRlDFd6NW5WC0SwBoN/d6c0F30fxw6f1M6t8M7bdb8WLOvLkdb7DN+nc/X1JUw3KX7CTEwf6ekVj1oyrgc83kB4PMZRLMPIKKjry4SzgyhyRB0HbpOJC/cOa+Glqnu9HMC7CgYnPO4=&os=IOS&token=ab31c4a9d5ef4bee8841e69dcd329113";
    final String KEY = "15586906ab31c4a9";

    // java
    byte[] msgJava = EncryptUtils.encryptAES2Base64(MSG.getBytes(), KEY.getBytes());
    Assert.assertEquals(MSG_ENCRYPT, new String(msgJava));
    Assert.assertEquals(MSG, new String(EncryptUtils.decryptBase64AES(msgJava, KEY.getBytes())));

    // native
    byte[] msgNative =
        EncryptUtils.crypt(MSG.getBytes(), KEY.getBytes(), System.currentTimeMillis(),
            EncryptUtils.ENCRYPT);
    Assert.assertEquals(MSG_ENCRYPT, EncodeUtils.base64Encode2String(msgNative));

    Assert.assertEquals(new String(
        EncryptUtils.crypt(msgNative, KEY.getBytes(), System.currentTimeMillis(),
            EncryptUtils.DECRYPT)), MSG);
  }

  @Test public void rsaTest() {
    final String PUBLIC_KEY =
        "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC49XXXomZEA4M2VIWcAxQcw3ZM"
        + "ncYk9VHdrcH7q6Zi9l1bMSXKBHZnw/5FeoArCr4ZvaS8myTu6IWcmS/9LJVtKJiR"
        + "sWf1VD6S2TsTr/qa2Dri3VK2bfJJ+TdPSKJOEznvCI12YXpdHrWhyoYtR8DF7vmz"
        + "ZBBjOI13dexnjfyekQIDAQAB";

    final String PRIVATE_KEY =
        "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALj1ddeiZkQDgzZU"
        + "hZwDFBzDdkydxiT1Ud2twfurpmL2XVsxJcoEdmfD/kV6gCsKvhm9pLybJO7ohZyZ"
        + "L/0slW0omJGxZ/VUPpLZOxOv+prYOuLdUrZt8kn5N09Iok4TOe8IjXZhel0etaHK"
        + "hi1HwMXu+bNkEGM4jXd17GeN/J6RAgMBAAECgYA7Yz1CTrfNcN9Jq5v2+xoTHkO2"
        + "2BYOOeLebfNSTswvDHHvXPmIQySNruXPA0VyDJH7i+QAV0Rlna2BGcLJ5O3ZQihF"
        + "VRd5sPWdrNtbBrFOap3JH0t9zCWV88hkwjIaT61JsCLY+EnFg5G7AyIh7Zigr389"
        + "IBQPi6kMrN8r35Nr0QJBANuPtTIv29q4/HixS1bqrteZ1EO3xtPX0eU+WyX69TUE"
        + "yTiCeYevdKqQ6q7SgDKMVY4UsezL0p0pZc1qaaf9K1UCQQDXp6DO6wiF1GNirdGF"
        + "KIgQMtcWapz6VA9EZj4VKe0YjvxoeI/I6PJ8cxQqDjMHbTlzDwSc5+RlByTxDYvM"
        + "+D5NAkEA0cupmgD9FSw5oqHWmYC5/K2yk9phFD9fAqvosIvyDe060NJrielvkyPR"
        + "+6aMdMB/Z7oCoX/JCf9qlTOfLbPN2QJAKYbyBGZmAb9gVAQ8sHOZn+TqXjqxd9Lw"
        + "Da/SjKjCK1R9bnCo33tcFnLikyag1PFHesyNitlstbemeXSuUw4KkQJAEjXZNxHz"
        + "buosc79n/A3chIT2oh7UR1cR4zyiZrurPEgaLHKJ5OMOZfSGonHvhMDSOGPI3TnE"
        + "FIpoKJJzoRwAfA==";

    String msg = "186-7837-2845";
    String encrypt = EncryptUtils.encryptBase64RSAByPublicKey(msg, PUBLIC_KEY);
    String decrypt = EncryptUtils.decryptBase64RSAByPrivateKey(encrypt, PRIVATE_KEY);
    Assert.assertEquals(msg, decrypt);
  }
}
