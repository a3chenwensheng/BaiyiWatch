package com.baiyi.watch.model;

import java.io.Serializable;


/**
 * # locationdata 定位数据
 *
 */
public class Locationdata implements Serializable{

	private String _id;// ObjectId
	private String user;// 用户 | Reference: [Person](#person)
	private String device;// 设备 | Reference: [Device](#device)
	private String created_at;// 记录时间 | DateTime
	private String time_begin;// 发生时间 | DateTime
	private String type;// 类型 0:Gps定位; 1:基站定位 |
	private String city;// 城市
	private String address;// 地址
	private String point;// 坐标 | Point
	private String cell;// CELL | List: Embedded [CELL](#cell)
	private String wifi;// WIFI | List: Embedded [WIFI](#wifi)
	
	private String gps_count;
	private String gps_speed;
	
	public Person mUser;
	public Device mDevice;
	public String mCreatedAt;
	public String mTimeBegin;
	public Point mPoint;
//	public List<Cell> mCells;
//	public List<WIFI> mWifis;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getTime_begin() {
		return time_begin;
	}
	public void setTime_begin(String time_begin) {
		this.time_begin = time_begin;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getCell() {
		return cell;
	}
	public void setCell(String cell) {
		this.cell = cell;
	}
	public String getWifi() {
		return wifi;
	}
	public void setWifi(String wifi) {
		this.wifi = wifi;
	}
	public String getGps_count() {
		return gps_count;
	}
	public void setGps_count(String gps_count) {
		this.gps_count = gps_count;
	}
	public String getGps_speed() {
		return gps_speed;
	}
	public void setGps_speed(String gps_speed) {
		this.gps_speed = gps_speed;
	}	
	
}
