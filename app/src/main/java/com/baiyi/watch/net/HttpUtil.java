package com.baiyi.watch.net;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

public class HttpUtil {
	
	public static final String network			= "网络错误";
	public static final String message			= "消息错误";
	public static final String jsonFormat		= "消息格式错误";
	
	//请求ID
	public static final int ACTION_ID_NORMAL = 0; //普通请求
	public static final int ACTION_ID_LOGIN = 1; //登陆，需要拿Cookie
	
	//cookie
	//public static String sCookie = "";
	
	
	public static final String model_package = "com.baiyi.watch.model.";
	static public int WAP_INT = 1;
	static public int NET_INT = 2;
	static public int WIFI_INT = 3;
	static public int NONET_INT = 4;
	
	static private Uri APN_URI = null;
	
	static public int getNetType (Context ctx) {
		if (ctx == null) {
			return -1;
		}
		// has network
		ConnectivityManager conn = null;
		try {
			conn = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (conn == null) {
			return HttpUtil.NONET_INT;
		}
		NetworkInfo info = conn.getActiveNetworkInfo();
		boolean available = info.isAvailable();
		if (!available){
			return HttpUtil.NONET_INT;
		}
		// check use wifi
		String type = info.getTypeName();
		if (type.equals("WIFI")) {
			return HttpUtil.WIFI_INT;
		}
		// check use wap
		APN_URI = Uri.parse("content://telephony/carriers/preferapn");
		Cursor uriCursor = ctx.getContentResolver().query(APN_URI, null, null, null, null);
		if (uriCursor != null && uriCursor.moveToFirst()) {
			String proxy = uriCursor.getString(uriCursor.getColumnIndex("proxy"));
			String port = uriCursor.getString(uriCursor.getColumnIndex("port"));
			String apn = uriCursor.getString(uriCursor.getColumnIndex("apn"));
			if (proxy != null && port != null && apn != null && apn.equals("cmwap") && port.equals("80") &&
				(proxy.equals("10.0.0.172") || proxy.equals("010.000.000.172"))) {
				return HttpUtil.WAP_INT;
			}
		}
		return HttpUtil.NET_INT;
	}
	
	/**
	 * 获取版本号
	 * 
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context) {
		String ret = "";
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			ret = pi.versionName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	/**
	 * 获取版本号
	 * 
	 * @param context
	 * @return
	 */
	public static int getVersionCode(Context context) {
		int ret = 1;
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			ret = pi.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}	
	
}