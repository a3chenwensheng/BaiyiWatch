package com.baiyi.watch.model;

import java.io.Serializable;
import java.util.List;

public class Device implements Serializable{
	private String did;// 设备编号
	private String _id;// TODO
	private String alerts;// 提醒 | List: Embedded [SettingAlert](#settingalert)
	private String sos_numbers;// 亲情号码 | List: Embedded [SettingSosNumber](#settingsosnumber)
	private String fall_model;
	private String sos_dial_cycle_times;// 轮播次数1-9 | Int
	private String sos_sendmail;// 紧急呼叫是否发送短信 | Boolean
	private String sos_readmail;// 紧急呼叫短信是否自动弹出 | Boolean
	private String sos_dial_cycle_mode;// 轮播模式 | Int
	private String frequency_location;// 位置上报频率 | Int
	private String frequency_step;// 记步上报频率 | Int
	private String frequency_heartrate;// 心率上报频率 | Int
	private String theshold_heartrate_h;// 心率上限 | Int
	private String theshold_heartrate_l;// 心率下限 | Int
	private String incoming_restriction;// 呼入限制 | Boolean
	private String service_number;// 服务号 | String
	private String bluetooth_enable;// 蓝牙开关 | Boolean
	private String bluetooth_devices;// 蓝牙设备 | List: String
	private String profile;// 情景模式 | Int
	private String sleep_period_begin;// 睡眠开始时间 | String
	private String sleep_period_end;// 睡眠结束时间 | String
	private String step_objective;// 计步目标 | Int
	private String theshold_sit;// 久坐门限 | Int
	private String theshold_low_battery;// 低电量门限 | Int
	private String fences;// 电子围栏 | List: Embedded [SettingFence](#settingfence)
	private String imei;// 移动设备识别码
	private String imsi;// 移动用户识别码
	private String type;// 设备类型 | String | | 20 | False | BY102 | |
	private String sim_phone;// 移动用户卡号
	private String sim_phone_type;// 移动用户卡类型 unicom:中国联通; cmcc:中国移动; ctcc:中国电信
	private String owner;// 拥有人 | Reference: [Person](#person)
	private String $owner;// TODO
	private String name;// 设备名称
	private String created_at;// 创建时间 | DateTime
	private String updated_at;// 更新时间 | DateTime
	private String lastlogin_ip;// 最近联网地址
	private String lastlogin_at;// 最近联网时间 | DateTime
	private String active;// 激活状态 | Boolean
	private String online;// 联网状态 | Boolean
	private String location_updated;// 坐标更新 | Boolean
	private String location_updated_at;// 坐标更新时间 | DateTime
	private String last_location;// 最新坐标 | Point
	private String last_city;// 城市
	private String last_address;// 地址
	private String wear_flag;// 佩戴状态 | Int
	private String wear_updated_at;// 佩戴状态更新时间 | DateTime
	private String software_version;// 软件版本
	private String iccid1;// 移动用户iccid号1
	private String iccid2;// 移动用户iccid号2
	private String pedometer_enable;//计步数据开关 | Boolean
	private String sleep_enable;//睡眠数据开关 | Boolean
	private String remaining_power;// 剩余电量(%） 
	private String fall_enable;//跌倒开关
	private String track_enable;//轨迹开关
	private String heartrate_enable;//心率测量开关
	private String tool_numbers;//服务号码
	private String home_wifi_addr;
	private String home_wifi_ssid;
	
	private String avatar_url;//缩略版...
	private String group_name;
	private String group_id;
	private String group_ownerid;
	private String have_community;//是否有机构
	
	public String mId;
	public String mOwnerId;
	public Person mOwner;
	public List<SettingSosNumber> mSosNumbers;
	public List<SettingToolNumber> mToolNumbers;
	public List<SettingAlert> mAlerts;
	public List<SettingFence> mFences;
	
