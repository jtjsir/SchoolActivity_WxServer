package com.du.wx.model.activity;

/**
 * 活动关注表
 * @author jtj
 *
 */
public class ActivityNotice {
	private int id ;
	private int uid ;
	private int activityid ;
	private int isnotice ;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getIsnotice() {
		return isnotice;
	}
	public void setIsnotice(int isnotice) {
		this.isnotice = isnotice;
	}
	

}
