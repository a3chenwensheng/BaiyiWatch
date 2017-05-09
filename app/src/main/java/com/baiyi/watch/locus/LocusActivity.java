package com.baiyi.watch.locus;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.Circle;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Device;
import com.baiyi.watch.model.DisplayLocationData;
import com.baiyi.watch.model.Person;
import com.baiyi.watch.net.BaseApi;
import com.baiyi.watch.net.BaseMessage;
import com.baiyi.watch.net.DeviceApi;
import com.baiyi.watch.net.HttpCallback;
import com.baiyi.watch.net.ParserServer;
import com.baiyi.watch.utils.ActivityUtil;
import com.baiyi.watch.utils.StringUtils;
import com.baiyi.watch.utils.TimeUtils;
import com.baiyi.watch.widget.MarkerIconView;

import toasty.Toasty;

public class LocusActivity extends BaseActivity implements OnClickListener {

    private final int LOC_FLAG = 1;//定位
    private final int CANCEL_FLAG = 2;//取消

    private TextView mBackTv;// 返回
    private TextView mTitleTv;// 标题

    private MapView mMapView;// 地图控件
    private AMap mAMap;
    private UiSettings mUiSettings;
    private Circle mCircle;
    private MarkerOptions mMarkerOption;
    private Marker mMarker;

    private TextView mUpdateTimeTv;
    private TextView mAddressTv;
    private LinearLayout mLocLoadingLayout;

    private Device mDevice;
    private Person mPerson;
    private boolean mIsManager = false;

    private final int ACTIONINTERVAL = 2 * 60 * 1000;
    private final int DATAINTERVAL = 10 * 1000;

