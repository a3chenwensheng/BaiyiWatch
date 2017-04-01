package com.baiyi.watch.net;

import android.content.Context;

import java.util.HashMap;

public class MessageApi extends BaseApi {

	public static MessageApi mInstance;

	public static MessageApi getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new MessageApi(context);
		}
		return mInstance;
	}

	public MessageApi(Context context) {
		init(context);
	}

	/**
	 * 查看当前用户消息列表，包括发出和接收。
	 * 
	 * @param bt
	 */
	public void getMessageList(HttpCallback bt) {
		String url = BASE_Url + "api/community/messages/";
		doRequest(url, null, bt);
	}
	
	/**
	 * 查看与自己有关的社区邀请和解除等信息，包括自己发出和自己收到的。
	 * 
	 * @param messageId
	 * @param request -->[accept|reject|cancel|delete]
	 * @param bt
	 */
	public void handleMessage(String messageId, String request, HttpCallback bt){
		String url = BASE_Url + "api/community/message/" + messageId + "/" + request;
		doRequest(url, new HashMap<String, String>(), bt);
	}
	
}
