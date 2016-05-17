package com.du.wx.util;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.du.wx.mapper.joggle.WechatTokenDao;
import com.du.wx.model.WxToken;
import com.google.gson.JsonObject;

public class TokenUtil {

	private static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
			"classpath:com/du/wx/resources/spring/springContext.xml");

	private static Map<String, WxToken> tokenMap = new ConcurrentHashMap<>();

	private static String tokenKey = "wechat_token";

	/**
	 * 获取access_token接口
	 * 
	 * @return
	 */
	public static WxToken getToken() {
		WxToken wxToken = null;
		if (isTokenOut()) {
			// token已经过期
			String queryUrl = WeChatConfUtil.getValue("wechat.access_token_url");
			String appid = WeChatConfUtil.getWECHAT_APPID();
			String appsecret = WeChatConfUtil.getWECHAT_APPSECRET();

			queryUrl = queryUrl.replace("APPID", appid).replace("APPSECRET", appsecret);

			JsonObject result = HttpUtils.processReq(queryUrl, "GET", null);
			// 返回无错误码
			if (result != null && result.get("errcode") == null) {
				wxToken = new WxToken();
				wxToken.setAccessToken(result.get("access_token").getAsString());
				wxToken.setExpiresIn(result.get("expires_in").getAsString());
				wxToken.setModifiedDate(new Date());
				tokenMap.put(tokenKey, wxToken);
				
				//更新数据库
				WechatTokenDao dao = context.getBean(WechatTokenDao.class) ;
				if(null!=dao.queryToken()){
					dao.updateToken(wxToken);
				}else{
					dao.insertToken(wxToken);
				}
			}
		} else {
			wxToken = tokenMap.get(tokenKey);
		}

		return wxToken;
	}

	/**
	 * 判断token是否过期
	 * 
	 * @return
	 */
	public static boolean isTokenOut() {
		boolean flag = false;

		WxToken token = null;

		if (null != tokenMap.get(tokenKey)) {
			token = tokenMap.get(tokenKey);
		} else {
			WechatTokenDao tokenDao = context.getBean(WechatTokenDao.class);
			token = tokenDao.queryToken();
			// 数据库表中没有数据
			if (null == token) {
				flag = true;
				return flag;
			}else{
				tokenMap.put(tokenKey, token) ;
			}
		}
		// 比较时间差
		Date modifiedTime = token.getModifiedDate();
		Date nowTime = new Date();
		long periodTime = (nowTime.getTime() - modifiedTime.getTime()) / 1000;
		if (periodTime >= Integer.valueOf(token.getExpiresIn())) {
			flag = true;
		}

		return flag;
	}
}
