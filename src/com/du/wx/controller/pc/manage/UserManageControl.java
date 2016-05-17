package com.du.wx.controller.pc.manage;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.du.wx.model.user.BaseUser;
import com.du.wx.pc.service.BaseUserService;
import com.du.wx.util.HttpUtils;
import com.google.gson.Gson;

/**
 * 微信用户管理
 * @author jtj
 *
 */
@Controller
@RequestMapping(value = "/pc/manage/user")
public class UserManageControl {

	@Resource
	public BaseUserService baseUserSerivce ;
	
	@RequestMapping(value = "/query/all")
	public void queryAll(HttpServletResponse response){
		List<BaseUser> users = baseUserSerivce.queryAll() ;
		if(null==users){
			HttpUtils.springmvcWrite(response, "null");
		}else {
			HttpUtils.springmvcWrite(response, new Gson().toJson(users));
		}
	}
	
	@RequestMapping(value = "/query/some")
	public void querySome(HttpServletRequest request,HttpServletResponse response){
		String nickname = request.getParameter("nickname") ;
		List<BaseUser> users = baseUserSerivce.queryBaseUsers(nickname) ;
		if(users==null){
			HttpUtils.springmvcWrite(response, "null");
		}else{
			HttpUtils.springmvcWrite(response, new Gson().toJson(users));
		}
	}
}
