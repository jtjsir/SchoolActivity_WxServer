package com.du.wx.model.type;

public class BtnAttribute {

	//基本button键名
	public static String BASE_KEY_ABOUT = "about_us" ;//关于我们
	
	//父级button键名
	public static String COMPLEX_KEY_ME = "my" ;//我的
	public static String COMPLEX_KEY_MORE = "more" ;//更多
	
	//button类型
	public static String TYPE_CLICK = "click" ;//点击事件
	public static String TYPE_VIEW = "view" ;//点击跳转url
	public static String TYPE_SCANCODE_PUSH = "scancode_push" ;//扫码推送事件
	public static String TYPE_SCANCODE_WAITMSG = "scancode_waitmsg" ;//扫码推事件且弹出“消息接收中”提示框
	public static String TYPE_PIC_SYSPHOTO = "pic_sysphoto" ;//弹出系统拍照发图
	public static String TYPE_PIC_PHOTO_OR_ALBUM = "pic_photo_or_album" ;//弹出拍照或者相册发图
	public static String TYPE_PIC_WEIXIN = "pic_weixin" ;//弹出微信相册发图器
	public static String TYPE_LOCATION_SELECT = "location_select" ;//弹出地理位置选择器
	public static String TYPE_MEDIA_ID = "media_id" ;//下发消息（除文本消息）
	public static String TYPE_VIEW_LIMITED = "view_limited" ;//跳转图文消息URL
	
	//url
	public static String URL_SUPER = "http://120.26.238.197:8080/wxServer" ;//父级url
	public static String URL_NEAR = URL_SUPER + "/mobile/index/" ;//近期活动
	public static String URL_SUBSCRIBE = URL_SUPER +"/mobile/index/tpl/reserve" ;//我的预约
	public static String URL_NOTICE = URL_SUPER + "/mobile/index/tpl/interest" ;//我的关注
	public static String URL_ACTIVITY_SUBMIT =URL_SUPER + "/mobile/index/activity" ;//活动投稿
	
}
