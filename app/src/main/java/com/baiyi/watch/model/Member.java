package com.baiyi.watch.model;

import java.io.Serializable;

public class Member implements Serializable{
	private String _id;// ObjectId
	private String $oid;// 
	private String group;// 组 | Reference
	private String $group;
	private String person;// 成员 | Reference
	private String $person;
	private String member_name;// 关系 | String
	
	public String mId;
	public String mGroupId;
	public String mPersonId;
	public Group mGroup;
	public Person mPerson;
	
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String get$oid() {
		return $oid;
	}
	public void set$oid(String $oid) {
		this.$oid = $oid;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	public String get$person() {
		return $person;
	}
	public void set$person(String $person) {
		this.$person = $person;
	}
	public String get$group() {
		return $group;
	}
	public void set$group(String $group) {
		this.$group = $group;
	}
	
	/**
	 * 获取成员的腕表ID
	 * @param member
	 * @return
	 */
	public String getMemberDeviceId()
	{
		if(this.mPerson == null){
			return null;
		}
		
		if(this.mPerson.mDevices == null ||this.mPerson.mDevices.size() == 0){
			return null;
		}
		
		if(this.mPerson.mDevices.get(0) == null){
			return null;
		}
		
		return this.mPerson.mDevices.get(0).mId;
	}
}
