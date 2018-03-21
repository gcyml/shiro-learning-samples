package com.boot.service;

import com.boot.base.BaseSpringTestCase;
import com.boot.domain.UserInfo;
import com.boot.service.ShiroService;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author wgc
 * @date 2018年2月27日
 */
public class UserServiceTest extends BaseSpringTestCase{
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired  
    private ShiroService userService;
	@Test  
    public void selectUserByIdTest(){  
        UserInfo user = userService.selectUserById(1);
        logger.info("查找结果:{}", user);  
    }  
	
	@Test  
    public void selectUserByNameTest(){  
        UserInfo user = userService.selectUserInfoByUsername("tom"); 
        logger.info("查找结果:{}", user);  
    }  
	
	@Test  
    public void md5PasswordTest(){  
		String str = "123456";
		String salt = "tom";
		int hashIterations = 1024;
        logger.info("md5 {}次加密-> str:{}, salt:{},hash:{}", hashIterations, str, salt, new Md5Hash(str, salt, hashIterations).toString());  
        salt = "jack";
        logger.info("md5 {}次加密-> str:{}, salt:{},hash:{}", hashIterations, str, salt, new Md5Hash(str, salt, hashIterations).toString());  
        salt = "rose";
        logger.info("md5 {}次加密-> str:{}, salt:{},hash:{}", hashIterations, str, salt, new Md5Hash(str, salt, hashIterations).toString());  

	}  
	
}
