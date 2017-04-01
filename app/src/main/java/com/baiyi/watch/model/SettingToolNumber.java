package com.baiyi.watch.model;

import java.io.Serializable;

/**
 * # settingtoolnum亲情号码
 *
 */
public class SettingToolNumber implements Serializable {
	private String seqid;// 序号 | Int
	private String name;// 名称
	private String num;// 号码

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

}
