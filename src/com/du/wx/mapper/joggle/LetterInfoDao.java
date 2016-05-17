package com.du.wx.mapper.joggle;

import java.util.List;

import com.du.wx.model.activity.LetterInfo;

public interface LetterInfoDao {

	void addLetterInfo(LetterInfo letterInfo) ;
	
	void deleteInfo(String letterTitle) ;
	
	List<LetterInfo> queryAll() ;
	
	List<LetterInfo> querySomeByTitle(String title) ;
	
	LetterInfo queryLetterByTitle(String title) ;
}
