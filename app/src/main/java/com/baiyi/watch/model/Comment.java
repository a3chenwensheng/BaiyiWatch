package com.baiyi.watch.model;

/**
 * 评论
 * @author mxj
 * 
 */
public class Comment {
  private String comment_id;
  private String created_by;
  private String content;
  private String created_at;
  
  public String mCreatedBy;
  public String mCreatedAt;
  
  public Comment(){}
  
  public Comment(String created_by, String content, String created_at){
	  this.created_by = created_by;
	  this.content = content;
	  this.created_at = created_at;
  }
  
  public String getComment_id() {
		return comment_id;
	}
	public void setComment_id(String comment_id) {
		this.comment_id = comment_id;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
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
}
