package com.baiyi.watch.model;

import java.io.Serializable;

public class Pedometer_detail implements Serializable {

	private String reachStepObjective;
	private String stepDate;
	private String stepValue;
	
	public String getReachStepObjective() {
		return reachStepObjective;
	}
	public void setReachStepObjective(String reachStepObjective) {
		this.reachStepObjective = reachStepObjective;
	}
	public String getStepDate() {
		return stepDate;
	}
	public void setStepDate(String stepDate) {
		this.stepDate = stepDate;
	}
	public String getStepValue() {
		return stepValue;
	}
	public void setStepValue(String stepValue) {
		this.stepValue = stepValue;
	}
	
	

}
