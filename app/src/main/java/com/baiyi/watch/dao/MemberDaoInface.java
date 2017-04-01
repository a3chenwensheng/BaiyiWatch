package com.baiyi.watch.dao;

import android.content.ContentValues;

import com.baiyi.watch.model.Member4Show;

import java.util.List;

public interface MemberDaoInface {
	
	public boolean addMember(Member4Show member4Show);

	public boolean deleteMember(String whereClause, String[] whereArgs);

	public boolean updateMember(ContentValues values, String whereClause, String[] whereArgs);

	public Member4Show viewMember(String selection, String[] selectionArgs);

	public List<Member4Show> listMember(String selection, String[] selectionArgs);

	public void clearMemberTable();
}
