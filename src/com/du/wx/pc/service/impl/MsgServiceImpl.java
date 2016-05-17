package com.du.wx.pc.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.du.wx.mapper.joggle.WxMsgDao;
import com.du.wx.model.WxMsg;
import com.du.wx.pc.service.MsgService;
import com.du.wx.util.StringUtil;

@Service
public class MsgServiceImpl implements MsgService {
	
	private static final Logger msgServiceLogger = LogManager.getLogger(MsgServiceImpl.class.getSimpleName()) ;
	
	@Resource
	public WxMsgDao wxMsgDao ;

	@Override
	public void updateStoreStatus(String id, String isstored) {
		int msgid = Integer.valueOf(id) ;
		int storeStatus = Integer.valueOf(isstored) ;
		
		wxMsgDao.updateIsStored(msgid, storeStatus);
		//打印log
		if(0==storeStatus){
			msgServiceLogger.info("id为: " + id + " 的消息没有被搜藏");
		}else{
			msgServiceLogger.info("id为: " + id + " 的消息被搜藏了");
		}
	}

	@Override
	public List<WxMsg> queryAllMsg() {
		return wxMsgDao.queryAllMsg() ;
	}

	@Override
	public List<WxMsg> querySomeMsg(String isstored) {
		int storeStatus = Integer.valueOf(isstored) ;
		
		return wxMsgDao.querySomeMsgByStored(storeStatus);
	}

	@Override
	public List<WxMsg> queryTimeMsg(String days,String msgType) {
		List<WxMsg> msgs = null ;
		
		int dayoutofToday = Integer.valueOf(days) ;
		if(msgType.equals("all")){
			msgs = queryAllMsg() ;
		}else if(msgType.equals("some")){
			msgs = querySomeMsg("1") ;
		}
		
		List<WxMsg> selectMsg = new ArrayList<>() ;
		int size = msgs.size() ;
		WxMsg wxMsg = null ;
		for(int i = 0; i < size ;i++){
			wxMsg = msgs.get(i) ;
			if(StringUtil.isDaysInOfToday(wxMsg.getTime(), dayoutofToday)){
				selectMsg.add(wxMsg) ;
			}
		}
		
		return selectMsg;
	}

}
