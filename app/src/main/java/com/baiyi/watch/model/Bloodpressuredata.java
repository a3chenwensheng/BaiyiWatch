package com.baiyi.watch.model;

/**
 * # bloodpressuredata血压数据
 *
 */
public class Bloodpressuredata {
	private String _id;// | ObjectId|
	private String user;// 用户 | Reference: [Person](#person)
	private String device;// 设备 | Reference: [Device](#device)
	private String created_at;// 记录时间 | DateTime
	private String time_begin;// 发生时间 | DateTime
	private String dbp;// 舒张压 | Int
	private String sbp;// 收缩压 | Int
	
	public String mId;
	public Person mUser;
	public Device mDevice;
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
	public String getDbp() {
		return dbp;
	}
	public void setDbp(String dbp) {
		this.dbp = dbp;
	}
	public String getSbp() {
		return sbp;
	}
	public void setSbp(String sbp) {
		this.sbp = sbp;
	}
	
	
}
