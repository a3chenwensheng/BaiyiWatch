package com.baiyi.watch.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Member4Show implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6081039344314226688L;

	private String _id;// ObjectId
	private String avatar_url;
	private String devices;
	private String nickname;
	private String username;

	private String group_name;
	private String group_id;
	private String group_ownerid;

	public String mId;
	public ArrayList<String> mDevices;

	public Member4Show() {
	}

	public Member4Show(String username) {
		this.username = username;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getAvatar_url() {
		return avatar_url;
	}

	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}

	public String getDevices() {
		return devices;
	}

	public void setDevices(String devices) {
		this.devices = devices;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public String getGroup_ownerid() {
		return group_ownerid;
	}

	public void setGroup_ownerid(String group_ownerid) {
		this.group_ownerid = group_ownerid;
	}

}
