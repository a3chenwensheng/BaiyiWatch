package com.baiyi.watch.model;

import java.io.Serializable;

/**
 * # wifi 基站标识
 *
 */
public class Wifi implements Serializable{

	private String addr;// | ADDR | String
	private String ssid;// | SSID | String
	private String rssi;// | RSSI | Int
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getSsid() {
		return ssid;
	}
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
	public String getRssi() {
		return rssi;
	}
	public void setRssi(String rssi) {
		this.rssi = rssi;
	}
	
	
}
