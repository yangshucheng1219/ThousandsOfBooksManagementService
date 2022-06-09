package com.wanjuanshu.dao;

import com.wanjuanshu.entity.LocalAuth;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yangshucheng
 * @create 2021-06-24 7:40 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocalAuthDaoTest {
    @Autowired
    private LocalAuthDao localAuthDao;

    @Test
    public void testAgetLocalAuthByUsernameAndPwd(){
        LocalAuth localAuth = localAuthDao.queryLocalByUserNameAndPwd("yang", "s05bse6q2qlb9qblls96s592y55y556s");
        System.out.println(localAuth.toString());
    }

    @Test
    public void testBgetLocalAuthByUserId(){
        LocalAuth localAuth = localAuthDao.queryLocalByUserId(8);
        System.out.println(localAuth.toString());
    }

}
