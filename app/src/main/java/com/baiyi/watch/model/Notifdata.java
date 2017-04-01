package com.baiyi.watch.model;

import java.io.Serializable;

public class Notifdata implements Serializable {

	private String $creater;
	private String $group;
	private String $recipient;
	private String _id;
	private String content;
	private String created_at;
	private String creater;
	private String group;
	private String notif_type;
	private String recipient;
	private String title;
	private String token;
	private String token_type;
	
	public String mId;
	public String mCreatedAt;
	public String mGroupId;
	public String mRecipientId;

	public String get$creater() {
		return $creater;
	}

	public void set$creater(String $creater) {
		this.$creater = $creater;
	}

	public String get$group() {
		return $group;
	}

	public void set$group(String $group) {
		this.$group = $group;
	}

	public String get$recipient() {
		return $recipient;
	}

	public void set$recipient(String $recipient) {
		this.$recipient = $recipient;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getNotif_type() {
		return notif_type;
	}

	public void setNotif_type(String notif_type) {
		this.notif_type = notif_type;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

}
