package com.du.wx.model.out;

import com.du.wx.model.common.BaseOutMessage;
import java.util.List;
import com.du.wx.model.Item;

public class ArticleOutMessage extends BaseOutMessage {

	// 图文
	private List<Item> Articles;
	private String ArticleCount;

	public List<Item> getArticles() {
		return Articles;
	}

	public void setArticles(List<Item> articles) {
		Articles = articles;
	}

	public String getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(String articleCount) {
		ArticleCount = articleCount;
	}

}
