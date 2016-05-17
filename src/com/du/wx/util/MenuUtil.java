package com.du.wx.util;

import com.du.wx.model.button.CommonButton;
import com.du.wx.model.button.ComplexButton;
import com.du.wx.model.button.Menu;
import com.du.wx.model.button.ViewButton;
import com.du.wx.model.common.Button;
import com.du.wx.model.type.BtnAttribute;

public class MenuUtil {
	
	/**
	 * 组建菜单
	 * @return
	 */
	public static Menu setMenu(){
		//近期活动
		ViewButton nearbtn = new ViewButton() ;
		nearbtn.setName("近期活动");
		nearbtn.setType(BtnAttribute.TYPE_VIEW);
		nearbtn.setUrl(BtnAttribute.URL_NEAR);
		
		//我的预约
		ViewButton mySubscribtn = new ViewButton() ;
		mySubscribtn.setName("我的预约");
		mySubscribtn.setType(BtnAttribute.TYPE_VIEW);
		mySubscribtn.setUrl(BtnAttribute.URL_SUBSCRIBE);
		
		//我的关注
		ViewButton myNoticebtn = new ViewButton() ;
		myNoticebtn.setName("我的关注");
		myNoticebtn.setType(BtnAttribute.TYPE_VIEW);
		myNoticebtn.setUrl(BtnAttribute.URL_NOTICE);
		
		//活动投稿
		ViewButton activitySubmitbtn = new ViewButton() ;
		activitySubmitbtn.setName("活动投稿");
		activitySubmitbtn.setType(BtnAttribute.TYPE_VIEW);
		activitySubmitbtn.setUrl(BtnAttribute.URL_ACTIVITY_SUBMIT);
		
		//关于我们
		CommonButton aboutUsBtn = new CommonButton() ;
		aboutUsBtn.setKey(BtnAttribute.BASE_KEY_ABOUT);
		aboutUsBtn.setName("关于我们");
		aboutUsBtn.setType(BtnAttribute.TYPE_CLICK);
		
		//我的
		ComplexButton myComplexButton = new ComplexButton() ;
		myComplexButton.setName("我的");
		myComplexButton.setSub_button(new Button[]{mySubscribtn,myNoticebtn});
		
		//更多
		ComplexButton moreComplexBtn = new ComplexButton() ;
		moreComplexBtn.setName("更多");
		moreComplexBtn.setSub_button(new Button[]{activitySubmitbtn,aboutUsBtn});
		
		//组成成菜单
		Menu menu = new Menu() ;
		menu.setButton(new Button[]{nearbtn,myComplexButton,moreComplexBtn});
		
		return menu ;
	}

	//获取主要菜单文字
	public static String getMainMenu(){
		StringBuilder builder = new StringBuilder() ;
		builder.append("感谢关注校园活动助手 !\ue32e\ue32e").append("\n\n")
		.append("回复\"JQHD\"").append(" -->").append(" 近期活动\n")
		.append("回复\"WDYY\"").append(" -->").append(" 我的预约\n")
		.append("回复\"WDGZ\"").append(" -->").append(" 我的关注\n")
		.append("回复\"GYWM\"").append(" -->").append(" 关于我们\n\n")
		.append("回复\"HELP\"").append(" 显示帮助菜单");
		
		return builder.toString() ;
	}
	
	//帮助菜单
	public static String getHelpMenu(){
		StringBuilder builder = new StringBuilder() ;
		builder.append("帮助菜单\ue415\ue415 ").append("\n\n")
		.append("回复\"JQHD\"").append(" -->").append(" 近期活动\n")
		.append("回复\"WDYY\"").append(" -->").append(" 我的预约\n")
		.append("回复\"WDGZ\"").append(" -->").append(" 我的关注\n")
		.append("回复\"GYWM\"").append(" -->").append(" 关于我们\n\n")
		.append("回复\"HELP\"").append(" 显示帮助菜单");
		
		return builder.toString() ;
	}
	
	//绑定邮箱链接
	public static String getHrefEmail(){
		StringBuilder builder  = new StringBuilder() ;
		builder.append("感谢关注校园活动助手 !\ue32e\ue32e").append("\n\n")
		.append("戳戳\ue231<a href='").append("").append("'>点击绑定邮箱</a>") ;
		
		return builder.toString() ;
	}
}
