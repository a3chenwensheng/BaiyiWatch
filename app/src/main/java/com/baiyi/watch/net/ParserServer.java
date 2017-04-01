package com.baiyi.watch.net;

import com.baiyi.watch.model.Abnormaldata;
import com.baiyi.watch.model.Bloodpressuredata;
import com.baiyi.watch.model.Care;
import com.baiyi.watch.model.Cellid;
import com.baiyi.watch.model.Comment;
import com.baiyi.watch.model.Community;
import com.baiyi.watch.model.Communitymessage;
import com.baiyi.watch.model.Coordinate;
import com.baiyi.watch.model.Date;
import com.baiyi.watch.model.Daylight_heart_rate;
import com.baiyi.watch.model.Daylight_heart_rate_detail;
import com.baiyi.watch.model.Device;
import com.baiyi.watch.model.Deviceenvvoice;
import com.baiyi.watch.model.DisplayLocationData;
import com.baiyi.watch.model.Fall;
import com.baiyi.watch.model.Fall_notification;
import com.baiyi.watch.model.Fall_report;
import com.baiyi.watch.model.Fall_report_detail;
import com.baiyi.watch.model.Falldata;
import com.baiyi.watch.model.Familymessage;
import com.baiyi.watch.model.Fence_notification;
import com.baiyi.watch.model.Goods;
import com.baiyi.watch.model.Group;
import com.baiyi.watch.model.Group4Show;
import com.baiyi.watch.model.Group_dev_notifi;
import com.baiyi.watch.model.Groupinvitecode;
import com.baiyi.watch.model.Groupinvitemessage;
import com.baiyi.watch.model.Heart_weekly;
import com.baiyi.watch.model.Heartratedata;
import com.baiyi.watch.model.Imei_notif;
import com.baiyi.watch.model.Locationcache;
import com.baiyi.watch.model.Locationdata;
import com.baiyi.watch.model.Mediamessage;
import com.baiyi.watch.model.Mediapage;
import com.baiyi.watch.model.Member;
import com.baiyi.watch.model.Member4Show;
import com.baiyi.watch.model.Msg_notification;
import com.baiyi.watch.model.Notifdata;
import com.baiyi.watch.model.Notification;
import com.baiyi.watch.model.Oid;
import com.baiyi.watch.model.Order;
import com.baiyi.watch.model.Page;
import com.baiyi.watch.model.PageComment;
import com.baiyi.watch.model.Pedometer;
import com.baiyi.watch.model.Pedometer_activity;
import com.baiyi.watch.model.Pedometer_activity_detail;
import com.baiyi.watch.model.Pedometer_detail;
import com.baiyi.watch.model.Pedometerdata;
import com.baiyi.watch.model.Person;
import com.baiyi.watch.model.Point;
import com.baiyi.watch.model.Posturedata;
import com.baiyi.watch.model.Powerdata;
import com.baiyi.watch.model.QRCode;
import com.baiyi.watch.model.Resting_heart_rate;
import com.baiyi.watch.model.Resting_heart_rate_detail;
import com.baiyi.watch.model.Safearea;
import com.baiyi.watch.model.Servicepage;
import com.baiyi.watch.model.Servicerecord;
import com.baiyi.watch.model.SettingAlert;
import com.baiyi.watch.model.SettingFence;
import com.baiyi.watch.model.SettingSosNumber;
import com.baiyi.watch.model.SettingToolNumber;
import com.baiyi.watch.model.SignIn;
import com.baiyi.watch.model.Sleep;
import com.baiyi.watch.model.Sleep_detail;
import com.baiyi.watch.model.Sleep_weekly;
import com.baiyi.watch.model.Sleepdata;
import com.baiyi.watch.model.Sleepdatasleep;
import com.baiyi.watch.model.Sos_notification;
import com.baiyi.watch.model.Sosdata;
import com.baiyi.watch.model.Star;
import com.baiyi.watch.model.Textmessage;
import com.baiyi.watch.model.Textpage;
import com.baiyi.watch.model.Unread_notification;
import com.baiyi.watch.model.Wifi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * mxj
 * 
 * @author Administrator
 * 
 */
public class ParserServer {

	public static Object paserObjcet(String modelName, Object object) throws Exception {
		if (modelName.equals("Person")) {

			return paserPerson((Person) object);
		} else if (modelName.equals("Device")) {

			return paserDevice((Device) object);
		} else if (modelName.equals("Group")) {

			return paserGroup((Group) object);
		} else if (modelName.equals("Powerdata")) {

			return paserPowerdata((Powerdata) object);
		} else if (modelName.equals("Locationdata")) {

			return paserLocationdata((Locationdata) object);
		} else if (modelName.equals("Pedometerdata")) {

			return paserPedometerdata((Pedometerdata) object);
		} else if (modelName.equals("Heartratedata")) {

			return paserHeartratedata((Heartratedata) object);
		} else if (modelName.equals("Abnormaldata")) {

			return paserAbnormaldata((Abnormaldata) object);
		} else if (modelName.equals("Sleepdata")) {

			return paserSleepdata((Sleepdata) object);
		} else if (modelName.equals("Sosdata")) {

			return paserSosdata((Sosdata) object);
		} else if (modelName.equals("Falldata")) {

			return paserFalldata((Falldata) object);
		} else if (modelName.equals("Bloodpressuredata")) {

			return paserBloodpressuredata((Bloodpressuredata) object);
		} else if (modelName.equals("Mediamessage")) {

			return paserMediamessage((Mediamessage) object);
		} else if (modelName.equals("Familymessage")) {

			return paserFamilymessage((Familymessage) object);
		} else if (modelName.equals("Textmessage")) {

			return paserTextmessage((Textmessage) object);
		} else if (modelName.equals("Groupinvitemessage")) {

			return paserGroupinvitemessage((Groupinvitemessage) object);
		} else if (modelName.equals("Textpage")) {

			return paserTextpage((Textpage) object);
		} else if (modelName.equals("Mediapage")) {

			return paserMediapage((Mediapage) object);
		} else if (modelName.equals("Member")) {

			return paserMember((Member) object);
		} else if (modelName.equals("Community")) {

			return paserCommunity((Community) object);
		} else if (modelName.equals("Communitymessage")) {

			return paserCommunitymessage((Communitymessage) object);
		} else if (modelName.equals("Groupinvitecode")) {

			return paserGroupinvitecode((Groupinvitecode) object);
		} else if (modelName.equals("PageComment")) {

			return paserPageComment((PageComment) object);
		} else if (modelName.equals("Servicepage")) {

			return paserServicepage((Servicepage) object);
		} else if (modelName.equals("Notification")) {

			return paserNotification((Notification) object);
		} else if (modelName.equals("Deviceenvvoice")) {

			return paserDeviceenvvoice((Deviceenvvoice) object);
		} else if (modelName.equals("SignIn")) {
			
			return paserSignIn((SignIn) object);
		} else if (modelName.equals("Unread_notification")) {

			return paserUnreadNotification((Unread_notification) object);
		} else if (modelName.equals("Notifdata")) {

			return paserNotifdata((Notifdata) object);
		} else if (modelName.equals("Sos_notification")) {

			return paserSos_notification((Sos_notification) object);
		} else if (modelName.equals("Fall_notification")) {

			return paserFall_notification((Fall_notification) object);
		} else if (modelName.equals("Fence_notification")) {

			return paserFence_notification((Fence_notification) object);
		} else if (modelName.equals("Msg_notification")) {

			return paserMsg_notification((Msg_notification) object);
		}

		return object;
	}

