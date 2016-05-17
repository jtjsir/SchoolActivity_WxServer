package com.du.wx.mapper.joggle;

import java.util.List;

import com.du.wx.model.WxNews;

public interface WxNewsDao {

	void addNews(WxNews wxNews) ;
	
	int queryCountByStatus(int status) ;
	
	List<WxNews> queryNews(int status) ;
	
	List<WxNews> queryNewsByTitle(String title,int status) ;
	
	WxNews queryOneByTitle(String title) ;
	
	void deleteOneByTitle(String title) ;
}
