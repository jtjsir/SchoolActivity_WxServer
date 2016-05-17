package com.du.wx.mapper.joggle;

import java.util.List;

import com.du.wx.model.user.BaseUser;

public interface BaseUserDao {
	void insertUser(BaseUser user) ;
	
	BaseUser queryUserInfoById(String openid) ;
	
	List<BaseUser> queryUsersByNickname(String nickname) ;
	
	void updateUser(BaseUser user);
	
	void deleteUser(String openid);
	
	List<BaseUser> queryUsers();
}
