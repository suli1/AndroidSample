package com.suli.lib.utils.tools;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密和解密算法
 *
 * @author suli
 */
public class AESUtils {

  /**
   * 获取解密后的字符串
   */
  public static String RevertAESCode(String content, String passcode) {
    byte[] decryptFrom = parseHexStr2Byte(content);
    byte[] decryptResult = decryptWithVi(decryptFrom, passcode.getBytes());
    String decryptString = new String(decryptResult);
    return decryptString;
  }

  /**
   * 获取加密后的字符串
   */
  public static String GetAESCode(String content, String passcode) {
    byte[] encryptResult = encryptWithVi(content.getBytes(), passcode.getBytes());
    String encryptResultStr = parseByte2HexStr(encryptResult);
    return encryptResultStr;
  }

  public static byte[] encrypt(byte[] content, byte[] password) {
    try {
      // 对密钥进行处理-S
      KeyGenerator kgen = KeyGenerator.getInstance("AES");
      SecureRandom secureRandom;
      // for Android
      //secureRandom = SecureRandom.getInstance("SHA1PRNG", "Crypto");
      // for Java
       secureRandom = SecureRandom.getInstance("SHA1PRNG");
      secureRandom.setSeed(password);
      kgen.init(128, secureRandom);
      SecretKey secretKey = kgen.generateKey();
      byte[] enCodeFormat = secretKey.getEncoded();
      SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, key);
      return cipher.doFinal(content);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static byte[] decrypt(byte[] encrypted, byte[] password) {
    try {
      // 对密钥进行处理-E
      KeyGenerator kgen = KeyGenerator.getInstance("AES");
      SecureRandom secureRandom;
      // for Android
      //secureRandom = SecureRandom.getInstance("SHA1PRNG", "Crypto");
      // for Java
       secureRandom = SecureRandom.getInstance("SHA1PRNG");
      secureRandom.setSeed(password);
      kgen.init(128, secureRandom);
      SecretKey secretKey = kgen.generateKey();
      byte[] enCodeFormat = secretKey.getEncoded();

      SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE, key);
      return cipher.doFinal(encrypted);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private static final byte[] KEY_VI = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6 };
  private static final String bm = "UTF-8";

  public static byte[] encryptWithVi(byte[] content, byte[] password) {
    try {
      // 对密钥进行处理-E
      IvParameterSpec zeroIv = new IvParameterSpec(password);
      SecretKeySpec key = new SecretKeySpec(password, "AES");
      //KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
      //keyGenerator.init(128, new SecureRandom(password));
      //SecretKey key = keyGenerator.generateKey();
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
      return cipher.doFinal(content);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static byte[] decryptWithVi(byte[] encrypted, byte[] password) {
    try {
      // 对密钥进行处理-E
      IvParameterSpec zeroIv = new IvParameterSpec(password);
      SecretKeySpec key = new SecretKeySpec(password, "AES");
      //KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
      //keyGenerator.init(128, new SecureRandom(password));
      //SecretKey key = keyGenerator.generateKey();
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
      return cipher.doFinal(encrypted);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 将16进制转换为二进制
   */
  public static byte[] parseHexStr2Byte(String hexStr) {
    if (hexStr.length() < 1) {
      return null;
    }
    byte[] result = new byte[hexStr.length() / 2];
    for (int i = 0; i < hexStr.length() / 2; i++) {
      int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
      int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
      result[i] = (byte) (high * 16 + low);
    }
    return result;
  }

  /**
   * 将二进制转换成16进制
   */
  public static String parseByte2HexStr(byte buf[]) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < buf.length; i++) {
      String hex = Integer.toHexString(buf[i] & 0xFF);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }
      sb.append(hex.toUpperCase());
    }
    return sb.toString();
  }

  private final static String MSG_ENCRYPT =
      "ICG7h2s5BV0pP3aseoNqjyaU62g39ta4xuEbW+tLlfp+ZsuuabyEJozCpFZBmkHq+goGrjPTijJ6KjZ96BuUwsPYtfzh5Xiakk5XnqpO9xMBWzhKBmW4w/e+6aIlN/2KOPL2MDFVWFhsVQgUhl3mdgojiZZ5UYfijZrZob1Xsls/dFHaE0K8DBDDs0uqP6FOJdLVfo/dVz18m5AeQx8r50/8bPFV2vq+W/mkSrRTADA8HLEdeP7SlXF1C2CXK5MR+o/3R1mr4tvyuTx5Zp98nRKDa55gMIvfJt4G5vBOU2DGwfJJLscchlJtgYsnuZiD9hmq6aXTiTGAOy+XXeqKA6sa9r2zgye4hlvvMZwaw+o=";
  private final static String MSG =
      "accountId=15586906&curVer=5.0.1&mobile=3UWLdbzJxnRftOXzUrL0CEieNy5Zr3ktUsRlDFd6NW5WC0SwBoN/d6c0F30fxw6f1M6t8M7bdb8WLOvLkdb7DN+nc/X1JUw3KX7CTEwf6ekVj1oyrgc83kB4PMZRLMPIKKjry4SzgyhyRB0HbpOJC/cOa+Glqnu9HMC7CgYnPO4=&os=IOS&token=ab31c4a9d5ef4bee8841e69dcd329113";
  private final static String KEY = "15586906ab31c4a9";

  public static void main(String[] args) {
    /** 数据初始化 **/
    String content = MSG;
    String password = KEY;
    /** 加密 **/
    System.out.println("加密前：" + content);
    byte[] encryptResultByte = encryptWithVi(content.getBytes(), password.getBytes());
    System.out.println("加密后：" + new String(encryptResultByte));
    System.out.println("加密后 byte2Hex：" + parseByte2HexStr(encryptResultByte));
    /** 解密 **/
    String decryptString = RevertAESCode(parseByte2HexStr(encryptResultByte), password);
    System.out.println("解密后：" + decryptString);
  }
}
