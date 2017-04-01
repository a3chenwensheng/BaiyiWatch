package com.baiyi.watch.model;

import java.io.Serializable;

/**
 * # SOS 推送通知
 *
 */
public class Sos_notification implements Serializable {

	private String notif;//
	private String sosdata;

	public Notification mNotification;
	public Sosdata mSosdata;

	public String getNotif() {
		return notif;
	}

	public void setNotif(String notif) {
		this.notif = notif;
	}

	public String getSosdata() {
		return sosdata;
	}

	public void setSosdata(String sosdata) {
		this.sosdata = sosdata;
	}

}
