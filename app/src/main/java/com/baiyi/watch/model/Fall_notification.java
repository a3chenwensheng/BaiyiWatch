package com.baiyi.watch.model;

import java.io.Serializable;

/**
 * # Fall 推送通知
 *
 */
public class Fall_notification implements Serializable {

	private String notif;//
	private String falldata;
	private String info;

	public Notification mNotification;
	public Falldata mFalldata;
	//public Info mInfo;

	public String getNotif() {
		return notif;
	}

	public void setNotif(String notif) {
		this.notif = notif;
	}

	public String getFalldata() {
		return falldata;
	}

	public void setFalldata(String falldata) {
		this.falldata = falldata;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	

}
