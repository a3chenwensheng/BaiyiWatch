package com.baiyi.watch.model;

import java.io.Serializable;

public class Topic implements Serializable {

	private String topicId;
	private String backNews;
	private String createTime;
	private String firstReplyContent;
	private String firstReplyId;
	private String locked;
	private String replyCount;
	private String topicContent;
	private String topicTitle;
//	private String userId;
//	private String userName;
//	private String userPicture;
	private String view;
	
	private String isRelease;
	private String personId;
	private String userImageUrl;

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public String getBackNews() {
		return backNews;
	}

	public void setBackNews(String backNews) {
		this.backNews = backNews;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getFirstReplyContent() {
		return firstReplyContent;
	}

	public void setFirstReplyContent(String firstReplyContent) {
		this.firstReplyContent = firstReplyContent;
	}

	public String getFirstReplyId() {
		return firstReplyId;
	}

	public void setFirstReplyId(String firstReplyId) {
		this.firstReplyId = firstReplyId;
	}

	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	public String getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(String replyCount) {
		this.replyCount = replyCount;
	}

	public String getTopicContent() {
		return topicContent;
	}

	public void setTopicContent(String topicContent) {
		this.topicContent = topicContent;
	}

	public String getTopicTitle() {
		return topicTitle;
	}

	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}

	public String getIsRelease() {
		return isRelease;
	}

	public void setIsRelease(String isRelease) {
		this.isRelease = isRelease;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getUserImageUrl() {
		return userImageUrl;
	}

	public void setUserImageUrl(String userImageUrl) {
		this.userImageUrl = userImageUrl;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

}
