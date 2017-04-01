package com.baiyi.watch.model;


public class Textpage extends Page {
	private String _id;// ObjectId
	private String group;// 拥有组 | Reference
	private String $group;// 拥有组 | Reference
	private String created_at;// 创建时间
	private String updated_at;// 更新时间

	private String comments;
	private String _cls;
    private String stars;

	private String $created_by;// 创建人 | Reference
	private String created_by;// 创建人 | Reference
	private String subject;// 标题
	private String body;

	public String toString(){
		return "created_by:"+created_by+";updated_at:"+updated_at
				+";subject:"+subject+";body:"+body+";comments:"+comments+";mCreatedBy:"+mCreatedBy
				+";mUpdateAt:"+mUpdateAt;
	}
	
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
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String get_cls() {
		return _cls;
	}
	public void set_cls(String _cls) {
		this._cls = _cls;
	}
	public String getStars() {
		return stars;
	}
	public void setStars(String stars) {
		this.stars = stars;
	}
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
}
