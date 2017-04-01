package com.baiyi.watch.net;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.HashMap;

public class OrderApi extends BaseApi {

	public static OrderApi mInstance;

	public static OrderApi getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new OrderApi(context);
		}
		return mInstance;
	}

	public OrderApi(Context context) {
		init(context);
	}
	
	/**
	 * 获取商品列表
	 * 
	 * @param onsale
	 * @param bt
	 */
	public void getGoodsList(String onsale, HttpCallback bt) {
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("onsale", onsale);
//		taskArgs.put("name", name);
//		taskArgs.put("sevicedate", sevicedate);
//		taskArgs.put("small", small);
		String url = BASE_Url + "api/goods/list/";
		doRequest(url, taskArgs, bt);
	}

	/**
	 * 订单列表
	 * 
	 * @param state
	 * @param bt
	 */
	public void getOrderList(String state, int rows_per_page, int page, HttpCallback bt) {
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		String url = BASE_Url + "api/order/list/";
		
		if (!TextUtils.isEmpty(state)) {
			taskArgs.put("state", state);
		}
		taskArgs.put("rows_per_page", rows_per_page + "");
		taskArgs.put("page", page + "");

		doRequest(url, taskArgs, bt);
	}
	
	/**
	 * 购买, 生成订单
	 * 
	 * @param imei
	 * @param goodsid
	 * @param bt
	 */
	public void buy(String imei, String goodsid, HttpCallback bt) {
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("imei", imei);
		taskArgs.put("goodsid", goodsid);
		String url = BASE_Url + "api/order/buy/";
		doRequest(url, taskArgs, bt);
	}
	
	/**
	 * 更改订单状态
	 * 
	 * @param orderid
	 * @param prepay_id
	 * @param state
	 * @param bt
	 */
	public void changestate(String orderid, String prepay_id, @NonNull String state, HttpCallback bt) {
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		if (!TextUtils.isEmpty(orderid)) {
			taskArgs.put("orderid", orderid);
		}
		if (!TextUtils.isEmpty(prepay_id)) {
			taskArgs.put("prepay_id", prepay_id);
		}
		taskArgs.put("state", state);// cancel delete
		String url = BASE_Url + "api/order/changestate/";
		doRequest(url, taskArgs, bt);
	}
	
	/**
	 * 检查更新Order,使之可支付
	 * @param orderid
	 * @param bt
	 */
	public void checkOrder(String orderid, HttpCallback bt) {
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("orderid", orderid);
		String url = BASE_Url + "api/order/check/";
		doRequest(url, taskArgs, bt);
	}
	
	
}
