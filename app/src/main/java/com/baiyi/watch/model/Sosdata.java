package com.baiyi.watch.model;

import java.io.Serializable;

/**
 * 
 * @author mxj
 *
 */
public class Sosdata implements Serializable{
	private String _id;// ObjectId
	private String user;// 用户 | Reference: [Person](#person)
	private String device;// 设备 | Reference: [Device](#device)
	private String created_at;// 记录时间 | DateTime
	private String time_begin;// 发生时间 | DateTime
	private String type;// 类型 0:Gps定位; 1:基站定位 |
	private String city;// 城市
	private String address;// 地址
	private String point;// 坐标 | Point | | | False | None | |
	private String cell;// CELL | List: Embedded [CELL](#cell)
	private String wifi;// WIFI | List: Embedded [WIFI](#wifi)
	private String heartrate;// 心率值 | Int
	private String is_removed;// 是否消除 | Boolean

	private String data_type; 
	private String time_end;
	private String sim_phone_type; 
	private String sim_phone; 
	private String _cls;
	
	public String mId;
	public Point mPoint;
	public Person mUser;
	public String mCreatedAt;
	public String mTimeBegin;
	
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
	public String getHeartrate() {
		return heartrate;
	}
	public void setHeartrate(String heartrate) {
		this.heartrate = heartrate;
	}
	public String getIs_removed() {
		return is_removed;
	}
	public void setIs_removed(String is_removed) {
		this.is_removed = is_removed;
	}
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	public String getTime_end() {
		return time_end;
	}
	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}
	public String getSim_phone_type() {
		return sim_phone_type;
	}
	public void setSim_phone_type(String sim_phone_type) {
		this.sim_phone_type = sim_phone_type;
	}
	public String getSim_phone() {
		return sim_phone;
	}
	public void setSim_phone(String sim_phone) {
		this.sim_phone = sim_phone;
	}
	public String get_cls() {
		return _cls;
	}
	public void set_cls(String _cls) {
		this._cls = _cls;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	
}
