package com.baiyi.watch.model;

import java.io.Serializable;

/**
 * # pagecomment评论
 *
 */
public class PageComment implements Serializable{
	private String created_by;// 创建人 | Reference: [Person](#person)
	private String created_at;// 创建时间 | DateTime
	private String comment_id;// 内部编号
	private String content;// 回复
	
	public String mCreatedBy;
	public String mCreatedAt;
	
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
	public String getComment_id() {
		return comment_id;
	}
	public void setComment_id(String comment_id) {
		this.comment_id = comment_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
