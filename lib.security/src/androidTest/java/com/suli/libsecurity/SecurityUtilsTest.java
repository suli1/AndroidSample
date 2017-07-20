package com.suli.libsecurity;

import android.support.test.runner.AndroidJUnit4;
import com.suli.lib.security.Constant;
import com.suli.lib.security.SecurityUtils;
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

  @Test public void testAES() {
    String encrypt = SecurityUtils.encryptAES(data, "1390dldml");
    Assert.assertEquals("", encrypt);
  }

  @Test public void testRSA() {
    String encrypt =
        SecurityUtils.encryptBase64RsaByPublicKey(data, Constant.NORMAL_RSA_PUBLIC_KEY);
    String decrypt =
        EncryptUtils.decryptBase64RSAByPrivateKey(encrypt, Constant.NORMAL_RSA_PRIVATE_KEY_JAVA);
    Assert.assertEquals(data, decrypt);
  }
}
