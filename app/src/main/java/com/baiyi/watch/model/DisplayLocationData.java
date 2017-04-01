package com.baiyi.watch.model;

import java.io.Serializable;

public class DisplayLocationData implements Serializable{
	
	private String weather;
	private String locationdata;
	private String wear_flag;
	private String online;
	private String pm25;
	private String remaining_power;
	private String temperature;
	
	public Locationdata mLocationdata;

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getLocationdata() {
		return locationdata;
	}

	public void setLocationdata(String locationdata) {
		this.locationdata = locationdata;
	}

	public String getWear_flag() {
		return wear_flag;
	}

	public void setWear_flag(String wear_flag) {
		this.wear_flag = wear_flag;
	}

	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}

	public String getPm25() {
		return pm25;
	}

	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}

	public String getRemaining_power() {
		return remaining_power;
	}

	public void setRemaining_power(String remaining_power) {
		this.remaining_power = remaining_power;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	
}