	public String mId;
	public String mSenderId;
	public Person mSender;
	public String mRecipientId;
	public Person mRecipient;
	public String mCreatedAt;
	public String mSendAt;
	public String mSendBy;
	public String mReadAt;
	public String mSenderDeletedAt;
	public String mRecipientDeleteAt;

	public static Posturedata paserPosturedata(Posturedata pd) throws Exception {
		if (pd == null) {
			return null;
		}
		pd.mId = paserOid(pd.get_id());
		pd.mUserId = paserOid(pd.getUser());
		pd.mUser = paserPerson(pd.get$user());
		pd.mDeviceId = paserOid(pd.getDevice());
		pd.mDevice = paserDevice(pd.get$device());
		pd.mCreatedAt = paserDate(pd.getCreated_at());
		pd.mTimebegin = paserDate(pd.getTime_begin());
		pd.mTimeEnd = paserDate(pd.getTime_end());
		return pd;
	}

	/**
	 * 解析Pedometerdata对象
	 * 
	 * @param Powerdata
	 * @return
	 * @throws Exception
	 */
	public static Pedometerdata paserPedometerdata(Pedometerdata pd) throws Exception {
		if (pd == null) {
			return null;
		}
		pd.mId = paserOid(pd.get_id());
		pd.mUser = paserPerson(pd.getUser());
		pd.mCreatedAt = paserDate(pd.getCreated_at());
		pd.mTimeBegin = paserDate(pd.getTime_begin());
		return pd;
	}

	/**
	 * 解析Heartratedata对象
	 * 
	 * @param Powerdata
	 * @return
	 * @throws Exception
	 */
	public static Heartratedata paserHeartratedata(Heartratedata hd) throws Exception {
		if (hd == null) {
			return null;
		}
		hd.mId = paserOid(hd.get_id());
		hd.mUserId = paserOid(hd.getUser());
		hd.mUser = paserPerson(hd.get$user());
		hd.mDeviceId = paserOid(hd.getDevice());
		hd.mDevice = paserDevice(hd.get$device());
		hd.mCreatedAt = paserDate(hd.getCreated_at());
		hd.mTimebegin = paserDate(hd.getTime_begin());
		hd.mTimeEnd = paserDate(hd.getTime_end());
		return hd;
	}
	
	public static ArrayList<Heartratedata> paserHeartratedatas(String datas) throws Exception {
		if (datas == null || datas.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(datas);
		ArrayList<Heartratedata> heartratedatas = new ArrayList<Heartratedata>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Heartratedata item = (Heartratedata) JsonUtil.json2model(HttpUtil.model_package + "Heartratedata", jo);
			heartratedatas.add(paserHeartratedata(item));
		}
		return heartratedatas;
	}
	
	public static ArrayList<Fall> paserFalldatas(String datas) throws Exception {
		if (datas == null || datas.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(datas);
		ArrayList<Fall> falldatas = new ArrayList<Fall>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Fall item = (Fall) JsonUtil.json2model(HttpUtil.model_package + "Fall", jo);
			falldatas.add(item);
		}
		return falldatas;
	}
	
	public static Pedometer paserPedometer(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONObject jo = new JSONObject(str);
		Pedometer pedometer = (Pedometer) JsonUtil.json2model(HttpUtil.model_package + "Pedometer", jo);
		return paserPedometer(pedometer);
	}
	
	public static Pedometer paserPedometer(Pedometer pedometer) throws Exception {
		if (pedometer == null) {
			return null;
		}
		pedometer.mDetails = paserPedometer_details(pedometer.getDetail());
		
		return pedometer;
	}
	
	public static Pedometer_activity paserPedometer_activity(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONObject jo = new JSONObject(str);
		Pedometer_activity pedometer_activity = (Pedometer_activity) JsonUtil.json2model(HttpUtil.model_package + "Pedometer_activity", jo);
		return paserPedometer_activity(pedometer_activity);
	}
	
	public static Pedometer_activity paserPedometer_activity(Pedometer_activity pedometer_activity) throws Exception {
		if (pedometer_activity == null) {
			return null;
		}
		pedometer_activity.mDetails = paserPedometer_activity_details(pedometer_activity.getDetail());
		
		return pedometer_activity;
	}
	
	public static Sleep paserSleep(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONObject jo = new JSONObject(str);
		Sleep sleep = (Sleep) JsonUtil.json2model(HttpUtil.model_package + "Sleep", jo);
		return paserSleep(sleep);
	}
	
	public static Sleep paserSleep(Sleep sleep) throws Exception {
		if (sleep == null) {
			return null;
		}
		sleep.mDetails = paserSleep_details(sleep.getDetail());
		
		return sleep;
	}
	
	public static Resting_heart_rate paserResting_heart_rate(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONObject jo = new JSONObject(str);
		Resting_heart_rate resting_heart_rate = (Resting_heart_rate) JsonUtil.json2model(HttpUtil.model_package + "Resting_heart_rate", jo);
		return paserResting_heart_rate(resting_heart_rate);
	}
	
	public static Resting_heart_rate paserResting_heart_rate(Resting_heart_rate resting_heart_rate) throws Exception {
		if (resting_heart_rate == null) {
			return null;
		}
		resting_heart_rate.mDetails = paserHeart_rate_details(resting_heart_rate.getDetail());
		
		return resting_heart_rate;
	}
	
	public static Daylight_heart_rate paseDaylight_heart_rate(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONObject jo = new JSONObject(str);
		Daylight_heart_rate daylight_heart_rate = (Daylight_heart_rate) JsonUtil.json2model(HttpUtil.model_package + "Daylight_heart_rate", jo);
		return paseDaylight_heart_rate(daylight_heart_rate);
	}
	
	public static Daylight_heart_rate paseDaylight_heart_rate(Daylight_heart_rate daylight_heart_rate) throws Exception {
		if (daylight_heart_rate == null) {
			return null;
		}
		daylight_heart_rate.mDetails = paserDaylight_heart_rate_details(daylight_heart_rate.getDetail());
		
		return daylight_heart_rate;
	}
	
	public static Fall_report paserFall_report(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONObject jo = new JSONObject(str);
		Fall_report fall_report = (Fall_report) JsonUtil.json2model(HttpUtil.model_package + "Fall_report", jo);
		return paserFall_report(fall_report);
	}
	
	public static Fall_report paserFall_report(Fall_report fall_report) throws Exception {
		if (fall_report == null) {
			return null;
		}
		fall_report.mDetails = paserFall_report_details(fall_report.getDetail());
		
		return fall_report;
	}
	
	public static Care paserCare(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONObject jo = new JSONObject(str);
		Care care = (Care) JsonUtil.json2model(HttpUtil.model_package + "Care", jo);
		return care;
	}
	
	public static ArrayList<Heart_weekly> paserHeart_weekly(String datas) throws Exception {
		if (datas == null || datas.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(datas);
		ArrayList<Heart_weekly> heart_weeklies = new ArrayList<Heart_weekly>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Heart_weekly item = (Heart_weekly) JsonUtil.json2model(HttpUtil.model_package + "Heart_weekly", jo);
			heart_weeklies.add(item);
		}
		return heart_weeklies;
	}
	
	public static ArrayList<Pedometer_detail> paserPedometer_details(String datas) throws Exception {
		if (datas == null || datas.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(datas);
		ArrayList<Pedometer_detail> pedometer_details = new ArrayList<Pedometer_detail>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Pedometer_detail item = (Pedometer_detail) JsonUtil.json2model(HttpUtil.model_package + "Pedometer_detail", jo);
			pedometer_details.add(item);
		}
		return pedometer_details;
	}
	
