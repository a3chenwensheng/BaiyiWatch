package com.baiyi.watch.utils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtils {

	/**
	 * 获取更改时区后的日期
	 * 
	 */
	public static Date changeTimeZone(Date date, TimeZone oldZone, TimeZone newZone) {
		if (date != null) {
			int timeOffset = oldZone.getRawOffset() - newZone.getRawOffset();
			return new Date(date.getTime() - timeOffset);
		}

		return null;
	}

	/**
	 * 获取今天日期
	 * 
	 * @return
	 */
	public static String getDateStr() {
		try {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
			return sdf.format(calendar.getTime());

		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * 获取今天日期
	 * 
	 * @return
	 */
	public static String getDateStr(String format) {
		try {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(calendar.getTime());
		} catch (Exception e) {
		}

		return "";
	}

	/**
	 * long时间按格式转String时间
	 * 
	 * @param time_long
	 * @param format
	 * @return
	 */
	public static String long2StrDate(long time_long, String format) {
		try {
			Date date = new Date(time_long);
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date.getTime());
		} catch (Exception e) {
		}
		return "";
	}

	public static Date jsonStr2StrDate(String time_json) {
		try {
			JSONObject jso = new JSONObject(time_json);
			long time_str = jso.getLong("$date");
			Date date = new Date(time_str);
			date = changeTimeZone(date, TimeZone.getTimeZone("Asia/Shanghai"), TimeZone.getTimeZone("GMT"));
			return date;
		} catch (Exception e) {
		}

		return null;
	}

	/**
	 * String时间按格式转Date
	 * 
	 * @param time_str
	 * @param format
	 * @return
	 */
	public static Date string2Date(String time_str, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(time_str);
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * json时间格式化
	 * 
	 * @param time_json
	 * @return
	 */
	public static String getJsonLong2StrDate(String time_json) {
		try {
			JSONObject jso = new JSONObject(time_json);
			long time_str = jso.getLong("$date");
			Date date = new Date(time_str);
			date = changeTimeZone(date, TimeZone.getTimeZone("Asia/Shanghai"), TimeZone.getTimeZone("GMT"));
			return date.getTime() + "";
		} catch (Exception e) {
		}

		return "";
	}

	/**
	 * Date按格式转String时间
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date.getTime());
		} catch (Exception e) {
		}
		return "";
	}

	public static String intervalTime(Date date) {

		try {

			long different = new Date().getTime() - date.getTime();

			long secondsInMilli = 1000;
			long minutesInMilli = secondsInMilli * 60;
			long hoursInMilli = minutesInMilli * 60;
			long daysInMilli = hoursInMilli * 24;

			int elapsedDays = (int) (different / daysInMilli);
			different = different % daysInMilli;

			int elapsedHours = (int) (different / hoursInMilli);
			different = different % hoursInMilli;

			int elapsedMinutes = (int) (different / minutesInMilli);
			different = different % minutesInMilli;

			int elapsedSeconds = (int) (different / secondsInMilli);

			if (elapsedDays == 0 && elapsedHours == 0 && elapsedMinutes == 0) {
				return "刚刚更新";
			}

			return (elapsedDays < 1 ? "" : elapsedDays + "天") + (elapsedHours < 1 ? "" : elapsedHours + "小时")
					+ (elapsedMinutes < 1 ? "" : elapsedMinutes + "分钟") + " 前";

		} catch (Exception e) {
			return "--:--";
		}
	}

	public static String intervalTime2(Date date) {

		try {

			long different = date.getTime() - new Date().getTime();

			if (different < 0) {
				return "时间已过";
			}

			long secondsInMilli = 1000;
			long minutesInMilli = secondsInMilli * 60;
			long hoursInMilli = minutesInMilli * 60;
			long daysInMilli = hoursInMilli * 24;

			int elapsedDays = (int) (different / daysInMilli);
			different = different % daysInMilli;

			int elapsedHours = (int) (different / hoursInMilli);
			different = different % hoursInMilli;

			int elapsedMinutes = (int) (different / minutesInMilli);
			different = different % minutesInMilli;

			int elapsedSeconds = (int) (different / secondsInMilli);

			if (elapsedDays == 0 && elapsedHours == 0 && elapsedMinutes == 0) {
				return "不到1分钟";
			}

			return (elapsedDays < 1 ? "" : elapsedDays + "天") + (elapsedHours < 1 ? "" : elapsedHours + "小时")
					+ (elapsedMinutes < 1 ? "" : elapsedMinutes + "分钟");

		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 首页的 位置更新时间差
	 * 
	 * @param date
	 * @return
	 */
	public static String intervalTime3(Date date) {

		try {

			long different = new Date().getTime() - date.getTime();

			long secondsInMilli = 1000;
			long minutesInMilli = secondsInMilli * 60;
			long hoursInMilli = minutesInMilli * 60;
			long daysInMilli = hoursInMilli * 24;

			int elapsedDays = (int) (different / daysInMilli);
			different = different % daysInMilli;

			int elapsedHours = (int) (different / hoursInMilli);
			different = different % hoursInMilli;

			int elapsedMinutes = (int) (different / minutesInMilli);
			different = different % minutesInMilli;

			int elapsedSeconds = (int) (different / secondsInMilli);

			if (elapsedDays == 0 && elapsedHours == 0 && elapsedMinutes < 2) {
				return "刚刚更新";
			}

			if (elapsedDays < 1) {
				return (elapsedHours < 1 ? "" : elapsedHours + "小时") + (elapsedMinutes < 1 ? "" : elapsedMinutes + "分钟")
						+ (elapsedSeconds < 1 ? "" : elapsedSeconds + "秒") + "前";
			} else {
				return (elapsedDays + "天") + (elapsedHours < 1 ? "" : elapsedHours + "小时") + " 前";
			}

		} catch (Exception e) {
			return "--:--";
		}
	}

	/**
	 * 获取月份的 天数
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getCurrentMonthDay(int year, int month) {

		Calendar calendar = Calendar.getInstance();
		if (year != 0) {
			calendar.set(Calendar.YEAR, year);
		}
		if (month != 0) {
			calendar.set(Calendar.MONTH, month - 1);
		}

		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		int maxDate = calendar.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 获取月份起始星期
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getCurrentMonthStart(int year, int month) {

		Calendar calendar = Calendar.getInstance();
		if (year != 0) {
			calendar.set(Calendar.YEAR, year);
		}
		if (month != 0) {
			calendar.set(Calendar.MONTH, month - 1);
		}

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		// SimpleDateFormat format = new SimpleDateFormat("E");
		// System.out.println("本月第一天是：" + format.format(calendar.getTime()) +
		// "====" + calendar.get(Calendar.DAY_OF_WEEK));
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		if (day == 1)
			day = 7;
		else
			day = day - 1;
		return day;
	}

	/**
	 * 获取星期
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekStr(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String str = "";
		str = calendar.get(Calendar.DAY_OF_WEEK) + "";
		if ("1".equals(str)) {
			str = "星期日";
		} else if ("2".equals(str)) {
			str = "星期一";
		} else if ("3".equals(str)) {
			str = "星期二";
		} else if ("4".equals(str)) {
			str = "星期三";
		} else if ("5".equals(str)) {
			str = "星期四";
		} else if ("6".equals(str)) {
			str = "星期五";
		} else if ("7".equals(str)) {
			str = "星期六";
		}
		return str;
	}

	public static String formatHours(int mins) {
		if (mins < 0) {
			return "--";
		}
		int hour = mins / 60;
		int min = mins % 60;

		return (hour < 1 ? "" : (hour + "小时")) + (min < 1 ? "" : (min + "分钟"));
	}

	/**
	 * 距离当天的天数
	 * 
	 * @param date
	 * @return
	 */
	public static int intervalCurrentDate(Date date) {

		try {

			long different = date.getTime() - new Date().getTime();

			long secondsInMilli = 1000;
			long minutesInMilli = secondsInMilli * 60;
			long hoursInMilli = minutesInMilli * 60;
			long daysInMilli = hoursInMilli * 24;

			if (different < 0) {
				return 0;
			}

			int elapsedDays = (int) (different / daysInMilli);
			// different = different % daysInMilli;

			return elapsedDays + 1;

		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * 当前日期周六
	 *
	 */
	public static String getSaturdayOfThisWeek(long longDatetime, String format) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(longDatetime);
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0) {
			day_of_week = 7;
		}
		c.add(Calendar.DATE, -day_of_week + 6);
		return date2Str(c.getTime(), format);
	}

	/**
	 * 当前日期周日
	 * 
	 */
	public static String getSundayOfThisWeek(long longDatetime, String format) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(longDatetime);
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0) {
			day_of_week = 7;
		}
		c.add(Calendar.DATE, -day_of_week);
		return date2Str(c.getTime(), format);
	}

}
