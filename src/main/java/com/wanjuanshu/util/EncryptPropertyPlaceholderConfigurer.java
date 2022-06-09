package com.wanjuanshu.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * @author yangshucheng
 * @create 2021-05-18 16:46
 */
public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    //需要加密的字段数组
    private String[] encryptPropNames = {"jdbc.username","jdbc.password"};

    protected String convertProperty(String propertyName,String propertyValue){
        if(isEncryptProp(propertyName)){
            //对已加密的字段进行解密工作
            String decryptValue = DESUtil.getDecryptString(propertyValue);
            return decryptValue;
        }else {
            return propertyValue;
        }
    }


    /**
     * 该属性是否已加密
     * @param propertyName
     * @return
     */
    private boolean isEncryptProp(String propertyName){
        //若等于需要加密的field，则进行加密
        for (String encryptpropertyName : encryptPropNames) {
            if(encryptpropertyName.equals(propertyName))
                return true;
        }
        return false;
    }
}
