package com.baiyi.watch.model;

import java.io.Serializable;
import java.util.List;


public class Safearea implements Serializable{
	
	private String type;
	private String coordinates;
	
	public List<Coordinate> mCoordinates;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

}
