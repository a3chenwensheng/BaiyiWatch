package com.baiyi.watch.model;

import java.io.Serializable;

public class Remind implements Serializable {

	private static final long serialVersionUID = 1L;
	private String remindName;
	private String remindTime;
	private boolean isRemind;

	public String getRemindName() {
		return remindName;
	}

	public void setRemindName(String remindName) {
		this.remindName = remindName;
	}

	public String getRemindTime() {
		return remindTime;
	}

	public void setRemindTime(String remindTime) {
		this.remindTime = remindTime;
	}

	public boolean isRemind() {
		return isRemind;
	}

	public void setRemind(boolean isRemind) {
		this.isRemind = isRemind;
	}

}
