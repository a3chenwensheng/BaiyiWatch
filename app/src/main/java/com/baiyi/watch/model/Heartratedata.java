package com.baiyi.watch.model;

/**
 * 
 * @author mxj
 *
 */
public class Heartratedata extends Database {
	private String _id;// ObjectId
	private String user;// 用户 | Reference
	private String $user;// 用户 | Reference
	private String device;// 设备 | Reference
	private String $device;// 设备 | Reference
	private String data_type;// 类型 | String
	private String created_at;// 记录时间 | DateTime
	private String time_begin;// 发生时间 | DateTime
	private String time_end;// 截止时间 | DateTime
	private String heartrate;// 心率 | Int
	private String _cls; 
	public String mId;
	public String mUserId;
	public Person mUser;
	public String mDeviceId;
	public Device mDevice;
	public String mCreatedAt;
	public String mTimebegin;
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
	public String get$user() {
		return $user;
	}
	public void set$user(String $user) {
		this.$user = $user;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String get$device() {
		return $device;
	}
	public void set$device(String $device) {
		this.$device = $device;
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
	public String getHeartrate() {
		return heartrate;
	}
	public void setHeartrate(String heartrate) {
		this.heartrate = heartrate;
	}
	public String get_cls() {
		return _cls;
	}
	public void set_cls(String _cls) {
		this._cls = _cls;
	}
}
