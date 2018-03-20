package com.boot.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义表单认证
 * @author wgc
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter{
    private String ERROR_REASON = "errorReason";
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
            message = "账户不存在";
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
        request.setAttribute(getFailureKeyAttribute(), className);
        request.setAttribute(ERROR_REASON, message);
    }
    
    /**
     * 校验验证码
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object map) throws Exception {
    	if(request.getAttribute(getFailureKeyAttribute()) != null) {  
    		request.setAttribute(ERROR_REASON, "验证码错误");
            return true;  
        }  
        return super.onAccessDenied(request, response, map);

    }
}