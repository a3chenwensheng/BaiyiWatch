package com.baiyi.watch.model;

import java.util.List;

/**
 * # sleepdata 睡眠数据
 *
 */
public class Sleepdata {

	private String _id;// ObjectId
	private String user;// 用户 | Reference: [Person](#person)
	private String device;// 设备 | Reference: [Device](#device)
	private String created_at;// 记录时间 | DateTime
	private String time_begin;// 发生时间 | DateTime
	private String time_end;// 截止时间 | DateTime
	private String interval;// 检测间隔 | Int
	private String total;// 检测次数 | Int
	private String data;// 睡眠数据 | List: Embedded [SleepDataSleep](#sleepdatasleep)
	
	private String $device;// 设备 | Reference: [Device](#device)
	
	public String mId;
	public Person mUser;
	public Device mDevice;
	public String mCreatedAt;
	public String mTimeBegin;
	public String mTimeEnd;
	public List<Sleepdatasleep> mDatas;
	
	
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
	public String getTime_end() {
		return time_end;
	}
	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String get$device() {
		return $device;
	}
	public void set$device(String $device) {
		this.$device = $device;
	}
	
	
	
}
