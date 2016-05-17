package com.du.wx.controller.mobile;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 微信端主页controller
 * @author jtj
 *
 */
@Controller
@RequestMapping(value = "/mobile/index")
public class WxIndexControl {

	@RequestMapping(value = "")
	public String redirectToIndex(){
		return "/mobilepages/index" ;
	}
	
	@RequestMapping(value = "/activity")
	public String redirectToMenu1(){
		return "/mobilepages/activity_submit" ;
	}
	
	@RequestMapping(value = "/tpl/{html}")
	public String redirectToMenu(@PathVariable String html){
		return "/mobilepages/tpl/" + html ;
		
		
	}
	
}
