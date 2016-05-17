package com.du.wx.mapper.joggle;

import java.util.List;

import com.du.wx.model.activity.Activity;
import com.du.wx.model.activity.ActivityNotice;
import com.du.wx.model.user.BaseUser;

public interface ActivityNoticeDao {

	void addNoticeInfo(ActivityNotice activityNotice) ;
	
	int queryCountNoticeByAct(int activityid) ;
	
	int queryCountNoticegByUser(int uid) ;
	
	List<BaseUser> queryUsersByAct(int activityid) ;
	
	List<Activity> queryActByUser(int uid) ;
	
	void updateIsNotice(int uid,int activityid,int isnotice) ;
	
	ActivityNotice queryNotice(int uid,int activityid) ;
}
