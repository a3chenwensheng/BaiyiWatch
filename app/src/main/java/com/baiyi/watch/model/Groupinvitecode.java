package com.baiyi.watch.model;

import java.io.Serializable;

public class Groupinvitecode implements Serializable{
	private String _id;// ObjectId
	private String code;// 
	private String group;// 拥有人 | Reference
	private String $group;// 创建时间 | DateTime
	
	public String mId;
	public Group mGroup;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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

}
