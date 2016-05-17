package com.du.wx.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.du.wx.model.WxNews;
import com.du.wx.model.type.MaterialType;
import com.du.wx.util.MenuUtil;
import com.du.wx.util.TokenUtil;
import com.du.wx.util.WeChatApiUtil;

public class ApiTest {

	@Test
	public void testQueryToken(){
		System.err.println(TokenUtil.isTokenOut());
//		System.out.println(TokenUtil.getToken().getAccessToken()) ;
	}
	
	@Test
	public void testQueryMenu(){
		System.out.println(WeChatApiUtil.queryMenu(TokenUtil.getToken().getAccessToken()));
	}
	
	@Test
	public void testCreateMenu(){
		Assert.assertTrue(WeChatApiUtil.deleteMenu(TokenUtil.getToken().getAccessToken()));
		System.out.println(WeChatApiUtil.createMenu(MenuUtil.setMenu(), TokenUtil.getToken().getAccessToken())) ;
	}
	
	@Test
	public void testQueryUserInfos(){
		String jtjId = "o6FSVxCKKr2ZtMEi8PCJSpTMrTGQ" ;
		System.err.println(WeChatApiUtil.queryUserInfos(jtjId, TokenUtil.getToken().getAccessToken()).getHeadimgurl()) ;
	}
	
	@Test
	public void testAddactivity(){
		File file = new File("src/test.jpg") ;
		try {
			String returnUrl = WeChatApiUtil.addPictureToWx(new FileInputStream(file), file.getName(), TokenUtil.getToken().getAccessToken(),"file") ;
			System.err.println("返回来的链接为: " + returnUrl);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testQueryTempMedia(){
		File file = new File("src/test.jpg") ;
		try {
			String mediaId = WeChatApiUtil.uploadTempMedia(new FileInputStream(file), file.getName(), MaterialType.MATERIAL_THUMB,"file") ;
			System.err.println("mediaId is: " + mediaId);
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testQueryNews() throws FileNotFoundException{
		File file = new File("src/test.jpg") ;
		
		WxNews wxNews = new WxNews() ;
		wxNews.setAuthor("杜秋");
		wxNews.setContent("一起来探讨泡妞的技巧。");
//		wxNews.setThumbmediaid("ugpMOZZGGaJIawrp2TZp-4_Ezc6pSPTO4KLTsdYQrTkHc-1t64RUJHBDFu36jscd");
		wxNews.setThumbmediaid(WeChatApiUtil.uploadTempMedia(new FileInputStream(file), file.getName(), MaterialType.MATERIAL_THUMB,"file"));
		wxNews.setTitle("泡妞计划");
		List<WxNews> newsList = new ArrayList<>() ;
		newsList.add(wxNews) ;
		System.out.println(WeChatApiUtil.uploadNews(newsList));
	}
	
	@Test 
	public void testSend(){
		String media = "WbmnE--228s8FFiY67pDskWbvlsJ4CvhVDm_aMpmuTu4weeq8XcmKktSIO5aUB47" ;
		List<String> openids = new ArrayList<>() ;
		openids.add("o6FSVxIj_Skb1r8A4UL2GWW0fkVw") ;
		openids.add("o6FSVxCKKr2ZtMEi8PCJSpTMrTGQ") ;
		openids.add("o6FSVxM4TIEmGgi8GNu-l8ypH9LE") ;
		
		WeChatApiUtil.sendToAll(openids, media, "mpnews",null);
	}
}
