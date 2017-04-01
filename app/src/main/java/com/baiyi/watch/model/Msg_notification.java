package com.baiyi.watch.model;

import java.io.Serializable;

/**
 * # Msg 推送通知
 *
 */
public class Msg_notification implements Serializable {

	private String notif;//
	private String envvoice;
	private String info;

	public Notification mNotification;
	public Deviceenvvoice mEnvvoice;
	//public Info mInfo;

	public String getNotif() {
		return notif;
	}

	public void setNotif(String notif) {
		this.notif = notif;
	}

	public String getEnvvoice() {
		return envvoice;
	}

	public void setEnvvoice(String envvoice) {
		this.envvoice = envvoice;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
