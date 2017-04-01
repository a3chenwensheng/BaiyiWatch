package com.baiyi.watch.ui;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.BaseFragment;
import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.device.ListDeviceActivity;
import com.baiyi.watch.locus.LocusActivity;
import com.baiyi.watch.model.Bloodpressuredata;
import com.baiyi.watch.model.Device;
import com.baiyi.watch.model.Group4Show;
import com.baiyi.watch.model.Heartratedata;
import com.baiyi.watch.model.Locationdata;
import com.baiyi.watch.model.Notification;
import com.baiyi.watch.model.Person;
import com.baiyi.watch.model.Posturedata;
import com.baiyi.watch.model.Sleepdata;
import com.baiyi.watch.model.Sleepdatasleep;
import com.baiyi.watch.net.BaseApi;
import com.baiyi.watch.net.BaseMessage;
import com.baiyi.watch.net.DeviceApi;
import com.baiyi.watch.net.HttpCallback;
import com.baiyi.watch.net.HttpUtil;
import com.baiyi.watch.net.JsonUtil;
import com.baiyi.watch.net.ParserServer;
import com.baiyi.watch.net.PersonApi;
import com.baiyi.watch.utils.StringUtils;
import com.baiyi.watch.utils.TimeUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import toasty.Toasty;

public class DataFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout mDeviceLayout;
    private LinearLayout mLocationLayout;
    private CircleImageView mDeviceAvatarImg;
    private TextView mDeviceNameTv;
    private TextView mIMEITv;
    private TextView mTypeTv;
    private TextView mLastAddressTv;
    private TextView mLastLocationTimeTv;
    private TextView mStepValueTv;
    private TextView mStepObjectiveTv;
    private TextView mHeartRateValueTv;
    private TextView mHeartRateStatusTv;
    private TextView mHeartRateTimeTv;
    private TextView mDeepSleepTv;
    private TextView mModerateSleepTv;
    private TextView mLightSleepTv;
    private TextView mBloodPressureValueTv;
    private TextView mBloodPressureStatusTv;
    private TextView mBloodPressureTimeTv;

    private CardView mPedometerCv;
    private CardView mHeartRateCv;
    private CardView mSleepCv;
    private CardView mBloodPressureCv;

    private List<Device> listDevice = new ArrayList<Device>();
    private Person mPerson;
    private Device mDevice;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragement_data, container, false);
        initView(view);
        initData();
        setListener();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // 相当于Fragment的onResume

        } else {
            // 相当于Fragment的onPause

        }
    }

    /**
     * 初始化布局
     */
    private void initView(View view) {

        mDeviceLayout = (LinearLayout) view.findViewById(R.id.device_layout);
        mLocationLayout = (LinearLayout) view.findViewById(R.id.location_layout);
        mDeviceAvatarImg = (CircleImageView) view.findViewById(R.id.device_avatar_imv);
        mDeviceNameTv = (TextView) view.findViewById(R.id.device_name_tv);
        mIMEITv = (TextView) view.findViewById(R.id.imei_tv);
        mTypeTv = (TextView) view.findViewById(R.id.type_tv);
        mLastAddressTv = (TextView) view.findViewById(R.id.last_address_tv);
        mLastLocationTimeTv = (TextView) view.findViewById(R.id.last_location_time_tv);
        mStepValueTv = (TextView) view.findViewById(R.id.step_value_tv);
        mStepObjectiveTv = (TextView) view.findViewById(R.id.step_objective_tv);
        mHeartRateValueTv = (TextView) view.findViewById(R.id.heart_rate_value_tv);
        mHeartRateStatusTv = (TextView) view.findViewById(R.id.heart_rate_status_tv);
        mHeartRateTimeTv = (TextView) view.findViewById(R.id.heart_rate_time_tv);
        mDeepSleepTv = (TextView) view.findViewById(R.id.deep_sleep_tv);
        mModerateSleepTv = (TextView) view.findViewById(R.id.moderate_sleep_tv);
        mLightSleepTv = (TextView) view.findViewById(R.id.light_sleep_tv);
        mBloodPressureValueTv = (TextView) view.findViewById(R.id.blood_pressure_value_tv);
        mBloodPressureStatusTv = (TextView) view.findViewById(R.id.blood_pressure_status_tv);
        mBloodPressureTimeTv = (TextView) view.findViewById(R.id.blood_pressure_time_tv);

        mPedometerCv = (CardView) view.findViewById(R.id.pedometer_layout);
        mHeartRateCv = (CardView) view.findViewById(R.id.heart_rate_layout);
        mSleepCv = (CardView) view.findViewById(R.id.sleep_layout);
        mBloodPressureCv = (CardView) view.findViewById(R.id.blood_pressure_layout);

    }

    /**
     * 初始化数据
     */
    private void initData() {
        refreshCurrentDevice();

        mPerson = MyApplication.getInstance().getPersonDaoInface().viewPerson(null, null);
        if (mPerson == null) {
            logout();
            return;
        }

        if (mDevice == null) {
            getAllDevices();// 加载所有设备
        }else {
            getDeviceMainData(true);
            getDeviceInfo();
        }


    }

    private void setListener() {
        mDeviceLayout.setOnClickListener(this);
        mLocationLayout.setOnClickListener(this);
        mPedometerCv.setOnClickListener(this);
        mHeartRateCv.setOnClickListener(this);
        mSleepCv.setOnClickListener(this);
        mBloodPressureCv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.location_layout:
                redictToActivity(mContext, LocusActivity.class,null);
                break;
            case R.id.device_layout:
                redictToActivity(mContext, ListDeviceActivity.class, null);
                break;
            default:
                break;

        }
    }

    /**
     * 更新当前设备
     */
    public void refreshCurrentDevice() {
        mDevice = MyApplication.getInstance().getDeviceDaoInface().viewDevice("iscurrent = ?", new String[] { "1" });

        if (mDevice != null) {
            String name = mDevice.getName();
            if (TextUtils.isEmpty(name)) {
                name = "无昵称";
            }
            mDeviceNameTv.setText(name);
            mIMEITv.setText(mDevice.mId);
            mTypeTv.setText(mDevice.getType());

            if (mDevice.mOwner != null && !TextUtils.isEmpty(mDevice.mOwner.getAvatar_url())) {
                String avtarUrl = mDevice.mOwner.getAvatar_url();
                if (!avtarUrl.contains("http")) {
                    avtarUrl = BaseApi.BASE_Url2 + mDevice.mOwner.getAvatar_url();
                }
                ImageLoader.getInstance().displayImage(avtarUrl, mDeviceAvatarImg, MyApplication.getInstance().getOptions(R.drawable.ic_avatar));
            }
        }else {
            mDeviceNameTv.setText("");
            mDeviceAvatarImg.setImageResource(R.drawable.ic_avatar);
        }

    }

    private void getAllDevices() {

        //showLoadingDialog("载入设备中...");
        PersonApi.getInstance(mContext).getAllDevices(mPerson.mId, new HttpCallback() {
            @Override
            public void onError(String error) {
                //dismissLoadingDialog();
                Toasty.error(mContext, error).show();
            }

            @Override
            public void onComplete(BaseMessage result) {
                //dismissLoadingDialog();
                if (result.isSuccess()) {

                    // 更新DB
                    ArrayList<Group4Show> group4ShowList = null;
                    try {
                        group4ShowList = ParserServer.paserGroup4Shows(result.getResultSrc());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

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

                    if (!listDevice.isEmpty()) {
                        // DB
                        MyApplication.getInstance().getDeviceDaoInface().clearDeviceTable();
                        for (Device device : listDevice) {
                            MyApplication.getInstance().getDeviceDaoInface().addDevice(device);
                        }

                        // 取第一个腕表，当默认查看腕表
                        Device device = MyApplication.getInstance().getDeviceDaoInface().viewDevice("_id != ?", new String[] { "" });
                        if (null != device && !TextUtils.isEmpty(device.mId)) {
                            ContentValues cv3 = new ContentValues();
                            cv3.put("iscurrent", 1);
                            MyApplication.getInstance().getDeviceDaoInface().updateDevice(cv3, "_id = ?", new String[] { device.mId });


                            // 更新Group DB
                            ContentValues cv1 = new ContentValues();
                            cv1.put("iscurrent", 0);
                            MyApplication.getInstance().getGroupDaoInface().updateGroup(cv1, null, null);

                            ContentValues cv2 = new ContentValues();
                            cv2.put("iscurrent", 1);
                            MyApplication.getInstance().getGroupDaoInface().updateGroup(cv2, "_id = ?", new String[] { device.getGroup_id() });
                        }

                        refreshCurrentDevice();
                        getDeviceMainData(true);
                        getDeviceInfo();

                    }else {
                        //showmDeviceTipsDialog();
                    }

                } else {
                    //showmDeviceTipsDialog();
                }
            }
        });
    }

    /**
     * 获取设备总览数据
     *
     */
    private void getDeviceMainData(boolean isShowLoading) {

        String deviceID = "";
        try {
            deviceID = mDevice.mId;
        } catch (Exception e) {
        }

        if (deviceID == null || deviceID.length() == 0) {
            //onLoad();
            return;
        }

        if (isShowLoading) {
            //showLoadingDialog("加载中...");
        }
        DeviceApi.getInstance(mContext).getDeviceMainData3(TimeUtils.getDateStr("yyyyMMdd"), deviceID, new HttpCallback() {

            @Override
            public void onError(String error) {
                //onLoad();
                //dismissLoadingDialog();
            }

            @Override
            public void onComplete(BaseMessage result) {
                //onLoad();
                //dismissLoadingDialog();

                Sleepdata sleepdata = null;
                Posturedata posturedata = null;
                Bloodpressuredata bloodpressuredata = null;
                Heartratedata heartratedata = null;
                Notification notification = null;

                Locationdata locationdata = null;

                if (result.isSuccess()) {
                    try {
                        String resultSrc = result.getResultSrc();
                        JSONObject jo = new JSONObject(resultSrc);

                        try {
                            if (jo.has("sleepdata")) {

                                JSONObject sleepJo = jo.getJSONObject("sleepdata");
                                sleepdata = handleSleepData(ParserServer.paserSleepdata((Sleepdata) JsonUtil.json2model(HttpUtil.model_package + "Sleepdata", sleepJo)));

                                if (sleepdata == null) {
                                    sleepdata = new Sleepdata();
                                }
                            }
                        } catch (Exception e) {
                        }

                        try {
                            if (jo.has("pedometerdata")) {
                                JSONObject postureJo = jo.getJSONObject("pedometerdata");
                                posturedata = ParserServer.paserPosturedata((Posturedata) JsonUtil.json2model(HttpUtil.model_package + "Posturedata", postureJo));
                            }
                        } catch (Exception e) {
                        }

                        try {
                            if (jo.has("bloodpressuredata")) {
                                JSONObject bloodpressureJo = jo.getJSONObject("bloodpressuredata");
                                bloodpressuredata = ParserServer.paserBloodpressuredata((Bloodpressuredata) JsonUtil.json2model(HttpUtil.model_package + "Bloodpressuredata", bloodpressureJo));

                                if (bloodpressuredata == null) {
                                    bloodpressuredata = new Bloodpressuredata();
                                }
                            }
                        } catch (Exception e) {
                        }

                        try {
                            if (jo.has("heartratedata")) {
                                JSONObject heartrateJo = jo.getJSONObject("heartratedata");
                                heartratedata = ParserServer.paserHeartratedata((Heartratedata) JsonUtil.json2model(HttpUtil.model_package + "Heartratedata", heartrateJo));

                                if (heartratedata == null) {
                                    heartratedata = new Heartratedata();
                                }

                            }
                        } catch (Exception e) {
                        }

                        try {
                            JSONObject notificationJo = jo.getJSONObject("notification");
                            notification = ParserServer.paserNotification((Notification) JsonUtil.json2model(HttpUtil.model_package + "Notification", notificationJo));
                        } catch (Exception e) {
                        }

                        try {
                            JSONArray locationdataJo = jo.getJSONArray("locationdata");
                            locationdata = ParserServer.paserLocationdatas(locationdataJo.toString()).get(0);
                        } catch (Exception e) {
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    showdata(sleepdata, posturedata, bloodpressuredata, heartratedata, notification, locationdata);

                } else {
                    showdata(null, null, null, null, null, null);
                    if ("109".equals(result.getError_code())) {
                        //TODO
                        //showAlerTipsDialog("提示", result.getError_desc());
                    }
                }

            }
        });

    }

    private Sleepdata handleSleepData(Sleepdata data) {
        if (data == null || (data!= null && data.mDatas == null)) {
            return null;
        }
        int count = 0;// 连续出现0的个数。
        for (int i = 0; i < data.mDatas.size(); i++) {
            if ("2".equals(data.mDatas.get(i).getState()) && StringUtils.string2Int(data.mDatas.get(i).getTurn_over()) == 0) {
                count++;
                if (i == data.mDatas.size() -1) {//如果最后一位是0
                    if (count < 3) {
                        for (int j = i; j > i - count; j--) {
                            data.mDatas.get(j).setTurn_over("1");
                        }
                    }
                }
            } else {
                if (count < 3) {
                    for (int j = i - 1; j >= i - count; j--) {
                        data.mDatas.get(j).setTurn_over("1");
                    }
                }
                count = 0;
            }
        }
        return data;
    }

    protected void showdata(Sleepdata sleepdata, Posturedata posturedata, Bloodpressuredata bloodpressuredata, Heartratedata heartratedata, Notification notification, Locationdata locationdata) {
        if (sleepdata != null) {
            mSleepCv.setVisibility(View.VISIBLE);
            if (sleepdata.mDatas != null) {
                int count1 = 0;
                int count2 = 0;
                int count3 = 0;
                for (Sleepdatasleep sleepdatasleep : sleepdata.mDatas) {
                    if ("2".equals(sleepdatasleep.getState()) && StringUtils.string2Int(sleepdatasleep.getTurn_over()) > 0) {
                        count1++;
                    }
                    if ("1".equals(sleepdatasleep.getState())) {
                        count2++;
                    }
                    if ("0".equals(sleepdatasleep.getState()) && StringUtils.string2Int(sleepdatasleep.getTurn_over()) != 0 && StringUtils.string2Int(sleepdatasleep.getTurn_over()) != 255) {
                        count3++;
                    }

                }
                mDeepSleepTv.setText(String.format("%.1f", count1 * 0.5f));
                mModerateSleepTv.setText(String.format("%.1f", count2 * 0.5f));
                mLightSleepTv.setText(String.format("%.1f", count3 * 0.5f));
            }else {
                mDeepSleepTv.setText("0");
                mModerateSleepTv.setText("0");
                mLightSleepTv.setText("0");
            }

            //mSleepTimeTv.setText(TimeUtils.date2Str(TimeUtils.jsonStr2StrDate(sleepdata.getTime_begin()),  "H:mm"));

        }else {
            mSleepCv.setVisibility(View.GONE);
            mDeepSleepTv.setText("0");
            mModerateSleepTv.setText("0");
            mLightSleepTv.setText("0");
        }

        if (posturedata != null) {
            int stepValue = StringUtils.string2Int(posturedata.getValue());
            mStepValueTv.setText(TextUtils.isEmpty(posturedata.getValue()) ? "--" : stepValue + "");// getCount()
            //mCalorieValueTv.setText(new BigDecimal(StringUtils.string2Float(posturedata.getCalorie()==null?"0":(posturedata.getCalorie()))).setScale(0, BigDecimal.ROUND_HALF_UP) + "");
            int stepObjective = StringUtils.string2Int(mDevice.getStep_objective());
            mStepObjectiveTv.setText("目标" + stepObjective + "步");

        }else {
            //GONE
        }

        if (bloodpressuredata != null) {
            mBloodPressureCv.setVisibility(View.VISIBLE);
            int sbpValue = StringUtils.string2Int(bloodpressuredata.getSbp());
            int dbpValue = StringUtils.string2Int(bloodpressuredata.getDbp());

            mBloodPressureValueTv.setText(dbpValue + " / " + sbpValue);

            if (dbpValue == 0 && sbpValue == 0) {
                mBloodPressureStatusTv.setText("无血压数据，需适配专用血压计");
                mBloodPressureTimeTv.setText("");
            }else {
                if (sbpValue >= 90 && sbpValue <= 140) {

                    if (dbpValue >= 60 && dbpValue <= 90) {
                        mBloodPressureStatusTv.setText("血压正常。");
                    } else {
                        mBloodPressureStatusTv.setText("血压异常。");
                    }

                } else {
                    mBloodPressureStatusTv.setText("血压异常。");
                }

                mBloodPressureTimeTv.setText(TimeUtils.date2Str(TimeUtils.jsonStr2StrDate(bloodpressuredata.getTime_begin()),  "yyyy年M月d日 HH:mm"));
            }

        }else {
            mBloodPressureValueTv.setText( "-- / --");
            mBloodPressureCv.setVisibility(View.GONE);
        }

        if (heartratedata != null) {
            mHeartRateCv.setVisibility(View.VISIBLE);
            int heartRate = StringUtils.string2Int(heartratedata.getHeartrate());
            mHeartRateValueTv.setText(heartRate + "");
            mHeartRateTimeTv.setText(TimeUtils.date2Str(TimeUtils.jsonStr2StrDate(heartratedata.getTime_begin()),  "HH:mm"));
            if (heartRate < 60) {
                mHeartRateStatusTv.setText("心率过缓");
            } else if (heartRate <= 100) {
                mHeartRateStatusTv.setText("心率正常");
            } else {
                mHeartRateStatusTv.setText("心率过速");
            }
        }else {
            mHeartRateCv.setVisibility(View.GONE);//GONE
        }

        if (notification != null) {
            if (TextUtils.isEmpty(notification.getContent())) {
                //mNotificationLayout.setVisibility(View.GONE);//GONE
                //mNotificationTipsTv.setVisibility(View.GONE);
               //mNotificationTv.setText("暂无消息");
            }else {
                //mNotificationLayout.setVisibility(View.VISIBLE);
                //mNotificationTipsTv.setVisibility(View.VISIBLE);
                //mNotificationTv.setText(notification.getContent());
            }

        }else {
            //mNotificationLayout.setVisibility(View.GONE);//GONE
            //mNotificationTipsTv.setVisibility(View.GONE);
            //mNotificationTv.setText("暂无消息");
        }

        if (locationdata != null) {

            String address = locationdata.getAddress();
            if ("0".equals(locationdata.getType())) {
                address += "(卫星定位)";
            } else if ("1".equals(locationdata.getType())) {
                address += "(网络定位)";
            }
            mLastAddressTv.setText(address);
            mLastLocationTimeTv.setText("更新于:" + TimeUtils.intervalTime3(TimeUtils.jsonStr2StrDate(locationdata.getTime_begin())));

            //showLocation(locationdata);
        }else {
            mLastAddressTv.setText("暂无位置数据");
            mLastLocationTimeTv.setText("");
            //mAMap.clear();
        }

    }

    /**
     * 获取设备信息数据(显示电量及在线状态)
     */
    private void getDeviceInfo() {

        if (mDevice == null || TextUtils.isEmpty(mDevice.mId)) {
            return;
        }

        //showLoadingDialog(getString(R.string.loading));
        DeviceApi.getInstance(mContext).getDeviceInfo(mDevice.mId, new HttpCallback() {

            @Override
            public void onError(String error) {
                //dismissLoadingDialog();

            }

            @Override
            public void onComplete(BaseMessage result) {
                //dismissLoadingDialog();
                if (result.isSuccess()) {
//                    Device device = (Device) result.getResult("Device");
//                    if (null != device) {
//                        // TODO
//                        int remain_power = StringUtils.string2Int(device.getRemaining_power());
//                        mBatteryView.setPower(remain_power);
//                        mBatteryTv.setText(device.getRemaining_power() + "%");
//
//                        if ("true".equals(device.getOnline())) {
//                            mDeviceAvatarImg.setSaturation(1);
//                        }else {
//                            mDeviceAvatarImg.setSaturation(0);
//                        }
//                    }else {
//                        mBatteryView.setPower(0);
//                        mBatteryTv.setText("");
//                        mDeviceAvatarImg.setSaturation(0);
//                    }
//                } else {
//                    ActivityUtil.showToast(mContext, result.getError_desc());
//                    mBatteryView.setPower(0);
//                    mBatteryTv.setText("");
//                    mDeviceAvatarImg.setSaturation(0);
                }

            }
        });

    }

}
