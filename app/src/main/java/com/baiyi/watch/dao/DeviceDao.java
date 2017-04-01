package com.baiyi.watch.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.baiyi.watch.db.SQLHelper;
import com.baiyi.watch.model.Device;
import com.baiyi.watch.model.Person;

import java.util.ArrayList;
import java.util.List;

public class DeviceDao implements DeviceDaoInface {
	private SQLHelper helper = null;

	public DeviceDao(Context context) {
		helper = new SQLHelper(context);
	}

	@Override
	public boolean addDevice(Device device) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SQLiteDatabase database = null;
		long id = -1;
		try {
			database = helper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("_id", device.mId);
			String avatar_url = "";
			try {
				avatar_url =  device.mOwner.getAvatar_url();
			} catch (Exception e) {
			}
			values.put("ownerurl", avatar_url);
			values.put("ownerid", device.mOwnerId);
			values.put("name", device.getName());
			values.put("step_objective", device.getStep_objective());
			values.put("wear_flag", device.getWear_flag());
			values.put("iccid2", device.getIccid2());
			values.put("sim_phone", device.getSim_phone());
			values.put("device_type", device.getType());
			values.put("software_version", device.getSoftware_version());
			values.put("group_id", device.getGroup_id());
			values.put("group_name", device.getGroup_name());
			values.put("group_ownerid", device.getGroup_ownerid());
			values.put("have_community", device.getHave_community());
			values.put("iscurrent", 0);
			
			id = database.insert(SQLHelper.TABLE_DEVICE, null, values);
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
	public boolean deleteDevice(String whereClause, String[] whereArgs) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SQLiteDatabase database = null;
		int count = 0;
		try {
			database = helper.getWritableDatabase();
			count = database.delete(SQLHelper.TABLE_DEVICE, whereClause, whereArgs);
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
	public boolean updateDevice(ContentValues values, String whereClause,
			String[] whereArgs) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SQLiteDatabase database = null;
		int count = 0;
		try {
			database = helper.getWritableDatabase();
			count = database.update(SQLHelper.TABLE_DEVICE, values, whereClause, whereArgs);
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
	public boolean updateDevice(Device device, String whereClause,
			String[] whereArgs) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SQLiteDatabase database = null;
		int count = 0;
		try {
			database = helper.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			String avatar_url = "";
			try {
				avatar_url =  device.mOwner.getAvatar_url();
			} catch (Exception e) {
			}
			values.put("ownerurl", avatar_url);
			values.put("ownerid", device.mOwnerId);
			values.put("name", device.getName());
			values.put("step_objective", device.getStep_objective());
			values.put("wear_flag", device.getWear_flag());
			values.put("iccid2", device.getIccid2());
			values.put("sim_phone", device.getSim_phone());
			values.put("device_type", device.getType());
			values.put("software_version", device.getSoftware_version());
			values.put("group_id", device.getGroup_id());
			values.put("group_name", device.getGroup_name());
			values.put("group_ownerid", device.getGroup_ownerid());
			values.put("have_community", device.getHave_community());
			count = database.update(SQLHelper.TABLE_DEVICE, values, whereClause, whereArgs);
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
	public Device viewDevice(String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		SQLiteDatabase database = null;
		Cursor cursor = null;
		Device device = null;
		try {
			database = helper.getReadableDatabase();
			cursor = database.query(true, SQLHelper.TABLE_DEVICE, null, selection,
					selectionArgs, null, null, null, null);
			int cols_len = cursor.getColumnCount();
			while (cursor.moveToNext()) {
				device = new Device();
				for (int i = 0; i < cols_len; i++) {
					String cols_name = cursor.getColumnName(i);
					String cols_values = cursor.getString(cursor
							.getColumnIndex(cols_name));
					if (cols_values == null) {
						cols_values = "";
					}
					
					if (cols_name.equals("_id")) {
						device.mId = cols_values;
					}
					if (cols_name.equals("ownerurl")) {
						Person person = new Person();
						person.setAvatar_url(cols_values);
						device.mOwner = person;
					}
					if (cols_name.equals("ownerid")) {
						device.mOwnerId = cols_values;
					}
					if (cols_name.equals("name")) {
						device.setName(cols_values);
					}
					if (cols_name.equals("step_objective")) {
						device.setStep_objective(cols_values);
					}
					if (cols_name.equals("wear_flag")) {
						device.setWear_flag(cols_values);
					}
					if (cols_name.equals("iccid2")) {
						device.setIccid2(cols_values);
					}
					if (cols_name.equals("sim_phone")) {
						device.setSim_phone(cols_values);
					}
					if (cols_name.equals("device_type")) {
						device.setType(cols_values);
					}
					if (cols_name.equals("software_version")) {
						device.setSoftware_version(cols_values);
					}
					if (cols_name.equals("group_id")) {
						device.setGroup_id(cols_values);
					}
					if (cols_name.equals("group_name")) {
						device.setGroup_name(cols_values);
					}
					if (cols_name.equals("group_ownerid")) {
						device.setGroup_ownerid(cols_values);
					}
					if (cols_name.equals("have_community")) {
						device.setHave_community(cols_values);
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
		return device;
	}

	@Override
	public List<Device> listDevice(String selection,String[] selectionArgs) {
		// TODO Auto-generated method stub
		List<Device> list = new ArrayList<Device>();
		SQLiteDatabase database = null;
		Cursor cursor = null;
		try {
			database = helper.getReadableDatabase();
			cursor = database.query(false, SQLHelper.TABLE_DEVICE, null, selection,selectionArgs, null, null, "_id", null);
			int cols_len = cursor.getColumnCount();
			while (cursor.moveToNext()) {
				Device device = new Device();

					for (int i = 0; i < cols_len; i++) {
						String cols_name = cursor.getColumnName(i);
						String cols_values = cursor.getString(cursor
								.getColumnIndex(cols_name));
						if (cols_values == null) {
							cols_values = "";
						}
						
						if (cols_name.equals("_id")) {
							device.mId = cols_values;
						}
						if (cols_name.equals("ownerurl")) {
							Person person = new Person();
							person.setAvatar_url(cols_values);
							device.mOwner = person;
						}
						if (cols_name.equals("ownerid")) {
							device.mOwnerId = cols_values;
						}
						if (cols_name.equals("name")) {
							device.setName(cols_values);
						}
						if (cols_name.equals("step_objective")) {
							device.setStep_objective(cols_values);
						}
						if (cols_name.equals("wear_flag")) {
							device.setWear_flag(cols_values);
						}
						if (cols_name.equals("iccid2")) {
							device.setIccid2(cols_values);
						}
						if (cols_name.equals("sim_phone")) {
							device.setSim_phone(cols_values);
						}
						if (cols_name.equals("device_type")) {
							device.setType(cols_values);
						}
						if (cols_name.equals("software_version")) {
							device.setSoftware_version(cols_values);
						}
						if (cols_name.equals("group_id")) {
							device.setGroup_id(cols_values);
						}
						if (cols_name.equals("group_name")) {
							device.setGroup_name(cols_values);
						}
						if (cols_name.equals("group_ownerid")) {
							device.setGroup_ownerid(cols_values);
						}
						if (cols_name.equals("have_community")) {
							device.setHave_community(cols_values);
						}
				}
				list.add(device);
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

	public void clearDeviceTable() {
		String sql = "DELETE FROM " + SQLHelper.TABLE_DEVICE + ";";
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(sql);
		revertSeq();
	}

	private void revertSeq() {
		String sql = "update sqlite_sequence set seq=0 where name='"
				+ SQLHelper.TABLE_DEVICE + "'";
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(sql);
	}

}
