package com.du.wx.model.input;

import com.du.wx.model.common.BaseInputMessage;

public class VoiceInputMessage extends BaseInputMessage{

	//语音消息
    private String MediaId ;
    private String Format ;
    private String Recognition ;
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getFormat() {
		return Format;
	}
	public void setFormat(String format) {
		Format = format;
	}
	public String getRecognition() {
		return Recognition;
	}
	public void setRecognition(String recognition) {
		Recognition = recognition;
	}
    
    
}
