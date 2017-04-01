package com.baiyi.watch.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper {
	public static final String DB_NAME = "watchdatabase.db";// 数据库名称
	public static final int VERSION = 9;
	
	public static final String TABLE_PERSON = "table_person";//用户数据表 
	public static final String TABLE_GROUP = "table_group";//家庭圈数据表 
	public static final String TABLE_MEMBER = "table_member";//家庭成员数据表 
	public static final String TABLE_DEVICE= "table_device";//腕表数据表 
	public static final String TABLE_POSTUREDATA = "table_posturedata";//步数 数据表 
	public static final String TABLE_REMIND_BIRTHDAY = "table_remind_birthday";//生日提醒数据表 

	private Context context;
	public SQLHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		this.context = context;
	}

	public Context getContext(){
		return context;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO 创建数据库后，对数据库的操作
		
		String sql_person = "create table if not exists "+TABLE_PERSON +
				"(_id_auto INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"_id" + " TEXT , " +
				"nickname" + " TEXT , " +
				"phone" + " TEXT , " +
				"role" + " TEXT , " +
				"telephone" + " TEXT , " +
				"username" + " TEXT , " +
				"avatar_url" + " TEXT)";
		
		String sql_group = "create table if not exists "+TABLE_GROUP +
				"(_id_auto INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"_id" + " TEXT , " +
				"name" + " TEXT , " +
				"iscurrent" + " INTEGER , " +
				"ownerid" + " TEXT)";
		
		String sql_member = "create table if not exists "+TABLE_MEMBER +
				"(_id_auto INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"_id" + " TEXT , " +
				"avatar_url" + " TEXT , " +
				"deviceid" + " TEXT , " +
				"nickname" + " TEXT , " +
				"iscurrent" + " INTEGER , " +
				"username" + " TEXT)";
		
		String sql_device = "create table if not exists "+TABLE_DEVICE +
				"(_id_auto INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"_id" + " TEXT , " +
				"ownerid" + " TEXT , " +
				"ownerurl" + " TEXT , " +
				"name" + " TEXT , " +
				"step_objective" + " TEXT , " +
				"wear_flag" + " TEXT , " +
				"device_type" + " TEXT , " +
				"software_version" + " TEXT , " +
				"group_id" + " TEXT , " +
				"group_name" + " TEXT , " +
				"group_ownerid" + " TEXT , " +
				"have_community" + " TEXT , " +
				"iscurrent" + " INTEGER , " +
				"iccid2" + " TEXT , " +
				"sim_phone" + " TEXT)";
		
		String sql_posturedata = "create table if not exists "+TABLE_POSTUREDATA +
				"(_id_auto INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"_id" + " TEXT , " +
				"search_date" + " TEXT , " +
				"count" + " TEXT , " +
				"calorie" + " TEXT)";
		
		String sql_remind_birthday = "create table if not exists "+TABLE_REMIND_BIRTHDAY +
				"(_id_auto INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"title" + " TEXT , " +
				"remind_date" + " TEXT)";
		
		db.execSQL(sql_person);
		db.execSQL(sql_group);
		db.execSQL(sql_member);
		db.execSQL(sql_device);
		db.execSQL(sql_posturedata);
		db.execSQL(sql_remind_birthday);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO 更改数据库版本的操作
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEVICE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTUREDATA);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMIND_BIRTHDAY);
		onCreate(db);
	}

}
