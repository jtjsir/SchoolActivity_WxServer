package com.du.wx.controller.pc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 主页面
 * @author jtj
 *
 */
@Controller
@RequestMapping(value = "/pc/index")
public class PcIndexControl {

	@RequestMapping(value = "/login")
	public String redirectToLogin(HttpServletRequest request){
		return "/pcpages/login" ;
	}
	
	@RequestMapping(value = "")
	public String redirectToIndex(){
		return "/pcpages/index" ;
	}
	
	//子页面跳转拦截
	@RequestMapping(value = "/tpl/{html}")
	public String redirectToMenu(@PathVariable String html){
		return "/pcpages/tpl/" + html ;
	}
}
