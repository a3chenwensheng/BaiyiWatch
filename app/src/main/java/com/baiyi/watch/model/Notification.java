package com.baiyi.watch.model;

import java.io.Serializable;

/**
 * # notification 推送通知
 *
 */
public class Notification implements Serializable{

	private String _id;// ObjectId
	private String token_type;// 终端类型 ios:ios; android:android |
	private String token;// 设备标识
	private String title;// 标题
	private String content;// 内容
	private String group;// 关键组 | Reference: [Group](#group)
	private String recipient;// 接收人 | Reference: [Person](#person)
	private String created_at;// 提交时间 | DateTime
	private String updated_at;// 更新时间 | DateTime
	private String expired_at;// 过期时间 | DateTime
	private String sched_send_at;// 预期发送时间 | DateTime
	private String error_code;// 返回码 | Int
	private String error_info;// 返回信息
	private String is_completed;// 已完成 | Boolean
	private String is_cleared;// 已清除 | Boolean
	private String type;

	public String mId;
	public Group mGroup;
	public Person mRecipient;
	public String mCreatedAt;
	public String mUpdatedAt;
	public String mExpiredAt;
	public String mSchedSendAt;
	
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	public String getExpired_at() {
		return expired_at;
	}
	public void setExpired_at(String expired_at) {
		this.expired_at = expired_at;
	}
	public String getSched_send_at() {
		return sched_send_at;
	}
	public void setSched_send_at(String sched_send_at) {
		this.sched_send_at = sched_send_at;
	}
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	public String getError_info() {
		return error_info;
	}
	public void setError_info(String error_info) {
		this.error_info = error_info;
	}
	public String getIs_completed() {
		return is_completed;
	}
	public void setIs_completed(String is_completed) {
		this.is_completed = is_completed;
	}
	public String getIs_cleared() {
		return is_cleared;
	}
	public void setIs_cleared(String is_cleared) {
		this.is_cleared = is_cleared;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
