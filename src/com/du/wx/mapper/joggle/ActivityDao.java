package com.du.wx.mapper.joggle;

import java.util.List;

import com.du.wx.model.activity.Activity;

public interface ActivityDao {
	
	void addActivity(Activity activity) ;
	
	Activity queryAct(String title) ;
	
	List<Activity> queryActByType(String type) ;
	
	List<Activity> queryAllActs() ;
}
