package com.baiyi.watch.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.widget.wheelview.ScreenInfo;
import com.baiyi.watch.widget.wheelview.WheelTimePicker;

import java.util.Calendar;

public class TimePickerDialog extends BaseDialog {

	private WheelTimePicker wheelTimePicker;
	
	public TimePickerDialog(Context context, Calendar calendar2) {
		super(context);
	
		LayoutInflater inflater = LayoutInflater.from(context);
		View timepickerview = inflater.inflate(R.layout.include_dialog_timepicker, null);
		setDialogContentView(timepickerview);
		
		ScreenInfo screenInfo = new ScreenInfo((Activity)context);
		wheelTimePicker = new WheelTimePicker(timepickerview);
		wheelTimePicker.screenheight = screenInfo.getHeight();
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		if (calendar2 != null) {
			hour = calendar2.get(Calendar.HOUR_OF_DAY);
			minute = calendar2.get(Calendar.MINUTE);
		}
		wheelTimePicker.initDateTimePicker(hour, minute);
		
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

	public String getTime() {
		return wheelTimePicker.getTime();
	}
	public int getHour() {
		return wheelTimePicker.getHour();
	}
	public int getMinute() {
		return wheelTimePicker.getMinute();
	}

}
