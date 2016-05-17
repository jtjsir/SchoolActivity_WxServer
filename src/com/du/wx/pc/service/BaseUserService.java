package com.du.wx.pc.service;

import java.util.List;

import com.du.wx.model.user.BaseUser;

public interface BaseUserService {

	BaseUser queryBaseUser(String openid) ;
	
	List<BaseUser> queryBaseUsers(String nickname) ;
	
	List<BaseUser> queryAll() ;
	
	void addUser(BaseUser baseUser) ;
	
	void deleteUser(String openid) ;
}
