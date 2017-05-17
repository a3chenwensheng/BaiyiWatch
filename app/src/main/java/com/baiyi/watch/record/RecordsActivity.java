package com.baiyi.watch.record;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Device;

import java.util.ArrayList;

/**
 * 
 * 选择记录类型Activity
 * 
 * @author 陈文声
 * @email a3chenwensheng@126.com
 * @date 2017-5-17 11:30
 * @version v4.0
 */
public class RecordsActivity extends BaseActivity implements OnClickListener {

	private TextView mBackTv;// 返回

	private TabLayout mTabLayout;
	private ViewPager mVpHome;
	private ArrayList<Fragment> mFragmentList = new ArrayList<>();

	private Device mDevice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_records);
		initView();
		initData();
		setListener();
		
	}

	/** 初始化布局 */
	private void initView() {
		mBackTv = (TextView) findViewById(R.id.back_tv);

		mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
		mVpHome = (ViewPager) findViewById(R.id.vp_home);
		
	}

	/** 初始化数据 */
	private void initData() {

		mDevice = MyApplication.getInstance().getDeviceDaoInface().viewDevice("iscurrent = ?", new String[] { "1" });
		//mImmediatelyRemindLayout.setVisibility(View.GONE);//所有设备不要即时提醒记录
//		if (mDevice != null) {
//
//			String softWare = mDevice.getSoftware_version();
//			if (TextUtils.isEmpty(softWare)) {
//				softWare = "";
//			} else {
//				softWare = softWare.toLowerCase();
//			}
//
//			if ("BY102".equals(mDevice.getType())) {
//
//				if (softWare.contains("single")) {
//					mRecordLayout.setVisibility(View.VISIBLE);
//				}else {
//					mRecordLayout.setVisibility(View.GONE);
//				}
//				mFallLayout.setVisibility(View.GONE);
//			} else if ("BY007".equals(mDevice.getType())) {
//				mRecordLayout.setVisibility(View.VISIBLE);
//				mFallLayout.setVisibility(View.VISIBLE);
//			} else if ("K1".equals(mDevice.getType())) {
//				mRecordLayout.setVisibility(View.VISIBLE);
//				mFallLayout.setVisibility(View.GONE);
//			} else if ("BY102_LOC".equals(mDevice.getType())) {
//				if (softWare.contains("single")) {
//					mRecordLayout.setVisibility(View.VISIBLE);
//				}else {
//					mRecordLayout.setVisibility(View.GONE);
//				}
//				mFallLayout.setVisibility(View.GONE);
//			} else if ("BY102_NO_HR".equals(mDevice.getType())) {
//				if (softWare.contains("single")) {
//					mRecordLayout.setVisibility(View.VISIBLE);
//				}else {
//					mRecordLayout.setVisibility(View.GONE);
//				}
//				mFallLayout.setVisibility(View.GONE);
//			} else {
//				mRecordLayout.setVisibility(View.GONE);
//				mFallLayout.setVisibility(View.GONE);
//			}
//		}else {
//			mRecordLayout.setVisibility(View.GONE);
//			mFallLayout.setVisibility(View.GONE);
//		}

		mFragmentList.add(new ListSOSFragment());
		mFragmentList.add(new ListFenceDataFragment());

		//为TabLayout添加tab名称
		 mTabLayout.addTab(mTabLayout.newTab().setText("SOS"));
		 mTabLayout.addTab(mTabLayout.newTab().setText("安全围栏"));

		mVpHome.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public Fragment getItem(int position) {
				return mFragmentList.get(position);
			}

			@Override
			public int getCount() {
				return mFragmentList.size();
			}
		});
		mVpHome.setOffscreenPageLimit(mFragmentList.size());
		mTabLayout.setupWithViewPager(mVpHome);//这会清空Tab的文字，所以要重写
		mTabLayout.getTabAt(0).setText("SOS");
		mTabLayout.getTabAt(1).setText("安全围栏");



	}

	private void setListener() {
		mBackTv.setOnClickListener(this);


	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_tv:
			onBackPressed();
			break;
		default:
			break;
		}

	}

}
