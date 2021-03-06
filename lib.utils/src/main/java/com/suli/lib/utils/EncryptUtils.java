package com.suli.lib.utils;

import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static com.suli.lib.utils.ConvertUtils.bytes2HexString;
import static com.suli.lib.utils.ConvertUtils.hexString2Bytes;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/2
 *     desc  : 加密解密相关的工具类
 * </pre>
 */
public class EncryptUtils {

  static {
    System.loadLibrary("utils-lib");
  }

  private EncryptUtils() {
    throw new UnsupportedOperationException("u can't instantiate me...");
  }

  /*********************** 哈希加密相关 ***********************/
  /**
   * MD2加密
   *
   * @param data 明文字符串
   * @return 16进制密文
   */
  public static String encryptMD2ToString(String data) {
    return encryptMD2ToString(data.getBytes());
  }

  /**
   * MD2加密
   *
   * @param data 明文字节数组
   * @return 16进制密文
   */
  public static String encryptMD2ToString(byte[] data) {
    return bytes2HexString(encryptMD2(data));
  }

  /**
   * MD2加密
   *
   * @param data 明文字节数组
   * @return 密文字节数组
   */
  public static byte[] encryptMD2(byte[] data) {
    return hashTemplate(data, "MD2");
  }

  /**
   * MD5加密
   *
   * @param data 明文字符串
   * @return 16进制密文
   */
  public static String encryptMD5ToString(String data) {
    return encryptMD5ToString(data.getBytes());
  }

  /**
   * MD5加密
   *
   * @param data 明文字符串
   * @param salt 盐
   * @return 16进制加盐密文
   */
  public static String encryptMD5ToString(String data, String salt) {
    return bytes2HexString(encryptMD5((data + salt).getBytes()));
  }

  /**
   * MD5加密
   *
   * @param data 明文字节数组
   * @return 16进制密文
   */
  public static String encryptMD5ToString(byte[] data) {
    return bytes2HexString(encryptMD5(data));
  }

  /**
   * MD5加密
   *
   * @param data 明文字节数组
   * @param salt 盐字节数组
   * @return 16进制加盐密文
   */
  public static String encryptMD5ToString(byte[] data, byte[] salt) {
    if (data == null || salt == null) return null;
    byte[] dataSalt = new byte[data.length + salt.length];
    System.arraycopy(data, 0, dataSalt, 0, data.length);
    System.arraycopy(salt, 0, dataSalt, data.length, salt.length);
    return bytes2HexString(encryptMD5(dataSalt));
  }

  /**
   * MD5加密
   *
   * @param data 明文字节数组
   * @return 密文字节数组
   */
  public static byte[] encryptMD5(byte[] data) {
    return hashTemplate(data, "MD5");
  }

  /**
   * MD5加密文件
   *
   * @param filePath 文件路径
   * @return 文件的16进制密文
   */
  public static String encryptMD5File2String(String filePath) {
    File file = StringUtils.isSpace(filePath) ? null : new File(filePath);
    return encryptMD5File2String(file);
  }

  /**
   * MD5加密文件
   *
   * @param filePath 文件路径
   * @return 文件的MD5校验码
   */
  public static byte[] encryptMD5File(String filePath) {
    File file = StringUtils.isSpace(filePath) ? null : new File(filePath);
    return encryptMD5File(file);
  }

  /**
   * MD5加密文件
   *
   * @param file 文件
   * @return 文件的16进制密文
   */
  public static String encryptMD5File2String(File file) {
    return bytes2HexString(encryptMD5File(file));
  }

  /**
   * MD5加密文件
   *
   * @param file 文件
   * @return 文件的MD5校验码
   */
  public static byte[] encryptMD5File(File file) {
    if (file == null) return null;
    FileInputStream fis = null;
    DigestInputStream digestInputStream;
    try {
      fis = new FileInputStream(file);
      MessageDigest md = MessageDigest.getInstance("MD5");
      digestInputStream = new DigestInputStream(fis, md);
      byte[] buffer = new byte[256 * 1024];
      while (digestInputStream.read(buffer) > 0) ;
      md = digestInputStream.getMessageDigest();
      return md.digest();
    } catch (NoSuchAlgorithmException | IOException e) {
      e.printStackTrace();
      return null;
    } finally {
      CloseUtils.closeIO(fis);
    }
  }

