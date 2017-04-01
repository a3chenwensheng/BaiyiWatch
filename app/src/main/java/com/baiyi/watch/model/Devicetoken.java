package com.baiyi.watch.model;


/**
 * # devicetoken终端标识
 *
 */
public class Devicetoken {
	
	private String token_type;// 设备类型 ios:ios; android:android 
	private String app;// 服务名 | String |  | 30 | True | aiqiangua |  |
	private String token;// 设备标识
	private boolean is_enable_aliase;// 别名是否可用 | Boolean
	private String created_at;// 创建时间 | DateTime
	
	public long mCreatedAt;

}
