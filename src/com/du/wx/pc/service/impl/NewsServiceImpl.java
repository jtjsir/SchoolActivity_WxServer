package com.du.wx.pc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.du.wx.mapper.joggle.WxNewsDao;
import com.du.wx.model.WxNews;
import com.du.wx.pc.service.NewsService;

@Service
public class NewsServiceImpl implements NewsService {
	
	@Resource
	public WxNewsDao wxNewsDao ;

	@Override
	public int queryCountByStatus(String status) {
		int newsCount = wxNewsDao.queryCountByStatus(Integer.valueOf(status)) ;
		return newsCount;
	}

	@Override
	public List<WxNews> queryNews(String status) {
		return wxNewsDao.queryNews(Integer.valueOf(status));
	}

	@Override
	public List<WxNews> queryNewsByTitle(String title, String status) {
		return wxNewsDao.queryNewsByTitle(title, Integer.valueOf(status));
	}

	@Override
	public WxNews queryOneByTitle(String title) {
		return wxNewsDao.queryOneByTitle(title);
	}

	@Override
	public void deleteOneNews(String title) {
		wxNewsDao.deleteOneByTitle(title);
	}


}
