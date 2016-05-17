package com.du.wx.pc.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.du.wx.mapper.joggle.LetterInfoDao;
import com.du.wx.pc.service.LetterService;

@Service
public class LetterServiceImpl implements LetterService{

	@Resource
	public LetterInfoDao letterInfoDao ;
	
	@Override
	public void deleteInfo(String title) {
		letterInfoDao.deleteInfo(title);
	}

}
