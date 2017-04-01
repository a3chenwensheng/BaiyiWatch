package com.baiyi.watch.net;

import android.content.Context;

public class NewsApi extends BaseApi {

	public static NewsApi mInstance;

	public static NewsApi getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new NewsApi(context);
		}
		return mInstance;
	}

	public NewsApi(Context context) {
		init(context);
	}

	/**
	 * 获取Banner
	 * 
	 * @param bt
	 */
	public void getBanner(HttpCallback bt) {
		String url = NEWS_URL + "AppS2_news/api/newsstickie.do";
		doRequest(url, null, bt);
	}

	/**
	 * 获取新闻列表
	 * 
	 * @param bt
	 */
	public void getNewsList(String start, String rows_per_page,HttpCallback bt) {
		String url = NEWS_URL + "AppS2_news/api/newslist.do?start="+ start + "&rows_per_page=" + rows_per_page;
//		HashMap<String, String> taskArgs = new HashMap<String, String>();
//		taskArgs.put("start", start);
//		taskArgs.put("rows_per_page", rows_per_page);
		doRequest(url, null, bt);
	}

}
