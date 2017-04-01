package com.baiyi.watch.model;

import java.io.Serializable;

public class Fall_report_detail implements Serializable {

	private String fallCount;
	private String fallDate;

	public String getFallCount() {
		return fallCount;
	}

	public void setFallCount(String fallCount) {
		this.fallCount = fallCount;
	}

	public String getFallDate() {
		return fallDate;
	}

	public void setFallDate(String fallDate) {
		this.fallDate = fallDate;
	}

}
