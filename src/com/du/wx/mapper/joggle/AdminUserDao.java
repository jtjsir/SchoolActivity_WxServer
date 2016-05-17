package com.du.wx.mapper.joggle;

import java.util.List;

import com.du.wx.model.user.AdminUser;

public interface AdminUserDao {
	List<AdminUser> queryAdminUsers(int type) ;
	
	AdminUser queryAdminUser(String username,String password) ;
	
	AdminUser queryAdminByName(String username) ;
	
	void addAdmin(AdminUser adminUser) ;
	
	void deleteAdmin(String name) ;
}
