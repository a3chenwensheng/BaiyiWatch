package com.baiyi.watch.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StringUtils {

	public static int string2Int(String str) {

		int i = 0;
		try {
			i = Integer.valueOf(str);
		} catch (Exception e) {
		}
		return i;
	}

	public static long string2Long(String str) {

		long i = 0;
		try {
			i = Long.valueOf(str);
		} catch (Exception e) {
		}
		return i;
	}

	public static Double string2Double(String str) {

		Double i = null;
		try {
			i = Double.valueOf(str);
		} catch (Exception e) {
		}
		return i;
	}
	
	public static Double string2double(String str) {

		Double i = 0.0;
		try {
			i = Double.valueOf(str);
		} catch (Exception e) {
		}
		return i;
	}
	
	public static Float string2Float(String str) {

		Float i = null;
		try {
			i = Float.valueOf(str);
		} catch (Exception e) {
		}
		return i;
	}

	/**
	 * 人性化显示星期周期
	 * 
	 * @param status
	 * @return
	 */
	public static String getWeekTips(String status) {
		StringBuffer res = new StringBuffer();
		if (status.length() == 7) {
			if ("1111111".equals(status)) {
				return "每天";
			} else if ("0000000".equals(status)) {
				return null;
			} else {

				char[] sArray = new char[7];

				// 出现这5个子串，说明不连续
				if (status.indexOf("101") != -1 || status.indexOf("1001") != -1 || status.indexOf("10001") != -1 || status.indexOf("100001") != -1 || "1000001".equals(status)) {

					for (int i = 0; i < status.length(); i++) {
						sArray[i] = status.charAt(i);
						if (sArray[i] == '1') {
							switch (i) {
							case 0:
								res.append("周日、");
								break;
							case 1:
								res.append("周一、");
								break;
							case 2:
								res.append("周二、");
								break;
							case 3:
								res.append("周三、");
								break;
							case 4:
								res.append("周四、");
								break;
							case 5:
								res.append("周五、");
								break;
							case 6:
								res.append("周六、");
								break;

							default:
								break;
							}
						}
					}

				} else { // 连续的

					// 数一数1出现的次数
					int countTotal = 0;
					for (int i = 0; i < status.length(); i++) {
						if (status.charAt(i) == '1') {
							countTotal++;
						}
					}
					int count = 0;
					for (int i = 0; i < status.length(); i++) {
						sArray[i] = status.charAt(i);
						if (sArray[i] == '1') {
							count++;
							if (count == 1 || count == countTotal) { // 第一个或者最后一个
								switch (i) {
								case 0:
									res.append("周日至");
									break;
								case 1:
									res.append("周一至");
									break;
								case 2:
									res.append("周二至");
									break;
								case 3:
									res.append("周三至");
									break;
								case 4:
									res.append("周四至");
									break;
								case 5:
									res.append("周五至");
									break;
								case 6:
									res.append("周六至");
									break;

								default:
									break;
								}
							}
						}
					}
				}

				return res.substring(0, res.length() - 1);
			}

		}
		return "";
	}

	public static String caculateTime(String weekStatus, String clock) {
		// TODO Auto-generated method stub
		String result = "";
		try {

			if (!"0000000".equals(weekStatus)) {
				if (clock.length() == 5) {
					// 计算闹钟时间（单位：秒）
					long clockMinute = changeTimeToMinute(clock);
					// 计算当前时间（单位：秒）
					Date now = new Date();
					long currentTime = now.getTime();
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
					long currentMinute = changeTimeToMinute(sdf.format(currentTime));
					// 观察两周的状态，因为有可能与下个星期的第一个1作比较
					weekStatus = weekStatus + weekStatus;
					// 求出今天是一周中的第几天（0：星期天、1：星期一，以此类推）
					int day_of_week = getWeekOfDate(now);

					// 从当前日期开始遍历
					for (int i = day_of_week; i < weekStatus.length(); i++) {
						char ch = weekStatus.charAt(i);
						if (ch == '1') { // 遇到第一个字符'1'，立即跳出循环
							long diff;
							// 如果在同一天，而且当前时间在闹钟时间之前
							if (i == day_of_week) {
								if (currentMinute <= clockMinute) {
									// 直接比较时分
									diff = clockMinute - currentMinute;

								} else { // 如果在同一天，而且当前时间在闹钟时间之后，直接跳到下一天
									continue;
								}

							} else { // 如果不在同一天，比较 天+时分
								currentMinute = day_of_week * 24 * 60 + currentMinute;
								clockMinute = i * 24 * 60 + clockMinute;
								diff = clockMinute - currentMinute;
							}
							
							long minutesInMilli = 1L;
							long hoursInMilli = minutesInMilli * 60;
							long daysInMilli = hoursInMilli * 24;

							int elapsedDays = (int) (diff / daysInMilli);
							diff = diff % daysInMilli;

							int elapsedHours = (int) (diff / hoursInMilli);
							diff = diff % hoursInMilli;

							int elapsedMinutes = (int) (diff / minutesInMilli);
							diff = diff % minutesInMilli;


							if (elapsedDays == 0 && elapsedHours == 0 && elapsedMinutes == 0) {
								return "不到1分钟";
							}

							result = (elapsedDays < 1 ? "" : elapsedDays + "天") + (elapsedHours < 1 ? "" : elapsedHours + "小时") + (elapsedMinutes < 1 ? "" : elapsedMinutes + "分钟") ;

							break;
						}
					}

				} else {
					result = "";
				}

			} else {
				result = "";
			}
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 获取当前日期是星期几
	 * 
	 * @param dt
	 * @return
	 */
	public static int getWeekOfDate(Date dt) {
		// TODO Auto-generated method stub
		// String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
		// "星期六"};
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		return w;
	}

	/**
	 * 时分 → 分
	 *
	 * @param t
	 * @return
	 */
	public static long changeTimeToMinute(String t) {
		// TODO Auto-generated method stub
		int h = Integer.valueOf(t.substring(0, 2));
		int m = Integer.valueOf(t.substring(3, 5));
		return (h * 60 + m);
	}

}
