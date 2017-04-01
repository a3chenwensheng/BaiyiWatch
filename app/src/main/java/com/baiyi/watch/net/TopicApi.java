package com.baiyi.watch.net;

import android.content.Context;
import android.text.TextUtils;

public class TopicApi extends BaseApi {

	public static TopicApi mInstance;

	public static TopicApi getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new TopicApi(context);
		}
		return mInstance;
	}

	public TopicApi(Context context) {
		init(context);
	}

	/**
	 * 获取论坛话题列表
	 * 
	 * @param start
	 * @param rows_per_page
	 * @param bt
	 */
	public void getTopicList(String start, String rows_per_page, HttpCallback bt) {
		String url = TOPIC_URL + "AppS2_bbs/api/topic/list.do?start=" + start + "&rows_per_page=" + rows_per_page;
		doRequest(url, null, bt);
	}

	/**
	 * 发贴
	 * 
	 * @param personid
	 * @param topicTitle
	 * @param topicContent
	 * @param bt
	 */
	public void addTopic(String topicTitle, String topicContent, String person_id, String person_nickname, String person_imageurl, HttpCallback bt) {
		String url = TOPIC_URL + "AppS2_bbs/api/topic/add.do?topicTitle=" + topicTitle + "&topicContent=" + topicContent + "&person_id=" + person_id;
		
		if (!TextUtils.isEmpty(person_nickname)) {
			url += ("&person_nickname=" + person_nickname);
		}
		if (!TextUtils.isEmpty(person_imageurl)) {
			url += ("&person_imageurl=" + person_imageurl);
		}
		
		doRequest(url, null, bt);
	}
	
	/**
	 * 获取论坛话题回复
	 * 
	 * @param bt
	 */
	public void getReplyList(String topicid, HttpCallback bt) {
		String url = TOPIC_URL + "AppS2_bbs/api/reply/list.do?topicid=" + topicid;
		doRequest(url, null, bt);
	}

	/**
	 * 回复
	 * 
	 * @param personid
	 * @param topicId
	 * @param replyContent
	 * @param bt
	 */
	public void addReply(String replyContent, String topic_id, String person_id, String person_nickname, String person_imageurl, HttpCallback bt) {
		String url = TOPIC_URL + "AppS2_bbs/api/reply/add.do?replyContent=" + replyContent + "&topic_id=" + topic_id + "&person_id=" + person_id;
		
		if (!TextUtils.isEmpty(person_nickname)) {
			url += ("&person_nickname=" + person_nickname);
		}
		if (!TextUtils.isEmpty(person_imageurl)) {
			url += ("&person_imageurl=" + person_imageurl);
		}
		
		doRequest(url, null, bt);
	}
	
	/**
	 * 获取我的贴子列表
	 * 
	 * @param personid
	 * @param bt
	 */
	public void getMyTopicList(String personid, HttpCallback bt) {
		String url = TOPIC_URL + "AppS2_bbs/api/topic/listMyOwn.do?personid=" + personid;
		doRequest(url, null, bt);
	}
	
}
