package com.du.wx.test.service;

import org.junit.Before;
import org.junit.Test;

import com.du.wx.model.common.BaseTest;
import com.du.wx.pc.service.AdminUserService;
import com.du.wx.pc.service.impl.AdminUserServiceImpl;
import com.google.gson.Gson;

public class UserServiceTest extends BaseTest{

	AdminUserService userService ;
	
	@Before
	public void setUp(){
		userService = context.getBean(AdminUserServiceImpl.class) ;
	}
	
	@Test
	public void testQuery(){
		System.err.println(new Gson().toJson(userService.queryAdmin("杜秋"))) ;
		
	}
	
}