    private DisplayLocationData mDisplayLocationData = null;

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locus);
        initView(savedInstanceState);
        initData();
        setListener();

        getDeviceLocationDataOnce();
        actionDevice4LocationDataOnce();

    }

    /**
     * 初始化布局
     */
    private void initView(Bundle savedInstanceState) {
        mBackTv = (TextView) findViewById(R.id.back_tv);
        mTitleTv = (TextView) findViewById(R.id.tv_title);

        mUpdateTimeTv = (TextView) findViewById(R.id.update_time_tv);
        mAddressTv = (TextView) findViewById(R.id.address_tv);
        mLocLoadingLayout = (LinearLayout) findViewById(R.id.loc_loading_layout);


        mMapView = (MapView) findViewById(R.id.bmapView);
        mMapView.onCreate(savedInstanceState);// 必须要写

        initMapView();// 初始化地图
    }

    private void initMapView() {
        if (mAMap == null) {
            mAMap = mMapView.getMap();
            mUiSettings = mAMap.getUiSettings();

            // mUiSettings.setCompassEnabled(true);// 指北针
            mUiSettings.setScaleControlsEnabled(true);// 设置地图默认的比例尺是否显示
            mUiSettings.setZoomControlsEnabled(false);// 设置地图是否显示默认的缩放控制
            //mUiSettings.setTiltGesturesEnabled(false);// 设置地图是否可以倾斜
            //mUiSettings.setRotateGesturesEnabled(false);// 设置地图是否可以旋转

            // mAMap.setOnMarkerClickListener(this);// 添加点击marker监听事件
            // mAMap.setInfoWindowAdapter(this);// 添加显示infowindow监听事件
            mAMap.moveCamera(CameraUpdateFactory.zoomTo(17));

            mAMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(23.163858, 113.449672)));// 公司地址
            // mAMap.moveCamera(CameraUpdateFactory.changeLatLng(new
            // LatLng(39.908712, 116.397475)));// Mao's home

            initMarkerOption();
        }

    }

    private void initMarkerOption() {
        mMarkerOption = new MarkerOptions();
        setLocationMarkerOption();

    }

    private void setLocationMarkerOption() {
        //mMarkerOption.title("").snippet("");
        mMarkerOption.draggable(true);
        mMarkerOption.anchor(0.5f, 0.5f);
        mMarkerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_location));

    }


    /**
     * 初始化数据
     */
    private void initData() {

        refreshCurrentDevice();
        mPerson = MyApplication.getInstance().getPersonDaoInface().viewPerson(null, null);

        //是否为管理员
        try {
            if ((mDevice.getGroup_ownerid()).equals(mPerson.mId)) {
                mIsManager = true;
            } else {
                mIsManager = false;
            }
        } catch (Exception e) {
            mIsManager = false;
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
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();

        actionHandler.removeCallbacks(actionRunnable);// 停止Timer
        dataHandler.removeCallbacks(dataRunnable); // 停止Timer

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        // 以上两句必须重写

        refreshCurrentDevice();

    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
        // 以上两句必须重写


    }

    /**
     * 更新当前设备
     */
    public void refreshCurrentDevice() {
        mDevice = MyApplication.getInstance().getDeviceDaoInface().viewDevice("iscurrent = ?", new String[]{"1"});

    }

    /**
     * 清除当前所显示的设备定位数据
     */
    private void clearData() {
        mAMap.clear();
        mDisplayLocationData = null;
    }

    private Handler dataHandler = new Handler();
    private Runnable dataRunnable = new Runnable() {
        public void run() {
            getDeviceLocationData();
        }

    };

    private Handler actionHandler = new Handler();
    private Runnable actionRunnable = new Runnable() {
        public void run() {
            actionDevice4LocationData();
        }

    };

    /**
     * 发送定位指令
     */
    private void actionDevice4LocationData() {

        if (mDevice == null || TextUtils.isEmpty(mDevice.mId)) {
            return;
        }

        actionHandler.postDelayed(actionRunnable, ACTIONINTERVAL);// 开始Timer
        dataHandler.removeCallbacks(dataRunnable); // 停止Timer

        String action = "get_locationdata";
//		if ("S1".equals(mDevice.getType())) {
//			action = "get_locationdataonce";//S1黄手环定位指令
//		}

        mLocLoadingLayout.setVisibility(View.VISIBLE);
        DeviceApi.getInstance(mContext).actionDevice(mDevice.mId, action, new HttpCallback() {

            @Override
            public void onError(String error) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mLocLoadingLayout.setVisibility(View.GONE);
                    }
                }, 2000);
            }

            @Override
            public void onComplete(BaseMessage result) {
                if (result.isSuccess()) {
                    // ActivityUtil.showToast(mContext, "指令发送成功");
                    dataHandler.postDelayed(dataRunnable, DATAINTERVAL);// 开始Timer
                } else {
                    mLocLoadingLayout.setVisibility(View.GONE);
                    Toasty.error(mContext, result.getError_desc()).show();

                }

            }
        });

    }

    /**
     * 发送定位指令(加载一次)
     */
    private void actionDevice4LocationDataOnce() {

        if (mDevice == null || TextUtils.isEmpty(mDevice.mId)) {
            return;
        }

        String action = "get_locationdata";
        if ("S1".equals(mDevice.getType())) {
            action = "get_locationdataonce";// S1黄手环定位指令
        }

        mLocLoadingLayout.setVisibility(View.VISIBLE);
        DeviceApi.getInstance(mContext).actionDevice(mDevice.mId, action, new HttpCallback() {

            @Override
            public void onError(String error) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mLocLoadingLayout.setVisibility(View.GONE);
                    }
                }, 2000);
            }

            @Override
            public void onComplete(BaseMessage result) {
                if (result.isSuccess()) {
                    // ActivityUtil.showToast(mContext, "指令发送成功");
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            getDeviceLocationDataOnce();
                            mLocLoadingLayout.setVisibility(View.GONE);
                        }
                    }, 20 * 1000);
                } else {
                    mLocLoadingLayout.setVisibility(View.GONE);
                    if ("107".equals(result.getError_code())) {//107表示两分钟内限定发送指令
                        //ActivityUtil.showToast(mContext, result.getError_desc(), 0);
                    } else {
                        if (result.getError_desc().contains("设备不在线")) {
                            Toasty.warning(mContext, "警告：设备不在线\n●如果设备长时间不在线\n请立即重启设备").show();
                        } else {
                            Toasty.error(mContext, result.getError_desc()).show();
                        }

                    }
                }

            }
        });

    }

    /**
     * 获取设备最新位置数据
     */
    private void getDeviceLocationData() {

        // mAMap.clear();// 清除地图上所有标记

        if (mDevice == null || TextUtils.isEmpty(mDevice.mId)) {
            mLocLoadingLayout.setVisibility(View.GONE);
            return;
        }

        dataHandler.postDelayed(dataRunnable, DATAINTERVAL);// 开始Timer

        // showLoadingDialog("请求中...");
        DeviceApi.getInstance(this).getDeviceLocationData(mDevice.mId, new HttpCallback() {
            @Override
            public void onError(String error) {
                // dismissLoadingDialog();
                mLocLoadingLayout.setVisibility(View.GONE);
            }

            @Override
            public void onComplete(BaseMessage result) {
                // dismissLoadingDialog();
                mLocLoadingLayout.setVisibility(View.GONE);
                if (result.isSuccess()) {

                    DisplayLocationData displayLocationData = null;
                    try {
                        displayLocationData = ParserServer.paserDisplayLocationData(result.getResultSrc());
                    } catch (Exception e) {
                    }

                    mDisplayLocationData = displayLocationData;
                    showLocation(mDisplayLocationData);

                }
            }
        });

    }

    /**
     * 获取设备最新位置数据(加载一次)
     */
    private void getDeviceLocationDataOnce() {

        // mAMap.clear();// 清除地图上所有标记

        if (mDevice == null || TextUtils.isEmpty(mDevice.mId)) {
            return;
        }

        // showLoadingDialog("请求中...");
        DeviceApi.getInstance(this).getDeviceLocationData(mDevice.mId, new HttpCallback() {
            @Override
            public void onError(String error) {
                // dismissLoadingDialog();
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
                    showLocation(mDisplayLocationData);

                } else {
                    if ("109".equals(result.getError_code())) {
                        //TODO
                        //showRenewDialog("提示", result.getError_desc());
                        Toasty.error(mContext, result.getError_desc()).show();
                    } else {
                        ActivityUtil.showToast(mContext, result.getError_desc());
                    }
                }
            }
        });

    }

    private void showLocation(DisplayLocationData displayLocationData) {

        if (displayLocationData != null && displayLocationData.mLocationdata != null && displayLocationData.mLocationdata.mPoint != null) {

            // mUpdateTimeTv.setText("更新时间" +
            // TimeUtils.date2Str(TimeUtils.jsonStr2StrDate(displayLocationData.mLocationdata.getTime_begin()),
            // "yyyy.M.d HH:mm"));
            mUpdateTimeTv.setText("更新于:" + TimeUtils.intervalTime3(TimeUtils.jsonStr2StrDate(displayLocationData.mLocationdata.getTime_begin())));

            String address = "";
            try {
                address = displayLocationData.mLocationdata.getAddress();
            } catch (Exception e) {
            }

            String type = "";
            try {
                if ("0".equals(displayLocationData.mLocationdata.getType())) {
                    type = "(卫星定位)";
                } else if ("1".equals(displayLocationData.mLocationdata.getType())) {
                    type = "(网络定位)";
                }
            } catch (Exception e) {
            }

//			try {
//				type += (displayLocationData.mLocationdata.getGps_speed() + "km/h    ");
//				type += (displayLocationData.mLocationdata.getGps_count() + "个卫星");
//			} catch (Exception e) {
//			}

            mAddressTv.setText(address + type);

            Double lat = null;
            Double lng = null;
            try {
                lat = StringUtils.string2Double(displayLocationData.mLocationdata.mPoint.mCoordinates.get(1));
                lng = StringUtils.string2Double(displayLocationData.mLocationdata.mPoint.mCoordinates.get(0));
            } catch (Exception e) {
            }

            if (lat == null || lat == null) {
                return;
            }

            // 先清除之前Marker
            if (mMarker != null) {
                mMarker.remove();
            }
            if (mCircle != null) {
                mCircle.remove();
            }

            LatLng latLng = new LatLng(lat, lng);
            mMarkerOption.position(latLng);
            if (mDevice.mOwner != null && !TextUtils.isEmpty(mDevice.mOwner.getAvatar_url())) {
                String avtarUrl = mDevice.mOwner.getAvatar_url();
                if (!avtarUrl.contains("http")) {
                    avtarUrl = BaseApi.BASE_Url2 + mDevice.mOwner.getAvatar_url();
                }
                mMarkerOption.icon(BitmapDescriptorFactory.fromView(new MarkerIconView(mContext, avtarUrl)));
            }
            mMarker = mAMap.addMarker(mMarkerOption);

            int radius = 0;
            if ("0".equals(displayLocationData.mLocationdata.getType())) {
                radius = 50;
            } else if ("1".equals(displayLocationData.mLocationdata.getType())) {
                radius = 100;
            }
            mCircle = mAMap.addCircle(new CircleOptions().center(latLng).radius(radius).strokeColor(Color.argb(50, 250, 83, 4)).fillColor(Color.argb(30, 250, 83, 4)).strokeWidth(2));

            // postDelayed...mMarker.showInfoWindow();

            // 动画移动到中心点
            mAMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
        } else {
            mAddressTv.setText("无位置信息");
            mUpdateTimeTv.setText("");
            clearData();
        }
    }

}
