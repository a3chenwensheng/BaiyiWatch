package com.baiyi.watch.locus;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.Polygon;
import com.amap.api.maps2d.model.PolygonOptions;
import com.amap.api.maps2d.overlay.PoiOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.dialog.BaseDialog;
import com.baiyi.watch.model.Device;
import com.baiyi.watch.model.DisplayLocationData;
import com.baiyi.watch.model.SettingFence;
import com.baiyi.watch.net.BaseMessage;
import com.baiyi.watch.net.DeviceApi;
import com.baiyi.watch.net.HttpCallback;
import com.baiyi.watch.net.ParserServer;
import com.baiyi.watch.utils.ActivityUtil;
import com.baiyi.watch.utils.StringUtils;
import com.baiyi.watch.widget.toggle.ToggleButton;
import com.baiyi.watch.widget.toggle.ToggleButton.OnToggleChanged;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import toasty.Toasty;

/**
 * 电子围栏（安全区域）Activity
 *
 * @author 陈文声
 * @version v2.0
 * @email a3chenwensheng@126.com
 * @date 2017-4-21 10:30
 */
public class FenceActivity extends BaseActivity implements OnClickListener, OnToggleChanged, AMap.OnMapClickListener{

	public static final int EDITVIEWSTATE = 0X01;
	public static final int EDITFENCESTATE = 0X03;
	private int mEditState = EDITVIEWSTATE;

	public static final int REQUESTCODE = 1;

	private static final int HOUR = 60 * 60;
	private static final int MINUTE = 60;

	private TextView mBackTv;// 返回
	private MapView mMapView;
	private AMap mAMap;
	private UiSettings mUiSettings;

	
	private LinearLayout mSettingsLayout;
	private Button mEditRangeBtn;
	private TextView mBeginTimeTv;
	private TextView mEndTimeTv;
	private ToggleButton mToggleButton;

	private DisplayLocationData mDisplayLocationData;

	private Device mDevice;

	private SettingFence mSettingFence;

	private BaseDialog mEditFenceTipsDialog;
	private BaseDialog mTooNearTipsDialog;// 电子围栏范围过小提示对话框
	//private RangeTimePickerDialog mRangeTimePickerDialog;// 选择时间范围对话框
	private Calendar mCalendar1 = Calendar.getInstance();
	private Calendar mCalendar2 = Calendar.getInstance();