	public static ArrayList<Pedometer_activity_detail> paserPedometer_activity_details(String datas) throws Exception {
		if (datas == null || datas.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(datas);
		ArrayList<Pedometer_activity_detail> pedometer_activity_details = new ArrayList<Pedometer_activity_detail>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Pedometer_activity_detail item = (Pedometer_activity_detail) JsonUtil.json2model(HttpUtil.model_package + "Pedometer_activity_detail", jo);
			pedometer_activity_details.add(item);
		}
		return pedometer_activity_details;
	}
	
	public static ArrayList<Sleep_detail> paserSleep_details(String datas) throws Exception {
		if (datas == null || datas.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(datas);
		ArrayList<Sleep_detail> sleep_details = new ArrayList<Sleep_detail>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Sleep_detail item = (Sleep_detail) JsonUtil.json2model(HttpUtil.model_package + "Sleep_detail", jo);
			sleep_details.add(item);
		}
		return sleep_details;
	}
	
	public static ArrayList<Resting_heart_rate_detail> paserHeart_rate_details(String datas) throws Exception {
		if (datas == null || datas.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(datas);
		ArrayList<Resting_heart_rate_detail> heart_rate_details = new ArrayList<Resting_heart_rate_detail>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Resting_heart_rate_detail item = (Resting_heart_rate_detail) JsonUtil.json2model(HttpUtil.model_package + "Resting_heart_rate_detail", jo);
			heart_rate_details.add(item);
		}
		return heart_rate_details;
	}
	
	public static ArrayList<Daylight_heart_rate_detail> paserDaylight_heart_rate_details(String datas) throws Exception {
		if (datas == null || datas.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(datas);
		ArrayList<Daylight_heart_rate_detail> daylight_heart_rate_details = new ArrayList<Daylight_heart_rate_detail>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Daylight_heart_rate_detail item = (Daylight_heart_rate_detail) JsonUtil.json2model(HttpUtil.model_package + "Daylight_heart_rate_detail", jo);
			daylight_heart_rate_details.add(item);
		}
		return daylight_heart_rate_details;
	}
	
	public static ArrayList<Fall_report_detail> paserFall_report_details(String datas) throws Exception {
		if (datas == null || datas.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(datas);
		ArrayList<Fall_report_detail> fall_report_details = new ArrayList<Fall_report_detail>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Fall_report_detail item = (Fall_report_detail) JsonUtil.json2model(HttpUtil.model_package + "Fall_report_detail", jo);
			fall_report_details.add(item);
		}
		return fall_report_details;
	}
	
	public static ArrayList<Sleep_weekly> paserSleep_weekly(String datas) throws Exception {
		if (datas == null || datas.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(datas);
		ArrayList<Sleep_weekly> sleep_weeklies = new ArrayList<Sleep_weekly>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Sleep_weekly item = (Sleep_weekly) JsonUtil.json2model(HttpUtil.model_package + "Sleep_weekly", jo);
			sleep_weeklies.add(item);
		}
		return sleep_weeklies;
	}

	/**
	 * 解析Abnormaldata对象
	 * 
	 * @param Powerdata
	 * @return
	 * @throws Exception
	 */
	public static Abnormaldata paserAbnormaldata(Abnormaldata hd) throws Exception {
		if (hd == null) {
			return null;
		}
		hd.mId = paserOid(hd.get_id());
		hd.mUserId = paserOid(hd.getUser());
		hd.mUser = paserPerson(hd.get$user());
		hd.mDeviceId = paserOid(hd.getDevice());
		hd.mDevice = paserDevice(hd.get$device());
		hd.mCreatedAt = paserDate(hd.getCreated_at());
		hd.mTimebegin = paserDate(hd.getTime_begin());
		hd.mTimeEnd = paserDate(hd.getTime_end());
		return hd;
	}

	/**
	 * 解析Sleepdata对象
	 * 
	 * @param Powerdata
	 * @return
	 * @throws Exception
	 */
	public static Sleepdata paserSleepdata(Sleepdata sd) throws Exception {
		if (sd == null) {
			return null;
		}
		sd.mId = paserOid(sd.get_id());
		sd.mUser = paserPerson(sd.getUser());
		sd.mCreatedAt = paserDate(sd.getCreated_at());
		sd.mTimeBegin = paserDate(sd.getTime_begin());
		sd.mTimeEnd = paserDate(sd.getTime_end());
		sd.mDatas = paserSleepdatasleep(sd.getData());
		sd.mDevice = paserDevice(sd.get$device());
		return sd;
	}

	/**
	 * 解析List<Sleepdatasleep>
	 * 
	 * @param datas
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<Sleepdatasleep> paserSleepdatasleep(String datas) throws Exception {
		if (datas == null || datas.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(datas);
		ArrayList<Sleepdatasleep> sleepdatasleeps = new ArrayList<Sleepdatasleep>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Sleepdatasleep member = (Sleepdatasleep) JsonUtil.json2model(HttpUtil.model_package + "Sleepdatasleep", jo);
			sleepdatasleeps.add(member);
		}
		return sleepdatasleeps;
	}

	/**
	 * 解析Bloodpressuredata对象
	 * 
	 * @param Powerdata
	 * @return
	 * @throws Exception
	 */
	public static Bloodpressuredata paserBloodpressuredata(Bloodpressuredata bpd) throws Exception {
		if (bpd == null) {
			return null;
		}

		bpd.mId = paserOid(bpd.get_id());
		bpd.mCreatedAt = paserDate(bpd.getCreated_at());
		bpd.mTimeBegin = paserDate(bpd.getTime_begin());

		return bpd;
	}
	
	public static ArrayList<Bloodpressuredata> paserBloodpressuredatas(String datas) throws Exception {
		if (datas == null || datas.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(datas);
		ArrayList<Bloodpressuredata> bloodpressuredatas = new ArrayList<Bloodpressuredata>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Bloodpressuredata item = (Bloodpressuredata) JsonUtil.json2model(HttpUtil.model_package + "Bloodpressuredata", jo);
			bloodpressuredatas.add(paserBloodpressuredata(item));
		}
		return bloodpressuredatas;
	}

	public static Sosdata paserSosdata(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONObject jo = new JSONObject(str);
		Sosdata sosdata = (Sosdata) JsonUtil.json2model(HttpUtil.model_package + "Sosdata", jo);
		return paserSosdata(sosdata);
	}

	/**
	 * 解析Sosdata对象
	 * 
	 * @param Powerdata
	 * @return
	 * @throws Exception
	 */
	public static Sosdata paserSosdata(Sosdata hd) throws Exception {
		if (hd == null) {
			return null;
		}
		hd.mId = paserOid(hd.get_id());
		hd.mPoint = paserPoint(hd.getPoint());
		hd.mUser = paserPerson(hd.getUser());
		hd.mCreatedAt = paserDate(hd.getCreated_at());
		return hd;
	}
	
	
	public static Falldata paserFalldata(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONObject jo = new JSONObject(str);
		Falldata falldata = (Falldata) JsonUtil.json2model(HttpUtil.model_package + "Falldata", jo);
		return paserFalldata(falldata);
		
	}
	

