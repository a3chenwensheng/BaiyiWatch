package com.baiyi.watch.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Serializable{
	private String _id;// ObjectId
	private String owner;// 拥有人 | Reference
	private String $owner;// 拥有人 | Reference
	private String created_at;// 创建时间 | DateTime
	private String name;// 组名称 | String
	private String member_num;// 成员数 | Int
	private String is_public;// 是否公开 | Boolean
	private String members; //成员对象列表
	private String $members;
	private String community;// 社区 | Reference
	private String $community;// 社区 | Reference
	
	public String mId;
	public String mOwnerId;
	public Person mOwner;
	public String mCreatedAt;
	public ArrayList<Member> mMemebers; //成员对象列表
	public String mCommunityId;
	
	public String iscurrent;
	
	public Community mCommunity;

	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMember_num() {
		return member_num;
	}
	public void setMember_num(String member_num) {
		this.member_num = member_num;
	}
	public String getIs_public() {
		return is_public;
	}
	public void setIs_public(String is_public) {
		this.is_public = is_public;
	}
	public String getMembers() {
		return members;
	}
	public void setMembers(String members) {
		this.members = members;
	}
	public String get$members() {
		return $members;
	}
	public void set$members(String $members) {
		this.$members = $members;
	}
	public String get$owner() {
		return $owner;
	}
	public void set$owner(String $owner) {
		this.$owner = $owner;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	public String get$community() {
		return $community;
	}
	public void set$community(String $community) {
		this.$community = $community;
	}
	
}
