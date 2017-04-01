package com.baiyi.watch.model;

import java.io.Serializable;

public class TsModel implements Serializable{

	public String type;
	public String groupId;
	public String imei;
	public String personId;
	public String url;

	public TsModel() {
	}

	public TsModel(String type, String groupId) {
		this.type = type;
		this.groupId = groupId;
	}

	public TsModel(String type, String groupId, String imei, String personId, String url) {
		this.type = type;
		this.groupId = groupId;
		this.imei = imei;
		this.personId = personId;
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
