package com.baiyi.watch.model;

import java.io.Serializable;

/**
 * # settingsosnumber亲情号码
 *
 */
public class SettingSosNumber implements Serializable{
	private String seqid;// 序号 | Int
	private String name;// 名称
	private String num;// 号码
	private String dial_flag;// 是否呼叫 | Boolean
	
	public String getSeqid() {
		return seqid;
	}
	public void setSeqid(String seqid) {
		this.seqid = seqid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getDial_flag() {
		return dial_flag;
	}
	public void setDial_flag(String dial_flag) {
		this.dial_flag = dial_flag;
	}
	
}
