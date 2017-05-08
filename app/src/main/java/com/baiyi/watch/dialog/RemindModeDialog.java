package com.baiyi.watch.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.R;

public class RemindModeDialog extends Dialog implements View.OnClickListener {

	private String typeStr;
	private String cycleStr;

	private CheckBox mOnceRadioButton;
	private CheckBox mMonCheckBox;
	private CheckBox mTueCheckBox;
	private CheckBox mWedCheckBox;
	private CheckBox mThursCheckBox;
	private CheckBox mFriCheckBox;
	private CheckBox mSatCheckBox;
	private CheckBox mSunCheckBox;
	private CheckBox[] mArray;
	
	private Button mCancel;
	private Button mConfirm;
	
	private OnClickListener mOnClickListener1;// 按钮1的单击监听事件

	public RemindModeDialog(Context context, String alertType, String cycle) {
		super(context);

		typeStr = alertType;
		cycleStr = cycle;

	    setContentView(R.layout.include_dialog_remind_mode);
		mOnceRadioButton = (CheckBox) findViewById(R.id.remind_mode_once);
		mSunCheckBox = (CheckBox) findViewById(R.id.remind_mode_sun);
		mMonCheckBox = (CheckBox) findViewById(R.id.remind_mode_mon);
		mTueCheckBox = (CheckBox) findViewById(R.id.remind_mode_tue);
		mWedCheckBox = (CheckBox) findViewById(R.id.remind_mode_wed);
		mThursCheckBox = (CheckBox) findViewById(R.id.remind_mode_thurs);
		mFriCheckBox = (CheckBox) findViewById(R.id.remind_mode_fri);
		mSatCheckBox = (CheckBox) findViewById(R.id.remind_mode_sat);
		
		mCancel = (Button) findViewById(R.id.remind_mode_cancel);
		mConfirm = (Button) findViewById(R.id.remind_mode_confirm);

		mArray = new CheckBox[] { mSunCheckBox, mMonCheckBox, mTueCheckBox, mWedCheckBox, mThursCheckBox, mFriCheckBox, mSatCheckBox };

		mOnceRadioButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					typeStr = "1";
					for (CheckBox checkBox : mArray) {
						checkBox.setChecked(false);
					}
				} else {
					typeStr = "0";
				}

			}
		});
		
		for (CheckBox checkBox : mArray) {
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (isChecked) {
						typeStr = "0";
						mOnceRadioButton.setChecked(false);
					} else {
						
					}

				}
			});
		}
		
		mCancel.setOnClickListener(this);
		mConfirm.setOnClickListener(this);

		if ("1".equals(typeStr)) {//仅一次
			mOnceRadioButton.setChecked(true);
			for (CheckBox checkBox : mArray) {
				checkBox.setChecked(false);
			}
		} else {
			mOnceRadioButton.setChecked(false);
			if (cycleStr.length() == 7) {
				for (int i = 0; i < 7; i++) {
					if ("1".equals(cycleStr.substring(i, i + 1))) {
						mArray[i].setChecked(true);
					} else {
						mArray[i].setChecked(false);
					}
				}
			}
		}

	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.remind_mode_cancel:
			super.cancel();
			break;
		case R.id.remind_mode_confirm:
			if (mOnClickListener1 != null) {
				mOnClickListener1.onClick(this, 0);
			}
			break;
		}
		
	}

	public String getTypeStr() {
		return typeStr;
	}
	
	public String getCycleStr() {
		if ("1".equals(typeStr)) {
			return cycleStr;
		} else {
			String str = "";
			for (int i = 0; i < mArray.length; i++) {
				str += mArray[i].isChecked() ? "1" : "0";
			}
			if ("0000000".equals(str)) {
				typeStr = "1";
			}
			return str;
		}

	}
	
	public void setConfirmBtn(OnClickListener listener) {
		mOnClickListener1 = listener;
	}

}
