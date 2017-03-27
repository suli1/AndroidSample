package com.suli.libutils;

import com.suli.lib.utils.EncryptUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by suli on 2017/3/22.
 * {@link EncryptUtils} 单元测试
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE ,sdk = 23)
public class EncryptUtilsTest {

  private final static String MSG_ENCRYPT =
      "ICG7h2s5BV0pP3aseoNqjyaU62g39ta4xuEbW+tLlfp+ZsuuabyEJozCpFZBmkHq+goGrjPTijJ6KjZ96BuUwsPYtfzh5Xiakk5XnqpO9xMBWzhKBmW4w/e+6aIlN/2KOPL2MDFVWFhsVQgUhl3mdgojiZZ5UYfijZrZob1Xsls/dFHaE0K8DBDDs0uqP6FOJdLVfo/dVz18m5AeQx8r50/8bPFV2vq+W/mkSrRTADA8HLEdeP7SlXF1C2CXK5MR+o/3R1mr4tvyuTx5Zp98nRKDa55gMIvfJt4G5vBOU2DGwfJJLscchlJtgYsnuZiD9hmq6aXTiTGAOy+XXeqKA6sa9r2zgye4hlvvMZwaw+o=";
  private final static String MSG =
      "accountId=15586906&curVer=5.0.1&mobile=3UWLdbzJxnRftOXzUrL0CEieNy5Zr3ktUsRlDFd6NW5WC0SwBoN/d6c0F30fxw6f1M6t8M7bdb8WLOvLkdb7DN+nc/X1JUw3KX7CTEwf6ekVj1oyrgc83kB4PMZRLMPIKKjry4SzgyhyRB0HbpOJC/cOa+Glqnu9HMC7CgYnPO4=&os=IOS&token=ab31c4a9d5ef4bee8841e69dcd329113";
  private final static String KEY = "15586906ab31c4a9";

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
    // java
    String msgJava = new String(EncryptUtils.encryptAES2Base64(MSG.getBytes(), KEY.getBytes()));
    Assert.assertEquals(MSG_ENCRYPT, msgJava);

    // native
    String msgNative = new String(
        EncryptUtils.crypt(MSG.getBytes(), KEY.getBytes(), System.currentTimeMillis(),
            EncryptUtils.ENCRYPT));
    Assert.assertEquals(MSG_ENCRYPT, msgNative);

    Assert.assertEquals(new String(
        EncryptUtils.crypt(msgNative.getBytes(), KEY.getBytes(), System.currentTimeMillis(),
            EncryptUtils.DECRYPT)), MSG);
  }
}
