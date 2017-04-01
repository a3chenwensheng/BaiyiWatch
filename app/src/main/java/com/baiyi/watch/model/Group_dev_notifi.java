package com.baiyi.watch.model;

import java.io.Serializable;

public class Group_dev_notifi implements Serializable {

	private String imei;
	private String notif_num;

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getNotif_num() {
		return notif_num;
	}

	public void setNotif_num(String notif_num) {
		this.notif_num = notif_num;
	}

}
