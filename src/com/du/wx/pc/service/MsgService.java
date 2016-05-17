package com.du.wx.pc.service;

import java.util.List;

import com.du.wx.model.WxMsg;

public interface MsgService {
	void updateStoreStatus(String id,String isstored) ;
	
	List<WxMsg> queryAllMsg() ;
	
	List<WxMsg> querySomeMsg(String isstored) ;
	
	List<WxMsg> queryTimeMsg(String days,String msgType) ;
}
