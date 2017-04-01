package com.baiyi.watch.model;

import java.util.Date;
import java.util.List;

public class Persondata {
	String id;// ObjectId
	Person user;// 用户 | Reference
	Device device;// 设备 | Reference
	Date created_at;// 时间
	String datatype;// 类型
	Date time_begin;// 开始时间
	Date time_end;// 结束时间
	List data;// 数据
}
