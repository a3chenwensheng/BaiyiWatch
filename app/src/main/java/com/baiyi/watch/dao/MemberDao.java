package com.baiyi.watch.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.baiyi.watch.db.SQLHelper;
import com.baiyi.watch.model.Member4Show;

import java.util.ArrayList;
import java.util.List;

public class MemberDao implements MemberDaoInface {
	private SQLHelper helper = null;

	public MemberDao(Context context) {
		helper = new SQLHelper(context);
	}

	@Override
	public boolean addMember(Member4Show member4Show) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SQLiteDatabase database = null;
		long id = -1;
		try {
			database = helper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("_id", member4Show.mId);
			values.put("avatar_url", member4Show.getAvatar_url());
			String deviceid = "";
			try {
				deviceid =  member4Show.mDevices.get(0);
			} catch (Exception e) {
			}
			values.put("deviceid", deviceid);
			values.put("nickname", member4Show.getNickname());
			values.put("username", member4Show.getUsername());
			values.put("iscurrent", 0);
			
			id = database.insert(SQLHelper.TABLE_MEMBER, null, values);
			flag = (id != -1 ? true : false);
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return flag;
	}

	@Override
	public boolean deleteMember(String whereClause, String[] whereArgs) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SQLiteDatabase database = null;
		int count = 0;
		try {
			database = helper.getWritableDatabase();
			count = database.delete(SQLHelper.TABLE_MEMBER, whereClause, whereArgs);
			flag = (count > 0 ? true : false);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return flag;
	}

	@Override
	public boolean updateMember(ContentValues values, String whereClause,
			String[] whereArgs) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SQLiteDatabase database = null;
		int count = 0;
		try {
			database = helper.getWritableDatabase();
			count = database.update(SQLHelper.TABLE_MEMBER, values, whereClause, whereArgs);
			flag = (count > 0 ? true : false);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return flag;
	}

	@Override
	public Member4Show viewMember(String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		SQLiteDatabase database = null;
		Cursor cursor = null;
		Member4Show member4Show = null;
		try {
			database = helper.getReadableDatabase();
			cursor = database.query(true, SQLHelper.TABLE_MEMBER, null, selection,
					selectionArgs, null, null, null, null);
			int cols_len = cursor.getColumnCount();
			while (cursor.moveToNext()) {
				member4Show = new Member4Show();
				for (int i = 0; i < cols_len; i++) {
					String cols_name = cursor.getColumnName(i);
					String cols_values = cursor.getString(cursor
							.getColumnIndex(cols_name));
					if (cols_values == null) {
						cols_values = "";
					}
					
					if (cols_name.equals("_id")) {
						member4Show.mId = cols_values;
					}
					if (cols_name.equals("avatar_url")) {
						member4Show.setAvatar_url(cols_values);
					}
					if (cols_name.equals("deviceid")) {
						ArrayList<String> devices = new ArrayList<String>();
						devices.add(cols_values);
						member4Show.mDevices = devices;
					}
					if (cols_name.equals("nickname")) {
						member4Show.setNickname(cols_values);
					}
					if (cols_name.equals("username")) {
						member4Show.setUsername(cols_values);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return member4Show;
	}

	@Override
	public List<Member4Show> listMember(String selection,String[] selectionArgs) {
		// TODO Auto-generated method stub
		List<Member4Show> list = new ArrayList<Member4Show>();
		SQLiteDatabase database = null;
		Cursor cursor = null;
		try {
			database = helper.getReadableDatabase();
			cursor = database.query(false, SQLHelper.TABLE_MEMBER, null, selection,selectionArgs, null, null, "deviceid", null);
			int cols_len = cursor.getColumnCount();
			while (cursor.moveToNext()) {
				Member4Show member4Show = new Member4Show();

					for (int i = 0; i < cols_len; i++) {
						String cols_name = cursor.getColumnName(i);
						String cols_values = cursor.getString(cursor
								.getColumnIndex(cols_name));
						if (cols_values == null) {
							cols_values = "";
						}
						
						if (cols_name.equals("_id")) {
							member4Show.mId = cols_values;
						}
						if (cols_name.equals("avatar_url")) {
							member4Show.setAvatar_url(cols_values);
						}
						if (cols_name.equals("deviceid")) {
							ArrayList<String> devices = new ArrayList<String>();
							devices.add(cols_values);
							member4Show.mDevices = devices;
						}
						if (cols_name.equals("nickname")) {
							member4Show.setNickname(cols_values);
						}
						if (cols_name.equals("username")) {
							member4Show.setUsername(cols_values);
						}
				}
				list.add(member4Show);
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return list;
	}

	public void clearMemberTable() {
		String sql = "DELETE FROM " + SQLHelper.TABLE_MEMBER + ";";
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(sql);
		revertSeq();
	}

	private void revertSeq() {
		String sql = "update sqlite_sequence set seq=0 where name='"
				+ SQLHelper.TABLE_MEMBER + "'";
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(sql);
	}

}
