package com.baiyi.watch.model;

import java.io.Serializable;
import java.util.List;


public class SignIn implements Serializable{
	
	private String signed_date_list;
	private String today_is_signed;
	private String points;
	private String continuous_sign_count;
	private String continuous_sign_count_2;//母亲节活动连续签到天数
	private String add_point_if_sign;
	
	public List<String> mSignedDatas;

	public String getSigned_date_list() {
		return signed_date_list;
	}

	public void setSigned_date_list(String signed_date_list) {
		this.signed_date_list = signed_date_list;
	}

	public String getToday_is_signed() {
		return today_is_signed;
	}

	public void setToday_is_signed(String today_is_signed) {
		this.today_is_signed = today_is_signed;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getContinuous_sign_count() {
		return continuous_sign_count;
	}

	public void setContinuous_sign_count(String continuous_sign_count) {
		this.continuous_sign_count = continuous_sign_count;
	}

	public String getContinuous_sign_count_2() {
		return continuous_sign_count_2;
	}

	public void setContinuous_sign_count_2(String continuous_sign_count_2) {
		this.continuous_sign_count_2 = continuous_sign_count_2;
	}

	public String getAdd_point_if_sign() {
		return add_point_if_sign;
	}

	public void setAdd_point_if_sign(String add_point_if_sign) {
		this.add_point_if_sign = add_point_if_sign;
	}
	
}
