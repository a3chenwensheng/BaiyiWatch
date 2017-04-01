package com.baiyi.watch.net;

import android.content.Context;

import com.baiyi.watch.model.SettingAlert;
import com.baiyi.watch.model.SettingSosNumber;
import com.baiyi.watch.model.SettingToolNumber;
import com.baiyi.watch.model.Wifi;
import com.baiyi.watch.utils.TimeUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class DeviceApi extends BaseApi {
	public static DeviceApi mInstance;

	public static DeviceApi getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new DeviceApi(context);
		}
		return mInstance;
	}

	public DeviceApi(Context context) {
		init(context);
	}
	
	/**
	 * 获取设备总览数据(V2.X调用)
	 * 
	 * @param timeBegin
	 * @param deviceId
	 * @param bt
	 */
	public void getDeviceMainData(String timeBegin, String deviceId, HttpCallback bt) {
		String url = BASE_Url + "/api/device/" + deviceId + "/data/" + timeBegin;
		doRequest(url, null, bt);
	}
	
	/**
	 * 获取设备总览数据(V3.X调用)(弃用)
	 * 
	 * @param timeBegin
	 * @param deviceId
	 * @param bt
	 */
	public void getDeviceMainData2(String timeBegin, String deviceId, HttpCallback bt) {
		String url = BASE_Url + "/api/device/" + deviceId + "/indexdata/" + timeBegin;
		doRequest(url, null, bt);
	}
	
	/**
	 * 获取设备总览数据(V3.X调用)
	 * 
	 * @param timeBegin
	 * @param deviceId
	 * @param bt
	 */
	public void getDeviceMainData3(String timeBegin, String deviceId, HttpCallback bt) {
		String url = BASE_Url + "/api/device/" + deviceId + "/newdata/" + timeBegin;
		doRequest(url, null, bt);
	}
	
	/**
	 * 查看设备运动数据
	 * 
	 * @param deviceId
	 * @param bt
	 */
	public void getDevicePedometerdata(String timeBegin, String deviceId, HttpCallback bt) {
		String url = BASE_Url + "api/pedometerdata/?depth=1&rows_per_page=0&device=" + deviceId + "&time_begin=" + timeBegin;
		doRequest(url, null, bt);
	}
	//卡路里专用/api/device/([0-9a-fA-F]{8,})/newpedometerdata/
	public void getDevicePedometerdata2(String timeBegin, String deviceId, HttpCallback bt) {
		String url = BASE_Url + "api/device/" + deviceId + "/newpedometerdata/?depth=1&rows_per_page=0&time_begin=" + timeBegin;
		doRequest(url, null, bt);
	}
	
	/**
	 * 查看设备运动数据
	 * @param time
	 * @param deviceID
	 * @param httpCallback
	 */
	public void getDevicePedometerdata(long time, String deviceID, HttpCallback httpCallback) {
		Date date = new Date(time);
		// date = TimeUtil.changeTimeZone(date,
		// TimeZone.getTimeZone("Asia/Shanghai"), TimeZone.getTimeZone("GMT"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		getDevicePedometerdata(sdf.format(date), deviceID, httpCallback);

	}
	
	/**
	 * 查看设备定位数据(旧)
	 * @param deviceId
	 * @param timedata
	 * @param bt
	 */
	public void getDeviceLocationData(String deviceId, String timedata, HttpCallback bt) {
		//String url = BASE_Url + "api/locationdata/?device=" + deviceId + "&time_begin=" + timedata + "&rows_per_page=1&depth=1";
		String url = BASE_Url + "api/locationdata/?device=" + deviceId + "&time_begin=" + timedata + "&order_by=-time_begin&is_track=1&depth=1&rows_per_page=0";
		doRequest(url, null, bt);
	}
	
	/**
	 * 查看设备心率数据
	 * @param time
	 * @param deviceID
	 * @param httpCallback
	 */
	public void getDeviceHeartrateData(long time, String deviceID, HttpCallback httpCallback) {
		Date date = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		getDeviceHeartrateData(sdf.format(date), deviceID, httpCallback);
	}
	
	
	/**
	 * 查看设备心率数据
	 * @param timeBegin
	 * @param deviceId
	 * @param bt
	 */
	public void getDeviceHeartrateData(String timeBegin, String deviceId, HttpCallback bt) {
		String url = BASE_Url + "api/heartratedata/?depth=1&device=" + deviceId + "&rows_per_page=100" + "&time_begin=" + timeBegin;
		doRequest(url, null, bt);
	}
	
	/**
	 * 查看设备心率数据（即使查询）
	 * @param timeBegin
	 * @param deviceId
	 * @param bt
	 */
	public void getDeviceHeartrateData2(String timeBegin, String deviceId, HttpCallback bt) {
		String url = BASE_Url + "api/heartratedata/?device=" + deviceId + "&rows_per_page=1" + "&time_begin=" + timeBegin;
		doRequest(url, null, bt);
	}
	
	/**
	 * 查看设备睡眠数据(测试)
	 */
	public void getDeviceSleepData(String timeBegin, String deviceId, HttpCallback bt) {
		String url = BASE_Url + "api/sleepdata/?depth=1&device=" + deviceId + "&time_begin=" + timeBegin;
		doRequest(url, null, bt);
	}
	
	/**
	 * 查看设备血压数据(测试)
	 */
	public void getDeviceBloodPressureData(String deviceId, HttpCallback bt) {
		String url = BASE_Url + "api/bloodpressuredata/?depth=1&device=" + deviceId;
		doRequest(url, null, bt);
	}
	
	public void getDeviceBloodPressureData(String deviceId, int rows_per_page, int page,HttpCallback bt) {
		String url = BASE_Url + "api/bloodpressuredata/?depth=1&order_by=-time_begin&page=" + page + "&rows_per_page=" + rows_per_page + "&device=" + deviceId;
		doRequest(url, null, bt);
	}
	
	/**
	 * 查看单个设备
	 * 
	 * @param deviceID
	 * @param bt
	 */
	public void getDeviceInfo(String deviceID, HttpCallback bt) {
		String url = BASE_Url + "api/device/" + deviceID;
		doRequest(url, null, bt);
	}
	
	/**
	 * 查看单个设备（depth=1）
	 * 
	 * @param deviceID
	 * @param bt
	 */
	public void getDeviceInfo2(String deviceID, HttpCallback bt) {
		String url = BASE_Url + "api/device/" + deviceID + "/?depth=1";
		doRequest(url, null, bt);
	}
	
	/**
	 * 查看单个设备（S1黄手环绑定专用）
	 * 
	 * @param deviceID
	 * @param bt
	 */
	public void getDeviceInfo3(String deviceID, HttpCallback bt) {
		String url = BASE_Url + "api/deviceinfo/find/?imei=" + deviceID + "&depth=1";
		doRequest(url, null, bt);
	}
	/**
	 * 完善设备信息（S1黄手环绑定专用）
	 * @param deviceID
	 * @param name
	 * @param care_phone
	 * @param address
	 * @param casehistory
	 * @param bt
	 */
	public void saveDeciceInfo(String deviceID, String name, String care_phone, String address, String casehistory, HttpCallback bt) {
		String url = BASE_Url + "api/deviceinfo/save/?imei=" + deviceID + "&name=" + name + "&care_phone=" + care_phone + "&address=" + address + "&casehistory=" + casehistory;
		doRequest(url, null, bt);
	}
	
	/**
	 * 编辑亲情号码
	 * 
	 * @param deviceID
	 * @param sosNumber
	 * @param bt
	 */
	public void editSosNumber(String deviceID, SettingSosNumber sosNumber, HttpCallback bt) {
		String url = BASE_Url + "api/device/" + deviceID + "/sos_numbers/" + sosNumber.getSeqid();
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("name", sosNumber.getName());
		taskArgs.put("num", sosNumber.getNum());
		taskArgs.put("dial_flag", ("true".equals(sosNumber.getDial_flag()))?"1":"0");
		doRequest(url, taskArgs, bt);
	}
	
	/**
	 * 清空亲情号码
	 * 
	 * @param deviceID
	 * @param sosNumber
	 * @param bt
	 */
	public void clearSosNumber(String deviceID, SettingSosNumber sosNumber, HttpCallback bt) {
		String url = BASE_Url + "api/device/" + deviceID + "/sos_numbers/" + sosNumber.getSeqid();
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("clear", "clear");
		doRequest(url, taskArgs, bt);
	}
	
	/**
	 * 修改设备
	 * 
	 * @param deviceID
	 * @param key
	 * @param value
	 * @param bt
	 */
	public void editDevice(String deviceID, String key, String value, HttpCallback bt) {
		String url = BASE_Url + "api/device/" + deviceID + "/edit";
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put(key, value);
		doRequest(url, taskArgs, bt);
	}
	public void editDevice(String deviceID, Wifi wifi, HttpCallback bt) {
		String url = BASE_Url + "api/device/" + deviceID + "/edit";
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("home_wifi_addr", wifi.getAddr());
		taskArgs.put("home_wifi_ssid", wifi.getSsid());
		doRequest(url, taskArgs, bt);
	}
	
	/**
	 * 修改设备 心率上下限
	 * @param deviceID
	 * @param theshold_heartrate_l
	 * @param theshold_heartrate_h
	 * @param bt
	 */
	public void editDevice(String deviceID, int theshold_heartrate_l, int theshold_heartrate_h, HttpCallback bt) {
		String url = BASE_Url + "api/device/" + deviceID + "/edit";
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("theshold_heartrate_l", theshold_heartrate_l+"");
		taskArgs.put("theshold_heartrate_h", theshold_heartrate_h+"");
		doRequest(url, taskArgs, bt);
	}
	
	/**
	 * 修改设备 睡眠时间
	 * 
	 * @param deviceID
	 * @param sleep_period_begin
	 * @param sleep_period_end
	 * @param bt
	 */
	public void editDeviceSleepTime(String deviceID, String sleep_period_begin, String sleep_period_end, HttpCallback bt) {
		String url = BASE_Url + "api/device/" + deviceID + "/edit";
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("sleep_period_begin", sleep_period_begin);
		taskArgs.put("sleep_period_end", sleep_period_end);
		doRequest(url, taskArgs, bt);
	}
	
	/**
	 * 在线设备操作
	 * poweroff | 关机
	 * ring     | 关机
	 * restart  | 重启
	 * restore  | 恢复出厂
	 * 
	 * @param deviceID
	 * @param action
	 * @param bt
	 */
	public void actionDevice(String deviceID, String action, HttpCallback bt) {
		String url = BASE_Url + "api/device/" + deviceID + "/" + action;
		doRequest(url, new HashMap<String, String>(), bt);
	}
	
	public void actionHeartrateDevice(String deviceID, HttpCallback bt) {
		String url = BASE_Url + "api/device/" + deviceID + "/get_heartratedata" ;
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("interval", "0");
		taskArgs.put("time", "1");
		doRequest(url, taskArgs, bt);
	}
	
	/**
	 * 获取设备最新位置数据
	 * 
	 * @param deviceID
	 * @param bt
	 */
	public void getDeviceLocationData(String deviceID, HttpCallback bt) {
		String url = BASE_Url + "api/device/" + deviceID + "/data/locationdata/";
		doRequest(url, null, bt);
	}
	
	/**
	 * 验证设备
	 * 
	 * @param deviceID
	 * @param bt
	 */
	public void validateDevice(String deviceID, HttpCallback bt) {
		String url = BASE_Url + "/api/device/validate/";
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("deviceid", deviceID);
		doRequest(url, taskArgs, bt);
	}
	
	/**
	 * 设备去激活
	 * 将设备的拥有人设置为缺省值。
	 * 
	 * @param deviceID
	 * @param bt
	 */
	public void unactiveDevice(String deviceID, HttpCallback bt) {
		String url = BASE_Url + "api/device/" + deviceID + "/unactive";
		doRequest(url, new HashMap<String, String>(), bt);
	}
	//20160704 后台会删除
	public void unactiveDevice2(String deviceID, HttpCallback bt) {
		String url = BASE_Url + "api/device/" + deviceID + "/unactivedelete";
		doRequest(url, new HashMap<String, String>(), bt);
	}
	
	/**
	 * 查看设备紧急呼叫
	 * 
	 * @param deviceID
	 * @param bt
	 */
	public void getDeviceSOSData(String timeBegin, String deviceID, HttpCallback bt) {
		String url = BASE_Url + "api/sosdata/?depth=1&device=" + deviceID + "&time_begin=" + timeBegin;
		doRequest(url, null, bt);
	}
	
	/**
	 * 查看设备跌倒数据
	 * 
	 * @param deviceID
	 * @param bt
	 */
	public void getDeviceFallData(String timeBegin, String deviceID, HttpCallback bt) {
		String url = BASE_Url + "api/falldata/?depth=1&device=" + deviceID + "&depth=1&time_begin=" + timeBegin;
		doRequest(url, null, bt);
	}
	
	/**
	 * 获取设备环境音
	 * 
	 * @param deviceID
	 * @param bt
	 */
	public void getDeviceenvvoiceList(String timeBegin, String deviceID, HttpCallback bt) {
		String url = BASE_Url + "api/device/" + deviceID + "/deviceenvvoice/list/?depth=1&created_at=" + timeBegin;
		doRequest(url, null, bt);
	}
	
	/**
	 * 获取设备环境音(当天最后一条)
	 * 
	 * @param deviceID
	 * @param bt
	 */
	public void getDeviceenvvoiceList2(String timeBegin, String deviceID, HttpCallback bt) {
		String url = BASE_Url + "api/device/" + deviceID + "/deviceenvvoice/list/?depth=1&rows_per_page=1&created_at=" + timeBegin;
		doRequest(url, null, bt);
	}
	
	/**
	 * 删除设备环境录音
	 * @param voiceid
	 * @param bt
	 */
	public void deleteDeviceenvvoice(String voiceid, HttpCallback bt) {
		String url = BASE_Url + "api/deviceenvvoice/delete/?voiceid=" + voiceid;
		doRequest(url, null, bt);
	}
	
	/**
	 * 设置提醒
	 * 
	 * @param deviceID
	 * @param alert
	 * @param file
	 * @param bt
	 */
	public void editAlert(String deviceID, SettingAlert alert, File file, HttpCallback bt) {
		String url = BASE_Url + "api/device/" + deviceID + "/alerts/" + alert.getSeqid();
		
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("is_medicine", ("true".equals(alert.getIs_medicine()))?"1":"0");
		taskArgs.put("name", alert.getName());
		if ("1".equals(alert.getAlert_type())) {
			taskArgs.put("alert_type", "1");
			taskArgs.put("cycle", "2");
		}else {
			taskArgs.put("alert_type", "0");
			taskArgs.put("cycle", "1");
		}
		taskArgs.put("time", alert.getTime());
		taskArgs.put("enable", ("true".equals(alert.getEnable()))?"1":"0");
		
		taskArgs.put("created_at", TimeUtils.getDateStr("yyyyMMddHHmmss"));
		
		HashMap<String, File> files = new HashMap<String, File>();
		if (file != null) {
			files.put("upfile", file);
		}
		
		doRequest(url, taskArgs, files,  bt);
	}
	
	/**
	 * 清空提醒
	 * @param deviceID
	 * @param alert
	 * @param bt
	 */
	public void clearAlert(String deviceID, SettingAlert alert, HttpCallback bt) {
		String url = BASE_Url + "api/device/" + deviceID + "/alerts/" + alert.getSeqid();
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("clear", "clear");
		doRequest(url, taskArgs, bt);
	}
	
	/**
	 * 设置安全区域
	 * 
	 * @param deviceID
	 * @param seqid
	 * @param key
	 * @param value
	 * @param bt
	 */
	public void editFences(String deviceID, String seqid, String key, String value, HttpCallback bt) {
		String url = BASE_Url + "api/device/" + deviceID + "/fences/" + seqid;
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put(key, value);
		doRequest(url, taskArgs, bt);
	}
	
	/**
	 * 设置安全区域时间范围
	 * 
	 * @param deviceID
	 * @param seqid
	 * @param value
	 * @param value2
	 * @param bt
	 */
	public void editFencesTime(String deviceID, String seqid, String value, String value2, HttpCallback bt) {
		String url = BASE_Url + "api/device/" + deviceID + "/fences/" + seqid;
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("time_begin", value);
		taskArgs.put("time_end", value2);
		doRequest(url, taskArgs, bt);
	}
	
	/**
	 * 查看设备开关机数据
	 * 
	 * @param deviceID
	 * @param bt
	 */
	public void getPowerdata(String deviceID, HttpCallback bt) {
		//String url = BASE_Url + "api/powerdata/?device=" + deviceID ;
		String url = BASE_Url + "api/powerdata/?depth=1&order_by=-time_begin&rows_per_page%3D1=1&type=3&device=" + deviceID ;
		doRequest(url, null, bt);
	}
	
	/**
	 * 查看设备附近WiFi数据
	 * 
	 * @param deviceID
	 * @param bt
	 */
	public void getWifiData(String deviceID, HttpCallback bt) {
		String url = BASE_Url + "api/device/" + deviceID + "/get_wifi";
		doRequest(url, null, bt);
	}
	
	/**
	 * 获取睡眠报告列表
	 * 
	 * @param deviceID
	 * @param bt
	 */
	public void getSleepCatalog(String deviceID, HttpCallback bt) {
		String url = REPORT_URL + "/AppS2_healthReport/api/sleepCatalog.do?device=" + deviceID;
		doRequest(url, null, bt);
	}
	
	/**
	 * 获取睡眠周报
	 * 
	 * @param deviceID
	 * @param date
	 * @param bt
	 */
	public void getSleepWeekly(String deviceID, String date, HttpCallback bt) {
		String url = REPORT_URL + "AppS2_healthReport/api/sleepWeekly.do?device=" + deviceID + "&date=" + date;
		doRequest(url, null, bt);
	}
	
	/**
	 * 获取心率报告列表
	 * 
	 * @param deviceID
	 * @param bt
	 */
	public void getHeartCatalog(String deviceID, HttpCallback bt) {
		String url = REPORT_URL + "/AppS2_healthReport/api/heartCatalog.do?device=" + deviceID;
		doRequest(url, null, bt);
	}
	
	/**
	 * 获取心率周报
	 * 
	 * @param deviceID
	 * @param date
	 * @param bt
	 */
	public void getHeartWeekly(String deviceID, String date, HttpCallback bt) {
		String url = REPORT_URL + "AppS2_healthReport/api/heartWeekly.do?device=" + deviceID + "&date=" + date;
		doRequest(url, null, bt);
	}
	
	/**
	 * 获取健康周报
	 * 
	 * @param deviceID
	 * @param date
	 * @param bt
	 */
	public void getHealthWeekly(String deviceID, String date, HttpCallback bt) {
		String url = REPORT_URL + "AppS2_healthReport/api/healthWeekly.do?device=" + deviceID + "&date=" + date;
		doRequest(url, null, bt);
	}
	
	/**
	 * 获取服务号码
	 * 
	 * @param deviceID
	 * @param flag
	 * @param bt
	 */
	public void getToolNumbers(String deviceID, String flag, HttpCallback bt) {
		String url = BASE_Url + "api/device/" + deviceID + "/tool_numbers/" + flag;
		doRequest(url, null, bt);
	}
	
	/**
	 * 编辑服务号码
	 * 
	 * @param deviceID
	 * @param toolNumber
	 * @param bt
	 */
	public void editToolNumber(String deviceID, SettingToolNumber toolNumber, HttpCallback bt) {
		String url = BASE_Url + "api/device/" + deviceID + "/tool_numbers/" + toolNumber.getSeqid();
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("name", toolNumber.getName());
		taskArgs.put("num", toolNumber.getNum());
		doRequest(url, taskArgs, bt);
	}
	
	/**
	 * 清空服务号码
	 * 
	 * @param deviceID
	 * @param toolNumber
	 * @param bt
	 */
	public void clearToolNumber(String deviceID, SettingToolNumber toolNumber, HttpCallback bt) {
		String url = BASE_Url + "api/device/" + deviceID + "/tool_numbers/" + toolNumber.getSeqid();
		HashMap<String, String> taskArgs = new HashMap<String, String>();
		taskArgs.put("clear", "clear");
		doRequest(url, taskArgs, bt);
	}
	
	/**
	 * 获取设备当地天气
	 * 
	 * @param deviceID
	 * @param bt
	 */
	public void getWeather(String deviceID, HttpCallback bt) {
		String url = BASE_Url + "api/device/getweather/?deviceid=" + deviceID;
		doRequest(url, null, bt);
	}
	
	/**
	 * 添加亲情通话记录
	 * 
	 * @param deviceID
	 * @param bt
	 */
	public void addCallRecrod(String imei, HttpCallback bt) {
		String url = BASE_Url + "api/device/call/?imei=" + imei;
		doRequest(url, null, bt);
	}
	
	/**
	 * 获取服务记录
	 * 
	 * @param imei
	 * @param bt
	 */
	public void findServiceRecord(String imei, HttpCallback bt) {
		String url = BASE_Url + "api/servicerecord/find/?depth=1&imei=" + imei;
		doRequest(url, null, bt);
	}
	
}
