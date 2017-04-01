package com.baiyi.watch.model;

import java.io.Serializable;

/**
 * 广告条实体
 *
 */
public class Banner implements Serializable {

	private String title;// 标题
	private String contentText;// 内容
	private String url;// 跳转Url

	public Banner(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContentText() {
		return contentText;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
