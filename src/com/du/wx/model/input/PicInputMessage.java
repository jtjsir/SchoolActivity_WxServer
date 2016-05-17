package com.du.wx.model.input;

import com.du.wx.model.common.BaseInputMessage;

public class PicInputMessage extends BaseInputMessage {

	//图片消息
    private String PicUrl ;

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
    
    
}
