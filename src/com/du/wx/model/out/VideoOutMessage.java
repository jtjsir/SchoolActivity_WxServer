package com.du.wx.model.out;

import com.du.wx.model.Video;
import com.du.wx.model.common.BaseOutMessage;

public class VideoOutMessage extends BaseOutMessage {

	//video
    private Video Video ;

	public Video getVideo() {
		return Video;
	}

	public void setVideo(Video video) {
		Video = video;
	}
    
    
}
