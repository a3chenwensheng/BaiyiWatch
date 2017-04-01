package com.baiyi.watch.device;

import android.content.ContentValues;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.watch.adapter.DevicesAdapter;
import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Device;
import com.baiyi.watch.model.Group4Show;
import com.baiyi.watch.model.Person;
import com.baiyi.watch.net.BaseMessage;
import com.baiyi.watch.net.HttpCallback;
import com.baiyi.watch.net.ParserServer;
import com.baiyi.watch.net.PersonApi;
import com.baiyi.watch.utils.ActivityUtil;
import com.baiyi.watch.utils.TimeUtils;
import com.baiyi.watch.widget.pulltorefresh.XListView;
import com.baiyi.watch.widget.pulltorefresh.XListView.IXListViewListener;

import java.util.ArrayList;
import java.util.List;

import toasty.Toasty;

/**
 * 
 * 设备列表Activity
 * 
 * @author 陈文声
 * @email a3chenwensheng@126.com
 * @date 2016-6-22 19:30
 * @version v3.0
 */
public class ListDeviceActivity extends BaseActivity implements OnClickListener, IXListViewListener {

	private TextView mBackTv;// 返回

	private LinearLayout mEmptyLayout;

	private XListView mListView;//
	private DevicesAdapter mDevicesAdapter;

	private List<Device> listDevice = new ArrayList<Device>();

	private Person mPerson;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_devices);
		initView();
		initData();
		setListener();
		
	}

	/** 初始化布局 */
	private void initView() {
		mBackTv = (TextView) findViewById(R.id.back_tv);

		mEmptyLayout = (LinearLayout) findViewById(R.id.empty_layout);
		
		mListView = (XListView) findViewById(R.id.devices_listView);
		mListView.setFooterDividersEnabled(false);
		mListView.setPullRefreshEnable(true);
		mListView.setPullLoadEnable(false);
		mListView.setAutoLoadEnable(true);
		mListView.setXListViewListener(this);
		mListView.setRefreshTime(TimeUtils.getDateStr("MM-dd HH:mm:ss"));
		
	}

	/** 初始化数据 */
	private void initData() {

		mPerson = MyApplication.getInstance().getPersonDaoInface().viewPerson(null, null);
		
		//listDevice.addAll(MyApplication.getInstance().getDeviceDaoInface().listDevice(null, null));

		mDevicesAdapter = new DevicesAdapter(mContext, listDevice);
		mListView.setAdapter(mDevicesAdapter);
	}

	private void setListener() {
		mBackTv.setOnClickListener(this);
		mEmptyLayout.setOnClickListener(this);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				
				Device device =  listDevice.get(position - 1);
				if (device == null) {
					return;
				}
				
				// 更新Group DB
				ContentValues cv1 = new ContentValues();
				cv1.put("iscurrent", 0);
				boolean a = MyApplication.getInstance().getGroupDaoInface().updateGroup(cv1, null, null);

				ContentValues cv2 = new ContentValues();
				cv2.put("iscurrent", 1);
				boolean b  =  MyApplication.getInstance().getGroupDaoInface().updateGroup(cv2, "_id = ?", new String[] { device.getGroup_id() });
				
				// 更新Device DB
				ContentValues cv3 = new ContentValues();
				cv3.put("iscurrent", 0);
				boolean c = MyApplication.getInstance().getDeviceDaoInface().updateDevice(cv3, null, null);

				ContentValues cv4 = new ContentValues();
				cv4.put("iscurrent", 1);
				boolean d = MyApplication.getInstance().getDeviceDaoInface().updateDevice(cv4, "_id = ?", new String[] { device.mId });
				
				//if (a&&b&&c&&d) {
				finish();

			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_tv:
			onBackPressed();
			break;
		case R.id.empty_layout:
			//redictToActivity(mContext, AddActivity.class, null);
			//finish();
			break;
		default:
			break;
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		new Handler().postDelayed(new Runnable() {
			public void run() {
				mListView.autoRefresh();
			}
		}, 100);
		
	}
	
	@Override
	public void onRefresh() {
		getAllDevices();

	}

	@Override
	public void onLoadMore() {
		

	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime(TimeUtils.getDateStr("MM-dd HH:mm:ss"));

		// if
		// mListView.setPullLoadEnable(true);
	}

	private void getAllDevices() {
		if (mPerson == null || TextUtils.isEmpty(mPerson.mId)) {
			onLoad();
			Toasty.error(mContext, "请登录").show();
			return;
		}
		
		//showLoadingDialog("载入家庭成员中...");
		PersonApi.getInstance(mContext).getAllDevices(mPerson.mId, new HttpCallback() {
			@Override
			public void onError(String error) {
				onLoad();
				//dismissLoadingDialog();
				Toasty.error(mContext, error).show();
			}

			@Override
			public void onComplete(BaseMessage result) {
				onLoad();
				//dismissLoadingDialog();
				if (result.isSuccess()) {

					// 更新DB
					ArrayList<Group4Show> group4ShowList = null;
					try {
						group4ShowList = ParserServer.paserGroup4Shows(result.getResultSrc());
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					//TODO
					// 添加家庭圈DB

					List<Device> listD = new ArrayList<Device>();
					if (group4ShowList != null) {
						for (Group4Show group4Show : group4ShowList) {
							if (group4Show.mDevices != null && !group4Show.mDevices.isEmpty()) {
								for (Device device : group4Show.mDevices) {
									device.mId = device.getImei();
									Person owner = new Person();
									owner.setAvatar_url(device.getAvatar_url());
									device.mOwner = owner;
									device.setGroup_name(group4Show.getName());
									device.setGroup_id(group4Show.mId);
									device.setGroup_ownerid(group4Show.mOwnerId);
								}
							}
							listD.addAll(group4Show.mDevices);
						}

					}
					
					listDevice.clear();
					listDevice.addAll(listD);
					
					if (listDevice.isEmpty()) {
						mEmptyLayout.setVisibility(View.VISIBLE);
					}else {
						mEmptyLayout.setVisibility(View.GONE);
					}
					
					if (!listDevice.isEmpty()) {
						// DB
						// 当前设备
						Device mDevice = MyApplication.getInstance().getDeviceDaoInface().viewDevice("iscurrent = ?", new String[] { "1" });
						
						MyApplication.getInstance().getDeviceDaoInface().clearDeviceTable();
						for (Device device : listDevice) {
							MyApplication.getInstance().getDeviceDaoInface().addDevice(device);
						}
						
						// 恢复默认查看腕表
						ContentValues cv = new ContentValues();
						cv.put("iscurrent", 1);
						if (mDevice == null || TextUtils.isEmpty(mDevice.mId)) {
							// 取第一个腕表，当默认查看腕表
							Device device = MyApplication.getInstance().getDeviceDaoInface().viewDevice("_id != ?", new String[] { "" });
							if (null != device && !TextUtils.isEmpty(device.mId)) {
								MyApplication.getInstance().getDeviceDaoInface().updateDevice(cv, "_id = ?", new String[] { device.mId });
							}
						} else {
							MyApplication.getInstance().getDeviceDaoInface().updateDevice(cv, "_id = ?", new String[] { mDevice.mId });
						}
						
					}else {
						MyApplication.getInstance().getDeviceDaoInface().clearDeviceTable();
					}
					
					mDevicesAdapter.notifyDataSetChanged();
					
				} else {
					Toasty.error(mContext, result.getError_desc()).show();
				}
			}
		});
	}

}
