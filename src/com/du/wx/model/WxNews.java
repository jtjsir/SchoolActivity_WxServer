package com.du.wx.model;

import java.util.Date;

/**
 * 图文信息Entity
 * 
 * @author jtj
 *
 */
public class WxNews {

	private int id;
	private String title;
	private String thumb_media_id;
	private String author;
	private String content;
	private Date time;
	private String url;
	private int status;

	public WxNews() {

	}

	public WxNews(String title, String thmubmediaid, String author, String content, Date time, String url) {
		super();
		this.title = title;
		this.thumb_media_id = thmubmediaid;
		this.author = author;
		this.content = content;
		this.time = time;
		this.url = url;
	}

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

	public String getThumbmediaid() {
		return thumb_media_id;
	}

	public void setThumbmediaid(String thumbmediaid) {
		this.thumb_media_id = thumbmediaid;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
