package com.wanjuanshu.o2o.interceptor.shopadmin;

import com.wanjuanshu.o2o.entity.PersonInfo;
import com.wanjuanshu.o2o.exceptions.LocalAuthOperationException;
import com.wanjuanshu.o2o.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 店家管理系统登录验证拦截器
 * 
 * @author yangshucheng
 *
 */
public class ShopLoginInterceptor extends HandlerInterceptorAdapter {
	/**
	 * 主要做事前拦截，即用户操作发生前，改写preHandle里的逻辑，进行拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 从session中取出用户信息来,利用session来验证
		PersonInfo userObj = (PersonInfo)request.getSession().getAttribute("user");
		if (userObj != null) {
			// 若用户信息不为空则将session里的用户信息转换成PersonInfo实体类对象
			PersonInfo user = (PersonInfo) userObj;
			// 做空值判断，确保userId不为空并且该帐号的可用状态为1，并且用户类型为店家
			if (user != null && user.getUserId() != null && user.getUserId() > 0 && user.getEnableStatus() == 1)
				// 若通过验证则返回true,拦截器返回true之后，用户接下来的操作得以正常执行
				return true;
		}
		/*****************************************/
		//利用token来验证
		// 从header中取出token
		String token = (String)request.getHeader("token");
		// 从session中取出token来
		//String token = (String) request.getSession().getAttribute("token");
		// 校验token
//		String method = request.getMethod();
//		if (method.equals("GET")){
//			return true;
//		}
		if(token == null){
			returnLogin(request, response);
			return false;
			//throw new LocalAuthOperationException("token为空");
		}
		// 校验token有效i性
		Claims claims = JwtUtils.parseJwt(token);
		if (claims == null){
			// 返回token过期了
			returnLogin(request, response);
			return false;
			//throw new LocalAuthOperationException("返回token过期了");
		}else{
			// 若用户信息不为空则将session里的用户信息转换成PersonInfo实体类对象
			//获取token中的用户标识信息，根据用户标识信息
			Integer userId =(Integer) claims.get("userId");
			Integer userEnableStatus =(Integer) claims.get("userEnableStatus");
			// 获取剩余有效时间，判断是否需要刷新token
			long current = System.currentTimeMillis();
			long expire = claims.getExpiration().getTime();
			if(expire-current<=60*60){
				String newtoken = JwtUtils.createJwt(userObj);
				response.addHeader("token",newtoken);
			}
			// 做空值判断，确保userId不为空并且该帐号的可用状态为1，并且用户类型为店家
			if (userId!= null && userId > 0 && userEnableStatus == 1)
				// 若通过验证则返回true,拦截器返回true之后，用户接下来的操作得以正常执行
				return true;
		}
		// 若不满足登录验证，则直接跳转到帐号登录页面
		returnLogin(request, response);
		return false;
	}

	private void returnLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<script>");
		out.println("window.open ('" + request.getContextPath() + "/local/login?usertype=2','_self')");
		out.println("</script>");
		out.println("</html>");
	}
}
