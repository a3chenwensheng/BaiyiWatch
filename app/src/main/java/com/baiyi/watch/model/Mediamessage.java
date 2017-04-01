package com.baiyi.watch.model;

/**
 * 
 * @author mxj
 *
 */
public class Mediamessage extends Message {
	private String _id; // ObjectId
	private String subject; // 标题
	private String sender; // 发送用户 | Reference
	private String $sender; // 发送用户 | Reference
	private String recipient; // 接收用户 | Reference
	private String $recipient; // 接收用户 | Reference
	private String created_at; // 记录时间
	private String send_at; // 发送时间
	private String send_by; // 发送终端
	private String read_at; // 读取时间
	private String sender_deleted; // 发送者删除 boolean
	private String sender_deleted_at; // 发送者删除时间
	private String recipient_deleted; // 接收者删除
	private String recipient_deleted_at; // 接收者删除时间

	private String media_type;// 类型
    private String media_length;// 长度
    private String filename;// 文件名
    private String url;// 下载地址
    private String _cls; //附加信息
    
    
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String get$sender() {
		return $sender;
	}
	public void set$sender(String $sender) {
		this.$sender = $sender;
	}
	public String get$recipient() {
		return $recipient;
	}
	public void set$recipient(String $recipient) {
		this.$recipient = $recipient;
	}
	public String getSender_deleted() {
		return sender_deleted;
	}
	public String getRecipient_deleted() {
		return recipient_deleted;
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
	public String getSend_at() {
		return send_at;
	}
	public void setSend_at(String send_at) {
		this.send_at = send_at;
	}
	public String getSend_by() {
		return send_by;
	}
	public void setSend_by(String send_by) {
		this.send_by = send_by;
	}
	public String getRead_at() {
		return read_at;
	}
	public void setRead_at(String read_at) {
		this.read_at = read_at;
	}
	public String isSender_deleted() {
		return sender_deleted;
	}
	public void setSender_deleted(String sender_deleted) {
		this.sender_deleted = sender_deleted;
	}
	public String getSender_deleted_at() {
		return sender_deleted_at;
	}
	public void setSender_deleted_at(String sender_deleted_at) {
		this.sender_deleted_at = sender_deleted_at;
	}
	public String isRecipient_deleted() {
		return recipient_deleted;
	}
	public void setRecipient_deleted(String recipient_deleted) {
		this.recipient_deleted = recipient_deleted;
	}
	public String getRecipient_deleted_at() {
		return recipient_deleted_at;
	}
	public void setRecipient_deleted_at(String recipient_deleted_at) {
		this.recipient_deleted_at = recipient_deleted_at;
	}
	public String getMedia_type() {
		return media_type;
	}
	public void setMedia_type(String media_type) {
		this.media_type = media_type;
	}
	public String getMedia_length() {
		return media_length;
	}
	public void setMedia_length(String media_length) {
		this.media_length = media_length;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String get_cls() {
		return _cls;
	}
	public void set_cls(String _cls) {
		this._cls = _cls;
	}

}
