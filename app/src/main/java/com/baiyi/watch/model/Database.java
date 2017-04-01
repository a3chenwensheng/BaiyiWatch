package com.baiyi.watch.model;

import java.util.Date;

public class Database {
	String id;// ObjectId | | | False | |
	Person user;// 用户 | Reference | | | False | |
	Device device;// 设备 | Reference | | | False | |
	String data_type;// 类型 | String | | 16 | True | |
	Date created_at;// 记录时间 | DateTime | | | False | |
	Date time_begin;// 发生时间 | DateTime | | | False | |
	Date time_end;// 截止时间 | DateTime | | | False | |
}