	/**
	 * 解析Falldata对象
	 * 
	 * @param hd
	 * @return
	 * @throws Exception
	 */
	public static Falldata paserFalldata(Falldata hd) throws Exception {
		if (hd == null) {
			return null;
		}
		hd.mId = paserOid(hd.get_id());
		hd.mPoint = paserPoint(hd.getPoint());
		hd.mUser = paserPerson(hd.getUser());
		hd.mCreatedAt = paserDate(hd.getCreated_at());
		return hd;
	}

	/**
	 * 解析$oid
	 * 
	 * @param idStr
	 * @return
	 */
	public static String paserOid(String idStr) {
		if (idStr == null || idStr.equals("")) {
			return null;
		}
		if (JsonUtil.isJson(idStr)) {
			Oid oid = JsonUtil.fromJson(idStr, Oid.class);
			return oid.get$oid();
		}
		return idStr;

	}

	/**
	 * 解析日期$date
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String paserDate(String dateStr) {
		if (dateStr == null || dateStr.equals("")) {
			return null;
		}

		if (JsonUtil.isJson(dateStr)) {
			Date date = JsonUtil.fromJson(dateStr, Date.class);
			return date.get$date();
		}
		return dateStr;
	}

	/**
	 * 解析List<Group>
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<Group> paserGroups(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}

		JSONArray array = new JSONArray(str);
		ArrayList<Group> groups = new ArrayList<Group>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Group group = (Group) JsonUtil.json2model(HttpUtil.model_package + "Group", jo);
			groups.add(paserGroup(group));
		}
		return groups;
	}

	/**
	 * 解析Group对象
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static Group paserGroup(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONObject jo = new JSONObject(str);
		Group group = (Group) JsonUtil.json2model(HttpUtil.model_package + "Group", jo);
		return paserGroup(group);
	}

	public static Group paserGroup(Group group) throws Exception {
		group.mId = paserOid(group.get_id());
		group.mOwnerId = paserOid(group.getOwner());
		group.mOwner = paserPerson(group.get$owner());
		group.mCreatedAt = paserDate(group.getCreated_at());
		// group.mMemebers = paserMembers(group.getMembers());
		group.mMemebers = paserMembers(group.get$members());
		group.mCommunityId = paserOid(group.getCommunity());
		return group;
	}
	
	/**
	 * 解析List<Group4Show>
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<Group4Show> paserGroup4Shows(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}

		JSONArray array = new JSONArray(str);
		ArrayList<Group4Show> groups = new ArrayList<Group4Show>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Group4Show group4Show = (Group4Show) JsonUtil.json2model(HttpUtil.model_package + "Group4Show", jo);
			groups.add(paserGroup4Show(group4Show));
		}
		return groups;
	}

	/**
	 * 解析Group4Show对象
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static Group4Show paserGroup4Show(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONObject jo = new JSONObject(str);
		Group4Show group4Show = (Group4Show) JsonUtil.json2model(HttpUtil.model_package + "Group4Show", jo);
		return paserGroup4Show(group4Show);
	}

	public static Group4Show paserGroup4Show(Group4Show group4Show) throws Exception {
		group4Show.mId = paserOid(group4Show.getId());
		group4Show.mOwnerId = paserOid(group4Show.getOwnerid());
		group4Show.mMemebers = paserMember4Shows(group4Show.getPersons());
		group4Show.mDevices = paserDevices(group4Show.getDevices());
		return group4Show;
	}
	
	/**
	 * 解析Community对象
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static Community paserCommunity(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		// JSONObject jo = new JSONObject(str);
		JSONObject jo = new JSONObject(str);
		Community community = (Community) JsonUtil.json2model(HttpUtil.model_package + "Community", jo);
		return paserCommunity(community);
	}

	public static Community paserCommunity(Community community) throws Exception {
		community.mId = paserOid(community.get_id());
		community.mCreatedAt = paserDate(community.getCreated_at());
		community.mAdministrators = paserMembers(community.getAdministrators());
		return community;
	}

	public static Notification paserNotification(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONObject jo = new JSONObject(str);
		Notification notification = (Notification) JsonUtil.json2model(HttpUtil.model_package + "Notification", jo);
		return paserNotification(notification);
	}

	public static Notification paserNotification(Notification notification) throws Exception {
		notification.mId = paserOid(notification.get_id());
		notification.mCreatedAt = paserDate(notification.getCreated_at());
		notification.mRecipient = paserPerson(notification.getRecipient());
		// ....
		return notification;
	}

	public static ArrayList<Notification> paserNotifications(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}

		JSONArray array = new JSONArray(str);
		ArrayList<Notification> notifications = new ArrayList<Notification>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Notification notification = (Notification) JsonUtil.json2model(HttpUtil.model_package + "Notification", jo);
			notifications.add(paserNotification(notification));
		}
		return notifications;
	}

	public static Deviceenvvoice paserDeviceenvvoice(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONObject jo = new JSONObject(str);
		Deviceenvvoice deviceenvvoice = (Deviceenvvoice) JsonUtil.json2model(HttpUtil.model_package + "Deviceenvvoice", jo);
		return paserDeviceenvvoice(deviceenvvoice);
	}

	public static Deviceenvvoice paserDeviceenvvoice(Deviceenvvoice deviceenvvoice) throws Exception {
		deviceenvvoice.mId = paserOid(deviceenvvoice.get_id());
		deviceenvvoice.mUserId = paserOid(deviceenvvoice.getUser());
		deviceenvvoice.mGroupId = paserOid(deviceenvvoice.getGroup());
		deviceenvvoice.mVoiceTime = paserDate(deviceenvvoice.getVoice_time());
		deviceenvvoice.mCreatedAt = paserDate(deviceenvvoice.getCreated_at());
		return deviceenvvoice;
	}

	public static ArrayList<Deviceenvvoice> paserDeviceenvvoices(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}

		JSONArray array = new JSONArray(str);
		ArrayList<Deviceenvvoice> deviceenvvoices = new ArrayList<Deviceenvvoice>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Deviceenvvoice deviceenvvoice = (Deviceenvvoice) JsonUtil.json2model(HttpUtil.model_package + "Deviceenvvoice", jo);
			deviceenvvoices.add(paserDeviceenvvoice(deviceenvvoice));
		}
		return deviceenvvoices;
	}

	public static Notifdata paserNotifdata(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONObject jo = new JSONObject(str);
		Notifdata notifdata = (Notifdata) JsonUtil.json2model(HttpUtil.model_package + "Notifdata", jo);
		return paserNotifdata(notifdata);
	}

	public static Notifdata paserNotifdata(Notifdata notifdata) throws Exception {
		notifdata.mId = paserOid(notifdata.get_id());
		notifdata.mCreatedAt = paserDate(notifdata.getCreated_at());
		notifdata.mGroupId = paserOid(notifdata.getGroup());
		notifdata.mRecipientId = paserOid(notifdata.getRecipient());
		return notifdata;
	}

	/**
	 * 解析ArrayList<Notifdata>
	 * 
	 * @param devicesStr
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<Notifdata> paserNotifdatas(String notifdataStr) throws Exception {
		if (notifdataStr == null || notifdataStr.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(notifdataStr);
		ArrayList<Notifdata> notifdatas = new ArrayList<Notifdata>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Notifdata notifdata = (Notifdata) JsonUtil.json2model(HttpUtil.model_package + "Notifdata", jo);
			notifdatas.add(paserNotifdata(notifdata));
		}
		return notifdatas;
	}

	public static Imei_notif paserImeiNotif(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONObject jo = new JSONObject(str);
		Imei_notif imei_notif = (Imei_notif) JsonUtil.json2model(HttpUtil.model_package + "Imei_notif", jo);
		return imei_notif;
	}

	public static ArrayList<Group_dev_notifi> paserGroupDevNotifiList(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}

		JSONArray array = new JSONArray(str);
		ArrayList<Group_dev_notifi> group_dev_notifis = new ArrayList<Group_dev_notifi>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Group_dev_notifi group_dev_notifi = (Group_dev_notifi) JsonUtil.json2model(HttpUtil.model_package + "Group_dev_notifi", jo);
			group_dev_notifis.add(group_dev_notifi);
		}
		return group_dev_notifis;
	}

	public static Unread_notification paserUnreadNotification(Unread_notification unread_notification) throws Exception {
		unread_notification.mImeiNotif = paserImeiNotif(unread_notification.getImei_notif());
		unread_notification.mGroupDevNotifiList = paserGroupDevNotifiList(unread_notification.getGroup_dev_notifi());
		return unread_notification;
	}

	public static Sos_notification paserSos_notification(Sos_notification sos_notification) throws Exception {
		sos_notification.mNotification = paserNotification(sos_notification.getNotif());
		sos_notification.mSosdata = paserSosdata(sos_notification.getSosdata());
		return sos_notification;
	}
	
	public static Fall_notification paserFall_notification(Fall_notification fall_notification) throws Exception {
		fall_notification.mNotification = paserNotification(fall_notification.getNotif());
		fall_notification.mFalldata = paserFalldata(fall_notification.getFalldata());
		//fall_notification.mInfo
		return fall_notification;
	}

	public static Fence_notification paserFence_notification(Fence_notification fence_notification) throws Exception {
		fence_notification.mNotification = paserNotification(fence_notification.getNotif());
		fence_notification.mFence = paserSettingFence(fence_notification.getFence());
		fence_notification.mLocationdata = paserLocationdata(fence_notification.getLocationdata());
		return fence_notification;
	}
	
	public static Msg_notification paserMsg_notification(Msg_notification msg_notification) throws Exception {
		msg_notification.mNotification = paserNotification(msg_notification.getNotif());
		msg_notification.mEnvvoice = paserDeviceenvvoice(msg_notification.getEnvvoice());
		//msg_notification.mInfo
		return msg_notification;
	}

	public static ArrayList<Wifi> paserWifis(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}

		JSONArray array = new JSONArray(str);
		ArrayList<Wifi> wifis = new ArrayList<Wifi>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Wifi wifi = (Wifi) JsonUtil.json2model(HttpUtil.model_package + "Wifi", jo);
			wifis.add(wifi);
		}
		return wifis;
	}

	/**
	 * 解析Communitymessage对象
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static Communitymessage paserCommunitymessage(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		// JSONObject jo = new JSONObject(str);
		JSONObject jo = new JSONObject(str);
		Communitymessage communitymessage = (Communitymessage) JsonUtil.json2model(HttpUtil.model_package + "Communitymessage", jo);
		return paserCommunitymessage(communitymessage);
	}

	public static Communitymessage paserCommunitymessage(Communitymessage communitymessage) throws Exception {

		communitymessage.mId = paserOid(communitymessage.get_id());
		communitymessage.mSenderId = paserOid(communitymessage.getSender());
		communitymessage.mRecipientId = paserOid(communitymessage.getRecipient());
		communitymessage.mCreatedAt = paserDate(communitymessage.getCreated_at());
		communitymessage.mCommunityId = paserOid(communitymessage.getCommunity());

		return communitymessage;
	}

	/**
	 * 解析person对象
	 * 
	 * @param person
	 * @return
	 * @throws Exception
	 */
	public static Person paserPerson(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONObject jo = new JSONObject(str);
		Person person = (Person) JsonUtil.json2model(HttpUtil.model_package + "Person", jo);
		return paserPerson(person);
	}

