package com.du.wx.model.activity;

import java.util.Date;

/**
 * 活动Entity
 * 
 * @author jtj
 *
 */
public class Activity {
	private int id;
	private String title;
	private String content;
	private String url;
	private String address;
	private Date time;
	private String type;
	private int status;
	private int actlimit ;//是否限制人数 0-不限制 1-限制
	private int people ;//限制的人数

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public int getActlimit() {
		return actlimit;
	}

	public void setActlimit(int actlimit) {
		this.actlimit = actlimit;
	}

	public int getPeople() {
		return people;
	}

	public void setPeople(int people) {
		this.people = people;
	}
	
}
