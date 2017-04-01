package com.baiyi.watch.model;

import java.io.Serializable;

/**
 * # settingfence围栏
 *
 */
public class SettingFence implements Serializable{
	private String seqid;// 序号 | Int 
	private String enable;// 启用 | Boolean
	private String name;// 名称 
	private String  time_begin;// 起始时间 | Int
	private String  time_end;// 终止时间 | Int
	private String  safe_area;// 安全区域 | Polygon
	
	public Safearea mSafearea;
	
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
	public String getSafe_area() {
		return safe_area;
	}
	public void setSafe_area(String safe_area) {
		this.safe_area = safe_area;
	}
	
}
