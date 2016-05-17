package com.du.wx.test.mapper;

import org.junit.Before;
import org.junit.Test;

import com.du.wx.mapper.joggle.WechatTokenDao;
import com.du.wx.model.WxToken;
import com.du.wx.model.common.BaseTest;
import com.du.wx.util.TokenUtil;
import com.du.wx.util.WeChatApiUtil;

public class WechatTokenMapperTest extends BaseTest{

	WechatTokenDao wechatTokenDao ;
	
	@Before
	public void setUp(){
		wechatTokenDao = context.getBean(WechatTokenDao.class) ;
	}
	
	@Test
	public void testInsert(){
		WxToken wxToken = TokenUtil.getToken() ;
		wechatTokenDao.insertToken(wxToken);
	}
	
	@Test
	public void testQuery(){
		System.err.println(wechatTokenDao.queryToken().getAccessToken()) ;
	}
}
