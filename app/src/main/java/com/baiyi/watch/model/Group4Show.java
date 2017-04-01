package com.baiyi.watch.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Group4Show implements Serializable {
	private String id;
	private String ownerid;
	private String name;
	private String persons;
	private String devices;

	public String mId;
	public String mOwnerId;
	public ArrayList<Member4Show> mMemebers; // 成员对象列表
	public ArrayList<Device> mDevices; // 设备对象列表

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(String ownerid) {
		this.ownerid = ownerid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPersons() {
		return persons;
	}

	public void setPersons(String persons) {
		this.persons = persons;
	}

	public String getDevices() {
		return devices;
	}

	public void setDevices(String devices) {
		this.devices = devices;
	}

}
