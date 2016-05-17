package com.du.wx.model.out;

import com.du.wx.model.Image;
import com.du.wx.model.common.BaseOutMessage;

public class PicOutMessage extends BaseOutMessage {
	//图片消息
    private String MediaId ;
    private Image Image ;
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public Image getImage() {
		return Image;
	}
	public void setImage(Image image) {
		Image = image;
	}
    
    
}
