package com.baiyi.watch.model;

import java.io.Serializable;

public class Care implements Serializable {

	private String alert_count;
	private String location_count;
	private String phone_count;

	public String getAlert_count() {
		return alert_count;
	}

	public void setAlert_count(String alert_count) {
		this.alert_count = alert_count;
	}

	public String getLocation_count() {
		return location_count;
	}

	public void setLocation_count(String location_count) {
		this.location_count = location_count;
	}

	public String getPhone_count() {
		return phone_count;
	}

	public void setPhone_count(String phone_count) {
		this.phone_count = phone_count;
	}

}
