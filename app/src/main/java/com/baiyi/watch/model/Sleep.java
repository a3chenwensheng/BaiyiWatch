package com.baiyi.watch.model;

import java.util.List;

/**
 * # sleepdata 睡眠数据
 *
 */
public class Sleep {

	private String sleepWeekEnd;//
	private String detail;//
	
	public List<Sleep_detail> mDetails;

	public String getSleepWeekEnd() {
		return sleepWeekEnd;
	}

	public void setSleepWeekEnd(String sleepWeekEnd) {
		this.sleepWeekEnd = sleepWeekEnd;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
