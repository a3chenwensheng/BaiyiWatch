package com.baiyi.watch.model;

import java.io.Serializable;
import java.util.List;

public class Pedometer_activity implements Serializable {

	private String stepActiveWeekEnd;
	private String detail;

	public List<Pedometer_activity_detail> mDetails;

	public String getStepActiveWeekEnd() {
		return stepActiveWeekEnd;
	}

	public void setStepActiveWeekEnd(String stepActiveWeekEnd) {
		this.stepActiveWeekEnd = stepActiveWeekEnd;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
