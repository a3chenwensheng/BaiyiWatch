package com.baiyi.watch.model;

import java.io.Serializable;
import java.util.List;

/**
 * 坐标
 */
public class Coordinate implements Serializable{
	
	
	private String str;
	
	public List<String> mLatLgns;
	
	public Coordinate(String str) {
		this.str = str;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

}
