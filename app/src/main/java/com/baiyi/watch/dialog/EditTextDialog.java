package com.baiyi.watch.dialog;

import android.content.Context;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.widget.EditText;

import com.baiyi.watch.aqgs2.R;

public class EditTextDialog extends BaseDialog {

	private EditText mEtEnter;

	public EditTextDialog(Context context) {
		super(context);
		setDialogContentView(R.layout.include_dialog_edittext);
		mEtEnter = (EditText) findViewById(R.id.dialog_edittext_enter);
	}

	@Override
	public void setTitle(CharSequence text) {
		super.setTitle(text);
	}
	
	public void setInputType(int inputType) {
		mEtEnter.setInputType(inputType);
	}
	
	public void setMaxLength(int maxLength) {
		 InputFilter[] filters = {new LengthFilter(maxLength)};    
		 mEtEnter.setFilters(filters);  
	}
	
	public void setEditContent(String content) {
		if (!TextUtils.isEmpty(content)) {
			mEtEnter.setText(content);
			mEtEnter.setSelection(content.length());
		}
		
	}

	public void setButton(CharSequence text,
			OnClickListener listener) {
		super.setButton1(text, listener);
	}

	public void setButton(CharSequence text1,
			OnClickListener listener1, CharSequence text2,
			OnClickListener listener2) {
		super.setButton1(text1, listener1);
		super.setButton2(text2, listener2);
	}

	public String getText() {
		if (isNull(mEtEnter)) {
			return null;
		}
		return mEtEnter.getText().toString().trim();
	}
	
	private boolean isNull(EditText editText) {
		String text = editText.getText().toString().trim();
		if (text != null && text.length() > 0) {
			return false;
		}
		return true;
	}

	public void requestFocus() {
		mEtEnter.requestFocus();
	}
	
	public EditText getEtEnter() {
		return mEtEnter;
	}
}
