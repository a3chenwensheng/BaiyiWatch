package com.baiyi.watch.model;

import java.io.Serializable;

public class Deviceenvvoice implements Serializable{
	private String _id;// TODO
	private String user;
	private String device;
	private String group;
	private String voice_time;
	private String voice_type;
	private String media_length;
	private String filename;
	private String url;
	private String created_at;
	
	public String mId;
	public String mUserId;
	public String mGroupId;
	public String mVoiceTime;
	public String mCreatedAt;
	
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
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getVoice_time() {
		return voice_time;
	}
	public void setVoice_time(String voice_time) {
		this.voice_time = voice_time;
	}
	public String getVoice_type() {
		return voice_type;
	}
	public void setVoice_type(String voice_type) {
		this.voice_type = voice_type;
	}
	public String getMedia_length() {
		return media_length;
	}
	public void setMedia_length(String media_length) {
		this.media_length = media_length;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
     
	
}
