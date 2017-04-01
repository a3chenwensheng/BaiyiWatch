package com.baiyi.watch.model;

import java.io.Serializable;

/**
 * # Fence 推送通知
 *
 */
public class Fence_notification implements Serializable {

	private String notif;//
	private String fence;
	private String locationdata;

	public Notification mNotification;
	public SettingFence mFence;
	public Locationdata mLocationdata;

	public String getNotif() {
		return notif;
	}

	public void setNotif(String notif) {
		this.notif = notif;
	}

	public String getFence() {
		return fence;
	}

	public void setFence(String fence) {
		this.fence = fence;
	}

	public String getLocationdata() {
		return locationdata;
	}

	public void setLocationdata(String locationdata) {
		this.locationdata = locationdata;
	}

}
