package com.du.wx.mapper.joggle;

import java.util.List;

import com.du.wx.model.activity.Activity;

public interface ActivityHistoryDao {
	
	List<Activity> queryActsByUser(int uid) ;

}
