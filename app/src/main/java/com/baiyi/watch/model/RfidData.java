package com.baiyi.watch.model;

public class RfidData {
	public String _id;
	public String user;
	private String $user;// 用户 | Reference
	public String data_type;
	public String created_at;
	public String time_begin;
	public String time_end;
	public String state;
	private String _cls;
	private String device;
	private String $device;// 设备 | Reference
	
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String get_cls() {
		return _cls;
	}
	public void set_cls(String _cls) {
		this._cls = _cls;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
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
	public String get$user() {
		return $user;
	}
	public void set$user(String $user) {
		this.$user = $user;
	}
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	public String get$device() {
		return $device;
	}
	public void set$device(String $device) {
		this.$device = $device;
	}
	
	

}
