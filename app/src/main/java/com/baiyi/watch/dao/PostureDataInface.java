package com.baiyi.watch.dao;

import android.content.ContentValues;

import com.baiyi.watch.model.Pedometerdata;

public interface PostureDataInface {
	
	public boolean addPostureData(Pedometerdata posturedata, String deviceId, String time);

	public boolean deletePostureData(String whereClause, String[] whereArgs);

	public boolean updatePostureData(ContentValues values, String whereClause, String[] whereArgs);

	public Pedometerdata viewPostureData(String selection, String[] selectionArgs);

	public void clearPostureDataTable();
}
