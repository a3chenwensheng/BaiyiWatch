package com.baiyi.watch.model;

import java.io.Serializable;

public class Heart_weekly implements Serializable {

	private String averageHeartRate;
	private String countAbnormal;
	private String device;
	private String heartDate;
	private String weekEnd;

	public String getAverageHeartRate() {
		return averageHeartRate;
	}

	public void setAverageHeartRate(String averageHeartRate) {
		this.averageHeartRate = averageHeartRate;
	}

	public String getCountAbnormal() {
		return countAbnormal;
	}

	public void setCountAbnormal(String countAbnormal) {
		this.countAbnormal = countAbnormal;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getHeartDate() {
		return heartDate;
	}

	public void setHeartDate(String heartDate) {
		this.heartDate = heartDate;
	}

	public String getWeekEnd() {
		return weekEnd;
	}

	public void setWeekEnd(String weekEnd) {
		this.weekEnd = weekEnd;
	}

}
