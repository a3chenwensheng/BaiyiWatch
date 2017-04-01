package com.baiyi.watch.net;

import android.content.Context;
import android.text.TextUtils;

import com.baiyi.watch.utils.Constant;

import java.util.HashMap;

public class AuthApi extends BaseApi {

	public static AuthApi mInstance;

	public static AuthApi getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new AuthApi(context);
		}
		return mInstance;
	}

	public AuthApi(Context context) {
		init(context);
	}

	/**
	 * 登陆
	 * 
	 * @param username
	 * @param password
	 * @param bt
	 */
	public void login(String username, String password, HttpCallback bt) {
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("username", username);
		taskArgs.put("password", password);
		taskArgs.put("app", Constant.APP);
		String url = BASE_Url + "api/auth/login";
		doRequest(url, taskArgs, bt);
	}

	/**
	 * 申请短信验证码 同一手机号1分钟内只允许申请一次短信验证码，一个验证码在5分钟内有效，只能使用一次。
	 * 
	 * @param phone
	 * @param bt
	 */
	public void SMSRequest(String phone, String purpose, String app, HttpCallback bt) {
		String url = BASE_Url + "/api/sms/request";
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("phone", phone);
		taskArgs.put("purpose", purpose);
		taskArgs.put("app", app);
		doRequest(url, taskArgs, bt);
	}
	
	/**
	 * 验证短信验证码
	 * 
	 * @param phone
	 * @param phone_checksum
	 * @param type
	 * @param bt
	 */
	public void checksMSG(String phone, String phone_checksum, String type, HttpCallback bt) {
		String url = BASE_Url + "/api/auth/checksmsg";
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("phone", phone);
		taskArgs.put("phone_checksum", phone_checksum);
		taskArgs.put("type", type);
		doRequest(url, taskArgs, bt);
	}
	
	/**
	 * 验证短信验证码(关联手机号码专用)
	 * 
	 * @param phone
	 * @param phone_checksum
	 * @param bt
	 */
	public void checks(String phone, String checknum, HttpCallback bt) {
		String url = BASE_Url + "/api/sms/check/";
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("phone", phone);
		taskArgs.put("checknum", checknum);
		doRequest(url, taskArgs, bt);
	}
	
	/**
	 * 注册
	 * 
	 * @param username
	 * @param password
	 * @param checksum
	 * @param bt
	 */
	public void regist(String username, String password, String checksum, HttpCallback bt) {
		String url = BASE_Url + "api/auth/register";
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("username", username);
		taskArgs.put("password", password);
		taskArgs.put("phone", username);
		taskArgs.put("phone_checksum", checksum);
		doRequest(url, taskArgs, bt);
	}
	
	/**
	 * 注册
	 * 
	 * @param username
	 * @param password
	 * @param auth_type
	 * @param auth_uid
	 * @param avatar_url
	 * @param gander
	 * @param bt
	 */
	public void regist(String username, String password, String auth_type, String auth_uid, String union_id, String avatar_url, String nickname, String phone, String gender, HttpCallback bt) {
		String url = BASE_Url + "api/auth/register";
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("username", username);
		taskArgs.put("password", password);
		taskArgs.put("auth_type", auth_type);
		taskArgs.put("auth_uid", auth_uid);
		if (!TextUtils.isEmpty(union_id)) {
			taskArgs.put("union_id", union_id);
		}
		taskArgs.put("avatar_url", avatar_url);
		taskArgs.put("nickname", nickname);
		if (!TextUtils.isEmpty(phone)) {
			taskArgs.put("phone", phone);
		}
		taskArgs.put("gender", gender);
		doRequest(url, taskArgs, bt);
	}

	/**
	 * 电话号码重设口令
	 * 
	 * @param phone
	 * @param password
	 * @param checksum
	 * @param bt
	 */
	public void resetPassWordByPhone(String phone, String password, String checksum, HttpCallback bt) {
		String url = BASE_Url + "api/auth/reset_passwd_by_phone";
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("phone", phone);
		taskArgs.put("password", password);
		taskArgs.put("phone_checksum", checksum);
		doRequest(url, taskArgs, bt);
	}
	
	/**
	 * 第三方验证
	 * 
	 * @param auth_type
	 * @param auth_uid
	 * @param app
	 * @param bt
	 */
	public void authorize(String auth_type, String auth_uid, String union_id, HttpCallback bt) {
		String url = BASE_Url + "api/auth/authorize";
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("auth_type", auth_type);
		taskArgs.put("auth_uid", auth_uid);
		if (!TextUtils.isEmpty(union_id)) {
			taskArgs.put("union_id", union_id);
		}
		taskArgs.put("app", Constant.APP);
		doRequest(url, taskArgs, bt);
	}

}
