package com.du.wx.model.button;

import com.du.wx.model.common.Button;

/**
 *
 * @author jing
 */
public class ViewButton extends Button {

	private String url;
	private String type;

	public String getUrl() {
		return url ;
	}

	public void setUrl(String url) {
		this.url = (url==null||url.equals(""))?"http://mail.qq.com":url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