	private PoiOverlay mPoiOverlay;
	private Marker mHomeMarker;
	private Polygon mPolygon;
	private List<LatLng> mFenceLatLngs = new ArrayList<LatLng>();
	private List<Marker> mFenceMarkers = new ArrayList<Marker>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fence);
		initView(savedInstanceState);
		initData();
		setListener();

		getDeviceInfo();
	}

	/** 初始化布局 */
	private void initView(Bundle savedInstanceState) {
		mBackTv = (TextView) findViewById(R.id.back_tv);

		mSettingsLayout = (LinearLayout) findViewById(R.id.fence_settings_layout);
		mEditRangeBtn = (Button) findViewById(R.id.edit_range_btn);
		mBeginTimeTv = (TextView) findViewById(R.id.fence_begin_time_tv);
		mEndTimeTv = (TextView) findViewById(R.id.fence_end_time_tv);
		mToggleButton = (ToggleButton) findViewById(R.id.fence_toggle);

		mMapView = (MapView) findViewById(R.id.bmapView);
		mMapView.onCreate(savedInstanceState);// 必须要写
		initMapView();// 初始化地图

	}

	private void initMapView() {
		if (mAMap == null) {
			mAMap = mMapView.getMap();
			mUiSettings = mAMap.getUiSettings();

			mUiSettings.setScaleControlsEnabled(true);// 设置地图默认的比例尺是否显示
			mUiSettings.setZoomGesturesEnabled(true);// 设置地图是否可以手势缩放大小
			mUiSettings.setZoomControlsEnabled(false);// 设置地图是否显示默认的缩放控制
			//mUiSettings.setTiltGesturesEnabled(false);// 设置地图是否可以倾斜
			//mUiSettings.setRotateGesturesEnabled(false);// 设置地图是否可以旋转

			mAMap.setOnMapClickListener(this);// 对amap添加单击地图事件监听器
			mAMap.moveCamera(CameraUpdateFactory.zoomTo(16));
			
			mDisplayLocationData = (DisplayLocationData) getIntent().getSerializableExtra("displayLocationData");
			if (mDisplayLocationData != null) {
				Double lat = null;
				Double lng = null;
				try {
					lat = StringUtils.string2Double(mDisplayLocationData.mLocationdata.mPoint.mCoordinates.get(1));
					lng = StringUtils.string2Double(mDisplayLocationData.mLocationdata.mPoint.mCoordinates.get(0));
				} catch (Exception e) {
				}

				if (lat != null && lng != null) {
					mAMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(lat, lng)));
				}

			}

		}

	}

	/** 初始化数据 */
	private void initData() {

		//mDevice = (Device) getIntent().getSerializableExtra("device");
		mDevice = MyApplication.getInstance().getDeviceDaoInface().viewDevice("iscurrent = ?", new String[] { "1" });

	}

	private void setListener() {
		mBackTv.setOnClickListener(this);

		mEditRangeBtn.setOnClickListener(this);
		mBeginTimeTv.setOnClickListener(this);
		mEndTimeTv.setOnClickListener(this);
		
		mToggleButton.setOnToggleChanged(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_tv:
			onBackPressed();
			break;
		case R.id.edit_range_btn:
			mEditState = EDITFENCESTATE;
			//mResetLayout.setVisibility(View.INVISIBLE);
			mSettingsLayout.setVisibility(View.GONE);
			
			for (Marker marker : mFenceMarkers) {
				marker.remove();
			}
			if (mPolygon != null) {
				mPolygon.remove();
			}

			mFenceLatLngs.clear();
			mFenceMarkers.clear();
			
			mAMap.clear();
			showEditFenceTipsDialog();
			
			//startActivityForResult(new Intent(mContext, PoiSearchActivity.class), REQUESTCODE);
			break;
		case R.id.fence_begin_time_tv:
		case R.id.fence_end_time_tv:
			showRangeTimePickerDialog();
			break;
		default:
			break;
		}
	}

	private void showMapClickInfo(LatLng latLng) {
		if (mHomeMarker != null) {
			// mHomeMarker.hideInfoWindow();
			mHomeMarker.remove();
		}

		MarkerOptions markerOption = new MarkerOptions();
		markerOption.position(latLng);
		markerOption.draggable(true);
		markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_location));

		mHomeMarker = mAMap.addMarker(markerOption);
		// 动画移动到中心点
		mAMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
	}

	@Override
	public void onMapClick(LatLng latLng) {

		if (mEditState == EDITFENCESTATE) {
			if (mFenceLatLngs.size() >= 4) {
				//mFenceSaveLayout.setVisibility(View.GONE);
				for (Marker marker : mFenceMarkers) {
					marker.remove();
				}
				if (mPolygon != null) {
					mPolygon.remove();
				}

				mFenceLatLngs.clear();
				mFenceMarkers.clear();

				return;
			}
			
			
			for (LatLng latLng2 : mFenceLatLngs) {
				if (AMapUtils.calculateLineDistance(latLng, latLng2) < 300) {
					ActivityUtil.showToast(mContext, "区域内不能点击");
					return;
				}
				
				if (AMapUtils.calculateLineDistance(latLng, latLng2) > 5000) {
					ActivityUtil.showToast(mContext, "电子围栏距离过远");
					return;
				}
				
			}
			
			MarkerOptions markerOption = new MarkerOptions();
			markerOption.position(latLng);
			markerOption.draggable(true);
			markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_location));

			Marker marker = mAMap.addMarker(markerOption);
			mAMap.addCircle(new CircleOptions().center(latLng).radius(300).strokeColor(Color.argb(50, 250, 83, 4)).fillColor(Color.argb(30, 250, 83, 4)).strokeWidth(2));

			mFenceLatLngs.add(latLng);
			mFenceMarkers.add(marker);

			if (mFenceLatLngs.size() == 4) {
				//mResetLayout.setVisibility(View.VISIBLE);
				mSettingsLayout.setVisibility(View.VISIBLE);
				showRange(mFenceLatLngs);
			}
		}
	}

	private void showEditFenceTipsDialog() {

		mEditFenceTipsDialog = new BaseDialog(mContext);
		mEditFenceTipsDialog.setMessage("请点击地图取4个点设置安全围栏（建议两点间距离大于300米）");
		mEditFenceTipsDialog.setTitleLineVisibility(View.INVISIBLE);
		mEditFenceTipsDialog.setButton2("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				mEditFenceTipsDialog.dismiss();
			}
		});
		mEditFenceTipsDialog.show();
	}

	private void showTooNearTipsDialog() {

		mTooNearTipsDialog = new BaseDialog(mContext);
		mTooNearTipsDialog.setMessage("电子围栏范围过小，建议两点间距离大于300米。请重绘");
		mTooNearTipsDialog.setTitleLineVisibility(View.INVISIBLE);
		mTooNearTipsDialog.setButton2("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				mAMap.clear();
				mFenceLatLngs.clear();
				mFenceMarkers.clear();
				mTooNearTipsDialog.dismiss();
			}
		});
		mTooNearTipsDialog.show();
	}

	
	private void showRangeTimePickerDialog() {
//		if (mRangeTimePickerDialog != null) {
//			mRangeTimePickerDialog.cancel();
//		}
//		mRangeTimePickerDialog = new RangeTimePickerDialog(mContext, mCalendar1, mCalendar2);
//		mRangeTimePickerDialog.setTitleLineVisibility(View.INVISIBLE);
//		mRangeTimePickerDialog.setTitle("请选择围栏时间范围");
//		mRangeTimePickerDialog.setButton("取消", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				mRangeTimePickerDialog.dismiss();
//			}
//		}, "确认", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//
//				int hour1 = mRangeTimePickerDialog.getHour1();
//				int min1 = mRangeTimePickerDialog.getMinute1();
//				int hour2 = mRangeTimePickerDialog.getHour2();
//				int min2 = mRangeTimePickerDialog.getMinute2();
//
//				mCalendar1.set(Calendar.HOUR_OF_DAY, hour1);
//				mCalendar1.set(Calendar.MINUTE, min1);
//				mCalendar2.set(Calendar.HOUR_OF_DAY, hour2);
//				mCalendar2.set(Calendar.MINUTE, min2);
//
//				//mFenceTimeTv.setText(mRangeTimePickerDialog.getTime());
//				mRangeTimePickerDialog.dismiss();
//
//				saveFencesTime((hour1 * HOUR + min1 * MINUTE) + "", (hour2 * HOUR + min2 * MINUTE) + "");
//
//			}
//		});
//		min2RangeTimePickerDialog.show();

	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mMapView.onPause();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);
	}
	
	@Override
	public void onToggle(boolean on, ToggleButton v) {
		switch (v.getId()) {

		case R.id.fence_toggle:
			editFences("enable", v);
			break;

		default:
			break;
		}
	}

	/* 获取设备信息数据 */
	private void getDeviceInfo() {

		if (mDevice == null || TextUtils.isEmpty(mDevice.mId)) {
			return;
		}

		//showLoadingDialog("加载中...");
		DeviceApi.getInstance(mContext).getDeviceInfo(mDevice.mId, new HttpCallback() {

			@Override
			public void onError(String error) {
				//dismissLoadingDialog();

			}

			@Override
			public void onComplete(BaseMessage result) {
				//dismissLoadingDialog();
				if (result.isSuccess()) {
					Device device = (Device) result.getResult("Device");
					if (null != device) {
						mSettingFence = device.mFences.get(0);
						showData(mSettingFence);
					}
				}

			}
		});

	}

	private void showData(SettingFence settingFence) {
		
		if (settingFence != null && settingFence.mSafearea != null && settingFence.mSafearea.mCoordinates == null) {
			//mResetLayout.performClick();
			mEditRangeBtn.performClick();
			return;
		}		
		
		mAMap.clear();
		mSettingsLayout.setVisibility(View.VISIBLE);

		if (null != settingFence) {
			//mFenceNameTv.setText(settingFence.getName());
			if ("true".equals(settingFence.getEnable())) {
				mToggleButton.setToggleOn();
			}else {
				mToggleButton.setToggleOff();
			}
			
			try {
				int hour1 = (StringUtils.string2Int(settingFence.getTime_begin())) / HOUR;
				int min1 = ((StringUtils.string2Int(settingFence.getTime_begin())) % HOUR) / MINUTE;
				int hour2 = (StringUtils.string2Int(settingFence.getTime_end())) / HOUR;
				int min2 = ((StringUtils.string2Int(settingFence.getTime_end())) % HOUR) / MINUTE;
				mCalendar1.set(Calendar.HOUR_OF_DAY, hour1);
				mCalendar1.set(Calendar.MINUTE, min1);
				mCalendar2.set(Calendar.HOUR_OF_DAY, hour2);
				mCalendar2.set(Calendar.MINUTE, min2);

				mBeginTimeTv.setText(((hour1 < 10) ? "0" : "") + hour1 + ":" + ((min1 < 10) ? "0" : "") + min1);
				mEndTimeTv.setText(((hour2 < 10) ? "0" : "") + hour2 + ":" + ((min2 < 10) ? "0" : "") + min2);
			
			} catch (Exception e) {
				mBeginTimeTv.setText("");
				mEndTimeTv.setText("");
			}

			List<LatLng> latLngs = new ArrayList<LatLng>();
			try {
				for (String str : settingFence.mSafearea.mCoordinates.get(0).mLatLgns) {
					Double lat = StringUtils.string2Double(ParserServer.paserStrings(str).get(1));
					Double lng = StringUtils.string2Double(ParserServer.paserStrings(str).get(0));
					LatLng latLng = new LatLng(lat, lng);
					latLngs.add(latLng);
				}
			} catch (Exception e) {
			}

			showRange(latLngs);
		}

	}

	/** 地图标记范围 */
	private void showRange(List<LatLng> latLngs) {
		if (latLngs != null && latLngs.size() > 2) {

			List<LatLng> showLatlngs = null;
			try {
				showLatlngs = ActivityUtil.convexHull(latLngs);
				mPolygon = mAMap.addPolygon(new PolygonOptions().addAll(showLatlngs).strokeColor(Color.argb(80, 250, 83, 4)).fillColor(Color.argb(50, 250, 83, 4)).strokeWidth(2));
				
			} catch (Exception e) {
			}

			if (mEditState == EDITVIEWSTATE) {
//				LatLng latLng = new LatLng(latLngs.get(0).latitude, (latLngs.get(0).longitude + latLngs.get(2).longitude) / 2.0d);
//				mAMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
				List<PoiItem> poiItems = new ArrayList<PoiItem>();
				for (LatLng latLng : latLngs) {
					LatLonPoint latLonPoint = new LatLonPoint(latLng.latitude, latLng.longitude);
					PoiItem poiItem = new PoiItem(null, latLonPoint, null, null);
					poiItems.add(poiItem);
				}
				mPoiOverlay = new PoiOverlay(mAMap, poiItems);
				mPoiOverlay.zoomToSpan();
				//mAMap.moveCamera(CameraUpdateFactory.zoomOut());
				//mAMap.moveCamera(CameraUpdateFactory.zoomTo(mAMap.getCameraPosition().zoom));
			}

			if (mEditState == EDITFENCESTATE) {
				boolean flag = true;
				try {
					if (showLatlngs != null && showLatlngs.size() > 1) {
						for (int i = 0; i < showLatlngs.size(); i++) {
							for (int j = i + 1; j < showLatlngs.size(); j++) {
								if (AMapUtils.calculateLineDistance(showLatlngs.get(i), showLatlngs.get(j)) < 300) {
									flag = false;
									break;
								}
							}
						}

					}

				} catch (Exception e) {
				}

				if (flag) {
					saveFencesSafeArea();
				} else {
					showTooNearTipsDialog();
					//mFenceSaveLayout.setVisibility(View.GONE);
				}
			}

		}
	}

	private void saveFencesSafeArea() {
		//
		List<LatLng> list = ActivityUtil.convexHull(mFenceLatLngs);
		String value = "";
		try {

			for (LatLng latLng : list) {
				if (!"".equals(value)) {
					value += ";";
				}
				value += (latLng.longitude + "," + latLng.latitude);
			}
			// 首尾要高置相同
			value += (";" + value.split("\\;")[0]);
		} catch (Exception e) {
		}

		editFences("safe_area", value);

	}

	/**
	 * 设置安全区域
	 * 
	 * @param key
	 * @param value
	 */
	private void editFences(String key, String value) {

		if (mDevice == null || TextUtils.isEmpty(mDevice.mId)) {
			return;
		}

		//showLoadingDialog("请稍候...");
		DeviceApi.getInstance(mContext).editFences(mDevice.mId, "1", key, value, new HttpCallback() {

			@Override
			public void onError(String error) {
				//dismissLoadingDialog();

			}

			@Override
			public void onComplete(BaseMessage result) {
				//dismissLoadingDialog();
				if (result.isSuccess()) {
					//ActivityUtil.showToast(mContext, "围栏区域设置成功,请设置时间范围和开关", 1);
					//showAlerTipsDialog("提示", "围栏区域设置成功,请设置时间范围和开关");
					mEditState = EDITVIEWSTATE;
					//mResetLayout.setVisibility(View.VISIBLE);
					mSettingsLayout.setVisibility(View.VISIBLE);
					getDeviceInfo();
				}

			}
		});

	}
	
	/**
	 * 设置安全区域(修改开关)
	 * @param key
	 * @param view
	 */
	private void editFences(String key, final View view) {

		if (mDevice == null || TextUtils.isEmpty(mDevice.mId)) {
			((ToggleButton) view).toggle2();
			return;
		}

		//showLoadingDialog("请稍候...");
		DeviceApi.getInstance(mContext).editFences(mDevice.mId, "1", key, ((ToggleButton) view).isChecked() ? "1" : "0", new HttpCallback() {

			@Override
			public void onError(String error) {
				//dismissLoadingDialog();
				((ToggleButton) view).toggle2();
			}

			@Override
			public void onComplete(BaseMessage result) {
				//dismissLoadingDialog();
				if (result.isSuccess()) {
					Toasty.success(mContext,"设置成功").show();
					mEditState = EDITVIEWSTATE;
					//mResetLayout.setVisibility(View.VISIBLE);
					mSettingsLayout.setVisibility(View.VISIBLE);
					getDeviceInfo();
				} else {
					Toasty.error(mContext,result.getError_desc()).show();
					((ToggleButton) view).toggle2();
				}

			}
		});

	}

	/**
	 * 设置安全区域时间范围
	 * 
	 * @param value
	 * @param value2
	 */
	private void saveFencesTime(String value, String value2) {

		if (mDevice == null || TextUtils.isEmpty(mDevice.mId)) {
			return;
		}

		//showLoadingDialog("请求中...");
		DeviceApi.getInstance(mContext).editFencesTime(mDevice.mId, "1", value, value2, new HttpCallback() {

			@Override
			public void onError(String error) {
				//dismissLoadingDialog();

			}

			@Override
			public void onComplete(BaseMessage result) {
				//dismissLoadingDialog();
				if (result.isSuccess()) {
					Toasty.success(mContext,"时间段设置成功").show();
					mEditState = EDITVIEWSTATE;
					//mResetLayout.setVisibility(View.VISIBLE);
					mSettingsLayout.setVisibility(View.VISIBLE);
					getDeviceInfo();
				}

			}
		});

	}
	
	/**
	 * 获取设备最新位置数据
	 * 
	 */
	private void getDeviceLocationData() {

		// mAMap.clear();// 清除地图上所有标记

		if (mDevice == null || TextUtils.isEmpty(mDevice.mId)) {
			return;
		}

		// showLoadingDialog("请求中...");
		DeviceApi.getInstance(this).getDeviceLocationData(mDevice.mId, new HttpCallback() {
			@Override
			public void onError(String error) {
				 //dismissLoadingDialog();
			}

			@Override
			public void onComplete(BaseMessage result) {
				// dismissLoadingDialog();
				if (result.isSuccess()) {

					DisplayLocationData displayLocationData = null;
					try {
						displayLocationData = ParserServer.paserDisplayLocationData(result.getResultSrc());
					} catch (Exception e) {
					}

					mDisplayLocationData = displayLocationData;
					
					if (mDisplayLocationData != null) {
						Double lat = null;
						Double lng = null;
						try {
							lat = StringUtils.string2Double(mDisplayLocationData.mLocationdata.mPoint.mCoordinates.get(1));
							lng = StringUtils.string2Double(mDisplayLocationData.mLocationdata.mPoint.mCoordinates.get(0));
						} catch (Exception e) {
						}

						if (lat != null && lng != null) {
							
							showMapClickInfo(new LatLng(lat, lng));
						}

					}

				}
			}
		});

	}

}
