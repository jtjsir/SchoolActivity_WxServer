package com.du.wx.pc.service;

import java.util.List;

import com.du.wx.model.WxNews;
import com.du.wx.model.activity.Activity;
import com.du.wx.model.activity.ActivityBooking;
import com.du.wx.model.activity.ActivityNotice;
import com.du.wx.model.activity.LetterInfo;
import com.du.wx.model.user.BaseUser;

public interface ActivityService {

	void addInfos(WxNews news,Activity activity) ;
	
	void addLetterData(LetterInfo letterInfo) ;
	
	List<LetterInfo> queryAllLetterInfo() ;
	
	List<LetterInfo> queryLetterInfoByTitle(String title) ;
	
	LetterInfo queryOneByTitle(String title) ;
	
	void addBookingInfo(ActivityBooking activityBooking) ;
	
	void addNoticeInfo(ActivityNotice activityNotice) ;
	
	List<Activity> queryNoticeActs(String uid) ;
	
	List<Activity> queryBookingActs(String uid) ;
	
	void updateNotice(String uid,String activityId,String isnotice) ;
	
	ActivityNotice queryNotice(String uid,String activityId) ;
	
	Activity queryActByTitle(String title) ;
	
	List<Activity> queryActsByType(String type) ;
	
	List<BaseUser> queryUsersByNoticeAct(String activityid) ;
	
	List<Activity> queryAllActs() ;
	
	List<Activity> queryHistoryActsByUser(String uid) ;
	
	int queryCountBookingByAct(String activityid) ;
	
	int queryCountNoticeByAct(String activityid) ;
	
	ActivityBooking queryBooking(String title,String uid) ;
	
	void updateBooking(String uid,String activityid,String isnotice) ;
}
