package com.baiyi.watch.model;

import java.io.Serializable;

public class Sleep_detail implements Serializable {

	private String lightSleepDuration;
	private String middleSleepDuration;
	private String deepSleepDuration;
	private String sleepBegin;
	private String sleepDuration;
	private String sleepEnd;

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

	public String getDeepSleepDuration() {
		return deepSleepDuration;
	}

	public void setDeepSleepDuration(String deepSleepDuration) {
		this.deepSleepDuration = deepSleepDuration;
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
