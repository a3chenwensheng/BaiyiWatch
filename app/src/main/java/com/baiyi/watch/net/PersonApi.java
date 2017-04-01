package com.baiyi.watch.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.baiyi.watch.utils.Constant;
import com.baiyi.watch.utils.ImageUtils;

import java.io.File;
import java.util.HashMap;

public class PersonApi extends BaseApi {

	public static PersonApi mInstance;

	public static PersonApi getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new PersonApi(context);
		}
		return mInstance;
	}

	public PersonApi(Context context) {
		init(context);
	}

	/**
	 * 查看账号信息
	 * 
	 * @param personId
	 * @param bt
	 */
	public void getPersonInfo(String personId, HttpCallback bt) {
		String url = BASE_Url + "api/person/" + personId;
		doRequest(url, null, bt);
	}

	/**
	 * 修改账号信息
	 * 
	 * @param personId
	 * @param key
	 * @param value
	 * @param bt
	 */
	public void editPerson(String personId, String key, String value, HttpCallback bt) {
		String url = BASE_Url + "api/person/" + personId + "/edit";
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put(key, value);
		doRequest(url, taskArgs, bt);
	}
	
	/**
	 * 修改用户信息
	 * 
	 * @param personId
	 * @param key
	 * @param value
	 * @param bt
	 */
	public void editPerson(String personId, String age, String weight, String height, String step, HttpCallback bt) {
		String url = BASE_Url + "api/person/" + personId + "/edit";
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("age", age);
		taskArgs.put("weight", weight);
		taskArgs.put("height", height);
		taskArgs.put("step", step);
		doRequest(url, taskArgs, bt);
	}

	/**
	 * 设置口令
	 * 
	 * @param personId
	 * @param old_password
	 * @param new_password
	 * @param bt
	 */
	public void changePassword(String personId, String old_password, String new_password, HttpCallback bt) {
		String url = BASE_Url + "api/person/" + personId + "/change_passwd";
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("old_password", old_password);
		taskArgs.put("password", new_password);
		doRequest(url, taskArgs, bt);
	}

	/**
	 * 设置口令2
	 * 
	 * @param old_password
	 * @param new_password
	 * @param bt
	 */
	public void changePassword(String old_password, String new_password, HttpCallback bt) {
		String url = BASE_Url + "api/person/change_passwd";
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("old_password", old_password);
		taskArgs.put("password", new_password);
		doRequest(url, taskArgs, bt);
	}

	/**
	 * 上传头像
	 * 
	 * @param bitmap
	 * @param bt
	 */
	public void changeAvatar(Bitmap bitmap, HttpCallback bt) {
		File file = ImageUtils.saveFile(bitmap, Constant.PATH_TEMP_HEAD_IMG);
		String url = BASE_Url + "api/person/change_avatar";
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		HashMap<String, File> files = null;
		if (file != null) {
			files = new HashMap<String, File>();
			files.put("upfile", file);
		}
		doRequest(url, taskArgs, files, bt);

	}

	/**
	 * 上传头像
	 * 
	 * @param bitmap
	 * @param personId
	 * @param bt
	 */
	public void changeAvatar(Bitmap bitmap, String personId, HttpCallback bt) {
		File file = ImageUtils.saveFile(bitmap, Constant.PATH_TEMP_HEAD_IMG);
		String url = BASE_Url + "api/person/" + personId + "/change_avatar";
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		HashMap<String, File> files = null;
		if (file != null) {
			files = new HashMap<String, File>();
			files.put("upfile", file);
		}
		doRequest(url, taskArgs, files, bt);

	}
	
	/**
	 * 增加推送设备
	 * 
	 * @param 
	 * @param bt
	 * curl -v -A CURL -b cookies.txt -d 'token=d471b6c9243c7fc2483f8d8167c4b04443140eab&token_type=android' http://127.0.0.1:8000/api/person/attach
	 */
	public void attach(String token, String token_type, String push_server, HttpCallback bt) {
		String url = BASE_Url + "api/person/attach";
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("token", token);
		taskArgs.put("token_type", token_type);
		if (!TextUtils.isEmpty(push_server)) {
			taskArgs.put("push_server", push_server);
		}
		doRequest(url, taskArgs, bt);
	}
	
	/**
	 * 移除推送设备
	 * 
	 * @param 
	 * @param bt
	 * curl -v -A CURL -b cookies.txt -d 'token=d471b6c9243c7fc2483f8d8167c4b04443140eab&token_type=android' http://127.0.0.1:8000/api/person/unattach
	 */
	public void unattach(String token, String token_type, HttpCallback bt) {
		String url = BASE_Url + "api/person/unattach";
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("token", token);
		taskArgs.put("token_type", token_type);
		doRequest(url, taskArgs, bt);
	}
	
	/**
	 * 查看推送的消息
	 * 
	 * @param token
	 * @param token_type
	 * @param pgeCount
	 * @param page
	 * @param bt
	 */
	public void getNotification(String token, String token_type, String title, int rows_per_page, int page,HttpCallback bt) {
		//CURL -b cookies.txt http://127.0.0.1:8000/api/notification
		String url = BASE_Url + "api/notification/" + "?page=" + page + "&rows_per_page=" + rows_per_page + "&token="
				+ token + "&token_type=" + token_type + "&title=" + title;
		doRequest(url, null, bt);
	}
	
	/**
	 * 查看推送的消息
	 * 
	 * @param recipient
	 * @param title
	 * @param created_at
	 * @param bt
	 */
	public void getNotification(String recipient, String title, String created_at, HttpCallback bt) {
		String url = BASE_Url + "api/notification/" + "?rows_per_page=0&recipient=" + recipient + "&title=" + title + "&created_at="
				+ created_at;
		doRequest(url, null, bt);
		
	}
	
	/**
	 * 查看推送的消息分页列表
	 * 
	 * @param personid
	 * @param imei
	 * @param type
	 * @param rows_per_page
	 * @param page
	 * @param is_cleared
	 * @param bt
	 */
	public void getNotification(String personid, String imei, String type, int rows_per_page, int page, String is_cleared, HttpCallback bt) {
		String url = BASE_Url + "api/getnotiflist/?depth=1&personid=" + personid + "&rows_per_page=" + rows_per_page + "&page=" + page;
		
		if (!TextUtils.isEmpty(imei)) {
			url += ("&imei=" + imei);
		}
		
		if (!TextUtils.isEmpty(type)) {
			url += ("&type=" + type);
		}

		if (!TextUtils.isEmpty(is_cleared)) {
			url += ("&is_cleared=" + is_cleared);
		}

		doRequest(url, null, bt);
	}
	
	/**
	 * 将消息标为已读
	 * 
	 * @param personid
	 * @param imei
	 * @param type
	 * @param bt
	 */
	public void readNotifi(String personid, String imei, String type, HttpCallback bt) {
		String url = BASE_Url + "api/readnotifi/?depth=1&personid=" + personid;
		
		if (!TextUtils.isEmpty(imei)) {
			url += ("&imei=" + imei);
		}
		
		if (!TextUtils.isEmpty(type)) {
			url += ("&type=" + type);
		}

		doRequest(url, null, bt);
	}	
		

	/**
	 * 查看推送的消息详情
	 * 
	 * @param notifid
	 * @param personid
	 * @param bt
	 */
	public void getNotifdata(String notifid, String personid, HttpCallback bt) {
		String url = BASE_Url + "api/getnotifdata/?notifid=" + notifid + "&personid=" + personid;

		doRequest(url, null, bt);
	}
	
	/**
	 * 查看用户列表(所有家庭圈)
	 * 
	 * @param personId
	 * @param bt
	 */
	public void getAllMembers(String personId, HttpCallback bt) {
		String url = BASE_Url + "api/person/getgroupmember/?personid=" + personId;
		doRequest(url, null, bt);
	}
	
	/**
	 * 查看所有设备(所有家庭圈)
	 * 
	 * @param personId
	 * @param bt
	 */
	public void getAllDevices(String personId, HttpCallback bt) {
		String url = BASE_Url + "api/person/getalldevice/?personid=" + personId;
		doRequest(url, null, bt);
	}
	
	/**
	 * 关联微信 等第三方应用
	 * 	
	 * @param personid
	 * @param auth_uid
	 * @param auth_type
	 * @param bt
	 */
	public void setAuthUid(String personid, String auth_uid, String union_id, String auth_type, HttpCallback bt) {
		String url = BASE_Url + "api/person/setauthuid/?personid=" + personid + "&auth_uid=" + auth_uid + "&auth_type=" + auth_type;
		if (!TextUtils.isEmpty(union_id)) {
			url +=  ("&union_id=" + union_id);
		}
		doRequest(url, null, bt);
	}

}
