package com.du.wx.controller.pc.manage;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.du.wx.model.WxNews;
import com.du.wx.model.activity.Activity;
import com.du.wx.model.activity.LetterInfo;
import com.du.wx.model.user.BaseUser;
import com.du.wx.pc.service.ActivityService;
import com.du.wx.pc.service.NewsService;
import com.du.wx.util.HttpUtils;
import com.du.wx.util.WeChatApiUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * 活动管理controller
 * @author jtj
 *
 */
@Controller
@RequestMapping(value = "/pc/manage/activity")
public class ActivityManageControl {
	
	@Resource
	public NewsService newsService ;
	
	@Resource
	public ActivityService activityService ;

	@RequestMapping(value = "/count/newsactivity")
	public void queryCount(HttpServletRequest request,HttpServletResponse response){
		int sendedCount = newsService.queryCountByStatus("1") ;
		int notSendCount = newsService.queryCountByStatus("0") ;
		int letterinfoCount = activityService.queryAllLetterInfo().size() ;
		
		JsonObject jsonObject = new JsonObject() ;
		jsonObject.addProperty("newsSended", sendedCount);
		jsonObject.addProperty("newsNotSend", notSendCount);
		jsonObject.addProperty("letterinfo", letterinfoCount);
		
		HttpUtils.springmvcWrite(response, jsonObject.toString());
	}
	
	@RequestMapping(value = "/query/all")
	public void queryInfos(HttpServletRequest request,HttpServletResponse response){
		String msgType = request.getParameter("msgType") ;
		String status = request.getParameter("status") ;
		if(msgType.equals("news")){
				List<WxNews> wxNews = newsService.queryNews(status) ;
				if(null==wxNews){
					HttpUtils.springmvcWrite(response, "null");
				}else{
					HttpUtils.springmvcWrite(response, new Gson().toJson(wxNews));
				}
			}else if(msgType.equals("letter")){
				List<LetterInfo> letterInfos = activityService.queryAllLetterInfo() ;
				if(null==letterInfos){
					HttpUtils.springmvcWrite(response, "null");
				}else{
					HttpUtils.springmvcWrite(response, new Gson().toJson(letterInfos));
				}
			}
	}
	
	@RequestMapping(value = "/query/some")
	public void querySome(HttpServletRequest request,HttpServletResponse response){
		String msgType = request.getParameter("msgType") ;
		String status = request.getParameter("status") ;
		String title = request.getParameter("title") ;
		if(msgType.equals("news")){
			List<WxNews> wxNews = newsService.queryNewsByTitle(title, status) ;
			if(null==wxNews){
				HttpUtils.springmvcWrite(response, "null");
			}else{
				HttpUtils.springmvcWrite(response, new Gson().toJson(wxNews));
			}
		}else if(msgType.equals("letter")){
			List<LetterInfo> letterInfos = activityService.queryLetterInfoByTitle(title) ;
			if(null==letterInfos){
				HttpUtils.springmvcWrite(response, "null");
			}else{
				HttpUtils.springmvcWrite(response, new Gson().toJson(letterInfos));
			}
		}
	}
	
	@RequestMapping(value = "/putboard")
	public void displayBoard(HttpServletRequest request,HttpServletResponse response){
		String content = request.getParameter("content") ;
		String title = request.getParameter("title") ;
		Activity activity = activityService.queryActByTitle(title) ;
		
		List<BaseUser> baseUsers = activityService.queryUsersByNoticeAct(String.valueOf(activity.getId())) ;
		List<String> openids = new ArrayList<>(baseUsers.size()) ;
		for(int i = 0; i < baseUsers.size() ; i++){
			openids.add(baseUsers.get(i).getOpenid()) ;
		}
		if(openids.size()<2){
			openids.add("test1") ;
		}
		WeChatApiUtil.sendToAll(openids, null, "text",content);
		
		//往前端推送关注该活动的人数
		HttpUtils.springmvcWrite(response, String.valueOf(baseUsers.size()));
	}
	
	@RequestMapping(value = "/query/letter")
	public void queryLetter(HttpServletRequest request,HttpServletResponse response){
		String title = request.getParameter("title") ;
		LetterInfo letterInfo = activityService.queryOneByTitle(title) ;
		if(null==letterInfo){
			HttpUtils.springmvcWrite(response, "null");
		}else{
			HttpUtils.springmvcWrite(response, new Gson().toJson(letterInfo));
		}
	}
	
	@RequestMapping(value = "/query/news")
	public void queryNews(HttpServletRequest request,HttpServletResponse response){
		String title = request.getParameter("title") ;
		WxNews wxNews = newsService.queryOneByTitle(title) ;
		if(null==wxNews){
			HttpUtils.springmvcWrite(response, "null");
		}else{
			HttpUtils.springmvcWrite(response, new Gson().toJson(wxNews));
		}
	}
}
