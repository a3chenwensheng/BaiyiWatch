package com.baiyi.watch.net;

import android.content.Context;

import java.util.HashMap;

public class ServicepageApi extends BaseApi {

	public static ServicepageApi mInstance;

	public static ServicepageApi getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new ServicepageApi(context);
		}
		return mInstance;
	}

	public ServicepageApi(Context context) {
		init(context);
	}

	/**
	 * 新建主题
	 * 
	 * @param subject
	 * @param body
	 * @param bt
	 */
	public void newServicePage(String subject, String body, String username, HttpCallback bt) {
		String url = BASE_Url + "api/servicepage/new";
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("subject", subject);
		taskArgs.put("body", body);
		taskArgs.put("username", username);
		doRequest(url, taskArgs, bt);
	}

	/**
	 * 查看自己主题列表
	 * 
	 * @param bt
	 */
	public void getServicePageList(HttpCallback bt) {
		//String url = BASE_Url + "api/servicepage/list";
		String url = BASE_Url + "api/servicepage/";
		doRequest(url, null, bt);
	}

	/**
	 * 资源备注(评论)
	 * 
	 * @param pageId
	 * @param content
	 * @param bt
	 */
	public void sendServicePageComment(String pageId, String content, HttpCallback bt) {
		String url = BASE_Url + "api/page/" + pageId + "/comment/new";
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("content", content);
		doRequest(url, taskArgs, bt);
	}

}
