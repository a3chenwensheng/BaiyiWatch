package com.baiyi.watch.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.baiyi.watch.db.SQLHelper;
import com.baiyi.watch.model.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonDao implements PersonDaoInface {
	private SQLHelper helper = null;

	public PersonDao(Context context) {
		helper = new SQLHelper(context);
	}

	@Override
	public boolean addPerson(Person person) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SQLiteDatabase database = null;
		long id = -1;
		try {
			database = helper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("_id", person.mId);
			values.put("nickname", person.getNickname());
			values.put("phone", person.getPhone());
			values.put("role", person.getRole());
			values.put("telephone", person.getTelephone());
			values.put("username", person.getUsername());
			values.put("avatar_url", person.getAvatar_url());
			id = database.insert(SQLHelper.TABLE_PERSON, null, values);
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
	public boolean deletePerson(String whereClause, String[] whereArgs) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SQLiteDatabase database = null;
		int count = 0;
		try {
			database = helper.getWritableDatabase();
			count = database.delete(SQLHelper.TABLE_PERSON, whereClause, whereArgs);
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
	public boolean updatePerson(ContentValues values, String whereClause,
			String[] whereArgs) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SQLiteDatabase database = null;
		int count = 0;
		try {
			database = helper.getWritableDatabase();
			count = database.update(SQLHelper.TABLE_PERSON, values, whereClause, whereArgs);
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
	public Person viewPerson(String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		SQLiteDatabase database = null;
		Cursor cursor = null;
		Person person = null;
		try {
			database = helper.getReadableDatabase();
			cursor = database.query(true, SQLHelper.TABLE_PERSON, null, selection,
					selectionArgs, null, null, null, null);
			int cols_len = cursor.getColumnCount();
			while (cursor.moveToNext()) {
				person = new Person();
				for (int i = 0; i < cols_len; i++) {
					String cols_name = cursor.getColumnName(i);
					String cols_values = cursor.getString(cursor
							.getColumnIndex(cols_name));
					if (cols_values == null) {
						cols_values = "";
					}
					
					if (cols_name.equals("_id")) {
						person.mId = cols_values;
					}
					if (cols_name.equals("nickname")) {
						person.setNickname(cols_values);
					}
					if (cols_name.equals("phone")) {
						person.setPhone(cols_values);
					}
					if (cols_name.equals("role")) {
						person.setRole(cols_values);
					}
					if (cols_name.equals("telephone")) {
						person.setTelephone(cols_values);
					}
					if (cols_name.equals("username")) {
						person.setUsername(cols_values);
					}
					if (cols_name.equals("avatar_url")) {
						person.setAvatar_url(cols_values);
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
		return person;
	}

	@Override
	public List<Person> listPerson(String selection,String[] selectionArgs) {
		// TODO Auto-generated method stub
		List<Person> list = new ArrayList<Person>();
		SQLiteDatabase database = null;
		Cursor cursor = null;
		try {
			database = helper.getReadableDatabase();
			cursor = database.query(false, SQLHelper.TABLE_PERSON, null, selection,selectionArgs, null, null, null, null);
			int cols_len = cursor.getColumnCount();
			while (cursor.moveToNext()) {
				Person person = new Person();

					for (int i = 0; i < cols_len; i++) {
						String cols_name = cursor.getColumnName(i);
						String cols_values = cursor.getString(cursor
								.getColumnIndex(cols_name));
						if (cols_values == null) {
							cols_values = "";
						}
						
						if (cols_name.equals("_id")) {
							person.mId = cols_values;
						}
						if (cols_name.equals("nickname")) {
							person.setNickname(cols_values);
						}
						if (cols_name.equals("phone")) {
							person.setPhone(cols_values);
						}
						if (cols_name.equals("role")) {
							person.setRole(cols_values);
						}
						if (cols_name.equals("telephone")) {
							person.setTelephone(cols_values);
						}
						if (cols_name.equals("username")) {
							person.setUsername(cols_values);
						}
						if (cols_name.equals("avatar_url")) {
							person.setAvatar_url(cols_values);
						}
				}
				list.add(person);
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

	public void clearPersonTable() {
		String sql = "DELETE FROM " + SQLHelper.TABLE_PERSON + ";";
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(sql);
		revertSeq();
	}

	private void revertSeq() {
		String sql = "update sqlite_sequence set seq=0 where name='"
				+ SQLHelper.TABLE_PERSON + "'";
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(sql);
	}

}
