package com.baiyi.watch.record;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Device;

/**
 * 
 * 选择记录类型Activity
 * 
 * @author 陈文声
 * @email a3chenwensheng@126.com
 * @date 2017-4-21 17:30
 * @version v4.0
 */
public class SelectRecordActivity extends BaseActivity implements OnClickListener {

	private TextView mBackTv;// 返回

	private LinearLayout mSOSLayout;
	private LinearLayout mFenceLayout;
	private LinearLayout mRecordLayout;
	private LinearLayout mFallLayout;
	private LinearLayout mImmediatelyRemindLayout;

	private Device mDevice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_record);
		initView();
		initData();
		setListener();
		
	}

	/** 初始化布局 */
	private void initView() {
		mBackTv = (TextView) findViewById(R.id.back_tv);

		mSOSLayout = (LinearLayout) findViewById(R.id.sos_data_record_layout);
		mFenceLayout = (LinearLayout) findViewById(R.id.fence_data_record_layout);
		mRecordLayout = (LinearLayout) findViewById(R.id.record_data_record_layout);
		mFallLayout = (LinearLayout) findViewById(R.id.fall_data_record_layout);
		mImmediatelyRemindLayout = (LinearLayout) findViewById(R.id.immediately_remind_layout);
		
	}

	/** 初始化数据 */
	private void initData() {

		mDevice = MyApplication.getInstance().getDeviceDaoInface().viewDevice("iscurrent = ?", new String[] { "1" });
		mImmediatelyRemindLayout.setVisibility(View.GONE);//所有设备不要即时提醒记录
		if (mDevice != null) {

			String softWare = mDevice.getSoftware_version();
			if (TextUtils.isEmpty(softWare)) {
				softWare = "";
			} else {
				softWare = softWare.toLowerCase();
			}

			if ("BY102".equals(mDevice.getType())) {

				if (softWare.contains("single")) {
					mRecordLayout.setVisibility(View.VISIBLE);
				}else {
					mRecordLayout.setVisibility(View.GONE);
				}
				mFallLayout.setVisibility(View.GONE);
			} else if ("BY007".equals(mDevice.getType())) {
				mRecordLayout.setVisibility(View.VISIBLE);
				mFallLayout.setVisibility(View.VISIBLE);
			} else if ("K1".equals(mDevice.getType())) {
				mRecordLayout.setVisibility(View.VISIBLE);
				mFallLayout.setVisibility(View.GONE);
			} else if ("BY102_LOC".equals(mDevice.getType())) {
				if (softWare.contains("single")) {
					mRecordLayout.setVisibility(View.VISIBLE);
				}else {
					mRecordLayout.setVisibility(View.GONE);
				}
				mFallLayout.setVisibility(View.GONE);
			} else if ("BY102_NO_HR".equals(mDevice.getType())) {
				if (softWare.contains("single")) {
					mRecordLayout.setVisibility(View.VISIBLE);
				}else {
					mRecordLayout.setVisibility(View.GONE);
				}
				mFallLayout.setVisibility(View.GONE);
			} else {
				mRecordLayout.setVisibility(View.GONE);
				mFallLayout.setVisibility(View.GONE);
			}
		}else {
			mRecordLayout.setVisibility(View.GONE);
			mFallLayout.setVisibility(View.GONE);
		}

	}

	private void setListener() {
		mBackTv.setOnClickListener(this);
		mSOSLayout.setOnClickListener(this);
		mFenceLayout.setOnClickListener(this);
		mRecordLayout.setOnClickListener(this);
		mFallLayout.setOnClickListener(this);
		mImmediatelyRemindLayout.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_tv:
			onBackPressed();
			break;
			case R.id.sos_data_record_layout:
				//redictToActivity(mContext, ListSOSActivity.class, null);
				break;
			case R.id.fence_data_record_layout:
				//redictToActivity(mContext, ListFenceDataActivity.class, null);
				break;
			case R.id.record_data_record_layout:
				//redictToActivity(mContext, ListRecordActivity.class, null);
				break;
			case R.id.fall_data_record_layout:
				//redictToActivity(mContext, ListFallActivity.class, null);
				break;
			case R.id.immediately_remind_layout:
				//redictToActivity(mContext, ChatActivity.class, null);
				break;
		default:
			break;
		}

	}

}
