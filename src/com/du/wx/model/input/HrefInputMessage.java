package com.du.wx.model.input;

import com.du.wx.model.common.BaseInputMessage;

public class HrefInputMessage extends BaseInputMessage {

	//链接消息
    private String Title ;
    private String Description ;
    private String Url ;
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
    
    
}
