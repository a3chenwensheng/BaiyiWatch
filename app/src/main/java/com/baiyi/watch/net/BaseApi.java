package com.baiyi.watch.net;

import android.app.ProgressDialog;
import android.content.Context;

import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.utils.ActivityUtil;

import java.io.File;
import java.util.HashMap;

public class BaseApi {
	// public static final String BASE_Url2 = "http://203.195.166.239:8889";
	// public static final String BASE_Url2 = "http://a.aiqiangua.com:8888";
	
	//public static final String BASE_Url2 = "http://120.24.56.48:8889";
	public static final String BASE_Url2 = "http://s2.aiqiangua.com:8888";
	public static final String BASE_Url = BASE_Url2 + "/";

	public static final String NEWS_URL = "http://manager.aiqiangua.com/";
	// public static final String NEWS_URL = "http://10.0.0.35:8080/";

	public static final String AD_URL = "http://10.0.0.16:8080/";

	// public static final String TOPIC_URL = "http://10.0.0.35:8080/";
	public static final String TOPIC_URL = "http://manager.aiqiangua.com/";
	
	public static final String REPORT_URL = "http://manager.aiqiangua.com/";
	//public static final String REPORT_URL = "http://test.aiqiangua.com:8888/";

	public static BaseTaskPool taskPool;
	public static boolean showLoadBar = false;
	public static ProgressDialog pd;

	public synchronized void init(Context context) {
		pd = new ProgressDialog(context);
		pd.setIndeterminate(true);
		pd.setMessage("加载中..");
		pd.setCancelable(true);
		if (taskPool == null) {
			taskPool = new BaseTaskPool(context);
		}
	}

	public void doRequest(String taskUrl, HashMap<String, String> taskArgs, HttpCallback bt) {
		if (ActivityUtil.hasNetwork(MyApplication.getInstance())) {
			taskPool.addTask(1, taskUrl, taskArgs, bt, 0);
		}else {
			bt.onError("网络连接失败，请检查网络！");
		}
		
	}

	/**
	 * 带文件上传
	 * 
	 * @param taskUrl
	 * @param taskArgs
	 * @param files
	 * @param bt
	 */
	public void doRequest(String taskUrl, HashMap<String, String> taskArgs, HashMap<String, File> files, HttpCallback bt) {
		if (files != null && files.size() > 0) {
			taskPool.addTask(1, taskUrl, taskArgs, files, bt, 0);
		} else {
			taskPool.addTask(1, taskUrl, taskArgs, bt, 0);
		}

	}

	public synchronized void showLoadBar() {
		showLoadBar = true;
		pd.show();
	}

	public void hideLoadBar() {
		if (showLoadBar) {
			pd.dismiss();
			showLoadBar = false;
		}
	}
}
