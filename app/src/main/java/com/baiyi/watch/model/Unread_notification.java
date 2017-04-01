package com.baiyi.watch.model;

import java.io.Serializable;
import java.util.List;

public class Unread_notification implements Serializable {

	private String group_dev_notifi;
	private String imei_notif;
	
	public List<Group_dev_notifi> mGroupDevNotifiList;
	public Imei_notif mImeiNotif;

	public String getGroup_dev_notifi() {
		return group_dev_notifi;
	}

	public void setGroup_dev_notifi(String group_dev_notifi) {
		this.group_dev_notifi = group_dev_notifi;
	}

	public String getImei_notif() {
		return imei_notif;
	}

	public void setImei_notif(String imei_notif) {
		this.imei_notif = imei_notif;
	}

}
