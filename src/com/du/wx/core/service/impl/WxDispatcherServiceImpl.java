package com.du.wx.core.service.impl;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.du.wx.core.service.WxDispatcherService;
import com.du.wx.mapper.joggle.BaseUserDao;
import com.du.wx.mapper.joggle.WxMsgDao;
import com.du.wx.model.WxMsg;
import com.du.wx.model.common.BaseOutMessage;
import com.du.wx.model.input.SummaryInputMessage;
import com.du.wx.model.out.TextOutMessage;
import com.du.wx.model.type.BtnAttribute;
import com.du.wx.model.type.EventType;
import com.du.wx.model.type.ReqAndResp;
import com.du.wx.model.type.ReqAndResp.reqType;
import com.du.wx.model.type.ReqAndResp.respType;
import com.du.wx.model.user.BaseUser;
import com.du.wx.pc.service.BaseUserService;
import com.du.wx.util.MenuUtil;
import com.du.wx.util.MessageUtil;
import com.du.wx.util.TokenUtil;
import com.du.wx.util.WeChatApiUtil;

/**
 * 核心处理消息类
 * @author jtj
 *
 */
@Service
public class WxDispatcherServiceImpl implements WxDispatcherService{

	private static final Logger wxDispatcherLogger = LogManager.getLogger("WxDispatcherServiceImpl") ;
	
	@Resource
	public BaseUserDao baseUserDao ;
	
	@Resource
	public WxMsgDao msgDao ;
	