  /**
   * SHA1加密
   *
   * @param data 明文字符串
   * @return 16进制密文
   */
  public static String encryptSHA1ToString(String data) {
    return encryptSHA1ToString(data.getBytes());
  }

  /**
   * SHA1加密
   *
   * @param data 明文字节数组
   * @return 16进制密文
   */
  public static String encryptSHA1ToString(byte[] data) {
    return bytes2HexString(encryptSHA1(data));
  }

  /**
   * SHA1加密
   *
   * @param data 明文字节数组
   * @return 密文字节数组
   */
  public static byte[] encryptSHA1(byte[] data) {
    return hashTemplate(data, "SHA1");
  }

  /**
   * SHA224加密
   *
   * @param data 明文字符串
   * @return 16进制密文
   */
  public static String encryptSHA224ToString(String data) {
    return encryptSHA224ToString(data.getBytes());
  }

  /**
   * SHA224加密
   *
   * @param data 明文字节数组
   * @return 16进制密文
   */
  public static String encryptSHA224ToString(byte[] data) {
    return bytes2HexString(encryptSHA224(data));
  }

  /**
   * SHA224加密
   *
   * @param data 明文字节数组
   * @return 密文字节数组
   */
  public static byte[] encryptSHA224(byte[] data) {
    return hashTemplate(data, "SHA224");
  }

  /**
   * SHA256加密
   *
   * @param data 明文字符串
   * @return 16进制密文
   */
  public static String encryptSHA256ToString(String data) {
    return encryptSHA256ToString(data.getBytes());
  }

  /**
   * SHA256加密
   *
   * @param data 明文字节数组
   * @return 16进制密文
   */
  public static String encryptSHA256ToString(byte[] data) {
    return bytes2HexString(encryptSHA256(data));
  }

  /**
   * SHA256加密
   *
   * @param data 明文字节数组
   * @return 密文字节数组
   */
  public static byte[] encryptSHA256(byte[] data) {
    return hashTemplate(data, "SHA256");
  }

  /**
   * SHA384加密
   *
   * @param data 明文字符串
   * @return 16进制密文
   */
  public static String encryptSHA384ToString(String data) {
    return encryptSHA384ToString(data.getBytes());
  }

  /**
   * SHA384加密
   *
   * @param data 明文字节数组
   * @return 16进制密文
   */
  public static String encryptSHA384ToString(byte[] data) {
    return bytes2HexString(encryptSHA384(data));
  }

  /**
   * SHA384加密
   *
   * @param data 明文字节数组
   * @return 密文字节数组
   */
  public static byte[] encryptSHA384(byte[] data) {
    return hashTemplate(data, "SHA384");
  }

  /**
   * SHA512加密
   *
   * @param data 明文字符串
   * @return 16进制密文
   */
  public static String encryptSHA512ToString(String data) {
    return encryptSHA512ToString(data.getBytes());
  }

  /**
   * SHA512加密
   *
   * @param data 明文字节数组
   * @return 16进制密文
   */
  public static String encryptSHA512ToString(byte[] data) {
    return bytes2HexString(encryptSHA512(data));
  }

  /**
   * SHA512加密
   *
   * @param data 明文字节数组
   * @return 密文字节数组
   */
  public static byte[] encryptSHA512(byte[] data) {
    return hashTemplate(data, "SHA512");
  }

