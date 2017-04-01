package com.baiyi.watch.model;

import java.io.Serializable;

public class QRCode implements Serializable {
	
	private String checknum;
	private String errorResult;
	private String success;

	public String getChecknum() {
		return checknum;
	}

	public void setChecknum(String checknum) {
		this.checknum = checknum;
	}

	public String getErrorResult() {
		return errorResult;
	}

	public void setErrorResult(String errorResult) {
		this.errorResult = errorResult;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}
	
	

}
