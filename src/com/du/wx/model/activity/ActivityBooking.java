package com.du.wx.model.activity;

/**
 * 预订活动Entity
 * 
 * @author jtj
 *
 */
public class ActivityBooking {

	private int bid;
	private int uid;// 用户id
	private int activityid;// 活动id
	private int isbooked ;

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getActivityid() {
		return activityid;
	}

	public void setActivityid(int activityid) {
		this.activityid = activityid;
	}

	public int getIsbooked() {
		return isbooked;
	}

	public void setIsbooked(int isbooked) {
		this.isbooked = isbooked;
	}
	
}
