package com.du.wx.controller.mobile.activity;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.du.wx.model.activity.Activity;
import com.du.wx.model.activity.ActivityBooking;
import com.du.wx.model.activity.ActivityNotice;
import com.du.wx.model.activity.LetterInfo;
import com.du.wx.model.user.BaseUser;
import com.du.wx.pc.service.ActivityService;
import com.du.wx.util.HttpUtils;
import com.du.wx.util.StringUtil;
import com.du.wx.util.TokenUtil;
import com.du.wx.util.WeChatApiUtil;
import com.google.gson.JsonObject;

/**
 * 微信端活动controller
 */
@Controller
@RequestMapping(value = "/mobile/activity")
public class MobileActivityControl {

	private static final Logger mobileLogger = LogManager.getLogger(MobileActivityControl.class.getSimpleName());

	@Resource
	public ActivityService activityService;

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addActivity(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("picFile") MultipartFile pictureFile) {
		// 上传的图片不可大于2M
		if (pictureFile.getSize() > 2097152) {
			request.setAttribute("msg", "图片不得大于2M，请上传jpg/png格式图片！");
			return "/mobilepages/activity_submit" ;
		}
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		// letter_info表字段
		String type = request.getParameter("type");
		String time = request.getParameter("time");// 待组装修改
		String address = request.getParameter("address");
		String limit = request.getParameter("limit");
		String url = "";
		try {
			url = WeChatApiUtil.addPictureToWx(pictureFile.getInputStream(), pictureFile.getOriginalFilename(),
					TokenUtil.getToken().getAccessToken(),"media");
			mobileLogger.info(title + " 对应的图片在微信服务器的url是: " + url);
		} catch (IOException e) {
			e.printStackTrace();
		}

		LetterInfo letterInfo = new LetterInfo();
		BaseUser baseUser = ((BaseUser)(request.getSession().getAttribute("wechatUser"))) ;
		if(baseUser==null){
			//默认加载杜秋微信帐号
			letterInfo.setOpenid("o6FSVxCKKr2ZtMEi8PCJSpTMrTGQ");
		}else{
			letterInfo.setOpenid(baseUser.getOpenid());
		}
		letterInfo.setAddress(address);
		letterInfo.setContent(content);
		letterInfo.setStatus(0);
		letterInfo.setTime(StringUtil.stringToDate(time));
		letterInfo.setTitle(title);
		letterInfo.setType(type);
		letterInfo.setUrl(url);
		if("on".equals(limit)){
			limit = "1" ;
			String people = request.getParameter("people");
			letterInfo.setPeople(Integer.valueOf(people));
		}else{
			limit = "0" ;
		}
		letterInfo.setLetterlimit(Integer.valueOf(limit));

		activityService.addLetterData(letterInfo);
		request.setAttribute("msg", "投稿信息上传成功!");
		
		return "/mobilepages/activity_submit" ;
	}

	@RequestMapping(value = "/page/submit")
	public String redirectToSubmitPage() {
		return "/mobilepages/activity_submit";
	}

	@RequestMapping(value = "/notice")
	public void notice(HttpServletRequest request,HttpServletResponse response) {
		String title = request.getParameter("act_title");
		BaseUser baseUser = ((BaseUser) (request.getSession().getAttribute("wechatUser")));
		int uid = baseUser.getUid();
//		int uid = 2 ;
		String isnotice = request.getParameter("isnotice");// 0/1

		int activityid = activityService.queryActByTitle(title).getId() ;
		ActivityNotice activityNotice = new ActivityNotice();
		activityNotice.setActivityid(activityid);
		activityNotice.setUid(uid);
		activityNotice.setIsnotice(Integer.valueOf(isnotice));
		// 如果不存在则插入
		if (null == activityService.queryNotice(String.valueOf(uid), String.valueOf(activityid))) {
			activityService.addNoticeInfo(activityNotice);
		} else {
			activityService.updateNotice(String.valueOf(uid), String.valueOf(activityid), isnotice);
		}
		mobileLogger.info(baseUser.getNickname() +(isnotice=="0"?"取消预约":"预约") +" 了一个activityid为: "+ activityid + " 的活动");
	}

