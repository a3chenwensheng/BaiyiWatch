package com.baiyi.watch.model;

public class Communitymessage {
	
	
	private String _id;
	private String _cls;
	private String subject;//标题
	private String sender;//发起用户
	private String sender_note;//发起留言
	private String recipient;//接收用户
	private String created_at;
	private String result;//处理结果  	open:待处理; expired:过期; cancel:取消; accept:接受; reject:拒绝; close:结
	private String recipient_note;//接收人留言
	private String community;//社区
	private String type;//类型（community_leave, community_invite, community_join）
	
	public String mId;
	public String mSenderId;
	public String mRecipientId;
	public String mCreatedAt;
	public String mCommunityId;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String get_cls() {
		return _cls;
	}
	public void set_cls(String _cls) {
		this._cls = _cls;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getSender_note() {
		return sender_note;
	}
	public void setSender_note(String sender_note) {
		this.sender_note = sender_note;
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
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getRecipient_note() {
		return recipient_note;
	}
	public void setRecipient_note(String recipient_note) {
		this.recipient_note = recipient_note;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