  /**
   * hash加密模板
   *
   * @param data 数据
   * @param algorithm 加密算法
   * @return 密文字节数组
   */
  private static byte[] hashTemplate(byte[] data, String algorithm) {
    if (data == null || data.length <= 0) return null;
    try {
      MessageDigest md = MessageDigest.getInstance(algorithm);
      md.update(data);
      return md.digest();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * HmacMD5加密
   *
   * @param data 明文字符串
   * @param key 秘钥
   * @return 16进制密文
   */
  public static String encryptHmacMD5ToString(String data, String key) {
    return encryptHmacMD5ToString(data.getBytes(), key.getBytes());
  }

  /**
   * HmacMD5加密
   *
   * @param data 明文字节数组
   * @param key 秘钥
   * @return 16进制密文
   */
  public static String encryptHmacMD5ToString(byte[] data, byte[] key) {
    return bytes2HexString(encryptHmacMD5(data, key));
  }

  /**
   * HmacMD5加密
   *
   * @param data 明文字节数组
   * @param key 秘钥
   * @return 密文字节数组
   */
  public static byte[] encryptHmacMD5(byte[] data, byte[] key) {
    return hmacTemplate(data, key, "HmacMD5");
  }

  /**
   * HmacSHA1加密
   *
   * @param data 明文字符串
   * @param key 秘钥
   * @return 16进制密文
   */
  public static String encryptHmacSHA1ToString(String data, String key) {
    return encryptHmacSHA1ToString(data.getBytes(), key.getBytes());
  }

  /**
   * HmacSHA1加密
   *
   * @param data 明文字节数组
   * @param key 秘钥
   * @return 16进制密文
   */
  public static String encryptHmacSHA1ToString(byte[] data, byte[] key) {
    return bytes2HexString(encryptHmacSHA1(data, key));
  }

  /**
   * HmacSHA1加密
   *
   * @param data 明文字节数组
   * @param key 秘钥
   * @return 密文字节数组
   */
  public static byte[] encryptHmacSHA1(byte[] data, byte[] key) {
    return hmacTemplate(data, key, "HmacSHA1");
  }

  /**
   * HmacSHA224加密
   *
   * @param data 明文字符串
   * @param key 秘钥
   * @return 16进制密文
   */
  public static String encryptHmacSHA224ToString(String data, String key) {
    return encryptHmacSHA224ToString(data.getBytes(), key.getBytes());
  }

  /**
   * HmacSHA224加密
   *
   * @param data 明文字节数组
   * @param key 秘钥
   * @return 16进制密文
   */
  public static String encryptHmacSHA224ToString(byte[] data, byte[] key) {
    return bytes2HexString(encryptHmacSHA224(data, key));
  }

  /**
   * HmacSHA224加密
   *
   * @param data 明文字节数组
   * @param key 秘钥
   * @return 密文字节数组
   */
  public static byte[] encryptHmacSHA224(byte[] data, byte[] key) {
    return hmacTemplate(data, key, "HmacSHA224");
  }

  /**
   * HmacSHA256加密
   *
   * @param data 明文字符串
   * @param key 秘钥
   * @return 16进制密文
   */
  public static String encryptHmacSHA256ToString(String data, String key) {
    return encryptHmacSHA256ToString(data.getBytes(), key.getBytes());
  }

  /**
   * HmacSHA256加密
   *
   * @param data 明文字节数组
   * @param key 秘钥
   * @return 16进制密文
   */
  public static String encryptHmacSHA256ToString(byte[] data, byte[] key) {
    return bytes2HexString(encryptHmacSHA256(data, key));
  }

  /**
   * HmacSHA256加密
   *
   * @param data 明文字节数组
   * @param key 秘钥
   * @return 密文字节数组
   */
  public static byte[] encryptHmacSHA256(byte[] data, byte[] key) {
    return hmacTemplate(data, key, "HmacSHA256");
  }

  /**
   * HmacSHA384加密
   *
   * @param data 明文字符串
   * @param key 秘钥
   * @return 16进制密文
   */
  public static String encryptHmacSHA384ToString(String data, String key) {
    return encryptHmacSHA384ToString(data.getBytes(), key.getBytes());
  }

  /**
   * HmacSHA384加密
   *
   * @param data 明文字节数组
   * @param key 秘钥
   * @return 16进制密文
   */
  public static String encryptHmacSHA384ToString(byte[] data, byte[] key) {
    return bytes2HexString(encryptHmacSHA384(data, key));
  }

  /**
   * HmacSHA384加密
   *
   * @param data 明文字节数组
   * @param key 秘钥
   * @return 密文字节数组
   */
  public static byte[] encryptHmacSHA384(byte[] data, byte[] key) {
    return hmacTemplate(data, key, "HmacSHA384");
  }

  /**
   * HmacSHA512加密
   *
   * @param data 明文字符串
   * @param key 秘钥
   * @return 16进制密文
   */
  public static String encryptHmacSHA512ToString(String data, String key) {
    return encryptHmacSHA512ToString(data.getBytes(), key.getBytes());
  }

  /**
   * HmacSHA512加密
   *
   * @param data 明文字节数组
   * @param key 秘钥
   * @return 16进制密文
   */
  public static String encryptHmacSHA512ToString(byte[] data, byte[] key) {
    return bytes2HexString(encryptHmacSHA512(data, key));
  }

  /**
   * HmacSHA512加密
   *
   * @param data 明文字节数组
   * @param key 秘钥
   * @return 密文字节数组
   */
  public static byte[] encryptHmacSHA512(byte[] data, byte[] key) {
    return hmacTemplate(data, key, "HmacSHA512");
  }

  /**
   * Hmac加密模板
   *
   * @param data 数据
   * @param key 秘钥
   * @param algorithm 加密算法
   * @return 密文字节数组
   */
  private static byte[] hmacTemplate(byte[] data, byte[] key, String algorithm) {
    if (data == null || data.length == 0 || key == null || key.length == 0) return null;
    try {
      SecretKeySpec secretKey = new SecretKeySpec(key, algorithm);
      Mac mac = Mac.getInstance(algorithm);
      mac.init(secretKey);
      return mac.doFinal(data);
    } catch (InvalidKeyException | NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
  }

  /************************ DES加密相关 ***********************/
  /**
   * DES转变
   * <p>法算法名称/加密模式/填充方式</p>
   * <p>加密模式有：电子密码本模式ECB、加密块链模式CBC、加密反馈模式CFB、输出反馈模式OFB</p>
   * <p>填充方式有：NoPadding、ZerosPadding、PKCS5Padding</p>
   */
  public static String DES_Transformation = "DES/ECB/NoPadding";
  private static final String DES_Algorithm = "DES";

  /**
   * DES加密后转为Base64编码
   *
   * @param data 明文
   * @param key 8字节秘钥
   * @return Base64密文
   */
  public static byte[] encryptDES2Base64(byte[] data, byte[] key) {
    return EncodeUtils.base64Encode(encryptDES(data, key));
  }

  /**
   * DES加密后转为16进制
   *
   * @param data 明文
   * @param key 8字节秘钥
   * @return 16进制密文
   */
  public static String encryptDES2HexString(byte[] data, byte[] key) {
    return bytes2HexString(encryptDES(data, key));
  }

  /**
   * DES加密
   *
   * @param data 明文
   * @param key 8字节秘钥
   * @return 密文
   */
  public static byte[] encryptDES(byte[] data, byte[] key) {
    return desTemplate(data, key, DES_Algorithm, DES_Transformation, true);
  }

  /**
   * DES解密Base64编码密文
   *
   * @param data Base64编码密文
   * @param key 8字节秘钥
   * @return 明文
   */
  public static byte[] decryptBase64DES(byte[] data, byte[] key) {
    return decryptDES(EncodeUtils.base64Decode(data), key);
  }

  /**
   * DES解密16进制密文
   *
   * @param data 16进制密文
   * @param key 8字节秘钥
   * @return 明文
   */
  public static byte[] decryptHexStringDES(String data, byte[] key) {
    return decryptDES(hexString2Bytes(data), key);
  }

  /**
   * DES解密
   *
   * @param data 密文
   * @param key 8字节秘钥
   * @return 明文
   */
  public static byte[] decryptDES(byte[] data, byte[] key) {
    return desTemplate(data, key, DES_Algorithm, DES_Transformation, false);
  }

  /************************ 3DES加密相关 ***********************/
  /**
   * 3DES转变
   * <p>法算法名称/加密模式/填充方式</p>
   * <p>加密模式有：电子密码本模式ECB、加密块链模式CBC、加密反馈模式CFB、输出反馈模式OFB</p>
   * <p>填充方式有：NoPadding、ZerosPadding、PKCS5Padding</p>
   */
  public static String TripleDES_Transformation = "DESede/ECB/NoPadding";
  private static final String TripleDES_Algorithm = "DESede";

  /**
   * 3DES加密后转为Base64编码
   *
   * @param data 明文
   * @param key 24字节秘钥
   * @return Base64密文
   */
  public static byte[] encrypt3DES2Base64(byte[] data, byte[] key) {
    return EncodeUtils.base64Encode(encrypt3DES(data, key));
  }

  /**
   * 3DES加密后转为16进制
   *
   * @param data 明文
   * @param key 24字节秘钥
   * @return 16进制密文
   */
  public static String encrypt3DES2HexString(byte[] data, byte[] key) {
    return bytes2HexString(encrypt3DES(data, key));
  }

  /**
   * 3DES加密
   *
   * @param data 明文
   * @param key 24字节密钥
   * @return 密文
   */
  public static byte[] encrypt3DES(byte[] data, byte[] key) {
    return desTemplate(data, key, TripleDES_Algorithm, TripleDES_Transformation, true);
  }

  /**
   * 3DES解密Base64编码密文
   *
   * @param data Base64编码密文
   * @param key 24字节秘钥
   * @return 明文
   */
  public static byte[] decryptBase64_3DES(byte[] data, byte[] key) {
    return decrypt3DES(EncodeUtils.base64Decode(data), key);
  }

  /**
   * 3DES解密16进制密文
   *
   * @param data 16进制密文
   * @param key 24字节秘钥
   * @return 明文
   */
  public static byte[] decryptHexString3DES(String data, byte[] key) {
    return decrypt3DES(hexString2Bytes(data), key);
  }

  /**
   * 3DES解密
   *
   * @param data 密文
   * @param key 24字节密钥
   * @return 明文
   */
  public static byte[] decrypt3DES(byte[] data, byte[] key) {
    return desTemplate(data, key, TripleDES_Algorithm, TripleDES_Transformation, false);
  }

  /************************ AES加密相关 ***********************/
  /**
   * AES转变
   * <p>法算法名称/加密模式/填充方式</p>
   * <p>加密模式有：电子密码本模式ECB、加密块链模式CBC、加密反馈模式CFB、输出反馈模式OFB</p>
   * <p>填充方式有：NoPadding、ZerosPadding、PKCS5Padding</p>
   */
  private static final String AES_Transformation = "AES/CBC/PKCS5Padding";
  private static final String AES_Algorithm = "AES";

  public static String encryptAES2Base64(String data, String key) {
    return EncodeUtils.base64Encode2String(encryptAES(data.getBytes(), key.getBytes()));
  }

  /**
   * AES加密后转为Base64编码
   *
   * @param data 明文
   * @param key 16、24、32字节秘钥
   * @return Base64密文
   */
  public static byte[] encryptAES2Base64(byte[] data, byte[] key) {
    return EncodeUtils.base64Encode(encryptAES(data, key));
  }

  /**
   * AES加密后转为16进制
   *
   * @param data 明文
   * @param key 16、24、32字节秘钥
   * @return 16进制密文
   */
  public static String encryptAES2HexString(byte[] data, byte[] key) {
    return bytes2HexString(encryptAES(data, key));
  }

  public static String decryptBase64AES(String data, String key) {
    return new String(decryptBase64AES(data.getBytes(), key.getBytes()));
  }

  /**
   * AES解密Base64编码密文
   *
   * @param data Base64编码密文
   * @param key 16、24、32字节秘钥
   * @return 明文
   */
  public static byte[] decryptBase64AES(byte[] data, byte[] key) {
    return decryptAES(EncodeUtils.base64Decode(data), key);
  }

  /**
   * AES解密16进制密文
   *
   * @param data 16进制密文
   * @param key 16、24、32字节秘钥
   * @return 明文
   */
  public static byte[] decryptHexStringAES(String data, byte[] key) {
    return decryptAES(hexString2Bytes(data), key);
  }

  /**
   * DES加密模板
   *
   * @param data 数据
   * @param key 秘钥
   * @param algorithm 加密算法
   * @param transformation 转变
   * @param isEncrypt {@code true}: 加密 {@code false}: 解密
   * @return 密文或者明文，适用于DES，3DES，AES
   */
  public static byte[] desTemplate(byte[] data, byte[] key, String algorithm, String transformation,
      boolean isEncrypt) {
    if (data == null || data.length == 0 || key == null || key.length == 0) return null;
    try {
      SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
      Cipher cipher = Cipher.getInstance(transformation);
      SecureRandom random = new SecureRandom();
      cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, random);
      return cipher.doFinal(data);
    } catch (Throwable e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * AES加密
   *
   * @param data 明文
   * @param key 16、24、32字节秘钥
   * @return 密文
   */
  public static byte[] encryptAES(byte[] data, byte[] key) {
    return aesTemplate(data, key, AES_Transformation, Cipher.ENCRYPT_MODE);
  }

  /**
   * AES解密
   *
   * @param data 密文
   * @param key 16、24、32字节秘钥
   * @return 明文
   */
  public static byte[] decryptAES(byte[] data, byte[] key) {
    return aesTemplate(data, key, AES_Transformation, Cipher.DECRYPT_MODE);
  }

  private static byte[] AES_IV = new byte[] {
      0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f
  };

  private static byte[] aesTemplate(byte[] data, byte[] password, String transformation, int mode) {
    try {
      // 对密钥进行处理-E
      IvParameterSpec zeroIv = new IvParameterSpec(password);
      SecretKeySpec key = new SecretKeySpec(password, AES_Algorithm);
      Cipher cipher = Cipher.getInstance(transformation);
      cipher.init(mode, key, zeroIv);
      return cipher.doFinal(data);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static final int ENCRYPT = 0;
  public static final int DECRYPT = 1;

  public static String encryptBase64AESNative(String data, String key) {
    byte[] encrypt = crypt(data.getBytes(), key.getBytes(), System.currentTimeMillis(), ENCRYPT);
    if (encrypt != null) {
      return EncodeUtils.base64Encode2String(encrypt);
    }
    return "";
  }

  public static String decryptBase64AESNative(String data, String key) {
    byte[] decrypt =
        crypt(EncodeUtils.base64Decode(data), key.getBytes(), System.currentTimeMillis(), DECRYPT);
    if (decrypt != null) {
      return new String(decrypt);
    }
    return "";
  }

  /**
   * 对数据进行AES加解密
   *
   * @param data 明文/密文
   * @param key 密钥
   * @param time 时间(暂时未使用）
   * @param mode {@value ENCRYPT} : 加密  {@value DECRYPT} : 解密
   * @return 密文/明文
   */
  public native static byte[] crypt(byte[] data, byte[] key, long time, int mode);

  /**
   *
   * @param path
   * @param time
   * @return
   */
  public native static byte[] read(String path, long time);

  /************************ RSA加密相关 ***********************/
  /**
   * RSA转变
   * <p>法算法名称/加密模式/填充方式</p>
   * <p>加密模式有：电子密码本模式ECB、加密块链模式CBC、加密反馈模式CFB、输出反馈模式OFB</p>
   * <p>填充方式有：NoPadding、ZerosPadding、PKCS5Padding</p>
   */
  private static final String RSA_Transformation = "RSA/ECB/PKCS1Padding";
  private static final String RSA_Algorithm = "RSA";

  /**
   * RSA公钥加密
   * RSA/ECB/PKCS1Padding
   *
   * @param data 明文
   * @param key 公钥
   * @return 密文
   */
  public static String encryptBase64RSAByPublicKey(String data, String key) {
    return EncodeUtils.base64Encode2String(
        rsaTemplate(data.getBytes(), EncodeUtils.base64Decode(key), RSA_Algorithm,
            RSA_Transformation, true));
  }

  /**
   * RSA公钥解密
   */
  public static String decryptBase64RSAByPublicKey(String data, String key) {
    return EncodeUtils.base64Encode2String(
        rsaTemplate(EncodeUtils.base64Decode(data), EncodeUtils.base64Decode(key), RSA_Algorithm,
            RSA_Transformation, false));
  }

  public static String decryptBase64RSAByPrivateKey(String data, String key) {
    if (TextUtils.isEmpty(data)) {
      return "";
    }

    try {
      byte[] keyBytes = EncodeUtils.base64Decode(key);
      PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
      KeyFactory keyFactory = KeyFactory.getInstance(RSA_Algorithm);
      Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
      Cipher cipher = Cipher.getInstance(RSA_Transformation);
      cipher.init(Cipher.DECRYPT_MODE, privateK);
      return new String(cipher.doFinal(EncodeUtils.base64Decode(data)));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static byte[] rsaTemplate(byte[] data, byte[] key, String algorithm, String transformation,
      boolean isEncrypt) {
    X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(key);
    try {
      KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
      Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
      Cipher cipher = Cipher.getInstance(transformation);
      cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, publicKey);
      return cipher.doFinal(data);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    } catch (InvalidKeySpecException e) {
      throw new RuntimeException(e);
    } catch (NoSuchPaddingException e) {
      throw new RuntimeException(e);
    } catch (InvalidKeyException e) {
      throw new RuntimeException(e);
    } catch (IllegalBlockSizeException e) {
      throw new RuntimeException(e);
    } catch (BadPaddingException e) {
      throw new RuntimeException(e);
    }
  }
}