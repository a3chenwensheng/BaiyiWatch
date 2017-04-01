package com.baiyi.watch.model;

import java.util.Date;

public class Weathercache {
	String _id; // id
	String city; // 城市
	String date; // 日期
	int weather_id; // 天气编号
	String weather_desc; // 天气描述
	int temp_now; // 当前温度
	int temp_low; // 今天低温
	int temp_high; // 今天高温
	int t_weather_id; // 明天天气编号
	String t_weather_desc; // 明天天气描述
	int t_temp_low; // 明天低温
	int t_temp_high; // 明天低温
	String advice; // 穿衣建议
	Date created_at; // 创建时间
	Date updated_at; // 更新时间
}
