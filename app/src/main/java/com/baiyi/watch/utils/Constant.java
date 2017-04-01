package com.baiyi.watch.utils;

import android.os.Environment;

/**
 * @author cws
 * @date 2014-12-10
 * TODO 
 */

public interface Constant {
	
	String PRE_NAME = "baiyi_watch";
	public static final String KEY_RFID = "is_open_rfid";
	public static final String KEY_TIME_INTERVAL = "time_interval";
	public static final String CONF_COOKIE = "cookie";
	public static final String APP  = "aiqiangua2.0";
	public static final String APP_ID = "wxc4d00c022748ef5f";
	
	public static final String PREFERENCES = "AlarmAndMusic";
	public static final String KEY_ALARM_SNOOZE = "alarm.snooze.time";
	
	public static final int MESSAGE_INIT_ALARM_LIST = 0;
	public static final int MESSAGE_START_LANDSCAPE = 1;
	public static final int MESSAGE_START_ALARM_LIST = 2;
	public static final int MESSAGE_INIT_LANDSCAPE = 4;
	
	public  static final String APP_DIR = Environment.getExternalStorageDirectory().getPath() + "/WatchApp";
	// 音频目录
	public static final String PATH_ARM_DIR = APP_DIR + "/voice";
	// 发消息录音
	public static final String PATH_MSG_ARM = PATH_ARM_DIR + "/msg_voice.amr";
	// 提醒录音
	public static final String PATH_REMIND_ARM = PATH_ARM_DIR + "/remind_voice.amr";
	// 头像文件存放路径
	public static final String PATH_TEMP_HEAD_IMG = APP_DIR + "image/head_img.png";
	// 分享图片存放路径
	public static final String PATH_ARM_SCREENSHOT = APP_DIR + "/screenshot/share.png";
	
	public static class ShowMsgActivity {
		public static final String STitle = "showmsg_title";
		public static final String SMessage = "showmsg_message";
		public static final String BAThumbData = "showmsg_thumb_data";
	}
	
	// 默认存放文件下载的路径
    public final static String DEFAULT_SAVE_FILE_PATH = APP_DIR + "/download/";

}
