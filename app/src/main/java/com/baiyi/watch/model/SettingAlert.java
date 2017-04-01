package com.baiyi.watch.model;

import java.io.Serializable;

/**
 * # settingalert事件提醒
 *
 */
public class SettingAlert implements Serializable{
	
	private String seqid;// 序号 | Int
	private String enable;// 启用 | Boolean
	private String name;// 名称 | String
	private String alert_type;// 提醒类型 | Int
	private String cycle;// 周期 | Int
	private String time;// 时间 | String
	private String media_length;// 语音文件长度 | Int
	private String file_checksum;// 语音文件（校验和） | Int
	private String filename;// 语音文件名 | String
	private String url;// 下载地址
	private String is_medicine;
	private String created_at;// 提交时间 | DateTime
	
	public String mCreatedAt;
	
	public String getSeqid() {
		return seqid;
	}
	public void setSeqid(String seqid) {
		this.seqid = seqid;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlert_type() {
		return alert_type;
	}
	public void setAlert_type(String alert_type) {
		this.alert_type = alert_type;
	}
	public String getCycle() {
		return cycle;
	}
	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getMedia_length() {
		return media_length;
	}
	public void setMedia_length(String media_length) {
		this.media_length = media_length;
	}
	public String getFile_checksum() {
		return file_checksum;
	}
	public void setFile_checksum(String file_checksum) {
		this.file_checksum = file_checksum;
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
	public String getIs_medicine() {
		return is_medicine;
	}
	public void setIs_medicine(String is_medicine) {
		this.is_medicine = is_medicine;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	
}
