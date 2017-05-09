package com.baiyi.watch.sosnumber;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Device;
import com.baiyi.watch.model.SettingSosNumber;
import com.baiyi.watch.net.BaseMessage;
import com.baiyi.watch.net.DeviceApi;
import com.baiyi.watch.net.HttpCallback;
import com.baiyi.watch.utils.ActivityUtil;
import com.baiyi.watch.widget.toggle.ToggleButton;

/**
 * 
 * 编辑亲情号码 Activity
 * 
 * @author 陈文声
 * @email a3chenwensheng@126.com
 * @date 2015-5-27 15:00
 * @version v2.0
 */
public class SOSNumberEditActivity extends BaseActivity implements OnClickListener {

	private TextView mBackTv;// 返回
	private RelativeLayout mNameLayout;
	private RelativeLayout mPhoneLayout;
	private TextView mNameTv;
	private TextView mPhoneTv;
	private ToggleButton mToggle;
	private Button mSaveBtn;

	//private EditTextDialog mEditTextNameDialog;// 姓名输入对话框
	//private EditTextDialog2 mEditTextPhoneDialog;// 手机号码输入对话框

	private SettingSosNumber mSOSNumber;
	private Device mDevice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sos_number_edit);
		initView();
		initData();
		setListener();

	}

	/** 初始化布局 */
	private void initView() {
		mBackTv = (TextView) findViewById(R.id.back_tv);
		mNameLayout = (RelativeLayout) findViewById(R.id.sos_number_name_layout);
		mPhoneLayout = (RelativeLayout) findViewById(R.id.sos_number_phone_layout);
		mNameTv = (TextView) findViewById(R.id.sos_number_name);
		mPhoneTv = (TextView) findViewById(R.id.sos_number_phone);
		mToggle = (ToggleButton) findViewById(R.id.sos_number_toggle);
		mSaveBtn = (Button) findViewById(R.id.sos_number_save_btn);

	}

	/** 初始化数据 */
	private void initData() {

		mSOSNumber = (SettingSosNumber) getIntent().getSerializableExtra("SettingSosNumber");
		//mDevice = (Device) getIntent().getSerializableExtra("device");
		mDevice = MyApplication.getInstance().getDeviceDaoInface().viewDevice("iscurrent = ?", new String[] { "1" });
		
		if (null != mSOSNumber) {
			
			if (TextUtils.isEmpty(mSOSNumber.getName())) {
				mNameTv.setText("亲情号码"+mSOSNumber.getSeqid());
			}else {
				mNameTv.setText(mSOSNumber.getName());
			}
			mPhoneTv.setText(mSOSNumber.getNum());
			
			if ("true".equals(mSOSNumber.getDial_flag())) {
				mToggle.setToggleOn();
			}else {
				mToggle.setToggleOff();
			}

		}

	}

	private void setListener() {
		mBackTv.setOnClickListener(this);
		mNameLayout.setOnClickListener(this);
		mPhoneLayout.setOnClickListener(this);
		mSaveBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_tv:
			onBackPressed();
			break;
		case R.id.sos_number_name_layout:
			showNameDialog(mNameTv.getText().toString());
			break;
		case R.id.sos_number_phone_layout:
			showPhoneDialog(mPhoneTv.getText().toString());
			break;
		case R.id.sos_number_save_btn:
			save();
			break;
		default:
			break;
		}

	}

	/**
	 * 显示姓名输入对话框
	 */
	private void showNameDialog(String content) {
//		mEditTextNameDialog = new EditTextDialog(mContext);
//		mEditTextNameDialog.setTitleLineVisibility(View.INVISIBLE);
//		mEditTextNameDialog.setTitle("姓名");
//		mEditTextNameDialog.setInputType(InputType.TYPE_CLASS_TEXT);
//		mEditTextNameDialog.setEditContent(content);
//		mEditTextNameDialog.setButton("取消", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				mEditTextNameDialog.cancel();
//			}
//		}, "确认", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				String text = mEditTextNameDialog.getText();
//				if (text == null) {
//					mEditTextNameDialog.requestFocus();
//				} else {
//					mNameTv.setText(text);
//					mEditTextNameDialog.dismiss();
//				}
//			}
//		});
//		mEditTextNameDialog.show();

	}

	/**
	 * 显示手机号码输入对话框
	 */
	private void showPhoneDialog(String content) {
//		mEditTextPhoneDialog = new EditTextDialog2(mContext);
//		mEditTextPhoneDialog.setTitleLineVisibility(View.INVISIBLE);
//		mEditTextPhoneDialog.setTitle("手机号码");
//		mEditTextPhoneDialog.setEditContent(content);
//		mEditTextPhoneDialog.setButton("取消", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				mEditTextPhoneDialog.cancel();
//			}
//		}, "确认", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				String text = mEditTextPhoneDialog.getText();
//				if (text == null) {
//					mEditTextPhoneDialog.requestFocus();
//				} else {
//					mPhoneTv.setText(text);
//					mEditTextPhoneDialog.dismiss();
//				}
//			}
//		});
//		mEditTextPhoneDialog.show();

	}

	private void save() {
		
		if (mSOSNumber == null) {
			return;
		}
		
		if (TextUtils.isEmpty(mNameTv.getText().toString().trim())) {
			ActivityUtil.showToast(mContext, "输入姓名");
			showNameDialog("");
			return;
		}
		if (TextUtils.isEmpty(mPhoneTv.getText())) {
			ActivityUtil.showToast(mContext, "输入手机号码");
			showPhoneDialog("");
			return;
		}

		SettingSosNumber sosNumber = new SettingSosNumber();
		sosNumber.setSeqid(mSOSNumber.getSeqid());
		sosNumber.setName(mNameTv.getText().toString().trim());
		sosNumber.setNum(mPhoneTv.getText().toString());
		sosNumber.setDial_flag(mToggle.isChecked()?"true":"false");
		
		editSosNumber(sosNumber);
		
	}

	private void editSosNumber(SettingSosNumber settingSosNumber) {
		
		if (mDevice == null || TextUtils.isEmpty(mDevice.mId)) {
			ActivityUtil.showToast(mContext, "请选择设备");
			return;
		}

		//showLoadingDialog("处理中...");
		DeviceApi.getInstance(mContext).editSosNumber(mDevice.mId, settingSosNumber, new HttpCallback() {

			@Override
			public void onError(String error) {
				//dismissLoadingDialog();
				
			}

			@Override
			public void onComplete(BaseMessage result) {
				//dismissLoadingDialog();
				if (result.isSuccess()) {
					finish();
				}else {
					ActivityUtil.showToast(mContext, result.getError_desc());
				}

			}
		});
		
	}

}
