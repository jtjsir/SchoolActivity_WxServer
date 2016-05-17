package com.du.wx.mapper.joggle;

import java.util.List;

import com.du.wx.model.activity.Activity;
import com.du.wx.model.activity.ActivityBooking;
import com.du.wx.model.user.BaseUser;

public interface ActivityBookingDao {
	
	void addBookingInfo(ActivityBooking activityBooking) ;
	
	int queryCountBookingByAct(int activityid) ;
	
	int queryCountBookingByUser(int uid) ;
	
	List<BaseUser> queryUsersByAct(int activityid) ;
	
	List<Activity> queryActByUser(int uid) ;
	
	ActivityBooking queryActBooking(int uid,int activityid) ;
	
	void updateBooking(int uid,int activityid,int isbooked) ;
}
