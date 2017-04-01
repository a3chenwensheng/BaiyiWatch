package com.baiyi.watch.model;

import java.io.Serializable;
import java.util.List;

public class Resting_heart_rate implements Serializable {

	private String heartWeekEnd;
	private String detail;
	
	public List<Resting_heart_rate_detail> mDetails;

	public String getHeartWeekEnd() {
		return heartWeekEnd;
	}

	public void setHeartWeekEnd(String heartWeekEnd) {
		this.heartWeekEnd = heartWeekEnd;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
