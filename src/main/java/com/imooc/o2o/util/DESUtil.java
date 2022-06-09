package com.wanjuanshu.o2o.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author yangshucheng
 * @create 2021-05-18 16:11
 */
public class DESUtil {
    private static Key key;
    //设置密钥key
    private static String KEY_STR = "myKey";
    private static String CHARSETNAME ="UTF-8";
    private static String ALGORITHM = "DES";

    static {
        try{
            // 生成DES算法对象
            KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
            // 运用SHA1安全策略
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            // 设置上密钥种子
            secureRandom.setSeed(KEY_STR.getBytes("UTF-8"));
            //初始化基于SHA1的算法对象
            generator.init(secureRandom);
            key = generator.generateKey();
            generator = null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取加密后的信息
     * @param str
     * @return
     */
    public static String getEncryptString(String str){
        //基于BASE64编码，接受byte[]并转换String
        Base64.Encoder base64Ecoder = Base64.getEncoder();
        try{
            //按UTF8编码
            byte[] bytes = str.getBytes(CHARSETNAME);
            //获取加密对象
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            //初始化密码信息
            cipher.init(Cipher.ENCRYPT_MODE,key);
            //加密
            byte[] doFile = cipher.doFinal(bytes);
            //byte[] to encoder 好的String并返回
            return base64Ecoder.encodeToString(doFile);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static String getDecryptString(String str){
        //基于BASE64编码，接受byte[]并转换成String
        Base64.Decoder base64Encoder = Base64.getDecoder();
        try{
            //将字符串decode成byte[]
            byte[] bytes = base64Encoder.decode(str);
            //获取解密对象
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            //初始化解密信息
            cipher.init(Cipher.DECRYPT_MODE,key);
            //解密
            byte[] doFile = cipher.doFinal(bytes);
            //返回解密之后的信息
            return new String(doFile,CHARSETNAME);
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println("root :" + getEncryptString("root"));
        System.out.println("123456 :" + getEncryptString("123456"));
    }
}
