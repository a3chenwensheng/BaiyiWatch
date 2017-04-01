package com.baiyi.watch.dao;//package com.baiyi.watch.dao;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.json.JSONObject;
//
//import com.baiyi.watch.adapter.LocusUsersAdapter;
//import com.baiyi.watch.adapter.WatchAdapter;
//import com.baiyi.watch.aqgs2.BaseActivity;
//import com.baiyi.watch.aqgs2.MyApplication;
//import com.baiyi.watch.aqgs2.R;
//import com.baiyi.watch.data.BloodPressureActivity;
//import com.baiyi.watch.data.HeartRateActivity;
//import com.baiyi.watch.data.PedometerActivity;
//import com.baiyi.watch.data.SleepActivity;
//import com.baiyi.watch.device.EditDeviceInfoActivity;
//import com.baiyi.watch.dialog.BaseDialog;
//import com.baiyi.watch.model2.Bloodpressuredata;
//import com.baiyi.watch.model2.Device;
//import com.baiyi.watch.model2.Group;
//import com.baiyi.watch.model2.Heartratedata;
//import com.baiyi.watch.model2.Person;
//import com.baiyi.watch.model2.Posturedata;
//import com.baiyi.watch.model2.Sleepdata;
//import com.baiyi.watch.model2.Sleepdatasleep;
//import com.baiyi.watch.net.BaseMessage;
//import com.baiyi.watch.net.DeviceApi;
//import com.baiyi.watch.net.GroupApi;
//import com.baiyi.watch.net.HttpCallback;
//import com.baiyi.watch.net.HttpUtil;
//import com.baiyi.watch.net.JsonUtil;
//import com.baiyi.watch.net.ParserServer;
//import com.baiyi.watch.utils.ActivityUtil;
//import com.baiyi.watch.utils.StringUtils;
//import com.baiyi.watch.utils.TimeUtils;
//
//import android.content.ContentValues;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.drawable.BitmapDrawable;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v4.widget.DrawerLayout.DrawerListener;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
//import android.view.ViewGroup.LayoutParams;
//import android.text.TextUtils;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.Button;
//import android.widget.GridView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.PopupWindow;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
///**
// * 
// * 监测数据主界面Activity
// * 
// * @author 陈文声
// * @email a3chenwensheng@126.com
// * @date 2015-3-19 11:00
// * @version v2.0
// */
//public class DataMainActivity extends BaseActivity implements OnClickListener {
//
//	private LinearLayout mBackLayout;// 返回
//	private TextView mTitleTv;// 标题
//
//	private RelativeLayout mWatchLayout;//
//	private TextView mWatchNameTv;
//	
//	private LinearLayout mStateLayout;
//	private LinearLayout mNoWearLayout;
//	private LinearLayout mWearLayout;
//	private LinearLayout mOnlineLayout;
//	private TextView mStateTv;
//	private ProgressBar mStateProgressBar;
//
//	private LinearLayout mHeartRateLayout;// 心率
//	private LinearLayout mPedometerLayout;// 计步
//	private LinearLayout mSleepLayout;// 睡眠
//	private LinearLayout mBloodPressureLayout;// 血压
//	private LinearLayout mBloodGlucoseLayout;// 血糖
//	private LinearLayout mBloodOxygenLayout;// 血氧
//	
//	private TextView mHeartRateTimeTv;
//	private TextView mHeartRateValueTv;
//	private TextView mPedometerTimeTv;
//	private TextView mPedometerValueTv;
//	private TextView mSleepTimeTv;
//	private TextView mSleepValueTv;
//	private TextView mBloodPressureTimeTv;
//	private TextView mBloodPressureSbpValueTv;
//	private TextView mBloodPressureDbpValueTv;
//	
//	private Button mDialBtn;
//	private BaseDialog mDialTipsDialog;//拨打电话提示对话框
//
//	private DrawerLayout mDrawerLayout;
//	private RelativeLayout mMenuLayout;
//	private SwipeRefreshLayout mSwipeRefreshLayout;// 谷歌下拉刷新控件
//	private ListView mListView;//
//	private WatchAdapter mWatchAdapter;
//	private List<Device> listDevice = new ArrayList<Device>();
//	
//	private Group mGroup;
//	private Person mPerson;
//	private boolean mIsManager = false;
//	
//	private Device mDevice;
//	private PopupWindow mUsersPopWin;
//	
//	private final int DATAINTERVAL1 = 20 * 1000;
//	private int count1 = 0;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_data_main);
//		initView();
//		initData();
//		setListener();
//
//	}
//
//	/** 初始化布局 */
//	private void initView() {
//		mBackLayout = (LinearLayout) findViewById(R.id.lay_go_back);
//		mTitleTv = (TextView) findViewById(R.id.navigation_bar_title);
//		mStateLayout = (LinearLayout) findViewById(R.id.watch_state_layout);
//		mNoWearLayout = (LinearLayout) findViewById(R.id.no_wear_layout);
//		mWearLayout = (LinearLayout) findViewById(R.id.wear_layout);
//		mOnlineLayout = (LinearLayout) findViewById(R.id.online_layout);
//		mStateTv = (TextView) findViewById(R.id.watch_state_tv);
//		mStateProgressBar = (ProgressBar) findViewById(R.id.watch_state_progress);
//		mWatchLayout = (RelativeLayout) findViewById(R.id.watch_name_layout);
//		mWatchNameTv = (TextView) findViewById(R.id.current_watch_name_tv);
//		mHeartRateLayout = (LinearLayout) findViewById(R.id.heart_rate_layout);
//		mPedometerLayout = (LinearLayout) findViewById(R.id.pedometer_layout);
//		mSleepLayout = (LinearLayout) findViewById(R.id.sleep_layout);
//		mBloodPressureLayout = (LinearLayout) findViewById(R.id.blood_pressure_layout);
//		mBloodGlucoseLayout = (LinearLayout) findViewById(R.id.blood_glucose_layout);
//		mBloodOxygenLayout = (LinearLayout) findViewById(R.id.blood_oxygen_layout);
//		mHeartRateTimeTv = (TextView) findViewById(R.id.heart_rate_time);
//		mHeartRateValueTv = (TextView) findViewById(R.id.heart_rate_value);
//		mPedometerTimeTv = (TextView) findViewById(R.id.pedometer_time);
//		mPedometerValueTv = (TextView) findViewById(R.id.pedometer_value);
//		mSleepTimeTv = (TextView) findViewById(R.id.sleep_time);
//		mSleepValueTv = (TextView) findViewById(R.id.sleep_value);
//		mBloodPressureTimeTv = (TextView) findViewById(R.id.blood_pressure_time);
//		mBloodPressureSbpValueTv = (TextView) findViewById(R.id.blood_pressure_sbp_value);
//		mBloodPressureDbpValueTv = (TextView) findViewById(R.id.blood_pressure_dbp_value);
//		mDialBtn = (Button) findViewById(R.id.data_dial_btn);
//		
//		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//		mMenuLayout = (RelativeLayout) findViewById(R.id.menu_layout);
//		mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED); //打开手势滑动
//		//mDrawerLayout.drawer
//		mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.device_swipeRefreshLayout);
//		// 设置下拉刷新控件卷内的颜色
//		mSwipeRefreshLayout.setColorSchemeResources(R.color.theme_color, R.color.theme_color, R.color.theme_color, R.color.theme_color);
//		mListView = (ListView) findViewById(R.id.watch_listView);
//		
//	}
//
//	/** 初始化数据 */
//	private void initData() {
//		mBackLayout.setVisibility(View.VISIBLE);
//		mTitleTv.setText(R.string.main_data);
//		
//		mWatchAdapter = new WatchAdapter(mContext, listDevice);
//		mListView.setAdapter(mWatchAdapter);
//		
//		mGroup = MyApplication.getInstance().getGroupDaoInface().viewGroup("iscurrent = ?", new String[] { "1" });
//		mPerson = MyApplication.getInstance().getPersonDaoInface().viewPerson(null, null);
//		//是否为管理员
//		try {
//			if ((mGroup.mOwnerId).equals(mPerson.mId)) {
//				mIsManager = true;
//			} else {
//				mIsManager = false;
//			}
//		} catch (Exception e) {
//			mIsManager = false;
//		}
//
//	}
//
//	private void setListener() {
//		mBackLayout.setOnClickListener(this);
//		mWatchLayout.setOnClickListener(this);
//		mStateLayout.setOnClickListener(this);
//		mHeartRateLayout.setOnClickListener(this);
//		mPedometerLayout.setOnClickListener(this);
//		mSleepLayout.setOnClickListener(this);
//		mBloodPressureLayout.setOnClickListener(this);
//		mBloodGlucoseLayout.setOnClickListener(this);
//		mBloodOxygenLayout.setOnClickListener(this);
//		mDialBtn.setOnClickListener(this);
//		
//		mDrawerLayout.setDrawerListener(new DrawerListener() {
//			
//			@Override
//			public void onDrawerStateChanged(int arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onDrawerSlide(View arg0, float arg1) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onDrawerOpened(View arg0) {
//				// TODO Auto-generated method stub
//			}
//			
//			@Override
//			public void onDrawerClosed(View arg0) {
//				// TODO Auto-generated method stub
//			}
//		});
//		
//		mListView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//				
//				mDrawerLayout.closeDrawer(mMenuLayout);
//				
//				mDevice = listDevice.get(position);
//				
//				String name = mDevice.getName();
//				if (TextUtils.isEmpty(name)) {
//					name = mDevice.getSim_phone();
//				}
//				mWatchNameTv.setText(name);
//				
//				// 更新DB
//				ContentValues cv = new ContentValues();
//				cv.put("iscurrent", 0);
//				boolean a = MyApplication.getInstance().getDeviceDaoInface().updateDevice(cv, null, null);
//
//				ContentValues cv2 = new ContentValues();
//				cv2.put("iscurrent", 1);
//				boolean b = MyApplication.getInstance().getDeviceDaoInface().updateDevice(cv2, "_id = ?", new String[] { mDevice.mId });
//
//				mDevice = MyApplication.getInstance().getDeviceDaoInface().viewDevice("iscurrent = ?", new String[] { "1" });
//				getDeviceInfo();
//				getDeviceMainData();
//				
//			}
//		});
//
//		// 设置下拉刷新监听
//		mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
//			@Override
//			public void onRefresh() {
//				getGroupDevices();
//			}
//		});
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.lay_go_back:
//			onBackPressed();
//			break;
//		case R.id.watch_name_layout:
//			//mDrawerLayout.openDrawer(mMenuLayout);
//			showUsersPopupWindow();
//			break;
//		case R.id.watch_state_layout:
//			if (mDevice != null && mDevice.getType().equals("BY102_NO_HR")) {
//				
//			} else {
//				refreshState();
//			}
//			break;
//		case R.id.heart_rate_layout:
//			if (mDevice != null && mDevice.getType().equals("BY102_NO_HR")) {
//				showTipsDialog("您的设备是标准版,不具备心率测量功能,如有需要,请购买其他版本。");
//			} else {
//				Bundle heartRateBundle = new Bundle();
//				heartRateBundle.putSerializable("device", mDevice);
//				redictToActivity(mContext, HeartRateActivity.class, heartRateBundle);
//			}
//			break;
//		case R.id.pedometer_layout:
//			Bundle bundle = new Bundle();
//			bundle.putSerializable("device", mDevice);
//			redictToActivity(mContext, PedometerActivity.class, bundle);
//			break;
//		case R.id.sleep_layout:
//			Bundle sleepBundle = new Bundle();
//			sleepBundle.putSerializable("device", mDevice);
//			redictToActivity(mContext, SleepActivity.class, sleepBundle);
//			break;
//		case R.id.blood_pressure_layout:
//			Bundle bloodPressureBundle = new Bundle();
//			bloodPressureBundle.putSerializable("device", mDevice);
//			redictToActivity(mContext, BloodPressureActivity.class, bloodPressureBundle);
//			break;
//		case R.id.blood_glucose_layout:
////			Bundle glucoseBundle = new Bundle();
////			glucoseBundle.putSerializable("device", mDevice);
////			redictToActivity(mContext, BloodGlucoseActivity.class, glucoseBundle);
//			break;
//		case R.id.blood_oxygen_layout:
//
//			break;
//		case R.id.data_dial_btn:
//			if (null == mDevice) {
//				ActivityUtil.showToast(mContext, "请选择设备");
//				return;
//			}
//			if (TextUtils.isEmpty(mDevice.getIccid2())) {
//				showDialTipsDialog("请给腕表插入新的SIM卡");
//			} else {
//				if (TextUtils.isEmpty(mDevice.getSim_phone())) {
//					ActivityUtil.showToast(mContext, "设备号码为空！");
//					//
//					if (mIsManager) {
//						Bundle phoneBundle = new Bundle();
//						phoneBundle.putSerializable("device", mDevice);
//						phoneBundle.putString("key", "sim_phone");
//						phoneBundle.putString("value", "");
//						phoneBundle.putString("tips", "设备SIM卡号");
//						redictToActivity(mContext, EditDeviceInfoActivity.class, phoneBundle);
//					}else {
//						showDialTipsDialog("请联系管理员设置腕表SIM卡号");
//					}
//
//				} else {
//					Intent intent = new Intent();
//					intent.setAction("android.intent.action.CALL");
//					intent.setData(Uri.parse("tel:" + mDevice.getSim_phone()));
//					startActivity(intent);
//				}
//			}
//			
//			break;
//		default:
//			break;
//		}
//
//	}
//	
//	@Override
//	protected void onResume() {
//		super.onResume();
//		
//		listDevice.clear();
//		listDevice.addAll(MyApplication.getInstance().getDeviceDaoInface().listDevice(null, null));
//		
//		mDevice = MyApplication.getInstance().getDeviceDaoInface().viewDevice("iscurrent = ?", new String[] { "1" });
//		if (null != mDevice) {
//			String name = mDevice.getName();
//			if (TextUtils.isEmpty(name)) {
//				name = mDevice.getSim_phone();
//			}
//			mWatchNameTv.setText(name);
//		} else {
//			mWatchNameTv.setText("暂没绑定腕表");
//		}
//		
//		getDeviceInfo();
//		getDeviceMainData();
//	}
//	
//	@Override
//	protected void onPause() {
//		super.onPause();
//		mStateProgressBar.setVisibility(View.INVISIBLE);
//		count = 0;
//		dataHandler.removeCallbacks(dataRunnable); // 停止Timer
//	}
//	
//	private void showDialTipsDialog(String message) {
//
//		mDialTipsDialog = new BaseDialog(mContext);
//		// mDialTipsDialog.setTitle("提示");
//		mDialTipsDialog.setMessage(message);
//		mDialTipsDialog.setTitleLineVisibility(View.INVISIBLE);
//		mDialTipsDialog.setButton2("确认", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				mDialTipsDialog.dismiss();
//			}
//		});
//		mDialTipsDialog.show();
//	}
//	
//	private void showUsersPopupWindow() {
//
//		LinearLayout layout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.popup_select_users, null);
//
//		GridView gridView = (GridView) layout.findViewById(R.id.gridView);
//
//		final List<Device> listname = MyApplication.getInstance().getDeviceDaoInface().listDevice("_id != ?", new String[] { "" });
//		gridView.setAdapter(new LocusUsersAdapter(mContext, listname));
//
//		gridView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//				// TODO
//				mDevice = listname.get(position);
//				
//				String name = mDevice.getName();
//				if (TextUtils.isEmpty(name)) {
//					name = mDevice.getSim_phone();
//				}
//				mWatchNameTv.setText(name);
//				
//				// 更新DB
//				ContentValues cv = new ContentValues();
//				cv.put("iscurrent", 0);
//				boolean a = MyApplication.getInstance().getDeviceDaoInface().updateDevice(cv, null, null);
//
//				ContentValues cv2 = new ContentValues();
//				cv2.put("iscurrent", 1);
//				boolean b = MyApplication.getInstance().getDeviceDaoInface().updateDevice(cv2, "_id = ?", new String[] { mDevice.mId });
//
//				mDevice = MyApplication.getInstance().getDeviceDaoInface().viewDevice("iscurrent = ?", new String[] { "1" });
//				getDeviceInfo();
//				getDeviceMainData();
//				
//				mUsersPopWin.dismiss();
//			}
//		});
//
//		mUsersPopWin = new PopupWindow(layout, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
//		mUsersPopWin.setBackgroundDrawable(new BitmapDrawable());
//		mUsersPopWin.showAsDropDown(mWatchLayout);
//		mUsersPopWin.update();
////		backgroundAlpha(0.5f);
////		mUsersPopWin.setOnDismissListener(new OnDismissListener() {
////			
////			@Override
////			public void onDismiss() {
////				backgroundAlpha(1f);
////			}
////		});
//
//	}
//	
//	@Override // 关闭侧滑页面
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			if (mDrawerLayout.isDrawerOpen(mMenuLayout)) {
//				mDrawerLayout.closeDrawer(mMenuLayout);
//			} else {
//				return super.onKeyDown(keyCode, event);
//			}
//		}
//		return true;
//	}
//	
//	private void getGroupDevices() {
//
//		if (mGroup == null || TextUtils.isEmpty(mGroup.mId)) {
//			mSwipeRefreshLayout.setRefreshing(false);
//			ActivityUtil.showToast(mContext, "没有选择家庭圈");
//			return;
//		}
//
//		// showLoadingDialog("载入设备中...");
//		GroupApi.getInstance(mContext).getGroupDevices(mGroup.mId, new HttpCallback() {
//			@Override
//			public void onError(String error) {
//				mSwipeRefreshLayout.setRefreshing(false);
//				dismissLoadingDialog();
//				ActivityUtil.showToast(mContext, error);
//			}
//
//			@Override
//			public void onComplete(BaseMessage result) {
//				mSwipeRefreshLayout.setRefreshing(false);
//				dismissLoadingDialog();
//				if (result.isSuccess()) {
//
//					// 更新DB
//					listDevice.clear();
//					try {
//						listDevice.addAll((ArrayList<Device>) result.getResultList("Device"));
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//
//					MyApplication.getInstance().getDeviceDaoInface().clearDeviceTable();
//					for (Device device : listDevice) {
//						MyApplication.getInstance().getDeviceDaoInface().addDevice(device);
//					}
//
//					// 恢复默认查看腕表
//					ContentValues cv = new ContentValues();
//					cv.put("iscurrent", 1);
//					if (mDevice == null || TextUtils.isEmpty(mDevice.mId)) {// 取第一个腕表，当默认查看腕表
//						Device device = MyApplication.getInstance().getDeviceDaoInface().viewDevice("_id != ?", new String[] { "" });
//						if (null != device && !TextUtils.isEmpty(device.mId)) {
//							MyApplication.getInstance().getDeviceDaoInface().updateDevice(cv, "_id = ?", new String[] { device.mId });
//						}
//					} else {
//						MyApplication.getInstance().getDeviceDaoInface().updateDevice(cv, "_id = ?", new String[] { mDevice.mId });
//					}
//
//					mWatchAdapter.notifyDataSetChanged();
//
//				} else {
//					ActivityUtil.showToast(mContext, result.getError_desc());
//				}
//			}
//		});
//
//	}
//
//
//	/* 获取设备信息数据 */
//	private void getDeviceInfo() {
//
//		if (mDevice == null || TextUtils.isEmpty(mDevice.mId)) {
//			return;
//		}
//
//		//showLoadingDialog("加载中...");
//		DeviceApi.getInstance(mContext).getDeviceInfo(mDevice.mId, new HttpCallback() {
//
//			@Override
//			public void onError(String error) {
//				dismissLoadingDialog();
//
//			}
//
//			@Override
//			public void onComplete(BaseMessage result) {
//				dismissLoadingDialog();
//				if (result.isSuccess()) {
//					Device device = (Device) result.getResult("Device");
//					if (null != device) {
//						mDevice = device;
//						MyApplication.getInstance().getDeviceDaoInface().updateDevice(device, "_id = ?", new String[] { device.mId });
//						showDevice(device);
//					}
//				}
//
//			}
//			
//		});
//
//	}
//
//
//	/**
//	 * 获取设备睡眠数据
//	 * 
//	 */
//	private void getDeviceMainData() {
//
//		String deviceID = "";
//		try {
//			deviceID = mDevice.mId;
//		} catch (Exception e) {
//		}
//
//		if (deviceID == null || deviceID.length() == 0) {
//			return;
//		}
//
//		showLoadingDialog("加载中...");
//		DeviceApi.getInstance(mContext).getDeviceMainData(TimeUtils.getDateStr("yyyyMMdd"), deviceID, new HttpCallback() {
//		//DeviceApi.getInstance(mContext).getDeviceMainData("20150512", "565999999999998", new HttpCallback() {
//
//			@Override
//			public void onError(String error) {
//				dismissLoadingDialog();
//			}
//
//			@Override
//			public void onComplete(BaseMessage result) {
//				dismissLoadingDialog();
//
//				Sleepdata sleepdata = null;
//				Posturedata posturedata = null;
//				Bloodpressuredata bloodpressuredata = null;
//				Heartratedata heartratedata = null;
//				
//				
//				if (result.isSuccess()) {
//					try {
//						String resultSrc = result.getResultSrc();
//						JSONObject jo = new JSONObject(resultSrc);
//						
//						try {
//							JSONObject sleepJo = jo.getJSONObject("sleepdata");
//							sleepdata = handleSleepData(ParserServer.paserSleepdata((Sleepdata) JsonUtil.json2model(HttpUtil.model_package + "Sleepdata", sleepJo)));
//						} catch (Exception e) {
//						}
//
//						try {
//							JSONObject postureJo = jo.getJSONObject("pedometerdata");
//							posturedata = ParserServer.paserPosturedata((Posturedata) JsonUtil.json2model(HttpUtil.model_package + "Posturedata", postureJo));
//						} catch (Exception e) {
//						}
//
//						try {
//							JSONObject bloodpressureJo = jo.getJSONObject("bloodpressuredata");
//							bloodpressuredata = ParserServer.paserBloodpressuredata((Bloodpressuredata) JsonUtil.json2model(HttpUtil.model_package + "Bloodpressuredata", bloodpressureJo));
//						} catch (Exception e) {
//						}
//
//						try {
//							JSONObject heartrateJo = jo.getJSONObject("heartratedata");
//							heartratedata = ParserServer.paserHeartratedata((Heartratedata) JsonUtil.json2model(HttpUtil.model_package + "Heartratedata", heartrateJo));
//						} catch (Exception e) {
//						}
//						
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					
//					showdata(sleepdata, posturedata, bloodpressuredata, heartratedata);
//
//				} else {
//					showdata(null, null, null, null);
//				}
//
//			}
//		});
//
//	}
//	
//	private Sleepdata handleSleepData(Sleepdata data) {
//		if (data == null || (data!= null && data.mDatas == null)) {
//			return null;
//		}
//		int count = 0;// 连续出现0的个数。
//		for (int i = 0; i < data.mDatas.size(); i++) {
//			if ("2".equals(data.mDatas.get(i).getState()) && StringUtils.string2Int(data.mDatas.get(i).getTurn_over()) == 0) {
//				count++;
//				if (i == data.mDatas.size() -1) {//如果最后一位是0
//					if (count < 3) {
//						for (int j = i; j > i - count; j--) {
//							data.mDatas.get(j).setTurn_over("1");
//						}
//					}
//				}
//			} else {
//				if (count < 3) {
//					for (int j = i - 1; j >= i - count; j--) {
//						data.mDatas.get(j).setTurn_over("1");
//					}
//				}
//				count = 0;
//			}
//		}
//		return data;
//	}
//
//	protected void showdata(Sleepdata sleepdata, Posturedata posturedata, Bloodpressuredata bloodpressuredata, Heartratedata heartratedata) {
//		if (sleepdata != null) {
//			if (sleepdata.mDatas != null) {
//				int count = 0;
//				for (Sleepdatasleep sleepdatasleep : sleepdata.mDatas) {
//					if ("2".equals(sleepdatasleep.getState()) && StringUtils.string2Int(sleepdatasleep.getTurn_over()) > 0) {
//						count++;
//					}
//				}
//				mSleepValueTv.setText(String.format("%.1f", count * 0.5f));
//				if (count < 3) {
//					mSleepValueTv.setTextColor(getResources().getColor(R.color.data_color3));
//				}else {
//					mSleepValueTv.setTextColor(getResources().getColor(R.color.data_color1));
//				}
//				
//			}else {
//				mSleepValueTv.setText("--");
//				mSleepValueTv.setTextColor(getResources().getColor(R.color.data_color3));
//			}
//
//			mSleepTimeTv.setText(TimeUtils.date2Str(TimeUtils.jsonStr2StrDate(sleepdata.getTime_begin()),  "H:mm"));
//			
//		}else {
//			mSleepValueTv.setText("--");
//			mSleepValueTv.setTextColor(getResources().getColor(R.color.data_color3));
//			mSleepTimeTv.setText("--:--");
//		}
//		
//		if (posturedata != null) {
//			int stepValue = StringUtils.string2Int(posturedata.getValue());
//			if (stepValue > 9999) {
//				mPedometerValueTv.setTextSize(42);
//			}else {
//				mPedometerValueTv.setTextSize(58);
//			}
//			mPedometerValueTv.setText(TextUtils.isEmpty(posturedata.getValue()) ? "--" : stepValue + "");// getCount()
//			
//			int stepObjective = StringUtils.string2Int(mDevice.getStep_objective());
//			int perCent = 0;
//			try {
//				perCent = (int) (stepValue * 100 / (stepObjective * 1.0D));
//				
//			} catch (Exception e) {
//			}
//			if (perCent < 25 ) {
//				mPedometerValueTv.setTextColor(getResources().getColor(R.color.data_color3));
//			}else if (perCent <= 75) {
//				mPedometerValueTv.setTextColor(getResources().getColor(R.color.data_color2));
//			}else {
//				mPedometerValueTv.setTextColor(getResources().getColor(R.color.data_color1));
//			}
//			
//			mPedometerTimeTv.setText(TimeUtils.date2Str(TimeUtils.jsonStr2StrDate(posturedata.getTime_begin()),  "H:mm"));
//		}else {
//			mPedometerValueTv.setTextSize(ActivityUtil.dip2px(mContext, 30));
//			mPedometerValueTv.setText("--");
//			mPedometerValueTv.setTextColor(getResources().getColor(R.color.data_color3));
//			mPedometerTimeTv.setText("--:--");
//		}
//		
//		if (bloodpressuredata != null) {
//			int sbpValue = StringUtils.string2Int(bloodpressuredata.getSbp());
//			if (sbpValue >= 90 && sbpValue <= 140) {
//				mBloodPressureSbpValueTv.setTextColor(getResources().getColor(R.color.data_color1));
//			} else {
//				mBloodPressureSbpValueTv.setTextColor(getResources().getColor(R.color.data_color3));
//			}
//
//			int dbpValue = StringUtils.string2Int(bloodpressuredata.getDbp());
//			if (dbpValue >= 60 && dbpValue <= 90) {
//				mBloodPressureDbpValueTv.setTextColor(getResources().getColor(R.color.data_color1));
//			} else {
//				mBloodPressureDbpValueTv.setTextColor(getResources().getColor(R.color.data_color3));
//			}
//
//			mBloodPressureSbpValueTv.setText(TextUtils.isEmpty(bloodpressuredata.getSbp()) ? "--" : sbpValue + "");
//			mBloodPressureDbpValueTv.setText(TextUtils.isEmpty(bloodpressuredata.getDbp()) ? "--" : dbpValue + "");
//			mBloodPressureTimeTv.setText(TimeUtils.date2Str(TimeUtils.jsonStr2StrDate(bloodpressuredata.getTime_begin()), "H:mm"));
//		}else {
//			mBloodPressureSbpValueTv.setTextColor(R.drawable.selector_data_txt);
//			mBloodPressureDbpValueTv.setTextColor(R.drawable.selector_data_txt);
//			mBloodPressureSbpValueTv.setText("--");
//			mBloodPressureDbpValueTv.setText("--");
//			mBloodPressureTimeTv.setText("--:--");
//		}
//		
//		if (heartratedata != null) {
//			mHeartRateValueTv.setText(heartratedata.getHeartrate());
//			int heartRate = StringUtils.string2Int(heartratedata.getHeartrate());
//			if (heartRate < 60) {
//				mHeartRateValueTv.setTextColor(getResources().getColor(R.color.data_color2));
//			}else if (heartRate <= 100) {
//				mHeartRateValueTv.setTextColor(getResources().getColor(R.color.data_color1));
//			}else {
//				mHeartRateValueTv.setTextColor(getResources().getColor(R.color.data_color3));
//			}
//			mHeartRateTimeTv.setText(TimeUtils.date2Str(TimeUtils.jsonStr2StrDate(heartratedata.getTime_begin()),  "H:mm"));
//		}else {
//			mHeartRateValueTv.setText("--");
//			mHeartRateValueTv.setTextColor(getResources().getColor(R.color.data_color2));
//			mHeartRateTimeTv.setText("--:--");
//		}
//		
//	}
//	
//	private Handler dataHandler1 = new Handler();
//	private Runnable dataRunnable1 = new Runnable() {
//		public void run() {
//			getDeviceInfo2();
//		}
//
//	};
//	
//	private void refreshState(){
//		if (count > 0 && count < 7) {
//			ActivityUtil.showToast(mContext, "正在获取腕表状态...");
//			return;
//		}
//		
//		actionHeartrateDevice();
//	}
//	
//	//通过即时测量心率来获取最新的在线佩戴状态
//	private void actionHeartrateDevice() {
//
//		if (mDevice == null || TextUtils.isEmpty(mDevice.mId)) {
//			return;
//		}
//
//		//showLoadingDialog("请求中...");
//		DeviceApi.getInstance(mContext).actionHeartrateDevice(mDevice.mId, new HttpCallback() {
//
//			@Override
//			public void onError(String error) {
//				//dismissLoadingDialog();
//
//			}
//
//			@Override
//			public void onComplete(BaseMessage result) {
//				dismissLoadingDialog();
//				if (result.isSuccess()) {
//					// TODO
//					mStateProgressBar.setVisibility(View.VISIBLE);
//					dataHandler.postDelayed(dataRunnable, 100);// 开始Timer
//				} else {
//					ActivityUtil.showToast(mContext, result.getError_desc());
//				}
//
//			}
//		});
//
//	}
//	
//	/* 获取设备信息数据 (获取设备在线，佩戴状态专用)*/
//	private void getDeviceInfo2() {
//
//		if (mDevice == null || TextUtils.isEmpty(mDevice.mId)) {
//			mStateProgressBar.setVisibility(View.INVISIBLE);
//			return;
//		}
//		
//
//		count ++;
//		if (count < 7) {
//			dataHandler.postDelayed(dataRunnable, DATAINTERVAL);// 开始Timer
//		}else {
//			mStateProgressBar.setVisibility(View.INVISIBLE);
//			count = 0;
//			dataHandler.removeCallbacks(dataRunnable); // 停止Timer
//			return;
//		}
//
//		//showLoadingDialog("加载中...");
//		DeviceApi.getInstance(mContext).getDeviceInfo(mDevice.mId, new HttpCallback() {
//
//			@Override
//			public void onError(String error) {
//				//dismissLoadingDialog();
//
//			}
//
//			@Override
//			public void onComplete(BaseMessage result) {
//				//dismissLoadingDialog();
//				if (result.isSuccess()) {
//					Device device = (Device) result.getResult("Device");
//					if (null != device) {
//						mDevice = device;
//						MyApplication.getInstance().getDeviceDaoInface().updateDevice(device, "_id = ?", new String[] { device.mId });
//						
//						if ( mDevice != null && !mDevice.getWear_updated_at().equals(device.getWear_updated_at())) {
//							mStateProgressBar.setVisibility(View.INVISIBLE);
//							count = 0;
//							dataHandler.removeCallbacks(dataRunnable); // 停止Timer
//							showDevice(device);
//						}
//					}
//				}
//
//			}
//			
//		});
//
//	}
//	
//	private void showDevice(Device device) {
//		if (device == null) {
//			return;
//		}
//
//		if ("true".equals(device.getOnline())) {
//			mOnlineLayout.setVisibility(View.GONE);
//			if (device.getType().equals("BY102_NO_HR")) {
//				mNoWearLayout.setVisibility(View.GONE);
//				mWearLayout.setVisibility(View.VISIBLE);
//				mStateTv.setText("在线");
//			} else {
//				if ("1".equals(device.getWear_flag())) {
//					mNoWearLayout.setVisibility(View.VISIBLE);
//					mWearLayout.setVisibility(View.GONE);
//					mStateTv.setText("未佩戴");
//				} else {
//					mNoWearLayout.setVisibility(View.GONE);
//					mWearLayout.setVisibility(View.VISIBLE);
//					mStateTv.setText("已佩戴");
//				}
//			}
//		} else {
//			mNoWearLayout.setVisibility(View.GONE);
//			mWearLayout.setVisibility(View.GONE);
//			mOnlineLayout.setVisibility(View.VISIBLE);
//			mStateTv.setText("已离线");
//			
//		}
//		
//	}
//
//}
