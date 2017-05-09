package com.baiyi.watch.dialog;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.R;

public class RenewDialog extends BaseDialog {

	private TextView mContentTv;
	private TextView mTimeTv;
	private Context mContext;

	public RenewDialog(Context context) {
		super(context);
		mContext = context;
		setDialogContentView(R.layout.include_dialog_renew);
		mContentTv = (TextView) findViewById(R.id.renew_tips_content_tv);
		mTimeTv = (TextView) findViewById(R.id.renew_tips_time_tv);
	}

	@Override
	public void setTitle(CharSequence text) {
		super.setTitle(text);
	}

	public void setData(String content, String time) {
		mContentTv.setText(content);
		String frontStr = "1年";
		if (content.contains(frontStr)) {
			int fstart = content.indexOf(frontStr);
			int fend = fstart + frontStr.length();
			SpannableStringBuilder style = new SpannableStringBuilder(content);
			style.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.theme_color)), fstart, fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			mContentTv.setText(style);
		}
		
		mTimeTv.setText(time);
	}

	public void setButton(CharSequence text, OnClickListener listener) {
		super.setButton1(text, listener);
	}

	public void setButton(CharSequence text1, OnClickListener listener1, CharSequence text2,
			OnClickListener listener2) {
		super.setButton1(text1, listener1);
		super.setButton2(text2, listener2);
	}

}
