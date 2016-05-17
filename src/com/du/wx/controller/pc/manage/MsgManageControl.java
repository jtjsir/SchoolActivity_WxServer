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

import com.du.wx.core.service.WxDispatcherService;
import com.du.wx.model.WxMsg;
import com.du.wx.model.out.TextOutMessage;
import com.du.wx.model.type.ReqAndResp.respType;
import com.du.wx.model.user.BaseUser;
import com.du.wx.pc.service.BaseUserService;
import com.du.wx.pc.service.MsgService;
import com.du.wx.util.HttpUtils;
import com.du.wx.util.WeChatApiUtil;
import com.google.gson.Gson;

/**
 * 消息管理controller
 * @author jtj
 *
 */
@Controller
@RequestMapping(value = "/pc/manage/msg")
public class MsgManageControl {
	private static final Logger msgManageControLogger = LogManager.getLogger(MsgManageControl.class.getSimpleName()) ;
	
	@Resource 
	public MsgService msgService ;
	
	@Resource
	public WxDispatcherService dispatchrService ;
	
	@Resource
	public BaseUserService baseUserService ;
	
	@RequestMapping(value="/store")
	public void storeMsg(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("msgid") ;
		String isstored = request.getParameter("isstored") ;
		
		msgService.updateStoreStatus(id, isstored);
	}
	
	@RequestMapping(value = "/querydays")
	public void queryTimeMsg(HttpServletRequest request,HttpServletResponse response){
		String days = request.getParameter("days") ;
		String msgType = request.getParameter("msgType") ;
		List<WxMsg> msgs = msgService.queryTimeMsg(days,msgType) ;
		if(msgs==null){
			HttpUtils.springmvcWrite(response, "null");
		}else{
			HttpUtils.springmvcWrite(response, new Gson().toJson(msgs));
		}
	}
	
	@RequestMapping(value = "/reply")
	public void reply(HttpServletRequest request,HttpServletResponse response){
		String openid = request.getParameter("openid") ;
		String content = request.getParameter("content") ;
		
		//目前采用文字交流
		TextOutMessage outMessage = new TextOutMessage() ;
		outMessage.setContent(content);
		outMessage.setCreateTime(System.currentTimeMillis());
		outMessage.setFromUserName("gh_3ebcf2364060");
		outMessage.setToUserName(openid);
		outMessage.setMsgType(respType.text.toString());
//		String result = dispatchrService.replyDirect(outMessage, respType.text) ;
		
		List<String> openids = new ArrayList<>(3) ;
		openids.add(openid) ;
		openids.add("test2") ;
		openids.add("test3") ;
		WeChatApiUtil.sendToAll(openids, null, "text", content);
		//打印日志
		BaseUser baseUser = baseUserService.queryBaseUser(openid) ;
		msgManageControLogger.info("微信测试号向昵称为 " + baseUser.getNickname() + " 的用户发送了以下消息: " + content);
	}
	
	@RequestMapping(value="/querystore")
	public void queryStoreInfo(){
		
	}
}
