package com.baiyi.watch.model;

/***
 * 
 * @author mxj
 *
 */
public class Star {
   private String created_by; 
   private String created_at;
   
   public String mCreatedBy;
   public String mCreatedAt;
   
   public Star(String createdBy, String createdAt){
	   mCreatedBy = createdBy;
	   mCreatedAt = createdAt;
   }
   
   public Star(){}
   
   public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		mCreatedBy = created_by;
		this.created_by = created_by;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		mCreatedAt = created_at;
		this.created_at = created_at;
	}
}
