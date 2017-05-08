package com.baiyi.watch.widget.wheelview;

import android.view.View;

import com.baiyi.watch.aqgs2.R;

public class WheelTimePicker {

	private View view;
	private WheelView wv_hours;
	private WheelView wv_mins;
	public int screenheight;

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public WheelTimePicker(View view) {
		super();
		this.view = view;
		setView(view);
	}

	public void initDateTimePicker() {
		this.initDateTimePicker(0, 0);
	}

	/**
	 * @Description: TODO 弹出日期时间选择器
	 */
	public void initDateTimePicker(int h, int m) {

		wv_hours = (WheelView) view.findViewById(R.id.timepicker_hour_wheelview);
		wv_mins = (WheelView) view.findViewById(R.id.timepicker_minute_wheelview);

		wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
		wv_hours.setCyclic(true);// 可循环滚动
		wv_hours.setLabel("时");// 添加文字
		wv_hours.setCurrentItem(h);

		wv_mins.setAdapter(new NumericWheelAdapter(0, 59));
		wv_mins.setCyclic(true);// 可循环滚动
		wv_mins.setLabel("分");// 添加文字
		wv_mins.setCurrentItem(m);

		// 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
		int textSize = 0;

		textSize = (int) (screenheight / 100.0 * 3.5);

		wv_hours.TEXT_SIZE = textSize;
		wv_mins.TEXT_SIZE = textSize;

	}

	public String getTime() {
		StringBuffer sb = new StringBuffer();
		int hour = wv_hours.getCurrentItem();
		int min = wv_mins.getCurrentItem();
		sb.append(hour < 10 ? "0" + hour : "" + hour).append(":").append(min < 10 ? "0" + min : "" + min);
		return sb.toString();
	}
	
	public int getHour() {
		return wv_hours.getCurrentItem();
	}
	
	public int getMinute() {
		return wv_mins.getCurrentItem();
	}
}
