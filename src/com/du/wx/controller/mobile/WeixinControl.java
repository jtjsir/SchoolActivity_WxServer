package com.du.wx.controller.mobile;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.du.wx.core.service.WxDispatcherService;
import com.du.wx.util.SignUtil;
import com.du.wx.util.WeChatConfUtil;

/**
 * 微信接口验证controller
 * @author jtj
 *
 */
@Controller
@RequestMapping(value = "/wxCtrl")
public class WeixinControl {
	private static final Logger wxControlLogger = LogManager.getLogger("WeixinControl") ;
	@Resource
	public WxDispatcherService dispatcherService ;
	
	@RequestMapping(value="",method = RequestMethod.GET)
	public void validateWeixin(HttpServletRequest request,HttpServletResponse response){
		PrintWriter out = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        try {
            out = response.getWriter();
            //获取数字签名
            String signature = request.getParameter("signature");
            //获取时间戳
            String timestamp = request.getParameter("timestamp");
            //获取随机数
            String nonce = request.getParameter("nonce");
            //获取随机字符串，返回给微信
            String echostr = request.getParameter("echostr");

            String token = WeChatConfUtil.getWECHAT_TOKEN();
            if (SignUtil.check(signature, timestamp, nonce, token)) {
                out.println(echostr);
                wxControlLogger.info("微信验证成功，可以进行通信处理了~");
            }

        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
	}
	
	@RequestMapping(value = "",method = RequestMethod.POST)
	public void process(HttpServletRequest request,HttpServletResponse response){
		PrintWriter writer = null ;
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		try {
			writer = response.getWriter() ;
			//处理业务逻辑
			String result = dispatcherService.processMsg(request) ;
			if(null != result){
				wxControlLogger.debug("处理后返回的信息为: " + result);
				writer.write(result);
				writer.flush();
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
