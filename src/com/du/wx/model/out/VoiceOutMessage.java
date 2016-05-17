package com.du.wx.model.out;

import com.du.wx.model.common.BaseOutMessage;
import com.du.wx.model.Voice;

public class VoiceOutMessage extends BaseOutMessage {

	// voice
	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}

}
