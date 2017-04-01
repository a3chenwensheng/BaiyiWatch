package com.baiyi.watch.net;

import android.content.Context;

import java.util.HashMap;

public class CommunityApi extends BaseApi {

	public static CommunityApi mInstance;

	public static CommunityApi getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new CommunityApi(context);
		}
		return mInstance;
	}

	public CommunityApi(Context context) {
		init(context);
	}

	
	/**
	 * 申请离开社区
	 * @param userid
	 * @param community_name
	 */
	public void leaveCommunity(String userid, String community_name, HttpCallback bt){
		String url = BASE_Url + "api/community/leave";
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("userid", userid);
		taskArgs.put("community_name", community_name);
		doRequest(url, taskArgs, bt);
	}
	
	/**
	 * 申请加入社区
	 * @param userid
	 * @param community_name 
	 */
	public void joinCommunity(String userid, String community_name, HttpCallback bt){
		String url = BASE_Url + "api/community/join";
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("userid", userid);
		taskArgs.put("community_name", community_name);
		doRequest(url, taskArgs, bt);
	}

}
