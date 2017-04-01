package com.baiyi.watch.model;

import java.io.Serializable;

public class News implements Serializable {

	private String newsId;
	private String newsDate;
	private String newsSource;
	private String newsTitle;
	private String newsTitleUrl;
	private String newsBannerUrl;

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public String getNewsDate() {
		return newsDate;
	}

	public void setNewsDate(String newsDate) {
		this.newsDate = newsDate;
	}

	public String getNewsSource() {
		return newsSource;
	}

	public void setNewsSource(String newsSource) {
		this.newsSource = newsSource;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getNewsTitleUrl() {
		return newsTitleUrl;
	}

	public void setNewsTitleUrl(String newsTitleUrl) {
		this.newsTitleUrl = newsTitleUrl;
	}

	public String getNewsBannerUrl() {
		return newsBannerUrl;
	}

	public void setNewsBannerUrl(String newsBannerUrl) {
		this.newsBannerUrl = newsBannerUrl;
	}

}
