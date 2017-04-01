package com.baiyi.watch.model;

import java.util.ArrayList;

/**
 * mxj
 * @author Administrator
 *
 */
public class Page {
	public static final int TYPE_TEXT = 0;
	public static final int TYPE_MEDIA = 1;
	public int mType;
	
	private String _id;// ObjectId
	private String group;// 拥有组 | Reference
	private String $group;// 拥有组 | Reference
	private String $created_by;// 创建人 | Reference
	private String created_by;// 创建人 | Reference
	private String created_at;// 创建时间
	private String updated_at;// 更新时间
	private String subject;// 标题
	private String body;// 内容
	private	String media_type;// 媒体类型
	private String media_file;// 媒体文件名
	private String media_url;// 媒体URL
	private String url; 

	public String mId;
	public String mGroupId;
	public Group mGroup;
	public Person mCreatedBy;
	public String mCreatedById;
	public String mCreatedAt;
	public String mUpdateAt;
	
	public ArrayList<Comment> mComments;
	public ArrayList<Star> mStars;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String get$group() {
		return $group;
	}
	public void set$group(String $group) {
		this.$group = $group;
	}
	public String get$created_by() {
		return $created_by;
	}
	public void set$created_by(String $created_by) {
		this.$created_by = $created_by;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
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
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getMedia_type() {
		return media_type;
	}
	public void setMedia_type(String media_type) {
		this.media_type = media_type;
	}
	public String getMedia_file() {
		return media_file;
	}
	public void setMedia_file(String media_file) {
		this.media_file = media_file;
	}
	public String getMedia_url() {
		return media_url;
	}
	public void setMedia_url(String media_url) {
		this.media_url = media_url;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
