package com.baiyi.watch.model;

public class Powerdata {
	private String _id;// ObjectId
	private String user;// 用户 | Reference
	private String device;// 设备 | Reference
	private String data_type;// 类型
	private String created_at;// 记录时间
	private String time_begin;// 发生时间
	private String time_end;// 截止时间
	private String power_type;// 开/关机类型 0:开机; 1:关机; 2:低电 |
	private String voltage;// 电压值（mV）
	private String remaining_power;// 剩余电量(%） 
	
	public String mUserId;
	public String mCreatedAt;
	public String mTimeBegin;
	public String mTimeEnd;
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
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
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
	public String getTime_end() {
		return time_end;
	}
	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}
	public String getPower_type() {
		return power_type;
	}
	public void setPower_type(String power_type) {
		this.power_type = power_type;
	}
	public String getVoltage() {
		return voltage;
	}
	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}
	public String getRemaining_power() {
		return remaining_power;
	}
	public void setRemaining_power(String remaining_power) {
		this.remaining_power = remaining_power;
	}
	
}
