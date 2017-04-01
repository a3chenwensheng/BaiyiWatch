package com.baiyi.watch.model;

import java.io.Serializable;

/**
 * 
 * @author mxj
 *
 */
public class Fall implements Serializable {
	private String date;
	private String num;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

}
