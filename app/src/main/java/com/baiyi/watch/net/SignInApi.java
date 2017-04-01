package com.baiyi.watch.net;

import android.content.Context;

public class SignInApi extends BaseApi {

	public static SignInApi mInstance;

	public static SignInApi getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new SignInApi(context);
		}
		return mInstance;
	}

	public SignInApi(Context context) {
		init(context);
	}

	/**
	 * 获取一个月的签名记录
	 * 
	 * @param personid
	 * @param time
	 * @param bt
	 */
	public void getMonthSigned(String personid, String time, HttpCallback bt) {
		String url ="http://manager.aiqiangua.com/AppS2_sign/api/getMonthSigned?personid=" + personid + "&yyyyMM=" + time;
		System.out.println(url);
		doRequest(url, null, bt);
	}
	
	/**
	 * 获取当月的签名记录
	 * 
	 * @param personid
	 * @param bt
	 */
	public void getMonthSigned(String personid, HttpCallback bt) {
		String url ="http://manager.aiqiangua.com/AppS2_sign/api/getMonthSigned?personid=" + personid;
		doRequest(url, null, bt);
	}
	
	/**
	 * 签名
	 * 
	 * @param personid
	 * @param bt
	 */
	public void SignIn(String personid, HttpCallback bt) {
		String url ="http://manager.aiqiangua.com/AppS2_sign/api/sign?personid=" + personid;
		doRequest(url, null, bt);
	}	
	
}
