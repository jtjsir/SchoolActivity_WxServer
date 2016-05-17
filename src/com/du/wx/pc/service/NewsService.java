package com.du.wx.pc.service;

import java.util.List;

import com.du.wx.model.WxNews;

public interface NewsService {

	int queryCountByStatus(String status) ;
	
	List<WxNews> queryNews(String status) ;
	
	List<WxNews> queryNewsByTitle(String title,String status) ;
	
	WxNews queryOneByTitle(String title) ;
	
	void deleteOneNews(String title) ;
	
}
