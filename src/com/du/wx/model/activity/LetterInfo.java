package com.du.wx.model.activity;

import java.util.Date;

/**
 * 投稿信息表
 * @author jtj
 *
 */
public class LetterInfo {
	
	private int id ;
	private String openid ;
	private String title ;
	private String content ;
	private String url ;
	private String address ;
	private Date time ;
	private String type ;
	private int status;
	private int letterlimit ;//是否限制人数 0-不限制 1-限制
	private int people ;//限制的人数
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getLetterlimit() {
		return letterlimit;
	}
	public void setLetterlimit(int letterlimit) {
		this.letterlimit = letterlimit;
	}
	public int getPeople() {
		return people;
	}
	public void setPeople(int people) {
		this.people = people;
	}
}
