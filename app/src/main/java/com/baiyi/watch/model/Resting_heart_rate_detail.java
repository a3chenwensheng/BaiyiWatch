package com.baiyi.watch.model;

import java.io.Serializable;

public class Resting_heart_rate_detail implements Serializable {

	private String heartRate;
	private String heartDate;

	public String getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(String heartRate) {
		this.heartRate = heartRate;
	}

	public String getHeartDate() {
		return heartDate;
	}

	public void setHeartDate(String heartDate) {
		this.heartDate = heartDate;
	}

}
