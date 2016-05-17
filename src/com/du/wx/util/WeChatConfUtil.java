package com.du.wx.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author jing
 */
public class WeChatConfUtil {
	public static final String FILE = "com/du/wx/resources/confs/conf.properties";
	public static final String WECHAT_TOKEN = "wechat.token";
	public static final String WECHAT_APPID = "wechat.testappid";
	public static final String WECHAT_APPSECRET = "wechat.testappsecret";
	public static final String WECHAT_HELLO_WORDS = "wechat.hello_words";
	public static final String FACE_APIKEY = "face.api_key";
	public static final String FACE_APTSECRET = "face.api_secret";
	
	//conf配置文件的输入流
	public static Properties confPro;

	public static String getFILE() {
		return getValue(FILE);
	}

	public static String getWECHAT_TOKEN() {
		return getValue(WECHAT_TOKEN);
	}

	public static String getWECHAT_APPID() {
		return getValue(WECHAT_APPID);
	}

	public static String getWECHAT_APPSECRET() {
		return getValue(WECHAT_APPSECRET);
	}

	public static String getWECHAT_HELLO_WORDS() {
		return getValue(WECHAT_HELLO_WORDS);
	}

	public static String getFACE_APIKEY() {
		return getValue(FACE_APIKEY);
	}

	public static String getFACE_APTSECRET() {
		return getValue(FACE_APTSECRET);
	}

	public static String getValue(String key) {
		if(confPro==null){
			confPro = new Properties() ;
			InputStream iStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(FILE);
			try {
				confPro.load(iStream);
			} catch (Exception e) {
				e.printStackTrace();
		}
		}
		return confPro.getProperty(key);
	}
	
	public static void main(String[] args){
		System.err.println(WeChatConfUtil.getWECHAT_APPID());
	}
}
