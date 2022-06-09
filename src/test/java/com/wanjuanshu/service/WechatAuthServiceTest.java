package com.wanjuanshu.service;

import com.wanjuanshu.dto.WechatAuthExecution;
import com.wanjuanshu.enums.WechatAuthStateEnum;
import com.wanjuanshu.entity.PersonInfo;
import com.wanjuanshu.entity.WechatAuth;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatAuthServiceTest {
	@Autowired
	private WechatAuthService wechatAuthService;

	@Test
	public void testRegister() {
		// 新增一条微信帐号
		WechatAuth wechatAuth = new WechatAuth();
		PersonInfo personInfo = new PersonInfo();
		String openId = "qqqqqqqqdafahizhfdhaih";
		// 给微信帐号设置上用户信息，但不设置上用户Id
		// 希望创建微信帐号的时候自动创建用户信息
		personInfo.setCreateTime(new Date());
		personInfo.setName("呜呜呜呜测试一下");
		personInfo.setUserType(1);
		wechatAuth.setPersonInfo(personInfo);
		wechatAuth.setOpenId(openId);
		wechatAuth.setCreateTime(new Date());
		WechatAuthExecution wae = wechatAuthService.register(wechatAuth);
		Assert.assertEquals(WechatAuthStateEnum.SUCCESS.getState(), wae.getState());
		// 通过openId找到新增的wechatAuth
		wechatAuth = wechatAuthService.getWechatAuthByOpenId(openId);
		// 打印用户名字看看跟预期是否相符
		System.out.println(wechatAuth.getPersonInfo().getName());
	}
}
