package com.du.wx.util;

import java.io.InputStream;
import java.util.List;

import com.du.wx.model.WxNews;
import com.du.wx.model.button.Menu;
import com.du.wx.model.type.MaterialType;
import com.du.wx.model.user.BaseUser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class WeChatApiUtil {
	
	/**
	 * 创建菜单api
	 * @param menu
	 * @param accessToken
	 * @return errmsg "OK"-代表创建成功
	 */
	public static String createMenu(Menu menu,String accessToken){
		String resultMsg = "" ;
		String create_menu_url = WeChatConfUtil.getValue("wechat.menu_create_url") ;
		String url = create_menu_url.replace("ACCESS_TOKEN", accessToken) ;
		
		Gson gson = new Gson() ;
		String menuStr = gson.toJson(menu) ;
		
		JsonObject resultJson = HttpUtils.processReq(url, "POST", menuStr) ;
		if(resultJson!=null){
            if(resultJson.get("errcode").getAsInt()!=0){
                System.out.println("创建菜单失败");
            }
            resultMsg = resultJson.get("errmsg").getAsString() ;
        }
        
        return resultMsg ;
	}
	
	/**
	 * 查询菜单接口
	 * @param accessToken
	 * @return
	 */
	public static String queryMenu(String accessToken){
		String queryMenuUrl = WeChatConfUtil.getValue("wechat.menu_query_url") ;
		queryMenuUrl = queryMenuUrl.replace("ACCESS_TOKEN", accessToken) ;
		
		JsonObject resultJson = HttpUtils.processReq(queryMenuUrl, "GET", null) ;
		
		return resultJson.toString() ;
	}
	
	/**
	 * 删除menu
	 * @param accessToken
	 * @return
	 */
	public static boolean deleteMenu(String accessToken){
		boolean flag = true ;
		String deleteMenuUrl = WeChatConfUtil.getValue("wechat.menu_delete_url") ;
		deleteMenuUrl = deleteMenuUrl.replace("ACCESS_TOKEN", accessToken) ;
		
		JsonObject resultJson = HttpUtils.processReq(deleteMenuUrl, "GET", null) ;
		if(resultJson!=null){
			if(resultJson.get("errcode").getAsInt()!=0){
				flag = false ;
			}
		}
		return flag ;
	}
	
	/**
	 * 获取用户的基本信息api
	 * @param openid	用户的openId
	 * @param accessToken
	 * @return
	 */
	public static BaseUser queryUserInfos(String openid,String accessToken){
		BaseUser user = null ;
		
		String queryUrl = WeChatConfUtil.getValue("wechat.user_info_url") ;
		queryUrl = queryUrl.replace("OPENID", openid).replace("ACCESS_TOKEN", accessToken) ;
		
		JsonObject jsonObject = HttpUtils.processReq(queryUrl, "GET", null) ;
		if(jsonObject.get("subscribe").getAsInt()==1){
			user = new BaseUser() ;
			user.setOpenid(jsonObject.get("openid").getAsString());
			user.setNickname(jsonObject.get("nickname").getAsString());
			user.setAddress(jsonObject.get("city").getAsString());
			user.setHeadimgurl(jsonObject.get("headimgurl").getAsString());
		}
		return user ;
	}
	
	/**
	 * 上传图片到微信以获取永久链接
	 * @param picStream
	 * @param filename
	 * @param accessToken
	 * @param type	获取url用"media" 获取mediaid用"file"
	 * @return
	 */
	public static String addPictureToWx(InputStream picStream,String filename ,String accessToken,String type){
		String returnPicUrl = null ;
		
		String uploadUrl = WeChatConfUtil.getValue("wechat.upload_others_url") ;
		uploadUrl = uploadUrl.replace("ACCESS_TOKEN", TokenUtil.getToken().getAccessToken()).replace("TYPE", MaterialType.MATERIAL_IMAGE) ;
		JsonObject jsonObject = null;
		try {
			jsonObject = HttpUtils.processReqFile(picStream, filename, uploadUrl,type) ;
			if(null!=jsonObject.get("url")){
				returnPicUrl = jsonObject.get("url").getAsString() ;
			}
		} catch (Exception e) {
			System.err.println(jsonObject.toString());
			e.printStackTrace();
		}
		
		return returnPicUrl ;
	}
	
	/**
	 * 上传媒体文件,获取临时mediaid
	 * @param iStream
	 * @param filename
	 * @param mediaType		不包括图文素材
	 * @param type file
 	 * @return
	 */
	public static String uploadTempMedia(InputStream iStream,String filename ,String mediaType,String type){
		String mediaId = null ;
		
		String requestUrl = WeChatConfUtil.getValue("wechat.upload_temp_url") ;
		
		requestUrl = requestUrl.replace("ACCESS_TOKEN", TokenUtil.getToken().getAccessToken())
				.replace("TYPE", mediaType);
		
		try {
			JsonObject jsonObject = HttpUtils.processReqFile(iStream, filename, requestUrl,type) ;
			if(null==jsonObject.get("errcode")){
				if(mediaType.equals(MaterialType.MATERIAL_IMAGE)){
					mediaId = jsonObject.get("media_id").getAsString() ;
				}else if(mediaType.equals(MaterialType.MATERIAL_THUMB)){
					mediaId = jsonObject.get(mediaType + "_media_id").getAsString() ;
				}
			}else{
				System.out.println(jsonObject.get("errcode").getAsString()) ;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mediaId ;
	}
	
	/**
	 * 上传图文素材
	 * @param newsList	图文信息集合
	 * @return	图文信息mediaid
	 */
	public static String  uploadNews(List<WxNews> newsList){
		String mediaId = null ;
		
		String requestUrl = WeChatConfUtil.getValue("wechat.upload_news_url") ;
		requestUrl  = requestUrl.replace("ACCESS_TOKEN", TokenUtil.getToken().getAccessToken()) ;
		//处理图文素材
		Gson gson = new Gson() ;
		JsonObject totoalJson = new JsonObject() ;
		totoalJson.add("articles", gson.toJsonTree(newsList));
		
		String outputStr =  gson.toJson(totoalJson) ;
		
		JsonObject resultJson = HttpUtils.processReq(requestUrl, "POST", outputStr) ;
		if(resultJson.get("errcode")==null){
			mediaId = resultJson.get("media_id").getAsString() ;
		}
		
		return mediaId ;
	}
	
	//群发接口
	public static void sendToAll(List<String> openids,String mediaId,String msgType,String content){
		String requestUrl = WeChatConfUtil.getValue("wechat.send_all_url") ;
		requestUrl = requestUrl.replace("ACCESS_TOKEN", TokenUtil.getToken().getAccessToken()) ;
		
		Gson gson = new Gson() ;
		JsonObject jsonObject = new JsonObject() ;
		if(msgType.equals("mpnews")){
			//分装成json对象
			jsonObject.add("touser", gson.toJsonTree(openids)) ;
			JsonObject mediaJson = new JsonObject() ;
			mediaJson.addProperty("media_id", mediaId);
			jsonObject.add("mpnews", mediaJson);
			jsonObject.addProperty("msgtype", msgType);
		}else if(msgType.equals("text")){
			jsonObject.add("touser", gson.toJsonTree(openids));
			jsonObject.addProperty("msgtype", msgType);
			JsonObject textJson = new JsonObject() ;
			textJson.addProperty("content", content);
			jsonObject.add("text", textJson);
		}
		
		String outputStr = jsonObject.toString() ;
		JsonObject resultJson = HttpUtils.processReq(requestUrl, "POST", outputStr) ;
		if(resultJson!=null){
			System.out.println(resultJson.get("errmsg").getAsString());
		}
	};
}