	public static Person paserPerson(Person person) throws Exception {
		person.mId = paserOid(person.get_id());
		// person.mDevices = paserDevices(person.getDevices());
		person.mGroups = paserGroups(person.get$groups());
		person.mCommunity = paserCommunity(person.get$community());
		// ...
		return person;
	}

	/**
	 * 解析powerdata对象
	 * 
	 * @param Powerdata
	 * @return
	 * @throws Exception
	 */
	public static Powerdata paserPowerdata(Powerdata pd) {
		if (pd == null) {
			return null;
		}
		// pd.mUserId = paserOid(pd.get_id());
		pd.mCreatedAt = paserDate(pd.getCreated_at());
		pd.mTimeBegin = paserDate(pd.getTime_begin());
		pd.mTimeEnd = paserDate(pd.getTime_end());
		return pd;
	}

	public static ArrayList<Powerdata> paserPowerdatas(String powerdataStr) throws Exception {
		if (powerdataStr == null || powerdataStr.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(powerdataStr);
		ArrayList<Powerdata> powerdatas = new ArrayList<Powerdata>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Powerdata powerdata = (Powerdata) JsonUtil.json2model(HttpUtil.model_package + "Powerdata", jo);
			powerdatas.add(paserPowerdata(powerdata));
		}
		return powerdatas;
	}

	public static DisplayLocationData paserDisplayLocationData(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONObject jo = new JSONObject(str);
		DisplayLocationData displayLocationData = (DisplayLocationData) JsonUtil.json2model(HttpUtil.model_package + "DisplayLocationData", jo);

		return paserDisplayLocationData(displayLocationData);
	}

	public static DisplayLocationData paserDisplayLocationData(DisplayLocationData displayLocationData) throws Exception {
		if (displayLocationData == null) {
			return null;
		}

		displayLocationData.mLocationdata = paserLocationdata(displayLocationData.getLocationdata());

		return displayLocationData;
	}
	
	public static ArrayList<Locationdata> paserLocationdatas(String locationdataStr) throws Exception {
		if (locationdataStr == null || locationdataStr.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(locationdataStr);
		ArrayList<Locationdata> locationdatas = new ArrayList<Locationdata>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Locationdata locationdata = (Locationdata) JsonUtil.json2model(HttpUtil.model_package + "Locationdata", jo);
			locationdatas.add(paserLocationdata(locationdata));
		}
		return locationdatas;
	}

	public static Locationdata paserLocationdata(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONObject jo = new JSONObject(str);
		Locationdata locationdata = (Locationdata) JsonUtil.json2model(HttpUtil.model_package + "Locationdata", jo);

		return paserLocationdata(locationdata);
	}

	public static Locationdata paserLocationdata(Locationdata locationdata) throws Exception {
		if (locationdata == null) {
			return null;
		}

		locationdata.mPoint = paserPoint(locationdata.getPoint());
		locationdata.mTimeBegin = paserDate(locationdata.getTime_begin());
		
		return locationdata;
	}

	public static Point paserPoint(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}

		JSONObject jo = new JSONObject(str);
		Point point = (Point) JsonUtil.json2model(HttpUtil.model_package + "Point", jo);

		return paserPoint(point);

