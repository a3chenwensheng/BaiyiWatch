package com.baiyi.watch.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.baiyi.watch.db.SQLHelper;
import com.baiyi.watch.model.Pedometerdata;

public class PostureDataDao implements PostureDataInface {
	private SQLHelper helper = null;

	public PostureDataDao(Context context) {
		helper = new SQLHelper(context);
	}

	@Override
	public boolean addPostureData(Pedometerdata posturedata, String deviceId, String date) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SQLiteDatabase database = null;
		long id = -1;
		try {
			database = helper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("_id", deviceId);
			values.put("search_date", date);
			values.put("count", posturedata.getValue());
			values.put("calorie", posturedata.getValue());//TODO
			id = database.insert(SQLHelper.TABLE_POSTUREDATA, null, values);
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
	public boolean deletePostureData(String whereClause, String[] whereArgs) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SQLiteDatabase database = null;
		int count = 0;
		try {
			database = helper.getWritableDatabase();
			count = database.delete(SQLHelper.TABLE_POSTUREDATA, whereClause, whereArgs);
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
	public boolean updatePostureData(ContentValues values, String whereClause, String[] whereArgs) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SQLiteDatabase database = null;
		int count = 0;
		try {
			database = helper.getWritableDatabase();
			count = database.update(SQLHelper.TABLE_POSTUREDATA, values, whereClause, whereArgs);
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
	public Pedometerdata viewPostureData(String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		SQLiteDatabase database = null;
		Cursor cursor = null;
		Pedometerdata posturedata = null;
		try {
			database = helper.getReadableDatabase();
			cursor = database.query(true, SQLHelper.TABLE_POSTUREDATA, null, selection, selectionArgs, null, null, null, null);
			int cols_len = cursor.getColumnCount();
			while (cursor.moveToNext()) {
				posturedata = new Pedometerdata();
				for (int i = 0; i < cols_len; i++) {
					String cols_name = cursor.getColumnName(i);
					String cols_values = cursor.getString(cursor.getColumnIndex(cols_name));
					if (cols_values == null) {
						cols_values = "";
					}

					if (cols_name.equals("_id")) {
						posturedata.setDevice(cols_values);
					}
					if (cols_name.equals("count")) {
						posturedata.setValue(cols_values);
					}
					if (cols_name.equals("calorie")) {
						//posturedata.setCalorie(cols_values);
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
		return posturedata;
	}

	public void clearPostureDataTable() {
		String sql = "DELETE FROM " + SQLHelper.TABLE_POSTUREDATA + ";";
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(sql);
		revertSeq();
	}

	private void revertSeq() {
		String sql = "update sqlite_sequence set seq=0 where name='" + SQLHelper.TABLE_POSTUREDATA + "'";
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(sql);
	}

}
