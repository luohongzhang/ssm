package com.jiangcheng.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jiangcheng.bean.UserBean;
import com.jiangcheng.service.UserService;


@RunWith(SpringJUnit4ClassRunner.class)		//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class TestMyBatis {
	private static Logger logger = Logger.getLogger(TestMyBatis.class);
	private ApplicationContext ac = null;
	@Resource
	private UserService userService = null;

	@Before
	public void before() {
		ac = new ClassPathXmlApplicationContext("spring.xml");//config.properties 和 component-scan 都在 spring=mybatis 中 配置,并且 这里使用 spring - mybatis 才能运行成功 不知道为什么
		userService = (UserService) ac.getBean("userService");
	}

	@Test
	public void test1() {
		
		UserBean user = new UserBean();
		user.setPassword("111");
		user.setUsername("aaa");
		
		boolean result = userService.login(user);
		 System.out.println(user.getUsername());
		 logger.info("值："+user.getUsername());
		logger.info("result"+result);
	}
}
