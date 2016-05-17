package com.du.wx.controller.mobile.manage;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.du.wx.model.activity.Activity;
import com.du.wx.model.user.BaseUser;
import com.du.wx.pc.service.ActivityService;
import com.du.wx.util.HttpUtils;
import com.du.wx.util.StringUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/mobile/manage/act")
public class ActManageControl {

	@Resource
	public ActivityService activityService;

	@RequestMapping(value = "/query/mynotice")
	public void getMyNotice(HttpServletRequest request, HttpServletResponse response) {
		BaseUser baseUser = (BaseUser) (request.getSession().getAttribute("wechatUser"));
		 int uid = baseUser.getUid() ;

		List<Activity> activities = activityService.queryNoticeActs(String.valueOf(uid));
		if (null == activities) {
			HttpUtils.springmvcWrite(response, "null");
		} else {
			HttpUtils.springmvcWrite(response, new Gson().toJson(activities));
		}
	}

	@RequestMapping(value = "/query/mybooking")
	public void getMyBooking(HttpServletRequest request, HttpServletResponse response) {
		BaseUser baseUser = (BaseUser) (request.getSession().getAttribute("wechatUser"));
		 int uid = baseUser.getUid() ;
		List<Activity> activities = activityService.queryBookingActs(String.valueOf(uid));
		if (null == activities) {
			HttpUtils.springmvcWrite(response, "null");
		} else {
			HttpUtils.springmvcWrite(response, new Gson().toJson(activities));
		}

	}

	// 查询 近期所有活动
	@RequestMapping(value = "/query/near/all")
	public void getAllNear(HttpServletRequest request, HttpServletResponse response) {
		List<Activity> activities = activityService.queryAllActs();
		if (null == activities) {
			HttpUtils.springmvcWrite(response, "null");
		} else {
			HttpUtils.springmvcWrite(response, new Gson().toJson(activities));
		}
	}
	
	// 查询 我的历史活动
	@RequestMapping(value = "/query/near/some")
	public void getMyNear(HttpServletRequest request, HttpServletResponse response) {
		BaseUser baseUser = (BaseUser) (request.getSession().getAttribute("wechatUser"));
		 int uid = baseUser.getUid() ;
		List<Activity> activities = activityService.queryHistoryActsByUser(String.valueOf(uid));
		if (null == activities) {
			HttpUtils.springmvcWrite(response, "null");
		} else {
			HttpUtils.springmvcWrite(response, new Gson().toJson(activities));
		}
	}

	// 根据活动类别来查找活动内容列表
	@RequestMapping(value = "/query/type")
	public void getActsByType(HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("act_type");
		List<Activity> activities = activityService.queryActsByType(type);
		if (null == activities) {
			HttpUtils.springmvcWrite(response, "null");
		} else {
			HttpUtils.springmvcWrite(response, new Gson().toJson(activities));
		}
	}

	// 根据活动title来查找活动内容列表
	@RequestMapping(value = "/query/title")
	public void getActsByTitle(HttpServletRequest request, HttpServletResponse response) {
		String title = request.getParameter("act_title");
		Activity activities = activityService.queryActByTitle(title);
		if (null == activities) {
			HttpUtils.springmvcWrite(response, "null");
		} else {
			HttpUtils.springmvcWrite(response, new Gson().toJson(activities));
		}
	}

	// 推送一个月后的活动 活动预告
	@RequestMapping(value = "/query/advance")
	public void getActsByTime(HttpServletRequest request ,HttpServletResponse response) {
		List<Activity> activities = activityService.queryAllActs();
		List<Activity> oneMonthLaterActs = new ArrayList<>();
		// 筛选出一个月后的活动
		int size = activities.size();
		Activity activity = null;
		for (int i = 0; i < size; i++) {
			activity = activities.get(i);
			if (StringUtil.isDaysOutOfToday(activity.getTime(), 30)) {
				oneMonthLaterActs.add(activity);
			}
		}

		if (0 == oneMonthLaterActs.size()) {
			HttpUtils.springmvcWrite(response, "null");
		} else {
			HttpUtils.springmvcWrite(response, new Gson().toJson(oneMonthLaterActs));
		}
	}
}
