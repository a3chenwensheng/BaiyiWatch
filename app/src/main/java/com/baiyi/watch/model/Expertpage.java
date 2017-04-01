package com.baiyi.watch.model;

import java.io.Serializable;
import java.util.List;

/**
 * # expertpage专家资源
 *
 */
public class Expertpage implements Serializable {
	
	private String _id;// ObjectId
	private String group;// 拥有组 | Reference: [Group](#group)
	private String created_by;// 创建人 | Reference: [Person](#person)
	private String created_at;// 创建时间 | DateTime
	private String updated_at;// 更新时间 | DateTime
	private String subject;// 标题
	private String type;// 资源类型(新增) text:text; photo:photo; voice:voice; other:other |
	private String comments;// 评论 | List: Embedded [PageComment](#pagecomment) 
	private String stars;// 赞 | List: Embedded [PageStar](#pagestar)
	private String is_published;// 是否发布 | Boolean
	private String body;// 文章内容
	private String filename;// 图片文件名
	private String media_length;// 文件长度 | Int
	private String url;// 图片下载地址 
	private String thumb_url;// 缩略图下载地址
	
	public Group mGroup;
	public Person mCreatedBy;
	public String mCreatedAt;
	public String mUpdateAt;
	public List<PageComment> mComments;
	//public List<PageStar> mStars;
	
	public String getGroup() {
		return group;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public void setGroup(String group) {
		this.group = group;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getStars() {
		return stars;
	}
	public void setStars(String stars) {
		this.stars = stars;
	}
	public String getIs_published() {
		return is_published;
	}
	public void setIs_published(String is_published) {
		this.is_published = is_published;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getMedia_length() {
		return media_length;
	}
	public void setMedia_length(String media_length) {
		this.media_length = media_length;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getThumb_url() {
		return thumb_url;
	}
	public void setThumb_url(String thumb_url) {
		this.thumb_url = thumb_url;
	}
	
}
