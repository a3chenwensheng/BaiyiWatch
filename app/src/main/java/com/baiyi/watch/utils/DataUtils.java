package com.baiyi.watch.utils;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;

import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.model.Device;
import com.baiyi.watch.model.Group4Show;
import com.baiyi.watch.model.Person;
import com.baiyi.watch.net.BaseMessage;
import com.baiyi.watch.net.HttpCallback;
import com.baiyi.watch.net.ParserServer;
import com.baiyi.watch.net.PersonApi;

import java.util.ArrayList;
import java.util.List;

public final class DataUtils {

	public static void getAllDevices(Context context) {
		Person person = MyApplication.getInstance().getPersonDaoInface().viewPerson(null, null);

		if (person == null || TextUtils.isEmpty(person.mId)) {
			return;
		}

		PersonApi.getInstance(context).getAllDevices(person.mId, new HttpCallback() {
			@Override
			public void onError(String error) {

			}

			@Override
			public void onComplete(BaseMessage result) {

				if (result.isSuccess()) {

					// 更新DB
					ArrayList<Group4Show> group4ShowList = null;
					try {
						group4ShowList = ParserServer.paserGroup4Shows(result.getResultSrc());
					} catch (Exception e) {
						e.printStackTrace();
					}

					// TODO
					// 添加家庭圈DB

					List<Device> listDevice = new ArrayList<Device>();
					if (group4ShowList != null) {
						for (Group4Show group4Show : group4ShowList) {
							if (group4Show.mDevices != null && !group4Show.mDevices.isEmpty()) {
								for (Device device : group4Show.mDevices) {
									device.mId = device.getImei();
									Person owner = new Person();
									owner.setAvatar_url(device.getAvatar_url());
									device.mOwner = owner;
									device.setGroup_name(group4Show.getName());
									device.setGroup_id(group4Show.mId);
									device.setGroup_ownerid(group4Show.mOwnerId);
								}
							}
							listDevice.addAll(group4Show.mDevices);
						}

					}

					if (!listDevice.isEmpty()) {
						// DB
						// 当前设备
						Device mDevice = MyApplication.getInstance().getDeviceDaoInface().viewDevice("iscurrent = ?", new String[] { "1" });

						MyApplication.getInstance().getDeviceDaoInface().clearDeviceTable();
						for (Device device : listDevice) {
							MyApplication.getInstance().getDeviceDaoInface().addDevice(device);
						}

						// 恢复默认查看腕表
						ContentValues cv = new ContentValues();
						cv.put("iscurrent", 1);
						if (mDevice == null || TextUtils.isEmpty(mDevice.mId)) {
							// 取第一个腕表，当默认查看腕表
							Device device = MyApplication.getInstance().getDeviceDaoInface().viewDevice("_id != ?", new String[] { "" });
							if (null != device && !TextUtils.isEmpty(device.mId)) {
								MyApplication.getInstance().getDeviceDaoInface().updateDevice(cv, "_id = ?", new String[] { device.mId });
							}
						} else {
							MyApplication.getInstance().getDeviceDaoInface().updateDevice(cv, "_id = ?", new String[] { mDevice.mId });
						}

					} else {
						MyApplication.getInstance().getDeviceDaoInface().clearDeviceTable();
					}

				}
			}
		});
	}

}
