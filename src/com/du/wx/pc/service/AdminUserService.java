package com.du.wx.pc.service;

import java.util.List;

import com.du.wx.model.user.AdminUser;

public interface AdminUserService {

	boolean isAdminUser(String username,String password) ;
	
	AdminUser queryAdmin(String username,String password) ;
	
	AdminUser queryAdmin(String username) ;
	
	List<AdminUser> queryAdmins(String type) ;
	
	void addAdmin(AdminUser adminUser) ;
	
	void deleteAdmin(String name) ;
}
