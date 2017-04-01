package com.baiyi.watch.model;

import java.io.Serializable;
import java.util.List;

public class Pedometer implements Serializable {

	private String calorie;
	private String detail;
	private String distance;
	private String reachDays;
	private String stepAverage;
	private String stepObjective;
	private String stepTotal;
	private String stepWeekEnd;
	
	public List<Pedometer_detail> mDetails;

	public String getCalorie() {
		return calorie;
	}

	public void setCalorie(String calorie) {
		this.calorie = calorie;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getReachDays() {
		return reachDays;
	}

	public void setReachDays(String reachDays) {
		this.reachDays = reachDays;
	}

	public String getStepAverage() {
		return stepAverage;
	}

	public void setStepAverage(String stepAverage) {
		this.stepAverage = stepAverage;
	}

	public String getStepObjective() {
		return stepObjective;
	}

	public void setStepObjective(String stepObjective) {
		this.stepObjective = stepObjective;
	}

	public String getStepTotal() {
		return stepTotal;
	}

	public void setStepTotal(String stepTotal) {
		this.stepTotal = stepTotal;
	}

	public String getStepWeekEnd() {
		return stepWeekEnd;
	}

	public void setStepWeekEnd(String stepWeekEnd) {
		this.stepWeekEnd = stepWeekEnd;
	}

	

}
