package com.du.wx.pc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.du.wx.mapper.joggle.AdminUserDao;
import com.du.wx.mapper.joggle.BaseUserDao;
import com.du.wx.model.user.AdminUser;
import com.du.wx.pc.service.AdminUserService;
import com.du.wx.util.PassUtil;

@Service
public class AdminUserServiceImpl implements AdminUserService{

	private static final Logger userServiceLogger = LogManager.getLogger("AdminUserServiceImpl") ;

	@Resource
	public AdminUserDao adminDao ;
	
	@Override
	public boolean isAdminUser(String username, String password) {
		boolean flag = false ;
		
		password = PassUtil.encodePass(password) ;
		AdminUser adminUser = adminDao.queryAdminUser(username, password) ;
		if(null!=adminUser){
			userServiceLogger.info(username + "这个用户存在");
			flag = true ;
		}else{
			userServiceLogger.info(username + "这个用户不存在");
		}
		return flag;
	}

	@Override
	public List<AdminUser> queryAdmins(String type) {
		int adminType = Integer.valueOf(type) ;
		
		return adminDao.queryAdminUsers(adminType) ;
	}

	@Override
	public AdminUser queryAdmin(String username, String password) {
		password = PassUtil.encodePass(password) ;
		AdminUser adminUser = adminDao.queryAdminUser(username, password) ;
		
		return adminUser ;
	}

	@Override
	public AdminUser queryAdmin(String username) {
		return adminDao.queryAdminByName(username) ;
	}

	@Override
	public void addAdmin(AdminUser adminUser) {
		adminDao.addAdmin(adminUser);
		userServiceLogger.info(adminUser.getName() + " 用户已添加到数据库中");
	}

	@Override
	public void deleteAdmin(String name) {
		adminDao.deleteAdmin(name);
		userServiceLogger.info(name + " 用户已从数据库被删除");
	}
	
	
}
