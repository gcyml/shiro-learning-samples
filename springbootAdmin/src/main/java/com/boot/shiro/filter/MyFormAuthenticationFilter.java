package com.boot.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.kaptcha.Constants;

/**
 * 自定义表单认证
 * @author wgc
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter{

	private static final Logger logger = LoggerFactory.getLogger(MyFormAuthenticationFilter.class);
	/**
	 * 重写该方法, 判断返回登录信息
	 */
    @Override
    protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
        String className = ae.getClass().getName();
        String message;
        String userName = getUsername(request);
        if (UnknownAccountException.class.getName().equals(className)) {
        	logger.info("对用户[{}]进行登录验证..验证未通过,未知账户", userName);
        	
            message = "未知账户";
        } else if (IncorrectCredentialsException.class.getName().equals(className)) {
        	logger.info("对用户[{}]进行登录验证..验证未通过,错误的凭证", userName);
            message = "密码不正确";
        
        } else if(LockedAccountException.class.getName().equals(className)) {
        	logger.info("对用户[{}]进行登录验证..验证未通过,账户已锁定", userName);
        	message = "账户已锁定";
        } else if(ExcessiveAttemptsException.class.getName().equals(className)) {
        	logger.info("对用户[{}]进行登录验证..验证未通过,错误次数过多", userName);
        	message = "用户名或密码错误次数过多，请十分钟后再试";
        } else if (AuthenticationException.class.getName().equals(className)) {
        	//通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
        	logger.info("对用户[{}]进行登录验证..验证未通过,未知错误", userName);
        	message = "用户名或密码不正确";
        } else{
        	message = className;
        }
        request.setAttribute(getFailureKeyAttribute(), message);
    }
    
    /**
     * 校验验证码
     * 
     * 原FormAuthenticationFilter的认证方法
     * 该方法会在realm前调用 
     * 由于验证码是我们自己生成存在session里的，所以我们需要在登录时判断验证码是否成功就可用该方法
     * 
     * 验证码为空或验证成功 继续执行父类的onAccessDenied方法
     * 验证码不为空且验证失败，返回true则不再走realm,直接进控制器的login.do
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object map) throws Exception {
    	String userName = getUsername(request);
//        logger.info("记住我{}",isRememberMe(request))
        // 从session获取正确的验证码
    	Session session = SecurityUtils.getSubject().getSession();
//        HttpSession session = ((HttpServletRequest)request).getSession()
        //页面输入的验证码
        String randomcode = request.getParameter("code");

        String validateCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);

        if(randomcode == null) {
        	return true;
        }
        	
        if(randomcode != null && validateCode != null) {
            if (!randomcode.equals(validateCode)) {
                //如果校验失败，将验证码错误失败信息，通过shiroLoginFailure设置到request中
            	logger.info("对用户[{}]进行登录验证..验证码错误", userName);
                request.setAttribute("shiroLoginFailure", "验证码错误");
                //拒绝访问，不再校验账号和密码
                return true;
            }
        }
        return super.onAccessDenied(request, response, map);

    }
}