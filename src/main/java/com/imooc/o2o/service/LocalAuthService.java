package com.wanjuanshu.o2o.service;

import com.wanjuanshu.o2o.dto.LocalAuthExecution;
import com.wanjuanshu.o2o.entity.LocalAuth;
import com.wanjuanshu.o2o.exceptions.LocalAuthOperationException;

/**
 * @author yangshucheng
 * @create 2021-05-24 10:02
 */
public interface LocalAuthService {

    /**
     * 通过账号和密码获取平台账号信息
     * @param userName
     * @param password
     * @return
     */
    LocalAuth getLocalAuthByUsernameAndPwd(String userName,String password);

    /**
     * 通过userId获取平台账号信息
     * @param userId
     * @return
     */
    LocalAuth getLocalAuthByUserId(long userId);

    /**
     * 修改平台账号的登录密码
     * @param localAuth
     * @return
     * @throws LocalAuthOperationException
     */
    LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException;


    LocalAuthExecution modifyLocalAuth(Long userId,String username,String password,String newPassword) throws LocalAuthOperationException;
}
