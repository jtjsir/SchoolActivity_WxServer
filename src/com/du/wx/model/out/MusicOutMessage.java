package com.du.wx.model.out;

import com.du.wx.model.Music;
import com.du.wx.model.common.BaseOutMessage;

public class MusicOutMessage extends BaseOutMessage {
	
	//music
    private Music Music ;

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}
    
    

}
