package com.baiyi.watch.model;

import java.io.Serializable;
import java.util.List;

public class Person implements Serializable {
	private String _id;// ObjectId
	private String username;// 用户名
	private String password;// 用户口令
	private String email;// 注册邮箱
	private String nickname;// 别名
	private String phone;// 手机号码
	private String telephone;// 电话号码
	private String devicetokens;// 终端标识列表 | List: Embedded [DeviceToken](#devicetoken)
	private String weight;// 体重（KG）
	private String step;// 步长（CM）
	private String age;//年龄（岁）
	private String height;//身高（CM）
	private String avatar;// 头像文件 | String
	private String avatar_url;// 头像
	private String community;// 社区 | Reference: [Community](#community)
	private String $community;
	private String role;// 角色 user:用户; operator:操作员; superuser:超级管理员 |
	private String gender;// 性别 male:男 ; female:女 |
	private String address;// 地址
	private String created_at;// 创建时间 | DateTime
	private String updated_at;// 更新时间 | DateTime
	private String lastlogin_ip;// 最近登录地址
	private String lastlogin_at;// 最近登录时间 | DateTime
	private String lastlogin_by;// 登录设备
	private String enable;// 启用 | Boolean
	private String email_is_checked;// 邮箱已验证 | Boolean
	private String phone_is_checked;// 手机已验证 | Boolean
	private String push_sos_enable;// 允许SOS推送 | Boolean
	private String push_fence_enable;// 允许电子围栏推送 | Boolean
	private String push_abnormal_enable;// 允许异常推送 | Boolean
	private String push_message_enable;// 允许社交消息推送 | Boolean
	private String push_lowpower_enable;// 允许低电推送 | Boolean
	private String push_system_enable;// 允许系统消息推送 | Boolean
	private String push_fall_enable;// 允许跌倒推送 | Boolean 
	private String push_env_enable;// 允许环境录音推送 | Boolean
	private String devices;// 设备列表 | List: [Device](#device)
	private String groups;// 小组列表 | List: [Group](#group)
	private String $groups;//
	private String home;//家的位置（经纬度和地址）
	private String home_wifi;//家庭wifi
	
	private String auth_type;
	private String auth_uid;
	
	public String mId;
	public List<Devicetoken> mDevicetokens;
	public Community mCommunity;
	public long mCreatedAt;
	public long mUpdatedAt;
	public long mLastloginAt;
	public List<Device> mDevices;
	public List<Group> mGroups;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getDevicetokens() {
		return devicetokens;
	}
	public void setDevicetokens(String devicetokens) {
		this.devicetokens = devicetokens;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getAvatar_url() {
		return avatar_url;
	}
	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	public String get$community() {
		return $community;
	}
	public void set$community(String $community) {
		this.$community = $community;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getLastlogin_by() {
		return lastlogin_by;
	}
	public void setLastlogin_by(String lastlogin_by) {
		this.lastlogin_by = lastlogin_by;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public String getEmail_is_checked() {
		return email_is_checked;
	}
	public void setEmail_is_checked(String email_is_checked) {
		this.email_is_checked = email_is_checked;
	}
	public String getPhone_is_checked() {
		return phone_is_checked;
	}
	public void setPhone_is_checked(String phone_is_checked) {
		this.phone_is_checked = phone_is_checked;
	}
	public String getPush_sos_enable() {
		return push_sos_enable;
	}
	public void setPush_sos_enable(String push_sos_enable) {
		this.push_sos_enable = push_sos_enable;
	}
	public String getPush_fence_enable() {
		return push_fence_enable;
	}
	public void setPush_fence_enable(String push_fence_enable) {
		this.push_fence_enable = push_fence_enable;
	}
	public String getPush_abnormal_enable() {
		return push_abnormal_enable;
	}
	public void setPush_abnormal_enable(String push_abnormal_enable) {
		this.push_abnormal_enable = push_abnormal_enable;
	}
	public String getPush_message_enable() {
		return push_message_enable;
	}
	public void setPush_message_enable(String push_message_enable) {
		this.push_message_enable = push_message_enable;
	}
	public String getPush_lowpower_enable() {
		return push_lowpower_enable;
	}
	public void setPush_lowpower_enable(String push_lowpower_enable) {
		this.push_lowpower_enable = push_lowpower_enable;
	}
	public String getPush_system_enable() {
		return push_system_enable;
	}
	public void setPush_system_enable(String push_system_enable) {
		this.push_system_enable = push_system_enable;
	}
	public String getPush_fall_enable() {
		return push_fall_enable;
	}
	public void setPush_fall_enable(String push_fall_enable) {
		this.push_fall_enable = push_fall_enable;
	}
	public String getPush_env_enable() {
		return push_env_enable;
	}
	public void setPush_env_enable(String push_env_enable) {
		this.push_env_enable = push_env_enable;
	}
	public String getDevices() {
		return devices;
	}
	public void setDevices(String devices) {
		this.devices = devices;
	}
	public String getGroups() {
		return groups;
	}
	public void setGroups(String groups) {
		this.groups = groups;
	}
	public String get$groups() {
		return $groups;
	}
	public void set$groups(String $groups) {
		this.$groups = $groups;
	}
	public String getHome() {
		return home;
	}
	public void setHome(String home) {
		this.home = home;
	}
	public String getHome_wifi() {
		return home_wifi;
	}
	public void setHome_wifi(String home_wifi) {
		this.home_wifi = home_wifi;
	}
	public String getAuth_type() {
		return auth_type;
	}
	public void setAuth_type(String auth_type) {
		this.auth_type = auth_type;
	}
	public String getAuth_uid() {
		return auth_uid;
	}
	public void setAuth_uid(String auth_uid) {
		this.auth_uid = auth_uid;
	}
	
}
