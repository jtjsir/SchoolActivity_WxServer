package com.du.wx.pc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.du.wx.mapper.joggle.BaseUserDao;
import com.du.wx.model.user.BaseUser;
import com.du.wx.pc.service.BaseUserService;

@Service
public class BaseUserServiceImpl implements BaseUserService {
	@Resource
	public BaseUserDao baseDao ;

	@Override
	public BaseUser queryBaseUser(String openid) {
		return baseDao.queryUserInfoById(openid) ;
	}

	@Override
	public List<BaseUser> queryAll() {
		return baseDao.queryUsers() ;
	}

	@Override
	public List<BaseUser> queryBaseUsers(String nickname) {
		return baseDao.queryUsersByNickname(nickname) ;
	}

	@Override
	public void addUser(BaseUser baseUser) {
		baseDao.insertUser(baseUser);
	}

	@Override
	public void deleteUser(String openid) {
		baseDao.deleteUser(openid);
	}

}