		// Point point = null;
		// JSONObject jo = new JSONObject(str);
		// if (jo.has("coordinates") && !jo.isNull("coordinates")) {
		// JSONArray array = new JSONArray(jo.getString("coordinates"));
		// if (array.length() > 1) {
		// Double pX = array.getDouble(0);
		// Double pY = array.getDouble(1);
		// point = new Point(pX, pY);
		// }
		// }
		// return point;
	}

	public static Point paserPoint(Point point) throws Exception {
		if (point == null) {
			return null;
		}

		point.mCoordinates = paserStrings(point.getCoordinates());

		return point;

	}

	public static ArrayList<Point> paserPoints(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}

		JSONArray array = new JSONArray(str);
		ArrayList<Point> points = new ArrayList<Point>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Point point = (Point) JsonUtil.json2model(HttpUtil.model_package + "Point", jo);
			points.add(paserPoint(point));
		}
		return points;
	}

	public static Safearea paserSafearea(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}

		JSONObject jo = new JSONObject(str);
		Safearea safearea = (Safearea) JsonUtil.json2model(HttpUtil.model_package + "Safearea", jo);

		return paserSafearea(safearea);

	}

	public static Safearea paserSafearea(Safearea safearea) throws Exception {
		if (safearea == null) {
			return null;
		}

		safearea.mCoordinates = paserCoordinates(safearea.getCoordinates());

		return safearea;

	}

	/**
	 * 解析Locationcache对象
	 * 
	 * @return
	 */
	public static Locationcache paserLocationcache(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONObject jo = new JSONObject(str);
		Locationcache lc = (Locationcache) JsonUtil.json2model(HttpUtil.model_package + "Locationcache", jo);
		return paserLocationcache(lc);
	}

	public static Locationcache paserLocationcache(Locationcache lc) throws Exception {
		// lc.mPoint = paserPoint(lc.getPoint());//TODO
		lc.mCreatedAt = paserDate(lc.getCreated_at());
		lc.mId = paserOid(lc.get_id());
		return lc;
	}

	/**
	 * 解析Cellid对象
	 * 
	 * @param member
	 * @return
	 */
	public static ArrayList<Cellid> paserCellids(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(str);
		ArrayList<Cellid> cellids = new ArrayList<Cellid>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Cellid cellid = (Cellid) JsonUtil.json2model(HttpUtil.model_package + "Cellid", jo);
			cellids.add(cellid);
		}
		return cellids;
	}

	public static Cellid paserCellid(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONObject jo = new JSONObject(str);
		Cellid cellid = (Cellid) JsonUtil.json2model(HttpUtil.model_package + "Cellid", jo);
		return cellid;
	}

	/**
	 * 解析devices对象
	 * 
	 * @param devicesStr
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<Device> paserDevices(String devicesStr) throws Exception {
		if (devicesStr == null || devicesStr.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(devicesStr);
		ArrayList<Device> devices = new ArrayList<Device>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Device device = (Device) JsonUtil.json2model(HttpUtil.model_package + "Device", jo);
			devices.add(paserDevice(device));
		}
		return devices;
	}

	/**
	 * 解析device对象
	 * 
	 * @param member
	 * @return
	 */

	public static Device paserDevice(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONObject jo = new JSONObject(str);
		Device device = (Device) JsonUtil.json2model(HttpUtil.model_package + "Device", jo);
		return paserDevice(device);
	}

	public static Device paserDevice(Device device) throws Exception {
		device.mId = paserOid(device.get_id());
		// device.mCreatedAt = paserDate(device.getCreated_at());
		// device.mLastLocation = paserPoint(device.getLast_location());
		// device.mLocationUpdatedAt =
		// paserDate(device.getLocation_updated_at());
		// device.mUpdatedAt = paserDate(device.getUpdated_at());
		// device.mLastloginAt = paserDate(device.getLastlogin_at());
		device.mOwnerId = paserOid(device.getOwner());
		device.mOwner = paserPerson(device.get$owner());
		device.mSosNumbers = paserSettingSosNumbers(device.getSos_numbers());
		device.mToolNumbers = paserSettingToolNumbers(device.getTool_numbers());
		device.mAlerts = paserSettingAlerts(device.getAlerts());
		device.mFences = paserSettingFences(device.getFences());
		return device;
	}

	/**
	 * 解析ArrayList<SettingSosNumber>
	 * 
	 * @param sosNumberStr
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<SettingSosNumber> paserSettingSosNumbers(String sosNumberStr) throws Exception {
		if (sosNumberStr == null || sosNumberStr.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(sosNumberStr);
		ArrayList<SettingSosNumber> settingSosNumbers = new ArrayList<SettingSosNumber>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			SettingSosNumber settingSosNumber = (SettingSosNumber) JsonUtil.json2model(HttpUtil.model_package + "SettingSosNumber", jo);
			settingSosNumbers.add(settingSosNumber);
		}
		return settingSosNumbers;
	}
	
	/**
	 * 解析ArrayList<SettingToolNumber>
	 * 
	 * @param settingToolNumber
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<SettingToolNumber> paserSettingToolNumbers(String toolNumberStr) throws Exception {
		if (toolNumberStr == null || toolNumberStr.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(toolNumberStr);
		ArrayList<SettingToolNumber> settingToolNumbers = new ArrayList<SettingToolNumber>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			SettingToolNumber settingToolNumber = (SettingToolNumber) JsonUtil.json2model(HttpUtil.model_package + "SettingToolNumber", jo);
			settingToolNumbers.add(settingToolNumber);
		}
		return settingToolNumbers;
	}

	/**
	 * 解析ArrayList<SettingAlert>
	 * 
	 * @param settingAlertStr
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<SettingAlert> paserSettingAlerts(String settingAlertStr) throws Exception {
		if (settingAlertStr == null || settingAlertStr.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(settingAlertStr);
		ArrayList<SettingAlert> settingAlerts = new ArrayList<SettingAlert>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			SettingAlert settingAlert = (SettingAlert) JsonUtil.json2model(HttpUtil.model_package + "SettingAlert", jo);
			settingAlerts.add(paserSettingAlert(settingAlert));
		}
		return settingAlerts;
	}

	public static SettingAlert paserSettingAlert(SettingAlert settingAlert) throws Exception {
		if (settingAlert == null) {
			return null;
		}
		settingAlert.mCreatedAt = paserDate(settingAlert.getCreated_at());

		return settingAlert;
	}

	/**
	 * 解析ArrayList<SettingFence>
	 * 
	 * @param settingFenceStr
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<SettingFence> paserSettingFences(String settingFenceStr) throws Exception {
		if (settingFenceStr == null || settingFenceStr.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(settingFenceStr);
		ArrayList<SettingFence> settingFences = new ArrayList<SettingFence>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			SettingFence settingFence = (SettingFence) JsonUtil.json2model(HttpUtil.model_package + "SettingFence", jo);
			settingFences.add(paserSettingFence(settingFence));
		}
		return settingFences;
	}

	public static SettingFence paserSettingFence(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONObject jo = new JSONObject(str);
		SettingFence settingFence = (SettingFence) JsonUtil.json2model(HttpUtil.model_package + "SettingFence", jo);
		return paserSettingFence(settingFence);
	}

	public static SettingFence paserSettingFence(SettingFence settingFence) throws Exception {
		if (settingFence == null) {
			return null;
		}
		settingFence.mSafearea = paserSafearea(settingFence.getSafe_area());

		return settingFence;
	}

	/**
	 * 解析members对象
	 * 
	 * @param memberStr
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<Member> paserMembers(String memberStr) throws Exception {
		if (memberStr == null || memberStr.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(memberStr);
		ArrayList<Member> memebers = new ArrayList<Member>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Member member = (Member) JsonUtil.json2model(HttpUtil.model_package + "Member", jo);
			memebers.add(paserMember(member));
		}
		return memebers;
	}

	/**
	 * 解析member对象
	 * 
	 * @param member
	 * @return
	 */
	public static Member paserMember(Member member) throws Exception {
		member.mId = paserOid(member.get_id());
		member.mGroupId = paserOid(member.getGroup());
		member.mGroup = paserGroup(member.get$group());
		member.mPersonId = paserOid(member.getPerson());
		member.mPerson = paserPerson(member.get$person());
		return member;
	}

	/**
	 * 解析Page对象
	 * 
	 * @param person
	 * @return
	 * @throws Exception
	 */
	public static Page paserpage(Page page) throws Exception {
		page.mId = paserOid(page.get_id());
		page.mGroupId = paserOid(page.getGroup());
		page.mGroup = paserGroup(page.get$group());
		page.mCreatedBy = paserPerson(page.getCreated_by());
		page.mCreatedAt = paserDate(page.getCreated_at());
		page.mUpdateAt = paserDate(page.getUpdated_at());
		return page;
	}

	/**
	 * 解析Textpage对象
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static Textpage paserTextpage(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONObject jo = new JSONObject(str);
		Textpage page = (Textpage) JsonUtil.json2model(HttpUtil.model_package + "Textpage", jo);
		return paserTextpage(page);
	}

	public static Textpage paserTextpage(Textpage page) throws Exception {
		page.mType = Page.TYPE_TEXT;
		page.mId = paserOid(page.get_id());
		page.mGroupId = paserOid(page.getGroup());
		page.mGroup = paserGroup(page.get$group());
		page.mCreatedById = paserOid(page.getCreated_by());
		page.mCreatedBy = paserPerson(page.get$created_by());
		page.mCreatedAt = paserDate(page.getCreated_at());
		page.mUpdateAt = paserDate(page.getUpdated_at());
		page.mStars = paserStars(page.getStars());
		page.mComments = paserComments(page.getComments());
		return page;
	}

	/**
	 * 解析Mediapage对象
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static Mediapage paserMediapage(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONObject jo = new JSONObject(str);
		Mediapage page = (Mediapage) JsonUtil.json2model(HttpUtil.model_package + "Mediapage", jo);
		return paserMediapage(page);
	}

	public static Mediapage paserMediapage(Mediapage page) throws Exception {
		page.mType = Page.TYPE_MEDIA;
		page.mId = paserOid(page.get_id());
		page.mGroupId = paserOid(page.getGroup());
		page.mGroup = paserGroup(page.get$group());
		page.mCreatedById = paserOid(page.getCreated_by());
		page.mCreatedBy = paserPerson(page.get$created_by());
		page.mCreatedAt = paserDate(page.getCreated_at());
		page.mUpdateAt = paserDate(page.getUpdated_at());
		page.mStars = paserStars(page.getStars());
		page.mComments = paserComments(page.getComments());
		return page;
	}

	public static Mediamessage paserMediamessage(Mediamessage obj) throws Exception {
		obj.mId = paserOid(obj.get_id());
		obj.mSenderId = paserOid(obj.getSender());
		obj.mRecipientId = paserOid(obj.getRecipient());
		obj.mSender = paserPerson(obj.get$sender());
		obj.mRecipient = paserPerson(obj.get$recipient());
		obj.mCreatedAt = paserDate(obj.getCreated_at());
		obj.mSendAt = paserDate(obj.getSend_at());
		obj.mSendBy = paserDate(obj.getSend_by());
		obj.mReadAt = paserDate(obj.getRead_at());
		obj.mSenderDeletedAt = paserDate(obj.getSender_deleted_at());
		obj.mRecipientDeleteAt = paserDate(obj.getRecipient_deleted_at());
		return obj;
	}

	public static Familymessage paserFamilymessage(Familymessage obj) throws Exception {
		obj.mId = paserOid(obj.get_id());
		obj.mSenderId = paserOid(obj.getSender());
		obj.mRecipientId = paserOid(obj.getRecipient());
		obj.mSender = paserPerson(obj.get$sender());
		obj.mRecipient = paserPerson(obj.get$recipient());
		obj.mCreatedAt = paserDate(obj.getCreated_at());
		obj.mSendAt = paserDate(obj.getSend_at());
		obj.mSendBy = paserDate(obj.getSend_by());
		obj.mReadAt = paserDate(obj.getRead_at());
		obj.mSenderDeletedAt = paserDate(obj.getSender_deleted_at());
		obj.mRecipientDeleteAt = paserDate(obj.getRecipient_deleted_at());
		return obj;
	}

	public static Groupinvitemessage paserGroupinvitemessage(Groupinvitemessage obj) throws Exception {
		obj.mId = paserOid(obj.get_id());
		obj.mSenderId = paserOid(obj.getSender());
		obj.mRecipientId = paserOid(obj.getRecipient());
		obj.mSender = paserPerson(obj.get$sender());
		obj.mRecipient = paserPerson(obj.get$recipient());
		obj.mCreatedAt = paserDate(obj.getCreated_at());
		obj.mSendAt = paserDate(obj.getSend_at());
		obj.mSendBy = paserDate(obj.getSend_by());
		obj.mReadAt = paserDate(obj.getRead_at());
		obj.mSenderDeletedAt = paserDate(obj.getSender_deleted_at());
		obj.mRecipientDeleteAt = paserDate(obj.getRecipient_deleted_at());
		obj.mGroupId = paserOid(obj.getGroup());
		obj.mGroup = paserGroup(obj.get$group());
		obj.mAcceptAt = paserDate(obj.getAccept_at());
		return obj;
	}

	public static Textmessage paserTextmessage(Textmessage obj) throws Exception {
		obj.mId = paserOid(obj.get_id());
		obj.mSenderId = paserOid(obj.getSender());
		obj.mRecipientId = paserOid(obj.getRecipient());
		obj.mSender = paserPerson(obj.get$sender());
		obj.mRecipient = paserPerson(obj.get$recipient());
		obj.mCreatedAt = paserDate(obj.getCreated_at());
		obj.mSendAt = paserDate(obj.getSend_at());
		obj.mSendBy = paserDate(obj.getSend_by());
		obj.mReadAt = paserDate(obj.getRead_at());
		obj.mSenderDeletedAt = paserDate(obj.getSender_deleted_at());
		obj.mRecipientDeleteAt = paserDate(obj.getRecipient_deleted_at());
		return obj;
	}

	/**
	 * 解析stars对象
	 * 
	 * @param starsStr
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<Star> paserStars(String starsStr) throws Exception {
		if (starsStr == null || starsStr.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(starsStr);
		ArrayList<Star> stars = new ArrayList<Star>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Star member = (Star) JsonUtil.json2model(HttpUtil.model_package + "Star", jo);
			stars.add(paserStar(member));
		}
		return stars;
	}

	/**
	 * 解析star对象
	 * 
	 * @param star
	 * @return
	 */
	public static Star paserStar(Star star) throws Exception {
		star.mCreatedBy = paserOid(star.getCreated_by());
		star.mCreatedAt = paserDate(star.getCreated_at());
		return star;
	}

	/**
	 * 解析Comments对象
	 * 
	 * @param starsStr
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<Comment> paserComments(String commentsStr) throws Exception {
		if (commentsStr == null || commentsStr.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(commentsStr);
		ArrayList<Comment> comments = new ArrayList<Comment>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Comment comment = (Comment) JsonUtil.json2model(HttpUtil.model_package + "Comment", jo);
			comments.add(paserComment(comment));
		}
		return comments;
	}

	/**
	 * 解析Comment对象
	 * 
	 * @param comment
	 * @return
	 * @throws Exception
	 */
	public static Comment paserComment(Comment comment) throws Exception {
		comment.mCreatedBy = paserOid(comment.getCreated_by());
		comment.mCreatedAt = paserDate(comment.getCreated_at());
		return comment;
	}

	/**
	 * 解析List<PageComment>
	 * 
	 * @param starsStr
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<PageComment> paserPageComments(String commentsStr) throws Exception {
		if (commentsStr == null || commentsStr.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(commentsStr);
		ArrayList<PageComment> comments = new ArrayList<PageComment>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			PageComment comment = (PageComment) JsonUtil.json2model(HttpUtil.model_package + "PageComment", jo);
			comments.add(paserPageComment(comment));
		}
		return comments;
	}

	/**
	 * 解析PageComment对象
	 * 
	 * @param comment
	 * @return
	 * @throws Exception
	 */
	public static PageComment paserPageComment(PageComment comment) throws Exception {
		comment.mCreatedBy = paserOid(comment.getCreated_by());
		comment.mCreatedAt = paserDate(comment.getCreated_at());
		return comment;
	}

	/**
	 * 解析ArrayList<Member4Show>
	 * 
	 * @param devicesStr
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<Member4Show> paserMember4Shows(String membersStr) throws Exception {
		if (membersStr == null || membersStr.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(membersStr);
		ArrayList<Member4Show> member4Shows = new ArrayList<Member4Show>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Member4Show member4Show = (Member4Show) JsonUtil.json2model(HttpUtil.model_package + "Member4Show", jo);
			member4Shows.add(paserMember4Show(member4Show));
		}
		return member4Shows;
	}

	/**
	 * 解析Member4Show对象
	 * 
	 * @param member
	 * @return
	 */
	public static Member4Show paserMember4Show(Member4Show member4Show) throws Exception {
		member4Show.mId = paserOid(member4Show.get_id());
		member4Show.mDevices = paserStrings(member4Show.getDevices());
		return member4Show;
	}

	public static ArrayList<Servicepage> paserServicepages(String servicepagesStr) throws Exception {
		if (servicepagesStr == null || servicepagesStr.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(servicepagesStr);
		ArrayList<Servicepage> servicepages = new ArrayList<Servicepage>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Servicepage servicepage = (Servicepage) JsonUtil.json2model(HttpUtil.model_package + "Servicepage", jo);
			servicepages.add(paserServicepage(servicepage));
		}
		return servicepages;
	}

	public static Servicepage paserServicepage(Servicepage servicepage) throws Exception {
		servicepage.mId = paserOid(servicepage.get_id());
		servicepage.mComments = paserPageComments(servicepage.getComments());
		// ...
		return servicepage;
	}

	public static Groupinvitecode paserGroupinvitecode(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONObject jo = new JSONObject(str);
		Groupinvitecode groupinvitecode = (Groupinvitecode) JsonUtil.json2model(HttpUtil.model_package + "Groupinvitecode", jo);
		return paserGroupinvitecode(groupinvitecode);
	}

	public static Groupinvitecode paserGroupinvitecode(Groupinvitecode groupinvitecode) throws Exception {
		groupinvitecode.mId = paserOid(groupinvitecode.get_id());
		groupinvitecode.mGroup = paserGroup(groupinvitecode.get$group());
		return groupinvitecode;
	}

	/**
	 * 解析ArrayList<String>
	 * 
	 * @param devicesStr
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<String> paserStrings(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(str);
		ArrayList<String> strings = new ArrayList<String>();
		for (int i = 0; i < array.length(); i++) {
			// JSONObject jo = array.getJSONObject(i);
			strings.add(array.getString(i));
		}

		return strings;
	}

	public static ArrayList<Coordinate> paserCoordinates(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(str);
		ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
		for (int i = 0; i < array.length(); i++) {
			// JSONObject jo = array.getJSONObject(i);
			coordinates.add(paserCoordinate(array.getString(i)));
		}

		return coordinates;
	}

	public static Coordinate paserCoordinate(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		Coordinate coordinate = new Coordinate(str);
		coordinate.mLatLgns = paserStrings(str);
		return coordinate;
	}

	public static QRCode paserQRCode(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}

		JSONObject jo = new JSONObject(str);
		QRCode qrCode = (QRCode) JsonUtil.json2model(HttpUtil.model_package + "QRCode", jo);

		return qrCode;
	}
	
	public static SignIn paserSignIn(SignIn signIn) throws Exception {
		if (signIn == null) {
			return null;
		}
		
		signIn.mSignedDatas = paserStrings(signIn.getSigned_date_list());
		
		return signIn;
		
	}
	
	public static SignIn paserSignIn(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		
		JSONObject jo = new JSONObject(str);
		SignIn signIn = (SignIn) JsonUtil.json2model(HttpUtil.model_package + "SignIn", jo);
		
		return paserSignIn(signIn);
		
	}
	
	public static ArrayList<Goods> paserGoods(String datas) throws Exception {
		if (datas == null || datas.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(datas);
		ArrayList<Goods> goodsList = new ArrayList<Goods>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Goods item = (Goods) JsonUtil.json2model(HttpUtil.model_package + "Goods", jo);
			goodsList.add(paserGoods(item));
		}
		return goodsList;
	}
	
	public static Goods paserGoods(Goods goods) throws Exception {
		goods.mId = paserOid(goods.get_id());
		return goods;
	}
	
	public static ArrayList<Order> paserOrders(String datas) throws Exception {
		if (datas == null || datas.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(datas);
		ArrayList<Order> orderList = new ArrayList<Order>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Order item = (Order) JsonUtil.json2model(HttpUtil.model_package + "Order", jo);
			orderList.add(paserOrder(item));
		}
		return orderList;
	}
	
	public static Order paserOrder(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONObject jo = new JSONObject(str);
		Order order = (Order) JsonUtil.json2model(HttpUtil.model_package + "Order", jo);
		return paserOrder(order);
	}
	
	public static Order paserOrder(Order order) throws Exception {
		order.mId = paserOid(order.get_id());
		order.mCreatedAt = paserDate(order.getCreated_at());
		order.mModifiedAt = paserDate(order.getModified_at());
		return order;
	}
	
	
	
	
	
	
	
	public static ArrayList<Servicerecord> paserServicerecords(String datas) throws Exception {
		if (datas == null || datas.equals("")) {
			return null;
		}
		JSONArray array = new JSONArray(datas);
		ArrayList<Servicerecord> servicerecordList = new ArrayList<Servicerecord>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject jo = array.getJSONObject(i);
			Servicerecord item = (Servicerecord) JsonUtil.json2model(HttpUtil.model_package + "Servicerecord", jo);
			servicerecordList.add(paserServicerecord(item));
		}
		return servicerecordList;
	}
	
	public static Servicerecord paserServicerecord(String str) throws Exception {
		if (str == null || str.equals("")) {
			return null;
		}
		JSONObject jo = new JSONObject(str);
		Servicerecord servicerecord = (Servicerecord) JsonUtil.json2model(HttpUtil.model_package + "Servicerecord", jo);
		return paserServicerecord(servicerecord);
	}
	
	public static Servicerecord paserServicerecord(Servicerecord servicerecord) throws Exception {
		servicerecord.mId = paserOid(servicerecord.get_id());
		servicerecord.mCreatedAt = paserDate(servicerecord.getCreated_at());
		servicerecord.mEndAt = paserDate(servicerecord.getEnd_at());
		return servicerecord;
	}
	
	
}
