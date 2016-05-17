package com.du.wx.controller.pc.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.du.wx.core.service.WxDispatcherService;
import com.du.wx.model.WxNews;
import com.du.wx.model.activity.Activity;
import com.du.wx.model.type.MaterialType;
import com.du.wx.model.user.BaseUser;
import com.du.wx.pc.service.ActivityService;
import com.du.wx.pc.service.BaseUserService;
import com.du.wx.pc.service.LetterService;
import com.du.wx.pc.service.NewsService;
import com.du.wx.util.StringUtil;
import com.du.wx.util.TokenUtil;
import com.du.wx.util.WeChatApiUtil;

/**
 * pc端活动群发controller 主动发送
 * 
 * @author jtj
 *
 */
@Controller
@RequestMapping(value = "/pc/activity")
public class PcActivityControl {
	private static final Logger pcActivityControLogger = LogManager.getLogger(PcActivityControl.class.getName());

	@Resource
	public WxDispatcherService dWxDispatcherService;

	@Resource
	public ActivityService activityService;

	@Resource
	public BaseUserService baseUserService;

	@Resource
	public LetterService letterService;

	@Resource
	public NewsService newsService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addNews(@RequestParam("imgFile") MultipartFile imgFile, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if (imgFile.getSize() > 2097152) {
			request.setAttribute("msg", "图片不得大于2M，请上传jpg/png格式图片！");
			return;
		}

		// 图文信息表字段
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		// String showCoverPic = "1" ;//默认显示
		String content = request.getParameter("content");
		File file = new File("src/test.jpg");
		imgFile.transferTo(file);
		String thmubMediaId = WeChatApiUtil.uploadTempMedia(new FileInputStream(file), file.getName(),
				MaterialType.MATERIAL_THUMB, "file");
		pcActivityControLogger.info(title + " 对应的图文素材mediaId: " + thmubMediaId);

		// activity表字段
		String type = request.getParameter("type");
		String time = request.getParameter("time");// 待组装修改
		String address = request.getParameter("address");
		String limit = request.getParameter("limit");
		String url = WeChatApiUtil.addPictureToWx(new FileInputStream(file), imgFile.getOriginalFilename(),
				TokenUtil.getToken().getAccessToken(), "media");
		pcActivityControLogger.info(title + " 对应的图片在微信服务器的url是: " + url);

		WxNews wxNews = new WxNews(title, thmubMediaId, author, content, StringUtil.stringToDate(time), url);
		Activity activity = new Activity();
		activity.setAddress(address);
		activity.setContent(content);
		activity.setStatus(0);
		activity.setTime(StringUtil.stringToDate(time));
		activity.setTitle(title);
		activity.setType(type);
		activity.setUrl(url);

		// 0-不群发，1-群发
		String isSend = request.getParameter("isSend");

		// 默认图文消息未发送
		int sendStatus = 0;
		String msg = "";
		if ("1".equals(isSend)) {
			// 得到图文mediaid
			List<WxNews> news = new ArrayList<>();
			news.add(wxNews);
			String mediaid = WeChatApiUtil.uploadNews(news);

			// 群发
			List<BaseUser> baseUsers = baseUserService.queryAll();
			List<String> openids = new ArrayList<>(baseUsers.size());
			for (int i = 0; i < baseUsers.size(); i++) {
				openids.add(baseUsers.get(i).getOpenid());
				pcActivityControLogger.info("正在发送给 [openid为: " + baseUsers.get(i).getOpenid() + " ,nickname为: "
						+ baseUsers.get(i).getNickname() + " 的用户] 发送图文消息mediaid---->" + mediaid);
			}
			WeChatApiUtil.sendToAll(openids, mediaid, "mpnews", null);
			sendStatus = 1;
			msg = "并群发消息成功！";
		}
		if ("on".equals(limit)) {
			limit = "1";
			String people = request.getParameter("people");
			activity.setPeople(Integer.valueOf(people));
		} else {
			limit = "0";
		}
		activity.setActlimit(Integer.valueOf(limit));
		wxNews.setStatus(sendStatus);
		// 保存操作放在最后
		activityService.addInfos(wxNews, activity);

		request.setAttribute("msg", "图文信息上传成功!" + msg);
	}

	@RequestMapping(value = "/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");
		String title = request.getParameter("title");
		if ("letter".equals(type)) {
			letterService.deleteInfo(title);
		} else if ("news".equals(type)) {
			newsService.deleteOneNews(title);
		}
	}
}
