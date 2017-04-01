package com.baiyi.watch.model;

import java.util.Date;

public class Mediadata {
	String id;// ObjectId
	Person user;// 用户 | Reference
	Device device;// 设备 | Reference
	String data_type;// 类型
	Date created_at;// 记录时间
	Date time_begin;// 发生时间
	Date time_end;// 截止时间
	int media_id;// 媒体编号
	int media_type;// 媒体类型
	int media_dest;// 目标
	int media_length;// 长度
	String media_file;// 文件名
}
