package com.baiyi.watch.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.widget.wheelview.ScreenInfo;
import com.baiyi.watch.widget.wheelview.WheelDateTimePicker;

import java.util.Calendar;

public class DateTimePickerDialog extends BaseDialog {

	private WheelDateTimePicker wheelDateTimePicker;
	
	public DateTimePickerDialog(Context context, Calendar calendar2, boolean hasSelectTime) {
		super(context);
	
		LayoutInflater inflater = LayoutInflater.from(context);
		View timepickerview = inflater.inflate(R.layout.include_dialog_datetimepicker, null);
		setDialogContentView(timepickerview);
		
		ScreenInfo screenInfo = new ScreenInfo((Activity)context);
		wheelDateTimePicker = new WheelDateTimePicker(timepickerview, hasSelectTime);
		wheelDateTimePicker.screenheight = screenInfo.getHeight();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(Calendar.MINUTE);
		if (calendar2 != null) {
			year = calendar2.get(Calendar.YEAR);
			month = calendar2.get(Calendar.MONTH);
			day = calendar2.get(Calendar.DAY_OF_MONTH);
			hour = calendar2.get(Calendar.HOUR_OF_DAY);
			min = calendar2.get(Calendar.MINUTE);
		}
		wheelDateTimePicker.initDateTimePicker(year, month, day, hour, min);
		
	}

	@Override
	public void setTitle(CharSequence text) {
		super.setTitle(text);
	}

	public void setButton(CharSequence text, OnClickListener listener) {
		super.setButton1(text, listener);
	}

	public void setButton(CharSequence text1, OnClickListener listener1, CharSequence text2, OnClickListener listener2) {
		super.setButton1(text1, listener1);
		super.setButton2(text2, listener2);
	}

	public String getDateTime() {
		return wheelDateTimePicker.getTime();
	}
	
	public int getYear() {
		return wheelDateTimePicker.getYear();
	}
	
	public int getMonth() {
		return wheelDateTimePicker.getMonth();
	}

	public int getDay() {
		return wheelDateTimePicker.getDay();
	}

	public int getHour() {
		return wheelDateTimePicker.getHour();
	}

	public int getMinute() {
		return wheelDateTimePicker.getMinute();
	}

}
