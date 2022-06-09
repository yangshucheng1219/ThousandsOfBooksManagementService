package com.wanjuanshu.o2o.web.superadmin;

import com.wanjuanshu.o2o.entity.LocalAuth;
import com.wanjuanshu.o2o.service.LocalAuthService;
import com.wanjuanshu.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wanjuanshu.o2o.util.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/superadmin")
public class LoginController {
	@Autowired
	private LocalAuthService localAuthService;

	/**
	 * 登录验证
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logincheck", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> loginCheck(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 获取前端传递过来的帐号和密码
		String userName = HttpServletRequestUtil.getString(request, "userName");
		String password = HttpServletRequestUtil.getString(request, "password");
		// 空值判断
		if (userName != null && password != null) {
			// 获取本地帐号授权信息
			LocalAuth localAuth = localAuthService.getLocalAuthByUsernameAndPwd(userName, password);
			if (localAuth != null) {
				// 若帐号密码正确
				// 生成token
				//1.生成token,把用户标识信息放进去
				String token = JwtUtils.createJwt(localAuth.getPersonInfo());
				//2.把token放在响应头，发送给客户端
				HttpHeaders headers = new HttpHeaders();
				headers.add("token",token);
				modelMap.put("token",token);
				// 验证用户的身份是否为超级管理员
				if (localAuth.getPersonInfo().getUserType() == 3) {
					modelMap.put("success", true);
					// 登录成功则设置上session信息
					request.getSession().setAttribute("user", localAuth.getPersonInfo());
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", "非管理员没有权限访问");
				}
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "用户名或密码错误");
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名和密码均不能为空");
		}
		return modelMap;
	}
}
