package com.du.wx.mapper.joggle;

import com.du.wx.model.WxToken;

public interface WechatTokenDao {
	WxToken queryToken();
	
	void insertToken(WxToken wxToken) ;
	
	void updateToken(WxToken wxToken);
}
