package com.baiyi.watch.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.baiyi.watch.db.SQLHelper;
import com.baiyi.watch.model.Group;

import java.util.ArrayList;
import java.util.List;

public class GroupDao implements GroupDaoInface {
	private SQLHelper helper = null;

	public GroupDao(Context context) {
		helper = new SQLHelper(context);
	}

	@Override
	public boolean addGroup(Group group) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SQLiteDatabase database = null;
		long id = -1;
		try {
			database = helper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("_id", group.mId);
			values.put("name", group.getName());
			values.put("ownerid", group.mOwnerId);
			values.put("iscurrent", 0);
			id = database.insert(SQLHelper.TABLE_GROUP, null, values);
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
	public boolean deleteGroup(String whereClause, String[] whereArgs) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SQLiteDatabase database = null;
		int count = 0;
		try {
			database = helper.getWritableDatabase();
			count = database.delete(SQLHelper.TABLE_GROUP, whereClause, whereArgs);
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
	public boolean updateGroup(ContentValues values, String whereClause,
			String[] whereArgs) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SQLiteDatabase database = null;
		int count = 0;
		try {
			database = helper.getWritableDatabase();
			count = database.update(SQLHelper.TABLE_GROUP, values, whereClause, whereArgs);
			flag = (count > 0 ? true : false);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return flag;
	}

	@Override
	public Group viewGroup(String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		SQLiteDatabase database = null;
		Cursor cursor = null;
		Group group = null;
		try {
			database = helper.getReadableDatabase();
			cursor = database.query(true, SQLHelper.TABLE_GROUP, null, selection,
					selectionArgs, null, null, null, null);
			int cols_len = cursor.getColumnCount();
			while (cursor.moveToNext()) {
				group = new Group();
				for (int i = 0; i < cols_len; i++) {
					String cols_name = cursor.getColumnName(i);
					String cols_values = cursor.getString(cursor
							.getColumnIndex(cols_name));
					if (cols_values == null) {
						cols_values = "";
					}
					
					if (cols_name.equals("_id")) {
						group.mId = cols_values;
					}
					if (cols_name.equals("name")) {
						group.setName(cols_values);
					}
					if (cols_name.equals("ownerid")) {
						group.mOwnerId= cols_values;
					}
					if (cols_name.equals("iscurrent")) {
						group.iscurrent= cols_values;
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
		return group;
	}

	@Override
	public List<Group> listGroup(String selection,String[] selectionArgs) {
		// TODO Auto-generated method stub
		List<Group> list = new ArrayList<Group>();
		SQLiteDatabase database = null;
		Cursor cursor = null;
		try {
			database = helper.getReadableDatabase();
			cursor = database.query(false, SQLHelper.TABLE_GROUP, null, selection,selectionArgs, null, null, null, null);
			int cols_len = cursor.getColumnCount();
			while (cursor.moveToNext()) {
				Group group = new Group();

					for (int i = 0; i < cols_len; i++) {
						String cols_name = cursor.getColumnName(i);
						String cols_values = cursor.getString(cursor
								.getColumnIndex(cols_name));
						if (cols_values == null) {
							cols_values = "";
						}
						
						if (cols_name.equals("_id")) {
							group.mId = cols_values;
						}
						if (cols_name.equals("name")) {
							group.setName(cols_values);
						}
						if (cols_name.equals("ownerid")) {
							group.mOwnerId = cols_values;
						}
						if (cols_name.equals("iscurrent")) {
							group.iscurrent= cols_values;
						}
				}
				list.add(group);
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

	public void clearGroupTable() {
		String sql = "DELETE FROM " + SQLHelper.TABLE_GROUP + ";";
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(sql);
		revertSeq();
	}

	private void revertSeq() {
		String sql = "update sqlite_sequence set seq=0 where name='"
				+ SQLHelper.TABLE_GROUP + "'";
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(sql);
	}

}
