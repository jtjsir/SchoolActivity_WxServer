package com.du.wx.controller.pc.manage;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.du.wx.model.user.AdminUser;
import com.du.wx.pc.service.AdminUserService;
import com.du.wx.util.HttpUtils;
import com.du.wx.util.PassUtil;
import com.google.gson.Gson;

/**
 * 管理员管理
 * @author jtj
 *
 */
@Controller
@RequestMapping(value = "/pc/manage/admin")
public class AdminManageControl {
	
	private static final Logger managerControlLogger = LogManager.getLogger("ManageControl") ;

	@Resource
	AdminUserService userService ;
	
	@RequestMapping(value = "/query/all")
	public void queryAll(HttpServletRequest request,HttpServletResponse response){
		//普通管理员
		String adminType = "0" ;
		List<AdminUser> users = userService.queryAdmins(adminType) ;
		
		if(users==null){
			HttpUtils.springmvcWrite(response, "null");
		}else{
			String jsonStr = new Gson().toJson(users) ;
			HttpUtils.springmvcWrite(response, jsonStr);
		}
	}
	
	@RequestMapping(value = "/query/some")
	public void queryName(HttpServletRequest request,HttpServletResponse response){
		String username = request.getParameter("searchname") ;
		
		AdminUser adminUser = userService.queryAdmin(username) ;
		if(null==adminUser){
			HttpUtils.springmvcWrite(response, "null");
		}else{
			List<AdminUser> adminUsers = new ArrayList<>(1) ;
			adminUsers.add(adminUser) ;
			HttpUtils.springmvcWrite(response, new Gson().toJson(adminUsers));
		}
	}
	
	@RequestMapping(value = "/add")
	public void addAdmin(HttpServletRequest request,HttpServletResponse response){
		String name = request.getParameter("name") ;
		String password = request.getParameter("password") ;
		password = PassUtil.encodePass(password) ;
		String lno = request.getParameter("lno") ;
		String phone = request.getParameter("phone") ;
		int type = 0 ;
		
		AdminUser adminUser = new AdminUser() ;
		adminUser.setLno(lno);
		adminUser.setName(name);
		adminUser.setPassword(password);
		adminUser.setPhone(phone);
		adminUser.setType(type);
		
		userService.addAdmin(adminUser);
		managerControlLogger.info("添加姓名为 " + name + " 普通管理员成功!");
	}
	
	@RequestMapping(value = "delete")
	public void deleteAdmin(HttpServletRequest request,HttpServletResponse response){
		String name = request.getParameter("name") ;
		
		userService.deleteAdmin(name);
	}
}
