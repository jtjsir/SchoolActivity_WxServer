package com.du.wx.core.service;

import javax.servlet.http.HttpServletRequest;

import com.du.wx.model.common.BaseOutMessage;
import com.du.wx.model.type.ReqAndResp;

public interface WxDispatcherService {

	public String processMsg(HttpServletRequest request) ;
	
	public String replyDirect(BaseOutMessage outMessage,ReqAndResp.respType resptype) ;

}
