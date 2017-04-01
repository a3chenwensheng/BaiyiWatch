package com.baiyi.watch.net;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.util.HashMap;

public class GroupApi extends BaseApi {

	public static GroupApi mInstance;

	public static GroupApi getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new GroupApi(context);
		}
		return mInstance;
	}

	public GroupApi(Context context) {
		init(context);
	}
	
	/**
	 * 查看家庭圈信息
	 * 
	 * @param groupID
	 * @param bt
	 */
	public void getGroupInfo(String groupID, HttpCallback bt) {
		String url = BASE_Url + "api/group/" + groupID;
		doRequest(url, null, bt);
	}

	/**
	 * 查看用户列表(单个家庭圈)
	 * 
	 * @param groupID
	 * @param bt
	 */
	public void getMemberList(String groupID, HttpCallback bt) {
		// String url = BASE_Url + "api/group/" + groupID;
		String url = BASE_Url + "api/group/" + groupID + "/person/list";
		doRequest(url, null, bt);
	}
	
	/**
	 * 查看成员信息
	 * 
	 * @param memberID
	 * @param bt
	 */
	public void getMemberInfo(String memberID, HttpCallback bt) {
		String url = BASE_Url + "api/group/member/" + memberID;
		doRequest(url, null, bt);
	}

	/**
	 * 编辑成员信息
	 * 
	 * @param memberId
	 * @param key
	 * @param value
	 * @param bt
	 */
	public void editCount(String memberId, String key, String value, HttpCallback bt) {
		String url = BASE_Url + "api/group/member/" + memberId + "/edit";
		// String url = BASE_Url + "api/person/" + memberId + "/edit";
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put(key, value);
		doRequest(url, taskArgs, bt);
	}

	/**
	 * 查看腕表列表
	 * 
	 * @param groupID
	 * @param bt
	 */
	public void getGroupDevices(String groupID, HttpCallback bt) {
		String url = BASE_Url + "api/group/" + groupID + "/device/list/?depth=3";
		doRequest(url, null, bt);
	}

	/**
	 * 家庭圈申请账号
	 * 
	 * @param username
	 * @param password
	 * @param nickname
	 * @param deviceid
	 * @param sim_phone
	 * @param bt
	 */
	public void groupRegist(String username, String password, String nickname, String deviceid, String sim_phone, String sim_phone_type, HttpCallback bt) {
		String url = BASE_Url + "api/group/register";
		// String url = "http://php.aiqiangua.com/phptest/try.php";

		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("username", username);
		taskArgs.put("password", password);

		// taskArgs.put("email", sim_phone+"@qq.com");

		if (nickname != null && !nickname.equals("")) {
			taskArgs.put("nickname", nickname);
			taskArgs.put("name", nickname);
		}
		if (deviceid != null && !deviceid.equals("")) {
			taskArgs.put("deviceid", deviceid);
		}
		if (sim_phone != null && !sim_phone.equals("")) {
			taskArgs.put("sim_phone", sim_phone);
		}

		if (sim_phone_type != null && !sim_phone_type.equals("")) {
			taskArgs.put("sim_phone_type", sim_phone_type);
		} else {
			taskArgs.put("sim_phone_type", "");// TODO
		}

		doRequest(url, taskArgs, bt);
	}

	/**
	 * 普通家庭圈邀请账号
	 * 
	 * @param username
	 * @param bt
	 */
	public void inviteMember(String username, String userid, HttpCallback bt) {
		String url = BASE_Url + "api/group/invite/";
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		if (!TextUtils.isEmpty(username)) {
			taskArgs.put("username", username);
		}
		if (!TextUtils.isEmpty(userid)) {
			taskArgs.put("userid", userid);
		}
		
		doRequest(url, taskArgs, bt);
	}

	/**
	 * 生成邀请码
	 * 
	 * @param groupId
	 * @param bt
	 */
	public void inviteCode(String groupId, HttpCallback bt) {
		String url = BASE_Url + "api/group/" + groupId + "/invite_code/new/";
		doRequest(url, new HashMap<String, String>(), bt);
	}

	/**
	 * 删除成员 向自己管理的小组删除一个或多个成员。
	 * 
	 * @param groupId
	 * @param userid
	 * @param bt
	 */
	public void deleteMember(String groupId, String userid, HttpCallback bt) {
		String url = BASE_Url + "api/group/" + groupId + "/member/delete";
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("userid", userid);
		doRequest(url, taskArgs, bt);
	}
	
	/**
	 * 当前用户离开小组
	 * 
	 * @param groupId
	 * @param bt
	 */
	public void leaveGroup(String groupId, HttpCallback bt) {
		String url = BASE_Url + "api/group/" + groupId + "/leave/";
		doRequest(url, new HashMap<String, String>(), bt);
	}
	
	/**
	 * 解散小组
	 * 
	 * @param groupId
	 * @param bt
	 */
	public void disbandGroup(String groupId, HttpCallback bt) {
		String url = BASE_Url + "api/group/" + groupId + "/disband/";
		doRequest(url, new HashMap<String, String>(), bt);
	}
	
	/**
	 * 使用邀请码
	 * 
	 * @param code
	 * @param bt
	 */
	public void JoinGroupByCode(String code, HttpCallback bt) {
		String url = BASE_Url + "api/group/invite_code/confirm/";
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("code", code);
		doRequest(url, taskArgs, bt);
	}
	
	public void getPageList(int rows_per_page, int page, String groupid, String type, HttpCallback bt) {
		String url = BASE_Url + "/api/group/" + groupid + "/page/list" + "/?page=" + page + "&rows_per_page=" + rows_per_page + "&depth=2&type=" + type;
		doRequest(url, null, bt);
	}
	
	/**
	 * 发表新图文（暂时用来发语音）
	 * @param subject
	 * @param body
	 * @param file
	 * @param bt
	 */
	public void newPage(String groupid, String subject, String body, File file, HttpCallback bt) {
		String url = BASE_Url + "api/page/new";

		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("groupid", groupid);
		taskArgs.put("subject", subject);
		if (body != null) {
			taskArgs.put("body", body);
		}

		HashMap<String, File> files = new HashMap<String, File>();
		if (file != null) {
			files.put("upfile", file);
		}
		doRequest(url, taskArgs, files, bt);
	}
	
	/**
	 * 删除资源(暂时用来删除语音)
	 * 
	 * @param pageid
	 * @param bt
	 */
	public void deletePage(String pageid, HttpCallback bt) {
		String url = BASE_Url + "api/page/delete/grouppage/?pageid=" + pageid;
		doRequest(url, null, bt);
	}
	
	/**
	 * 获取推送数量
	 * 
	 * @param groupid
	 * @param personid
	 * @param imei
	 * @param bt
	 */
	public void getDevPushByGroup(String groupid, String personid, String imei, HttpCallback bt) {
		String url = BASE_Url + "api/getdevpushbygroup/?groupid=" + groupid + "&personid=" + personid + "&imei=" + imei;
		doRequest(url, null, bt);
	}
	
}