	@Override
	public String processMsg(HttpServletRequest request) {
		String resultMsg = null ;
		SummaryInputMessage inputMessage = null ;
		try {
			//获取微信推送内容
			inputMessage = MessageUtil.readMessge(request.getInputStream()) ;
			wxDispatcherLogger.debug("处理的消息类型为: " + inputMessage.getMsgType());
			reqType requestType = ReqAndResp.reqType.valueOf(inputMessage.getMsgType()) ;
			resultMsg = reply(requestType, inputMessage,request) ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return resultMsg ;
	}
	
	private String reply(ReqAndResp.reqType reqType,SummaryInputMessage inputMessage,HttpServletRequest request){
		String resultInfo = null ;
		
		switch (reqType) {
		case text:
			wxDispatcherLogger.debug("微信发送的内容为: " + inputMessage.getContent());
			resultInfo = dealWithText(inputMessage);
			break;
		case image:
			break;
		case voice:
			wxDispatcherLogger.debug("微信发送的语音内容为: " + inputMessage.getRecognition());
			resultInfo = dealWithVoice(inputMessage) ;
			break ;
		case video:
			break ;
		case shortvideo:
			break;
		case location:
			break;
		case link:
			break;
		case event:
			String event = inputMessage.getEvent() ;
			resultInfo = dealWithEvent(event,inputMessage,request) ;
			break ;
		default:
			break;
		}
		return resultInfo ;
	}

	//处理文本消息
	private String dealWithText(SummaryInputMessage inputMessage) {
		TextOutMessage outMessage = new TextOutMessage() ;
		outMessage.setCreateTime(inputMessage.getCreateTime());
		outMessage.setFromUserName(inputMessage.getToUserName());
		outMessage.setMsgType(respType.text.toString());
		outMessage.setToUserName(inputMessage.getFromUserName());
		
		String result = null;
		String content = inputMessage.getContent() ;
		StringBuilder msgBuilder = new StringBuilder() ;
		
		//文本信息插入
		BaseUser baseUser = baseUserDao.queryUserInfoById(inputMessage.getFromUserName()) ;
		WxMsg wxMsg = new WxMsg() ;
		wxMsg.setIsstored(0);
		wxMsg.setMsg(content);
		wxMsg.setOpenid(baseUser.getOpenid());
		wxMsg.setTime(new Date());
		wxMsg.setHeadimgurl(baseUser.getHeadimgurl());
		wxMsg.setNickname(baseUser.getNickname());
		msgDao.insertMsg(wxMsg);
		
		if("JQHD".equalsIgnoreCase(content)){
			msgBuilder.append("戳戳\ue231查看").append("<a href='").append(BtnAttribute.URL_NEAR).append("'>").append("近期活动").append("</a>") ;
		}else if("WDYY".equalsIgnoreCase(content)){
			msgBuilder.append("戳戳\ue231查看").append("<a href='").append(BtnAttribute.URL_SUBSCRIBE).append("'>").append("我的预约").append("</a>") ;
		}else if("WDGZ".equalsIgnoreCase(content)){
			msgBuilder.append("戳戳\ue231查看").append("<a href='").append(BtnAttribute.URL_NOTICE).append("'>").append("我的关注").append("</a>") ;
		}else if("GYWM".equalsIgnoreCase(content)){
			msgBuilder.append(MenuUtil.getMainMenu()) ;
		}else if("HELP".equalsIgnoreCase(content)||"?".equals(content)||"？".equals(content)){
			msgBuilder.append(MenuUtil.getHelpMenu()) ;
		}else{
			msgBuilder.append("请输入正确的 提示文字\ue416") ;
		}
		outMessage.setContent(msgBuilder.toString());
		
		try {
			result = MessageUtil.handMsg(outMessage, respType.text) ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result ;
	}
	
	private String dealWithVoice(SummaryInputMessage inputMessage){
		String dealResult = null ;
		
		TextOutMessage om = new TextOutMessage() ;
		om.setCreateTime(inputMessage.getCreateTime());
		om.setFromUserName(inputMessage.getToUserName());
		om.setMsgType(respType.text.toString());
		om.setToUserName(inputMessage.getFromUserName());
		try {
            if (inputMessage.getRecognition() == null || inputMessage.getRecognition().equals("")) {
                om.setContent("请大声点,我听不清撒~");
            } else {
                StringBuilder voiceBuilder = new StringBuilder();
                voiceBuilder.append("你所说的话是: \n").append(inputMessage.getRecognition()
                ).append("\n 我说的没错吧~ /得意 ");
                om.setContent(voiceBuilder.toString());
            }
		}catch(Exception e){
			e.printStackTrace();
		}
		try {
			dealResult =  MessageUtil.handMsg(om, respType.text) ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return dealResult ;
	}

	//处理事件消息
	private String dealWithEvent(String event,SummaryInputMessage inputMessage,HttpServletRequest request){
		String result = null ;
		
		if(event.equals(EventType.EVENT_CLICK)){
			result = processClickEvent(inputMessage.getEventKey(),inputMessage);
		}else if(event.equals(EventType.EVENT_LOCATION)){
			
		}else if(event.equals(EventType.EVENT_SCAN)){
			
		}else if(event.equals(EventType.EVENT_SUBSCRIBE)){
			//插入数据库
			BaseUser user = null ;
			user = WeChatApiUtil.queryUserInfos(inputMessage.getFromUserName(), TokenUtil.getToken().getAccessToken()) ;
			if(null!=user){
				baseUserDao.insertUser(user);
			}
			//封装消息
			String msg =  MenuUtil.getMainMenu() ;
			TextOutMessage outMessage = new TextOutMessage() ;
			outMessage.setContent(msg);
			outMessage.setCreateTime(inputMessage.getCreateTime());
			outMessage.setFromUserName(inputMessage.getToUserName());
			outMessage.setMsgType(respType.text.toString());
			outMessage.setToUserName(inputMessage.getFromUserName());
			
			try {
				result = MessageUtil.handMsg(outMessage, respType.text) ;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(event.equals(EventType.EVENT_UNSUBSCRIBE)){
			//删除不关注者的记录
			baseUserDao.deleteUser(inputMessage.getFromUserName());
		}
		else if(event.equals(EventType.EVENT_VIEW)){
			//页面跳转链接需要绑定用户
			if(request.getSession().getAttribute("wechatUser")==null){
				request.getSession().setAttribute("wechatUser", baseUserDao.queryUserInfoById(inputMessage.getFromUserName()));
			}
		}
		
		return result ;
	}

	public String processClickEvent(String clickKey,SummaryInputMessage inputMessage) {
		String result = null ;
		if(clickKey.equals(BtnAttribute.BASE_KEY_ABOUT)){
			//关于我们点击事件的回复语言
			String msg =  MenuUtil.getMainMenu() ;
			TextOutMessage outMessage = new TextOutMessage() ;
			outMessage.setContent(msg);
			outMessage.setCreateTime(inputMessage.getCreateTime());
			outMessage.setFromUserName(inputMessage.getToUserName());
			outMessage.setMsgType(respType.text.toString());
			outMessage.setToUserName(inputMessage.getFromUserName());
			
			try {
				result = MessageUtil.handMsg(outMessage, respType.text) ;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return result ;
	}

	@Override
	public String replyDirect(BaseOutMessage outMessage, respType resptype) {
		String result = null ;
		try {
			result = MessageUtil.handMsg(outMessage, resptype) ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result ;
	}

}
