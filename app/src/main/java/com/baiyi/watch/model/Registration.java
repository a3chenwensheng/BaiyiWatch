package com.baiyi.watch.model;

import java.io.Serializable;

public class Registration implements Serializable {

	private String name;
	private String num;

	public Registration() {
	}

	public Registration(String name, String num) {
		this.name = name;
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

}
