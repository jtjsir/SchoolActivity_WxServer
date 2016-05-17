package com.du.wx.resources.test;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

import com.du.wx.model.Video;
import com.du.wx.model.input.SummaryInputMessage;
import com.du.wx.model.out.VideoOutMessage;
import com.du.wx.model.type.ReqAndResp.respType;
import com.du.wx.util.MessageUtil;

public class MessageTest {

	private static final String wx_test_file = "com/du/wx/resources/test/wx_test.xml" ;
	@Before
	public void setUp(){
		
	}
	
	@Test
	public void testOutMessage(){
//		InputStream iStream = this.getClass().getClassLoader().getResourceAsStream(wx_test_file) ;
		try {
//			SummaryInputMessage inputMessage = MessageUtil.readMessge(iStream) ;
			VideoOutMessage outMessage = new VideoOutMessage() ;
			outMessage.setCreateTime(123l);
			outMessage.setFromUserName("jingtj");
			outMessage.setMsgType("video");
			outMessage.setToUserName("duqiu");
			Video video = new Video() ;
			video.setDescription("this is a test");
			video.setMediaId("123");
			video.setTitle("test_title");
			outMessage.setVideo(video);
			System.out.println(MessageUtil.handMsg(outMessage, respType.video)) ;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
