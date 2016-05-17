package com.du.wx.model.out;

import com.du.wx.model.common.BaseOutMessage;

public class TextOutMessage extends BaseOutMessage {
	// 文本消息
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

}
