package com.qs.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * AES加解密
 *
 * Created by fbin on 2018/6/7.
 */
public class AESCoder {

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//默认的加密算法
    private static final int KEY_LENGTH = 128;

    public static String initSecretKey() {
        //返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
        //初始化此密钥生成器，使其具有确定的密钥大小
        //AES 要求密钥长度为 128
        kg.init(KEY_LENGTH);
        //生成一个密钥
        SecretKey secretKey = kg.generateKey();
        return Base64.encodeBase64String(secretKey.getEncoded());
    }

    private static Key toKey(byte[] key){
        //生成密钥
        return new SecretKeySpec(key, KEY_ALGORITHM);
    }

    public static byte[] encrypt(byte[] data,Key key) throws Exception{
        return encrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
    }

    public static byte[] encrypt(byte[] data,byte[] key) throws Exception{
        return encrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
    }

    public static byte[] encrypt(byte[] data,byte[] key,String cipherAlgorithm) throws Exception{
        //还原密钥
        Key k = toKey(key);
        return encrypt(data, k, cipherAlgorithm);
    }

    public static byte[] encrypt(byte[] data,Key key,String cipherAlgorithm) throws Exception{
        //实例化
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        //使用密钥初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, key);
        //执行操作
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte[] data,byte[] key) throws Exception{
        return decrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
    }

    public static byte[] decrypt(byte[] data,Key key) throws Exception{
        return decrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
    }

    public static byte[] decrypt(byte[] data,byte[] key,String cipherAlgorithm) throws Exception{
        //还原密钥
        Key k = toKey(key);
        return decrypt(data, k, cipherAlgorithm);
    }

    public static byte[] decrypt(byte[] data,Key key,String cipherAlgorithm) throws Exception{
        //实例化
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        //使用密钥初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, key);
        //执行操作
        return cipher.doFinal(data);
    }

    private static String showByteArray(byte[] data){
        if(null == data){
            return null;
        }
        StringBuilder sb = new StringBuilder("{");
        for(byte b : data){
            sb.append(b).append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("}");
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        String key = initSecretKey();
        System.out.println("key："+key);
        Key k = toKey(Base64.decodeBase64(key)); //生成秘钥
        String data ="AES数据";
        System.out.println("加密前数据: string:"+data);
        System.out.println("加密前数据: byte[]:"+showByteArray(data.getBytes()));
        System.out.println();
        byte[] encryptData = encrypt(data.getBytes(), k);//数据加密
        System.out.println("加密后数据: byte[]:"+showByteArray(encryptData));
       System.out.println("加密后数据: hexStr:"+ Hex.encodeHexString(encryptData));
        System.out.println();
        byte[] decryptData = decrypt(encryptData, k);//数据解密
        System.out.println("解密后数据: byte[]:"+showByteArray(decryptData));
        System.out.println("解密后数据: string:"+new String(decryptData));
    }

}