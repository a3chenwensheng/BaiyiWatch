package com.baiyi.watch.model;

import java.io.Serializable;
import java.util.List;

public class Fall_report implements Serializable {

	private String fallWeekEnd;
	private String fallTotal;
	private String detail;
	
	public List<Fall_report_detail> mDetails;

	public String getFallWeekEnd() {
		return fallWeekEnd;
	}


	public void setFallWeekEnd(String fallWeekEnd) {
		this.fallWeekEnd = fallWeekEnd;
	}


	public String getFallTotal() {
		return fallTotal;
	}


	public void setFallTotal(String fallTotal) {
		this.fallTotal = fallTotal;
	}


	public String getDetail() {
		return detail;
	}


	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	


}
