package com.baiyi.watch.model;

import java.io.Serializable;

public class Imei_notif implements Serializable {

	private String imei;
	private String fall_num;
	private String fence_num;
	private String sos_num;
	
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getFall_num() {
		return fall_num;
	}
	public void setFall_num(String fall_num) {
		this.fall_num = fall_num;
	}
	public String getFence_num() {
		return fence_num;
	}
	public void setFence_num(String fence_num) {
		this.fence_num = fence_num;
	}
	public String getSos_num() {
		return sos_num;
	}
	public void setSos_num(String sos_num) {
		this.sos_num = sos_num;
	}

}
