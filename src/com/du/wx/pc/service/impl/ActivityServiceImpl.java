package com.du.wx.pc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.du.wx.mapper.joggle.ActivityBookingDao;
import com.du.wx.mapper.joggle.ActivityDao;
import com.du.wx.mapper.joggle.ActivityHistoryDao;
import com.du.wx.mapper.joggle.ActivityNoticeDao;
import com.du.wx.mapper.joggle.LetterInfoDao;
import com.du.wx.mapper.joggle.WxNewsDao;
import com.du.wx.model.WxNews;
import com.du.wx.model.activity.Activity;
import com.du.wx.model.activity.ActivityBooking;
import com.du.wx.model.activity.ActivityNotice;
import com.du.wx.model.activity.LetterInfo;
import com.du.wx.model.user.BaseUser;
import com.du.wx.pc.service.ActivityService;

@Service
public class ActivityServiceImpl implements ActivityService{
	private static final Logger activityServiceLogger = LogManager.getLogger(ActivityServiceImpl.class.getSimpleName()) ;
	
	@Resource
	public WxNewsDao newsDao ;
	
	@Resource
	public ActivityDao activityDao ;
	
	@Resource
	public LetterInfoDao letterInfoDao ;
	
	@Resource
	public ActivityBookingDao bookingDao ;
	
	@Resource
	public ActivityNoticeDao noticeDao ;
	
	@Resource
	public ActivityHistoryDao historyDao ;

	@Override
	public void addInfos(WxNews news, Activity activity) {
		newsDao.addNews(news);
		activityServiceLogger.info("添加 "+ news.getAuthor() + " 的图文信息到数据库成功!");
		
		activityDao.addActivity(activity);
		activityServiceLogger.info("添加 " + activity.getTitle() + " 的活动到数据库成功");
	}

	@Override
	public void addLetterData(LetterInfo letterInfo) {
		letterInfoDao.addLetterInfo(letterInfo);
		activityServiceLogger.info("添加 " + letterInfo.getTitle() + " 的投稿信息到数据库成功");
	}

	@Override
	public List<LetterInfo> queryAllLetterInfo() {
		return letterInfoDao.queryAll() ;
	}

	@Override
	public List<LetterInfo> queryLetterInfoByTitle(String title) {
		return letterInfoDao.querySomeByTitle(title) ;
	}

	@Override
	public void addBookingInfo(ActivityBooking activityBooking) {
		bookingDao.addBookingInfo(activityBooking);
	}

	@Override
	public void addNoticeInfo(ActivityNotice activityNotice) {
		noticeDao.addNoticeInfo(activityNotice);
	}

	@Override
	public List<Activity> queryNoticeActs(String uid) {
		return noticeDao.queryActByUser(Integer.valueOf(uid));
	}

	@Override
	public List<Activity> queryBookingActs(String uid) {
		return bookingDao.queryActByUser(Integer.valueOf(uid));
	}

	@Override
	public void updateNotice(String uid, String activityId, String isnotice) {
		noticeDao.updateIsNotice(Integer.valueOf(uid), Integer.valueOf(activityId), Integer.valueOf(isnotice));
	}

	@Override
	public ActivityNotice queryNotice(String uid, String activityId) {
		return noticeDao.queryNotice(Integer.valueOf(uid), Integer.valueOf(activityId));
	}

	@Override
	public Activity queryActByTitle(String title) {
		return activityDao.queryAct(title);
	}

	@Override
	public List<BaseUser> queryUsersByNoticeAct(String activityid) {
		return noticeDao.queryUsersByAct(Integer.valueOf(activityid));
	}

	@Override
	public List<Activity> queryAllActs() {
		return activityDao.queryAllActs();
	}

	@Override
	public List<Activity> queryHistoryActsByUser(String uid) {
		return historyDao.queryActsByUser(Integer.valueOf(uid));
	}

	@Override
	public List<Activity> queryActsByType(String type) {
		return activityDao.queryActByType(type);
	}

	@Override
	public int queryCountBookingByAct(String activityid) {
		return bookingDao.queryCountBookingByAct(Integer.valueOf(activityid));
	}

	@Override
	public int queryCountNoticeByAct(String activityid) {
		return noticeDao.queryCountNoticeByAct(Integer.valueOf(activityid));
	}

	@Override
	public ActivityBooking queryBooking(String title, String uid) {
		Activity activity = activityDao.queryAct(title) ;
		int activityid = activity.getId() ;
		ActivityBooking booking = bookingDao.queryActBooking(Integer.valueOf(uid), activityid) ;
		
		return booking ;
	}

	@Override
	public void updateBooking(String uid, String activityid, String isnotice) {
		bookingDao.updateBooking(Integer.valueOf(uid), Integer.valueOf(activityid), Integer.valueOf(isnotice));
	}

	@Override
	public LetterInfo queryOneByTitle(String title) {
		return letterInfoDao.queryLetterByTitle(title);
	}

}
