package com.du.wx.model;

import java.util.Date;
/**
 * 微信access_token基类
 * @author jtj
 *
 */
public class WxToken {

	private String accessToken ;
	private String expiresIn ; 
	private Date modifiedTime ;
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
	public Date getModifiedDate() {
		return modifiedTime;
	}
	public void setModifiedDate(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	
	
	
}
