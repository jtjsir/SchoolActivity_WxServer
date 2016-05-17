package com.du.wx.controller.pc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.du.wx.model.user.AdminUser;
import com.du.wx.pc.service.AdminUserService;

@Controller
@RequestMapping(value = "/pc/admin/user")
public class UserControl {
	private static final Logger userControlLogger = LogManager.getLogger("UserControl") ;
	
	@Resource
	public AdminUserService userService ;
	
	//登录
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request,HttpServletResponse response){
		String rediretUrl = "" ;
		
		String username = request.getParameter("username") ;
		String password = request.getParameter("password") ;
		
		boolean isExist = userService.isAdminUser(username, password) ;
		if(isExist){
			userControlLogger.debug(username + "验证信息成功");
			rediretUrl = "/pcpages/index" ;
			AdminUser adminUser = userService.queryAdmin(username, password) ;
			request.getSession().setAttribute("user", adminUser);
			
			if(adminUser.getType()==0){
				//表明普通用户,不显示管理员管理模块
				request.setAttribute("isSuper", 0);
			}else{
				request.setAttribute("isSuper", 1);
			}
		}else{
			userControlLogger.debug(username + "验证信息失败");
			request.setAttribute("error", true);
			rediretUrl = "/pcpages/login" ;
		}
		
		return rediretUrl ;
	}
	
	//退出
	@RequestMapping(value = "logout")
	public String logout(HttpServletRequest request){
		request.getSession().removeAttribute("user");
		
		return "/pcpages/login" ;
	}
	
}
