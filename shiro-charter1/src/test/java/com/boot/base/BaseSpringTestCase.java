package com.boot.base;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
/**
 * 
 * @author wgc
 * @date 2018年2月27日
 */
public abstract class BaseSpringTestCase {  
	Logger logger = LoggerFactory.getLogger(this.getClass());
} 
