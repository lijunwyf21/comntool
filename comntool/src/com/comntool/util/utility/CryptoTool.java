package com.comntool.util.utility;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.comntool.constcla.comn.ComnConst;

public class CryptoTool {

  private static Logger log = LoggerFactory.getLogger(CryptoTool.class);


  public static final String KEY_ALGORITHM_AES = "AES";
  public static final String ALGORITHM_AES = "AES/CBC/PKCS5Padding";
  public static final String KEY_ALGORITHM_DES = "DES";
  public static final String ALGORITHM_DES = "DES/CBC/PKCS5PADDING";
  public static final String KEY_ALGORITHM_3DES = "DESede";
  public static final String ALGORITHM_3DES = "DESede/CBC/PKCS5PADDING";

  public static final String KEY_ALGORITHM_RSA = "RSA";
  public static final String ALGORITHM_RSA = "RSA/ECB/PKCS1Padding";
  public static final String RSA_SIGNATURE_ALGORITHM = "MD5withRSA";
  public static final String RSA_PUBLIC_KEY = "RSAPublicKey";
  public static final String RSA_PRIVATE_KEY = "RSAPrivateKey";

  /**
   * 将字节数组加密成base64字符串
   *
   * @param src 明文的字节数组
   * @return 经过base64计算后的base64格式的字符串
   */
  public static String base64Encode(byte[] src) {
    byte[] encodeBase64 = Base64.encodeBase64(src);
    return new String(encodeBase64);
  }

  /**
   * 将字符串转换成字节数组加密成base64字符串
   *
   * @param src 明文的字节数组
   * @return 经过base64计算后的base64格式的字符串
   */
  public static String base64Encode(String src) {
    try {
      byte[] encodeBase64 = Base64.encodeBase64(src.getBytes(StandardCharsets.UTF_8));
      return new String(encodeBase64);
    } catch (Exception e) {
      log.info(e.getMessage(), e);
    }
    return null;
  }

  /**
   * 将base64字符串解密成字节数组
   *
   * @param base64 base64格式的字符串
   * @return 解密后的字节数组
   */
  public static byte[] base64Decode(String base64) {
    try {
      byte[] strByte = Base64.decodeBase64(base64.getBytes(StandardCharsets.UTF_8));
      return strByte;
    } catch (Exception e) {
      log.info(e.getMessage(), e);
    }
    return null;
  }


  /**
   * 将base64字符串解密成字节数组，再转成字符串
   *
   * @param base64 base64格式的字符串
   * @return 解密后的字节数组
   */
  public static String base64Decode2str(String base64) {
    try {
      byte[] strByte = Base64.decodeBase64(base64.getBytes(StandardCharsets.UTF_8));
      return new String(strByte, StandardCharsets.UTF_8);
    } catch (Exception e) {
      log.info(e.getMessage(), e);
    }
    return null;
  }

  /**
   * 将base64字符串解密成字符串 先将base64字符串解密成字节数组，再用UTF-8编码成字符串
   *
   * @param base64 base64格式的字符串
   * @return 解密后的字符串
   */
  public static String decodeToString(String base64) {
    return new String(base64Decode(base64), StandardCharsets.UTF_8);
  }