	@RequestMapping(value = "/isnotice")
	public void isnotice(HttpServletRequest request,HttpServletResponse response){
		String title = request.getParameter("act_title") ;
		Activity activity = activityService.queryActByTitle(title) ;
		int activityid = activity.getId() ;
		
		BaseUser baseUser = ((BaseUser) (request.getSession().getAttribute("wechatUser")));
		int uid = baseUser.getUid() ;
//		int uid = 2 ;
		ActivityNotice activityNotice = activityService.queryNotice(String.valueOf(uid), String.valueOf(activityid)) ;
		int isnotice = 0 ;
		if(null!=activityNotice){
			isnotice = activityNotice.getIsnotice() ;
		}
		//直接传给前台isnotice值
		HttpUtils.springmvcWrite(response, String.valueOf(isnotice));
	}
	
	@RequestMapping(value = "/booking")
	public void booking(HttpServletRequest request,HttpServletResponse response) {
		String title = request.getParameter("act_title");
		String isbooked = request.getParameter("isbooked") ;
		BaseUser baseUser = ((BaseUser) (request.getSession().getAttribute("wechatUser")));
		int uid = baseUser.getUid();
//		int uid = 2 ;

		Activity activity = activityService.queryActByTitle(title) ;
		int limit = activity.getActlimit() ;
		if(limit==0){
			HttpUtils.springmvcWrite(response, "0");
		}else{
			int people = activity.getPeople() ;
			int nowbookedCount = activityService.queryCountBookingByAct(String.valueOf(activity.getId())) ;
			if(nowbookedCount<people){
				if(activityService.queryBooking(title, String.valueOf(uid))==null){
					ActivityBooking activityBooking = new ActivityBooking() ;
					activityBooking.setActivityid(activityService.queryActByTitle(title).getId());
					activityBooking.setIsbooked(1);
					activityBooking.setUid(uid);
					activityService.addBookingInfo(activityBooking);
				}else{
					activityService.updateBooking(String.valueOf(uid), String.valueOf(activityService.queryActByTitle(title).getId()), isbooked);
				}
		mobileLogger.info(baseUser.getNickname() + " 预约了一个活动");
			}else{
				HttpUtils.springmvcWrite(response, "1");
			}
		}
	}
	
	@RequestMapping(value="/booking/limit")
	public void bookingLimit(HttpServletRequest request,HttpServletResponse response){
		String title = request.getParameter("act_title");
		BaseUser baseUser = ((BaseUser) (request.getSession().getAttribute("wechatUser")));

		Activity activity = activityService.queryActByTitle(title) ;
		int limit = activity.getActlimit() ;
		mobileLogger.info(baseUser.getNickname() + " 预约了一个活动");
		HttpUtils.springmvcWrite(response, String.valueOf(limit));
	}
	
	@RequestMapping(value="/isbooked")
	public void isBooked(HttpServletRequest request,HttpServletResponse response){
		String title = request.getParameter("act_title") ;
		int uid = 2 ;
		ActivityBooking booking = activityService.queryBooking(title, String.valueOf(uid)) ;
		int isbooked = 0 ;
		if(null!=booking){
			isbooked = booking.getIsbooked() ;
		}
		
		HttpUtils.springmvcWrite(response, String.valueOf(isbooked));
	}
	
	@RequestMapping(value = "/bookingCount")
	public void queryCountBooking(HttpServletRequest request,HttpServletResponse response){
		String title = request.getParameter("act_title") ;
		int BookedCount = activityService.queryCountBookingByAct(String.valueOf(activityService.queryActByTitle(title).getId())) ;
		
		JsonObject jsonObject = new JsonObject() ;
		jsonObject.addProperty("bookedcount", BookedCount);
		
		HttpUtils.springmvcWrite(response, jsonObject.toString());
	}
	
	@RequestMapping(value = "/noticeCount")
	public void queryCountNotice(HttpServletRequest request,HttpServletResponse response){
		String title = request.getParameter("act_title") ;
		int noticedCount = activityService.queryCountNoticeByAct(String.valueOf(activityService.queryActByTitle(title).getId())) ;
		
		
		HttpUtils.springmvcWrite(response, String.valueOf(noticedCount));
	}
	
	@RequestMapping(value = "/test")
	public String test(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("msg", "this is a message");
		return "/mobilepages/activity_submit" ;
	}
}
