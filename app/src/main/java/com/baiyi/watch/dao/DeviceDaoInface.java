package com.baiyi.watch.dao;

import android.content.ContentValues;

import com.baiyi.watch.model.Device;

import java.util.List;

public interface DeviceDaoInface {
	
	public boolean addDevice(Device device);

	public boolean deleteDevice(String whereClause, String[] whereArgs);

	public boolean updateDevice(ContentValues values, String whereClause, String[] whereArgs);
	
	public boolean updateDevice(Device device, String whereClause, String[] whereArgs);

	public Device viewDevice(String selection, String[] selectionArgs);

	public List<Device> listDevice(String selection, String[] selectionArgs);

	public void clearDeviceTable();
}
