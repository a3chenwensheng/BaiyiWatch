package com.baiyi.watch.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Community implements Serializable{
	private String _id;// ObjectId
	private String name;// 社区名称
	private String email;// 企业邮箱
	private String telephone;// 客服电话
	private String administrators;// 社区管理员 | List: Reference [Person](#person)
	private String created_at;// 创建时间 | DateTime
	
	public String mId;
	public String mCreatedAt;
	public ArrayList<Member> mAdministrators;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getAdministrators() {
		return administrators;
	}
	public void setAdministrators(String administrators) {
		this.administrators = administrators;
	}

	
	
}