	public String getDid() {
		return did;
	}
	public void setDid(String did) {
		this.did = did;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getAlerts() {
		return alerts;
	}
	public void setAlerts(String alerts) {
		this.alerts = alerts;
	}
	public String getFall_model() {
		return fall_model;
	}
	public void setFall_model(String fall_model) {
		this.fall_model = fall_model;
	}
	public String getSos_numbers() {
		return sos_numbers;
	}
	public void setSos_numbers(String sos_numbers) {
		this.sos_numbers = sos_numbers;
	}
	public String getSos_dial_cycle_times() {
		return sos_dial_cycle_times;
	}
	public void setSos_dial_cycle_times(String sos_dial_cycle_times) {
		this.sos_dial_cycle_times = sos_dial_cycle_times;
	}
	public String getSos_sendmail() {
		return sos_sendmail;
	}
	public void setSos_sendmail(String sos_sendmail) {
		this.sos_sendmail = sos_sendmail;
	}
	public String getSos_readmail() {
		return sos_readmail;
	}
	public void setSos_readmail(String sos_readmail) {
		this.sos_readmail = sos_readmail;
	}
	public String getSos_dial_cycle_mode() {
		return sos_dial_cycle_mode;
	}
	public void setSos_dial_cycle_mode(String sos_dial_cycle_mode) {
		this.sos_dial_cycle_mode = sos_dial_cycle_mode;
	}
	public String getFrequency_location() {
		return frequency_location;
	}
	public void setFrequency_location(String frequency_location) {
		this.frequency_location = frequency_location;
	}
	public String getFrequency_step() {
		return frequency_step;
	}
	public void setFrequency_step(String frequency_step) {
		this.frequency_step = frequency_step;
	}
	public String getFrequency_heartrate() {
		return frequency_heartrate;
	}
	public void setFrequency_heartrate(String frequency_heartrate) {
		this.frequency_heartrate = frequency_heartrate;
	}
	public String getTheshold_heartrate_h() {
		return theshold_heartrate_h;
	}
	public void setTheshold_heartrate_h(String theshold_heartrate_h) {
		this.theshold_heartrate_h = theshold_heartrate_h;
	}
	public String getTheshold_heartrate_l() {
		return theshold_heartrate_l;
	}
	public void setTheshold_heartrate_l(String theshold_heartrate_l) {
		this.theshold_heartrate_l = theshold_heartrate_l;
	}
	public String getIncoming_restriction() {
		return incoming_restriction;
	}
	public void setIncoming_restriction(String incoming_restriction) {
		this.incoming_restriction = incoming_restriction;
	}
	public String getService_number() {
		return service_number;
	}
	public void setService_number(String service_number) {
		this.service_number = service_number;
	}
	public String getBluetooth_enable() {
		return bluetooth_enable;
	}
	public void setBluetooth_enable(String bluetooth_enable) {
		this.bluetooth_enable = bluetooth_enable;
	}
	public String getBluetooth_devices() {
		return bluetooth_devices;
	}
	public void setBluetooth_devices(String bluetooth_devices) {
		this.bluetooth_devices = bluetooth_devices;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getSleep_period_begin() {
		return sleep_period_begin;
	}
	public void setSleep_period_begin(String sleep_period_begin) {
		this.sleep_period_begin = sleep_period_begin;
	}
	public String getSleep_period_end() {
		return sleep_period_end;
	}
	public void setSleep_period_end(String sleep_period_end) {
		this.sleep_period_end = sleep_period_end;
	}
	public String getStep_objective() {
		return step_objective;
	}
	public void setStep_objective(String step_objective) {
		this.step_objective = step_objective;
	}
	public String getTheshold_sit() {
		return theshold_sit;
	}
	public void setTheshold_sit(String theshold_sit) {
		this.theshold_sit = theshold_sit;
	}
	public String getTheshold_low_battery() {
		return theshold_low_battery;
	}
	public void setTheshold_low_battery(String theshold_low_battery) {
		this.theshold_low_battery = theshold_low_battery;
	}
	public String getFences() {
		return fences;
	}
	public void setFences(String fences) {
		this.fences = fences;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSim_phone() {
		return sim_phone;
	}
	public void setSim_phone(String sim_phone) {
		this.sim_phone = sim_phone;
	}
	public String getSim_phone_type() {
		return sim_phone_type;
	}
	public void setSim_phone_type(String sim_phone_type) {
		this.sim_phone_type = sim_phone_type;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String get$owner() {
		return $owner;
	}
	public void set$owner(String $owner) {
		this.$owner = $owner;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	public String getLastlogin_ip() {
		return lastlogin_ip;
	}
	public void setLastlogin_ip(String lastlogin_ip) {
		this.lastlogin_ip = lastlogin_ip;
	}
	public String getLastlogin_at() {
		return lastlogin_at;
	}
	public void setLastlogin_at(String lastlogin_at) {
		this.lastlogin_at = lastlogin_at;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getOnline() {
		return online;
	}
	public void setOnline(String online) {
		this.online = online;
	}
	public String getLocation_updated() {
		return location_updated;
	}
	public void setLocation_updated(String location_updated) {
		this.location_updated = location_updated;
	}
	public String getLocation_updated_at() {
		return location_updated_at;
	}
	public void setLocation_updated_at(String location_updated_at) {
		this.location_updated_at = location_updated_at;
	}
	public String getLast_location() {
		return last_location;
	}
	public void setLast_location(String last_location) {
		this.last_location = last_location;
	}
	public String getLast_city() {
		return last_city;
	}
	public void setLast_city(String last_city) {
		this.last_city = last_city;
	}
	public String getLast_address() {
		return last_address;
	}
	public void setLast_address(String last_address) {
		this.last_address = last_address;
	}
	public String getWear_flag() {
		return wear_flag;
	}
	public void setWear_flag(String wear_flag) {
		this.wear_flag = wear_flag;
	}
	public String getWear_updated_at() {
		return wear_updated_at;
	}
	public void setWear_updated_at(String wear_updated_at) {
		this.wear_updated_at = wear_updated_at;
	}
	public String getSoftware_version() {
		return software_version;
	}
	public void setSoftware_version(String software_version) {
		this.software_version = software_version;
	}
	public String getIccid1() {
		return iccid1;
	}
	public void setIccid1(String iccid1) {
		this.iccid1 = iccid1;
	}
	public String getIccid2() {
		return iccid2;
	}
	public void setIccid2(String iccid2) {
		this.iccid2 = iccid2;
	}
	public String getPedometer_enable() {
		return pedometer_enable;
	}
	public void setPedometer_enable(String pedometer_enable) {
		this.pedometer_enable = pedometer_enable;
	}
	public String getSleep_enable() {
		return sleep_enable;
	}
	public void setSleep_enable(String sleep_enable) {
		this.sleep_enable = sleep_enable;
	}
	public String getRemaining_power() {
		return remaining_power;
	}
	public void setRemaining_power(String remaining_power) {
		this.remaining_power = remaining_power;
	}
	public String getFall_enable() {
		return fall_enable;
	}
	public void setFall_enable(String fall_enable) {
		this.fall_enable = fall_enable;
	}
	public String getTrack_enable() {
		return track_enable;
	}
	public void setTrack_enable(String track_enable) {
		this.track_enable = track_enable;
	}
	public String getHeartrate_enable() {
		return heartrate_enable;
	}
	public void setHeartrate_enable(String heartrate_enable) {
		this.heartrate_enable = heartrate_enable;
	}
	public String getTool_numbers() {
		return tool_numbers;
	}
	public void setTool_numbers(String tool_numbers) {
		this.tool_numbers = tool_numbers;
	}
	public String getAvatar_url() {
		return avatar_url;
	}
	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public String getGroup_ownerid() {
		return group_ownerid;
	}
	public void setGroup_ownerid(String group_ownerid) {
		this.group_ownerid = group_ownerid;
	}	
	public String getHave_community() {
		return have_community;
	}
	public void setHave_community(String have_community) {
		this.have_community = have_community;
	}
	public String getHome_wifi_addr() {
		return home_wifi_addr;
	}
	public void setHome_wifi_addr(String home_wifi_addr) {
		this.home_wifi_addr = home_wifi_addr;
	}
	public String getHome_wifi_ssid() {
		return home_wifi_ssid;
	}
	public void setHome_wifi_ssid(String home_wifi_ssid) {
		this.home_wifi_ssid = home_wifi_ssid;
	}
	
}
