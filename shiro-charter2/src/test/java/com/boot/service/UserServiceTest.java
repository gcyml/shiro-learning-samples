package com.boot.service;

import com.boot.base.BaseSpringTestCase;
import com.boot.domain.UserInfo;
import com.boot.service.ShiroService;

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
        
        logger.info("查找结果" + user);  
    }  
	
	@Test  
    public void selectUserByNameTest(){  
        UserInfo user = userService.selectUserInfoByUsername("tom");
        
        logger.info("查找结果:" + user);  
    }  
	
}
