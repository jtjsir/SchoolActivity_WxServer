package com.du.wx.model;

import java.util.Date;

/**
 * 微信msgEntity
 * @author jtj
 *
 */
public class WxMsg {

	private int id;
	private String openid;
	private String headimgurl ;
	private String nickname ;
	private String msg;
	private Date time;
	private int isstored ;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getIsstored() {
		return isstored;
	}

	public void setIsstored(int isstored) {
		this.isstored = isstored;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
}
