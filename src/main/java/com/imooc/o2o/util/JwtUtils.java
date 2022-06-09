package com.wanjuanshu.o2o.util;

import com.wanjuanshu.o2o.entity.LocalAuth;
import com.wanjuanshu.o2o.entity.PersonInfo;
import com.wanjuanshu.o2o.service.LocalAuthService;
import com.wanjuanshu.o2o.service.impl.LocalAuthServiceImpl;
import io.jsonwebtoken.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangshucheng
 * @create 2021-06-24 4:27 下午
 */
public class JwtUtils {

    /**
     * 签名需要的私有密钥
     */
    private static final String SECRET = "7786df7fc3a34e26a61c034d5ec8245d";


    /**
     * 重载createJwt，通过用户信息和生成Jwt
     * @param user 用户信息
     * @return token
     */
    public static String createJwt(PersonInfo user) {
        return createJwt(user,60 * 60 * 24);
    }

    /**
     * 通过用户信息和过期时间生成Jwt
     * @param user 用户信息
     * @param ttlMillis 过期时间
     * @return token
     */
    public static String createJwt(PersonInfo user, long ttlMillis) {
        // 签名算法 HS256，即jjwt已经封装header中需要的算法名称
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 生成Jwt的时间，即签发时间
        long nowMillis = System.currentTimeMillis();
        SecretKey key = generalKey();//生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取
        Map<String,Object> claims = new HashMap<String,Object>();//创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        claims.put("userId", user.getUserId());
        claims.put("userName",user.getName());
        claims.put("userEnableStatus",user.getEnableStatus());
        //构建Jwt
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                //jwt的唯一标识
                .setId(String.valueOf(user.getUserId()))
                //jwt面向用户
                .setSubject(user.getName())
                //jwt的签发者
                .setIssuer("ysc")
                //jwt的签发时间
                .setIssuedAt(new Date(nowMillis))
                //设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm,key);
        //自定义payload的claim信息
        builder.claim("role", "admin");
        // 设置过期时间，需要大于签发时间
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            builder.setExpiration(new Date(expMillis));
        }
        return builder.compact();
    }

    /**
     * 解析jwt，解析时若过期会抛出ExpiredJwtException异常
     * @param jwt token
     * @return jwt对象
     */
    public static Claims parseJwt(String jwt){
        //解析jwt
        SecretKey key = generalKey();
        //获取解析后的对象
        Claims claims = Jwts.parser()
                //设置签名秘钥，和生成的签名的秘钥一模一样
                .setSigningKey(key)
                //设置需要解析的jwt
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }

    public static SecretKey generalKey(){
        String stringKey = SECRET;//本地配置文件中加密的密文7786df7fc3a34e26a61c034d5ec8245d
        byte[] encodedKey = Base64.decodeBase64(stringKey);//本地的密码解码[B@152f6e2
        //System.out.println(encodedKey);//[B@152f6e2
        //System.out.println(Base64.encodeBase64URLSafeString(encodedKey));//7786df7fc3a34e26a61c034d5ec8245d
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");// 根据给定的字节数组使用AES加密算法构造一个密钥，使用 encodedKey中的始于且包含 0 到前 leng 个字节这是当然是所有。（后面的文章中马上回推出讲解Java加密和解密的一些算法）
        return key;
    }

/*    //测试
    public static void main(String[] args) {

        PersonInfo personInfo = new PersonInfo();
        personInfo.setName("yang");
        personInfo.setEnableStatus(1);
        personInfo.setUserId(8L);
        String token = JwtUtils.createJwt(personInfo);
        System.out.println(token);
        Claims claims = JwtUtils.parseJwt(token);
        System.out.println("jwtId："+claims.getId());
        System.out.println("jwtSubject："+claims.getSubject());
        System.out.println("jwtIssuer："+claims.getIssuer());
        System.out.println("jwtIssuedAt："+claims.getIssuedAt());
        System.out.println("role："+claims.get("role"));
        System.out.println("expiration："+claims.getExpiration());

    }*/

}

