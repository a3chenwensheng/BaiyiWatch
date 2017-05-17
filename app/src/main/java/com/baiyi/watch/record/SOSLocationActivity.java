package com.baiyi.watch.record;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Falldata;
import com.baiyi.watch.model.Notification;
import com.baiyi.watch.model.Sosdata;
import com.baiyi.watch.utils.StringUtils;
import com.baiyi.watch.utils.TimeUtils;

import java.util.ArrayList;

/**
 * 
 * SOS位置Activity
 * 
 * @author 陈文声
 * @email a3chenwensheng@126.com
 * @date 2015-7-29 13:30
 * @version v2.0
 */
public class SOSLocationActivity extends BaseActivity implements OnClickListener {

	private TextView mBackTv;// 返回

	private MapView mMapView;// 地图控件
	private AMap mAMap;
	private UiSettings mUiSettings;
	
	private TextView mTimeTv;
	private TextView mContentTv;
	private TextView mAddressTv;

	private Sosdata mSosdata;
	private Falldata mFalldata;
	
	private Notification mNotification;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sos_location);
		initView(savedInstanceState);
		initData();
		setListener();

	}

	/** 初始化布局 */
	private void initView(Bundle savedInstanceState) {
		mBackTv = (TextView) findViewById(R.id.back_tv);
		
		mTimeTv = (TextView) findViewById(R.id.location_time_tv);
		mContentTv = (TextView) findViewById(R.id.location_content_tv);
		mAddressTv = (TextView) findViewById(R.id.location_address_tv);

		mMapView = (MapView) findViewById(R.id.bmapView);
		mMapView.onCreate(savedInstanceState);// 必须要写

		initMapView();// 初始化地图
	}

	private void initMapView() {
		if (mAMap == null) {
			mAMap = mMapView.getMap();
			mUiSettings = mAMap.getUiSettings();

			mUiSettings.setCompassEnabled(true);// 指北针
			mUiSettings.setScaleControlsEnabled(true);// 设置地图默认的比例尺是否显示
			mUiSettings.setZoomControlsEnabled(false);// 设置地图是否显示默认的缩放控制
			//mUiSettings.setTiltGesturesEnabled(true);// 设置地图是否可以倾斜
			//mUiSettings.setRotateGesturesEnabled(true);// 设置地图是否可以旋转
			
			//mAMap.setOnMarkerClickListener(this);// 添加点击marker监听事件
			//mAMap.setInfoWindowAdapter(this);// 添加显示infowindow监听事件
			mAMap.moveCamera(CameraUpdateFactory.zoomTo(17));
		}

	}

	/** 初始化数据 */
	private void initData() {
		mSosdata = (Sosdata) getIntent().getSerializableExtra("sosdata");
		mFalldata = (Falldata) getIntent().getSerializableExtra("falldata");
		mNotification = (Notification) getIntent().getSerializableExtra("notification");
		
		// TODO
		Double lat = null;
		Double lng = null;
		try {
			if (mSosdata != null) {
				lat = StringUtils.string2Double(mSosdata.mPoint.mCoordinates.get(1));
				lng = StringUtils.string2Double(mSosdata.mPoint.mCoordinates.get(0));
			} else if (mFalldata != null) {
				lat = StringUtils.string2Double(mFalldata.mPoint.mCoordinates.get(1));
				lng = StringUtils.string2Double(mFalldata.mPoint.mCoordinates.get(0));
			}
			
		} catch (Exception e) {
		}

		if (lat != null && lat != null) {
			LatLng latLng = new LatLng(lat, lng);
			MarkerOptions markerOption = new MarkerOptions();
			//markerOption.title("").snippet("");
			markerOption.draggable(true);
			markerOption.anchor(0.5f, 0.5f);
			ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
			giflist.add(BitmapDescriptorFactory.fromResource(R.mipmap.alert_1));
			giflist.add(BitmapDescriptorFactory.fromResource(R.mipmap.alert_2));
			giflist.add(BitmapDescriptorFactory.fromResource(R.mipmap.alert_3));
			giflist.add(BitmapDescriptorFactory.fromResource(R.mipmap.alert_4));
			giflist.add(BitmapDescriptorFactory.fromResource(R.mipmap.alert_5));
			giflist.add(BitmapDescriptorFactory.fromResource(R.mipmap.alert_6));
			markerOption.icons(giflist);
			markerOption.period(1);//设置多少帧刷新一次图片资源，Marker动画的间隔时间，值越小动画越快。
			markerOption.position(latLng);
			mAMap.addMarker(markerOption);
			mAMap.addCircle(new CircleOptions().center(latLng).radius(50).strokeColor(Color.argb(50, 250, 83, 4)).fillColor(Color.argb(30, 250, 83, 4)).strokeWidth(2));
			// 动画移动到中心点
			mAMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
		}
		
		if (mSosdata != null) {
			mTimeTv.setText(TimeUtils.date2Str(TimeUtils.jsonStr2StrDate(mSosdata.getCreated_at()), "yyyy-MM-dd HH:mm:ss"));
			mAddressTv.setText(mSosdata.getAddress());
		} else if (mFalldata != null) {
			mTimeTv.setText(TimeUtils.date2Str(TimeUtils.jsonStr2StrDate(mFalldata.getCreated_at()), "yyyy-MM-dd HH:mm:ss"));
			mAddressTv.setText(mFalldata.getAddress());
		}
		
		if (mNotification != null) {
			mContentTv.setVisibility(View.VISIBLE);
			mContentTv.setText(mNotification.getContent());
		}else {
			mContentTv.setVisibility(View.GONE);
		}
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mMapView.onResume();
		// 以上两句必须重写
	}

	@Override
	protected void onPause() {
		super.onPause();
		mMapView.onPause();
		// 以上两句必须重写
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);
	}

}
