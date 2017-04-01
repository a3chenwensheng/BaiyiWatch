package com.baiyi.watch.model;

import java.io.Serializable;

public class Pedometer_activity_detail implements Serializable {

	private String active_hour;
	private String step_date;

	public String getActive_hour() {
		return active_hour;
	}

	public void setActive_hour(String active_hour) {
		this.active_hour = active_hour;
	}

	public String getStep_date() {
		return step_date;
	}

	public void setStep_date(String step_date) {
		this.step_date = step_date;
	}

}
