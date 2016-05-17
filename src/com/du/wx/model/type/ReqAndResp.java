
package com.du.wx.model.type;

public class ReqAndResp {

	//7种请求类型+1=8
	public static enum reqType {

        text,image, voice,video,shortvideo,location,link,event;
    }

	//6种响应类型
    public static enum respType {

        text, image, voice, music, news, video;
    }
}
