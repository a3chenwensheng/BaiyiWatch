package com.baiyi.watch.model;

import java.util.List;

/**
 * # servicepage 用户服务
 *
 */
public class Servicepage {

	private String _id;// ObjectId
	private String group;// 拥有组 | Reference: [Group](#group)
	private String created_by;// 创建人 | Reference: [Person](#person)
	private String created_at;// 创建时间 | DateTime
	private String updated_at;// 更新时间 | DateTime
	private String subject;// 标题
	private String type;// 资源类型(新增) text:text; photo:photo; voice:voice; other:other |
	private String comments;// 评论 | List: Embedded [PageComment](#pagecomment)
	private String stars;// 赞 | List: Embedded [PageStar](#pagestar)
	private String user;// 反馈用户 | Reference: [Person](#person)
	private String body;// 反馈内容
	private String is_closed;// 是否关闭 | Boolean
	
	public String mId;
	public Group mGroup;
	public String mCreatedBy;
	public String mCreatedAt;
	public String mUpdatedAt;
	public List<PageComment> mComments;
	//public List<PageStar> mStars;
	//public Person mUser;
	
	
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
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getIs_closed() {
		return is_closed;
	}
	public void setIs_closed(String is_closed) {
		this.is_closed = is_closed;
	}
	
}
