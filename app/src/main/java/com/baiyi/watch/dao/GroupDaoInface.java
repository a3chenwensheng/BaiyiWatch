package com.baiyi.watch.dao;

import android.content.ContentValues;

import com.baiyi.watch.model.Group;

import java.util.List;

public interface GroupDaoInface {
	
	public boolean addGroup(Group group);

	public boolean deleteGroup(String whereClause, String[] whereArgs);

	public boolean updateGroup(ContentValues values, String whereClause, String[] whereArgs);

	public Group viewGroup(String selection, String[] selectionArgs);

	public List<Group> listGroup(String selection, String[] selectionArgs);

	public void clearGroupTable();
}
