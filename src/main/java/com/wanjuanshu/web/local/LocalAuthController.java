package com.wanjuanshu.web.local;

import com.wanjuanshu.dto.LocalAuthExecution;
import com.wanjuanshu.entity.LocalAuth;
import com.wanjuanshu.enums.LocalAuthStateEnum;
import com.wanjuanshu.entity.PersonInfo;
import com.wanjuanshu.exceptions.LocalAuthOperationException;
import com.wanjuanshu.service.LocalAuthService;
import com.wanjuanshu.util.CodeUtil;
import com.wanjuanshu.util.HttpServletRequestUtil;
import com.wanjuanshu.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangshucheng
 * @create 2021-05-24 12:02
 */
@Controller
@RequestMapping(value = "local",method = {RequestMethod.GET,RequestMethod.POST})
public class LocalAuthController {
    @Autowired
    private LocalAuthService localAuthService;

    /**
     * 将用户信息与平台账号绑定
     * @param request
     * @return
     */
    @RequestMapping(value = "/bindlocalauth",method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> bindLocalAuth(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        //验证码校验
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入错误的验证码");
            return modelMap;
        }
        //获取输入的账号
        String userName = HttpServletRequestUtil.getString(request,"userName");
        //获取输入的密码
        String password = HttpServletRequestUtil.getString(request,"password");
        //从session中获取当前用户信息（用户一旦通过微信登录之后，便能获取用户的信息）
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        //非空判断，要求账号密码以及当前的用户session非空
        if (userName != null && password != null && user != null && user.getUserId() != null){
            //创建LocalAuth对象并赋值
            LocalAuth localAuth = new LocalAuth();
            localAuth.setUsername(userName);
            localAuth.setPassword(password);
            localAuth.setPersonInfo(user);
            //绑定账号
            LocalAuthExecution le = localAuthService.bindLocalAuth(localAuth);
            if(le.getState() == LocalAuthStateEnum.SUCCESS.getState()){
                modelMap.put("success",true);
            }else{
                modelMap.put("success",false);
                modelMap.put("errMsg",le.getStateInfo());
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户名和密码均不能为空");
        }
        return modelMap;
    }


    /**
     * 修改密码
     * @param request
     * @return
     */
    @RequestMapping(value = "/changelocalpwd",method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> changeLocalPwd(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        //验证码校验
        if (!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        //获取账号
        String userName = HttpServletRequestUtil.getString(request,"userName");
        //获取原密码
        String password = HttpServletRequestUtil.getString(request,"password");
        //获取新密码
        String newPassword = HttpServletRequestUtil.getString(request,"newPassword");
        //从session中获取当前用户信息(用户一旦通过微信登录后，便能获得用户信息)
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        //非空判断，要求账号新旧密码以及当前的用户session非空，且新旧密码不同
        if(userName != null && password != null && newPassword != null && user != null
                && user.getUserId() != null && !password.equals(newPassword)){
            try{
                // 查看原先账号，看看与输入的账号是否一致，不一致则认为是非法操作
                LocalAuth localAuth = localAuthService.getLocalAuthByUserId(user.getUserId());
                if(localAuth == null || !localAuth.getUsername().equals(userName)){
                    //不一致则直接退出
                    modelMap.put("success",false);
                    modelMap.put("errMsg","输入的账号非本次登录的账号");
                    return modelMap;
                }
                //修改平台账号的用户密码
                LocalAuthExecution le = localAuthService.modifyLocalAuth(user.getUserId(),userName,password,newPassword);
                if (le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", le.getStateInfo());
                }
            } catch (LocalAuthOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入密码");
        }
        return modelMap;
    }

    /**
     * 登入
     * @param request
     * @return
     */
    @RequestMapping(value = "/logincheck",method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> loginCheck(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> modelMap = new HashMap<>();
        //获取是否需要进行验证码校验的标识符
        boolean needVerify = HttpServletRequestUtil.getBoolean(request,"needVerify");
        if(needVerify && !CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }
        //获取输入的账号
        String userName = HttpServletRequestUtil.getString(request,"userName");
        //获取输入的密码
        String password = HttpServletRequestUtil.getString(request,"password");
        //非空校验
        if (userName != null && password != null){
            //传入账号和密码去获取平台账号信息
            LocalAuth localAuth = localAuthService.getLocalAuthByUsernameAndPwd(userName,password);
            if (localAuth != null){
                //若取到账号信息则登录成功
                // 若帐号密码正确
                // 生成token
                //1.生成token,把用户标识信息放进去
                //2.把token放在响应头，发送给客户端
                String token = JwtUtils.createJwt(localAuth.getPersonInfo());
                HttpHeaders headers = new HttpHeaders();
                headers.add("token",token);
                response.addHeader("token",token);
                modelMap.put("token",token);
                request.getSession().setAttribute("token",token);
                modelMap.put("success",true);
                //同时在session里设置用户信息
                request.getSession().setAttribute("user",localAuth.getPersonInfo());
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "用户名或密码错误");
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户名和密码均不能为空");
        }
        return modelMap;
    }

    /**
     * 登出
     * @param request
     * @return
     */
    @RequestMapping(value = "logout",method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> logOut(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        //将用户session置为空
        request.getSession().setAttribute("user",null);
        modelMap.put("success",true);
        return modelMap;
    }
}