  /**
   * 将字符串加密成base64字符串 先将字符串用UTF-8解码成字节数组， 再加密成base64字符串
   *
   * @param plaintext base64格式的字符串
   * @return 解密后的字符串
   */
  public static String encodeString(String plaintext) {
    return base64Encode(plaintext.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * 将二进制转换成16进制
   *
   * @param buf 字节数组
   * @return 16进制字符串
   */
  public static String parseByte2HexStr(byte[] buf) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < buf.length; i++) {
      String hex = Integer.toHexString(buf[i] & 0xFF);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }
      sb.append(hex.toUpperCase());
    }
    return sb.toString();
  }

  /**
   * 将16进制转换为二进制
   *
   * @param hexStr 16进制字符串
   */
  public static byte[] parseHexStr2Byte(String hexStr) {
    if (hexStr.length() < 1) {
      return null;
    }
    byte[] result = new byte[hexStr.length() / 2];
    for (int i = 0; i < hexStr.length() / 2; i++) {
      int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
      int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
          16);
      result[i] = (byte) (high * 16 + low);
    }
    return result;
  }

  public static String base64hex(String base64) {
    try {
      byte[] b1 = base64Decode(base64);
      String hex = parseByte2HexStr(b1);
      return hex;
    } catch (Exception e) {
      log.info(e.getMessage(), e);
    }
    return null;
  }

  public static String hex2base64(String hex) {
    try {
      byte[] b1 = parseHexStr2Byte(hex);
      String base64 = base64Encode(b1);
      return base64;
    } catch (Exception e) {
      log.info(e.getMessage(), e);
    }
    return null;
  }

  // map对象中存放公私钥
  public static Map<String, Object> initKey() throws Exception {
    //获得对象 KeyPairGenerator 参数 RSA 1024个字节
    KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM_RSA);
    keyPairGen.initialize(1024);
    //通过对象 KeyPairGenerator 获取对象KeyPair
    KeyPair keyPair = keyPairGen.generateKeyPair();

    //通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    //公私钥对象存入map中
    Map<String, Object> keyMap = new HashMap<String, Object>(2);
    keyMap.put(RSA_PUBLIC_KEY, publicKey);
    keyMap.put(RSA_PRIVATE_KEY, privateKey);
    return keyMap;
  }

  /**
   * 从PKCS8格式的字符串中加载公钥 通过PKCS1格式的私钥导出PKCS8的公钥： openssl rsa -in rsa_pkcs1_private_key.pem -pubout
   * -out rsa_public_key.pem
   *
   * @param base64pub base64格式的公钥数据字符串
   * @throws Exception 加载公钥时产生的异常
   */
  public static PublicKey loadPublicKey(String base64pub) {
    try {
      byte[] buffer = base64Decode(base64pub);
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
      PublicKey publicKey = keyFactory.generatePublic(keySpec);
      return publicKey;
    } catch (NoSuchAlgorithmException e) {
      log.info("无此算法");
    } catch (InvalidKeySpecException e) {
      log.info("公钥非法");
    } catch (NullPointerException e) {
      log.info("公钥数据为空");
    } catch (Exception e) {
      log.info(e.getMessage(), e);
    }
    return null;
  }

  /**
   * PKCS8格式的字符串中加载私钥
   *
   * @param base64 base64格式的私钥数据字符串
   */
  public static PrivateKey loadPrivateKey(String base64) {
    try {
      byte[] buffer = base64Decode(base64);
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
      PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
      return privateKey;
    } catch (NoSuchAlgorithmException e) {
      log.info("无此算法");
    } catch (InvalidKeySpecException e) {
      log.info("私钥非法", e);
    } catch (NullPointerException e) {
      log.info("私钥数据为空");
    }
    return null;
  }

  /**
   * 计算MD5
   *
   * @return 小写的16进制的md5值
   */
  public static String md5(byte[] plaintextByte) {
    try {
      MessageDigest mdInst = MessageDigest.getInstance("MD5");
      // 使用指定的字节更新摘要
      mdInst.update(plaintextByte);
      // 获得密文
      byte[] md = mdInst.digest();
      String ciphertext = Hex.encodeHexString(md);
      return ciphertext;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }


  /**
   * 计算MD5
   */
  public static String md5(String str) {
    try {
      return md5(str.getBytes(ComnConst.CHARCODE));
    } catch (Exception e) {
      log.info(e.getMessage(), e);
    }
    return null;

  }

  /**
   * 将Map中的数据按照字典顺序拼成a=123&b=456&的形式。删除签名
   */
  public static <T> String map2signStr(Map<String, T> map, String signStrKey) {
    map.remove(signStrKey);
    return map2signStr(map);
  }

  /**
   * 将Map中的数据按照字典顺序拼成a=123&b=456&的形式 空的key或者value将被忽略
   */
  public static <T> String map2signStr(Map<String, T> map) {
    if (MapUtils.isEmpty(map)) {
      return null;
    }
    TreeMap<String, T> mapTree = map instanceof TreeMap ? (TreeMap) map : new TreeMap<>(map);
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, T> enty : mapTree.entrySet()) {
      if (StringUtils.isBlank(enty.getKey()) || enty.getValue() == null
          || StringUtils.isBlank(enty.getValue().toString())) {
        continue;
      }
      sb.append(enty.getKey()).append("=").append(enty.getValue()).append("&");
    }
    return sb.toString();
  }


  /**
   * SHA256withRSA算法的签名
   *
   * @param plaintext 明文。会被转成UTF-8对应的字节数组
   * @param base64privKey PKCS8格式的base64私钥
   * @return 签名后的小写16进制字符串
   */
  // 参考： https://hutool.cn/docs/#/crypto/%E7%AD%BE%E5%90%8D%E5%92%8C%E9%AA%8C%E8%AF%81-Sign?id=%E4%BD%BF%E7%94%A8
  public static String sign(String algo, String plaintext, String base64privKey) {
    if (StringUtils.isBlank(plaintext)) {
      return null;
    }
    byte[] privByte = Base64.decodeBase64(base64privKey);
    Sign sign = SecureUtil.sign(getSignAlgorithm(algo), privByte, null);
    try {
      byte[] signPlaintextByte = plaintext.trim().getBytes(ComnConst.CHARCODE);
      byte[] signByte = sign.sign(signPlaintextByte);
      String signStr = Hex.encodeHexString(signByte).toLowerCase();
      return signStr;
    } catch (Exception e) {

    }
    return null;
  }


  /**
   * SHA256withRSA算法的签名
   *
   * @param plaintext 明文。会被转成
   * @param signStr16 对方传过来的，签名后的16进制字符串，用于验签比对
   * @param base64pubKey PKCS8格式的base64公钥
   */
  // 参考： https://hutool.cn/docs/#/crypto/%E7%AD%BE%E5%90%8D%E5%92%8C%E9%AA%8C%E8%AF%81-Sign?id=%E4%BD%BF%E7%94%A8
  public static boolean verify(String algo, String plaintext, String signStr16,
      String base64pubKey) {
    if (StringUtils.isBlank(plaintext)) {
      return false;
    }
    byte[] pubByte = Base64.decodeBase64(base64pubKey);
    Sign sign = SecureUtil.sign(getSignAlgorithm(algo), null, pubByte);
    try {
      byte[] signPlaintextByte = plaintext.trim().getBytes(ComnConst.CHARCODE);
      byte[] signByte = Hex.decodeHex(signStr16.toCharArray());
      boolean verifyResult = sign.verify(signPlaintextByte, signByte);
      if (verifyResult == Boolean.FALSE) {
        log.info("plaintext={}, plaintext={}", plaintext, verifyResult);
      }
      return verifyResult;
    } catch (Exception e) {

    }
    return false;
  }
  
  public static SignAlgorithm getSignAlgorithm(String val) {
      for (SignAlgorithm SignAlgorithm : SignAlgorithm.values()) {
          if (StringUtils.equals(val, SignAlgorithm.getValue())) {
              return SignAlgorithm;
          }
      }
      return null;
  }
  
  /**
   * SHA256withRSA算法的签名
   *
   * @param plaintext 明文。会被转成UTF-8对应的字节数组
   * @param base64privKey PKCS8格式的base64私钥
   * @return 签名后的小写16进制字符串
   */
  // 参考： https://hutool.cn/docs/#/crypto/%E7%AD%BE%E5%90%8D%E5%92%8C%E9%AA%8C%E8%AF%81-Sign?id=%E4%BD%BF%E7%94%A8
  public static String signSHA256withRSA16(String plaintext, String base64privKey) {
    if (StringUtils.isBlank(plaintext)) {
      return null;
    }
    byte[] privByte = Base64.decodeBase64(base64privKey);
    Sign sign = SecureUtil.sign(SignAlgorithm.SHA256withRSA, privByte, null);
    try {
      byte[] signPlaintextByte = plaintext.trim().getBytes(ComnConst.CHARCODE);
      byte[] signByte = sign.sign(signPlaintextByte);
      String signStr = Hex.encodeHexString(signByte).toLowerCase();
      return signStr;
    } catch (Exception e) {

    }
    return null;
  }


  /**
   * SHA256withRSA算法的签名
   *
   * @param plaintext 明文。会被转成
   * @param signStr16 对方传过来的，签名后的16进制字符串，用于验签比对
   * @param base64pubKey PKCS8格式的base64公钥
   */
  // 参考： https://hutool.cn/docs/#/crypto/%E7%AD%BE%E5%90%8D%E5%92%8C%E9%AA%8C%E8%AF%81-Sign?id=%E4%BD%BF%E7%94%A8
  public static boolean verifySHA256withRSA16(String plaintext, String signStr16,
      String base64pubKey) {
    if (StringUtils.isBlank(plaintext)) {
      return false;
    }
    byte[] pubByte = Base64.decodeBase64(base64pubKey);
    Sign sign = SecureUtil.sign(SignAlgorithm.SHA256withRSA, null, pubByte);
    try {
      byte[] signPlaintextByte = plaintext.trim().getBytes(ComnConst.CHARCODE);
      byte[] signByte = Hex.decodeHex(signStr16.toCharArray());
      boolean verifyResult = sign.verify(signPlaintextByte, signByte);
      if (verifyResult == Boolean.FALSE) {
        log.info("plaintext={}, plaintext={}", plaintext, verifyResult);
      }
      return verifyResult;
    } catch (Exception e) {

    }
    return false;
  }

  /**
   * 16进制转成long类型
   *
   * @author lijun
   */
  public static long hex2long(String hex) {
    if (StringUtils.isBlank(hex)) {
      return 0;
    }
    byte[] bys = parseHexStr2Byte(hex);
    long lng = 0;
    for (byte by : bys) {
      lng = (lng << 8) | (by & 0xff);
    }
    return lng;
  }


  public static void main(String[] args) {
    test_rsa1();

  }

  public static void test_rsa1() {

//    String privOT = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMl9XolH2Mg3BEo1RlbXOLT86i4bTDwsMWe1+pSuI3cS1HdJ+PUD9qFyYpmu2o42UgBFInnk8+Kvrf8lhUwyGwHIhJxIDFTwTOWD7xrt4+ozynn24wIRvqBQoO80q5uDuBKxSCB8SEt7AfICmRFJXWvCrLr0VYvmXaN059POPK+VAgMBAAECgYBJb6YbBtKd9l9b5JrkX8DNRjjb4Rsh+0FueUcBFY7bgUNqzs0sD+u94ADL3ozNtEKi0o/EOYwiHMGf8r7ojIKGuE4vS/70pPnxvkI6ZmAGK2J3nGt36p5Nyj/2dBS8MHYgAbTq/7BL4TzCkWhdzwoQ4ERMiQwrQ+nLW9vgZxHkaQJBAOQ9gVWqD65+fFjdk6j0kbMgw35yNThEA2OAAKt/hEZqLAPNm2LGclmknBJmJUmG+dnhYe/q70PZmJhRgJigGYsCQQDh/vV0bZ/SQF+xFNKFiTOOAp13s0RZfMCLqmjHr5IzOfYc3f4uKCWNOCtSymQbXJvExUtevJOWm6D/Rwia3j9fAkEAsDeIbsJRPzDrApV4lt7Uyw9FwI+e5WUllKHUYpSeZNs+RmVtTfLXgLylv1LcAQvURdNMkTa7KpCKzzdF6RDbBwJBANTuF+5e61p59K8v4zqWDtEafasuaJO4CuKM6LRcI+/ICK8iKj7q7Jal+YvDzZZKJym6ikz0eEpKz+I111GzXRkCQH2jesqJHXWNlFnvZ4Ped81basUEvT2oatJgBN/iB7i8gJ8CsfZF7LWCku5Mz8oSNfGx1WjtVgXJ54eGPLmM0Qk=";
    String privOT = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMl9XolH2Mg3BEo1RlbXOLT86i4bTDwsMWe1+pSuI3cS1HdJ+PUD9qFyYpmu2o42UgBFInnk8+Kvrf8lhUwyGwHIhJxIDFTwTOWD7xrt4+ozynn24wIRvqBQoO80q5uDuBKxSCB8SEt7AfICmRFJXWvCrLr0VYvmXaN059POPK+VAgMBAAECgYBJb6YbBtKd9l9b5JrkX8DNRjjb4Rsh+0FueUcBFY7bgUNqzs0sD+u94ADL3ozNtEKi0o/EOYwiHMGf8r7ojIKGuE4vS/70pPnxvkI6ZmAGK2J3nGt36p5Nyj/2dBS8MHYgAbTq/7BL4TzCkWhdzwoQ4ERMiQwrQ+nLW9vgZxHkaQJBAOQ9gVWqD65+fFjdk6j0kbMgw35yNThEA2OAAKt/hEZqLAPNm2LGclmknBJmJUmG+dnhYe/q70PZmJhRgJigGYsCQQDh/vV0bZ/SQF+xFNKFiTOOAp13s0RZfMCLqmjHr5IzOfYc3f4uKCWNOCtSymQbXJvExUtevJOWm6D/Rwia3j9fAkEAsDeIbsJRPzDrApV4lt7Uyw9FwI+e5WUllKHUYpSeZNs+RmVtTfLXgLylv1LcAQvURdNMkTa7KpCKzzdF6RDbBwJBANTuF+5e61p59K8v4zqWDtEafasuaJO4CuKM6LRcI+/ICK8iKj7q7Jal+YvDzZZKJym6ikz0eEpKz+I111GzXRkCQH2jesqJHXWNlFnvZ4Ped81basUEvT2oatJgBN/iB7i8gJ8CsfZF7LWCku5Mz8oSNfGx1WjtVgXJ54eGPLmM0Qk=+pSuI3cS1HdJ+PUD9qFyYpmu2o42UgBFInnk8+Kvrf8lhUwyGwHIhJxIDFTwTOWD7xrt4+ozynn24wIRvqBQoO80q5uDuBKxSCB8SEt7AfICmRFJXWvCrLr0VYvmXaN059POPK+VAgMBAAECgYBJb6YbBtKd9l9b5JrkX8DNRjjb4Rsh+0FueUcBFY7bgUNqzs0sD+u94ADL3ozNtEKi0o/EOYwiHMGf8r7ojIKGuE4vS/70pPnxvkI6ZmAGK2J3nGt36p5Nyj/2dBS8MHYgAbTq/7BL4TzCkWhdzwoQ4ERMiQwrQ+nLW9vgZxHkaQJBAOQ9gVWqD65+fFjdk6j0kbMgw35yNThEA2OAAKt/hEZqLAPNm2LGclmknBJmJUmG+dnhYe/q70PZmJhRgJigGYsCQQDh/vV0bZ/SQF+xFNKFiTOOAp13s0RZfMCLqmjHr5IzOfYc3f4uKCWNOCtSymQbXJvExUtevJOWm6D/Rwia3j9fAkEAsDeIbsJRPzDrApV4lt7Uyw9FwI+e5WUllKHUYpSeZNs+RmVtTfLXgLylv1LcAQvURdNMkTa7KpCKzzdF6RDbBwJBANTuF+5e61p59K8v4zqWDtEafasuaJO4CuKM6LRcI+/ICK8iKj7q7Jal+YvDzZZKJym6ikz0eEpKz+I111GzXRkCQH2jesqJHXWNlFnvZ4Ped81basUEvT2oatJgBN/iB7i8gJ8CsfZF7LWCku5Mz8oSNfGx1WjtVgXJ54eGPLmM0Qk=";
    String pubOT = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDJfV6JR9jINwRKNUZW1zi0/OouG0w8LDFntfqUriN3EtR3Sfj1A/ahcmKZrtqONlIARSJ55PPir63/JYVMMhsByIScSAxU8Ezlg+8a7ePqM8p59uMCEb6gUKDvNKubg7gSsUggfEhLewHyApkRSV1rwqy69FWL5l2jdOfTzjyvlQIDAQAB";
    String pubDAE = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDLrrLP9NW8YVqVU8vofKUl5yAOOQ1zL5/l21I6IH4TnVYbnahKzPYz7etC6NZzz0b+CRaCg+wd4nW+Nz4xivuW/Vonf7IUHSGxFIxGiIyBUGQG+AwZOByhPFAVdT4y83qVD9+TUYrsUFl/evfOuOZaX4+7/iz2qmxQzgODrqXtmwIDAQAB";

    String aa = "endDay=20190511&exSort=future&merchantId=979df1228b7244f6a4f9ece084757d22&merchantUserId=979df1228b7244f6a4f9ece084757d22&reqSeq=07194a30936ad7d298cc74b9c2cd6e4b&startDay=20190511&transTimestamp=1557991639112&";
    String rsaSign16 = signSHA256withRSA16(aa, privOT);
    log.info("rsaSign16={}, verify={}", rsaSign16, verifySHA256withRSA16(aa, rsaSign16, pubOT));

  }

  public static void test_rsa2() {
    String priv = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMLpc3ccPQ57e4gJkpHo6yOZoNcAlg6GM0lN3Z1r46nnO8FhEPi0tr0X1PZOO5xlUYAtxpby2tyGq0osEAxG0Gq+1dxdv4C0Z+iqTG8XfxACXz8rbTyYyKKMMuAjFt2tIDUhM8XhMqU6WCTcVGVEP9wrahH/h3lsDAafw7SfWAGjAgMBAAECgYEAqKRUwzormdw9hfbCWnys9qB2CZD4RJjYxxQYK5yt3tOzYB4cERRLCFEWcgY+jZMccUsqCHEX7LaYfhShzf13VWHhc1Y76PprqzP6OqGfkNQ3V2VWh3P7RIevSczp0K20kjxfnktMGT0NfNchoK/Qo87P3DFRkcW7Cg+qrP8A1AECQQDpKpi4hapv45VYu7iYRAibq4VDZFim6TDUieBpYAHYEwLRTcpPYvlQ1VAo5Pe78tqMsqUr6/if2cpNx7phdL11AkEA1f/T3REZolQxFLgWTIxILb6CTPTAW5y7rChXlH8/su7bsseGEQq9NQnLACfO032/mZI/Kz0BjywYu391uDDntwJAHA2Y9FJ4sB+Un2luiztcQcaAMRyIogRacKfqDSOU9TdMVnxig+ynjHctvs3VlJJigx0XTFKGxkzAz1zhaspN5QJBAMp+371p9vWx8ReH8iHRBGO0x5uGZZbKwpNvQSBVILNybhXH00bBALT1ZU/qWz2o2eq5hmilu5n8whJ506zNKhUCQGD4hvtCRM4a1Ig9Zm4+YWkrEs0LVw24ndPSRGEqDui821mxEaMjyeG39/67Pw9tZn5V+K4/iLEM1EePFZdwPlI=";
    String pub = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDC6XN3HD0Oe3uICZKR6OsjmaDXAJYOhjNJTd2da+Op5zvBYRD4tLa9F9T2TjucZVGALcaW8trchqtKLBAMRtBqvtXcXb+AtGfoqkxvF38QAl8/K208mMiijDLgIxbdrSA1ITPF4TKlOlgk3FRlRD/cK2oR/4d5bAwGn8O0n1gBowIDAQAB";
    String aa = "aaa=bbb";
    String rsaSign16 = signSHA256withRSA16(aa, priv);
    log.info("rsaSign16={}, verify={}", rsaSign16, verifySHA256withRSA16(aa, rsaSign16, pub));
    log.info("rsaSign16={}, verify={}", rsaSign16,
        verifySHA256withRSA16(aa, rsaSign16 + "12", pub));
  }
}
