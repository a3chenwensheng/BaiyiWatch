package com.baiyi.watch.model;

import java.io.Serializable;

public class Sleep_weekly implements Serializable {

	private String createDate;
	private String createTime;
	private String deepSleepDuration;
	private String device;
	private String lightSleepDuration;
	private String middleSleepDuration;
	private String sleepBegin;
	private String sleepDuration;
	private String sleepEnd;

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDeepSleepDuration() {
		return deepSleepDuration;
	}

	public void setDeepSleepDuration(String deepSleepDuration) {
		this.deepSleepDuration = deepSleepDuration;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getLightSleepDuration() {
		return lightSleepDuration;
	}

	public void setLightSleepDuration(String lightSleepDuration) {
		this.lightSleepDuration = lightSleepDuration;
	}

	public String getMiddleSleepDuration() {
		return middleSleepDuration;
	}

	public void setMiddleSleepDuration(String middleSleepDuration) {
		this.middleSleepDuration = middleSleepDuration;
	}

	public String getSleepBegin() {
		return sleepBegin;
	}

	public void setSleepBegin(String sleepBegin) {
		this.sleepBegin = sleepBegin;
	}

	public String getSleepDuration() {
		return sleepDuration;
	}

	public void setSleepDuration(String sleepDuration) {
		this.sleepDuration = sleepDuration;
	}

	public String getSleepEnd() {
		return sleepEnd;
	}

	public void setSleepEnd(String sleepEnd) {
		this.sleepEnd = sleepEnd;
	}

}
