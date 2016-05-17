package com.du.wx.test.mapper;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.du.wx.mapper.joggle.ActivityDao;
import com.du.wx.model.activity.Activity;
import com.du.wx.model.common.BaseTest;
import com.du.wx.util.StringUtil;

public class ActivityMapperTest extends BaseTest{

	ActivityDao activityDao ;
	
	@Before
	public void setUp(){
		activityDao = context.getBean(ActivityDao.class) ;
	}
	
	@Test
	public void add(){
//		Activity activity = new Activity() ;
//		activity.setAddress("杭州");
//		activity.setContent("jing");
//		activity.setStatus(0);
//		activity.setTime(new Date());
//		activity.setTitle("标题");
//		activity.setType(0);
//		activity.setUrl("url");
//		
//		activityDao.addActivity(activity);
		
//		System.out.println(StringUtil.stringToDate(StringUtil.dateToStr(new Date()))) ;
	}
}
