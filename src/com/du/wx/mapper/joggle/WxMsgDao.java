package com.du.wx.mapper.joggle;

import java.util.List;

import com.du.wx.model.WxMsg;

public interface WxMsgDao {

	void insertMsg(WxMsg wxMsg) ;
	
	void deleteMsgById(int id) ;
	
	List<WxMsg> queryAllMsg() ;
	
	List<WxMsg> querySomeMsgByStored(int isstored) ;
	
	void updateIsStored(int id,int storeStatus) ;
	
}
