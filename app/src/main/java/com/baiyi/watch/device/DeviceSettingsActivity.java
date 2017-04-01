package com.baiyi.watch.device;

import android.content.ContentValues;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Device;
import com.baiyi.watch.model.Person;
import com.baiyi.watch.net.BaseMessage;
import com.baiyi.watch.net.DeviceApi;
import com.baiyi.watch.net.HttpCallback;
import com.baiyi.watch.utils.ActivityUtil;
import com.baiyi.watch.utils.StringUtils;
import com.baiyi.watch.widget.toggle.ToggleButton;

import toasty.Toasty;

/**
 * 设备设置Activity
 *
 * @author 陈文声
 * @version v4.0
 * @email a3chenwensheng@126.com
 * @date 2017-3-20 9:30
 */
public class DeviceSettingsActivity extends BaseActivity implements OnClickListener, ToggleButton.OnToggleChanged {

    private TextView mBackTv;// 返回
    private LinearLayout mNickNameLayout;
    private LinearLayout mPhoneLayout;
    private LinearLayout mImeiLayout;
    private RelativeLayout mFallLayout;
    private LinearLayout mPedometerLayout;
    private RelativeLayout mStepObjectiveLayout;
    private RelativeLayout mSleepTimeLayout;
    private LinearLayout mSleepLayout;
    private LinearLayout mTrackLayout;
    private LinearLayout mTrackFrequencyLayout;

    private LinearLayout mFrequencyHeartrateLayout;
    private LinearLayout mDeviceWifiLayout;
    private LinearLayout mSosDialCycleModeLayout;
    private LinearLayout mRestoreLayout;
    private TextView mNickNameTv;
    private TextView mPhoneTv;
    private TextView mIMEITv;
    private TextView mStepObjectiveTv;
    private TextView mSleepTimeTv;
    private TextView mFrequencyHeartrateTv;
    private TextView mThesholdHeartrateTv;
    private TextView mDeviceWifiTv;
    private TextView mSosDialCycleModeTv;
    private TextView mTrackFrequenceTv;
    private LinearLayout mProfileLayout;
    private LinearLayout mIncomingRestrictionLayout;
    private LinearLayout mBluetoothLayout;

    private Button mUnbindLayout;

    private ToggleButton mFallToggle;// 跌倒监测开关
    private ToggleButton mPedometerToggle;// 计步数据开关
    private ToggleButton mSleepToggle;// 睡眠数据开关
    private ToggleButton mHeartrateToggle;//心率开关
    private ToggleButton mTrackToggle;// 轨迹开关
    private ToggleButton mProfileToggle;// 响铃开关(静音)
    private ToggleButton mIncomingRestrictionToggle;// 防骚扰
    private ToggleButton mBluetoothToggle;// 蓝牙开关

    private Device mDevice;
    private Person mPerson;
    private boolean mIsManager = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_settings);
        initView();
        initData();
        setListener();

    }

    /**
     * 初始化布局
     */
    private void initView() {
        mBackTv = (TextView) findViewById(R.id.back_tv);

        mNickNameLayout = (LinearLayout) findViewById(R.id.device_name_layout);
        mPhoneLayout = (LinearLayout) findViewById(R.id.device_sim_layout);
        mImeiLayout = (LinearLayout) findViewById(R.id.device_imei_layout);
        mPedometerLayout = (LinearLayout) findViewById(R.id.watch_pedometer_layout);
        mStepObjectiveLayout = (RelativeLayout) findViewById(R.id.watch_step_objective_layout);
        mSleepLayout = (LinearLayout) findViewById(R.id.watch_sleep_layout);
        mSleepTimeLayout = (RelativeLayout) findViewById(R.id.watch_sleep_time_layout);
        mTrackLayout = (LinearLayout) findViewById(R.id.watch_track_layout);
        mTrackFrequencyLayout = (LinearLayout) findViewById(R.id.watch_track_frequency_layout);
        mFallLayout = (RelativeLayout) findViewById(R.id.watch_fall_layout);
        mFrequencyHeartrateLayout = (LinearLayout) findViewById(R.id.frequency_heartrate_layout);
        mSosDialCycleModeLayout = (LinearLayout) findViewById(R.id.sos_dial_cycle_mode_layout);
        mDeviceWifiLayout = (LinearLayout) findViewById(R.id.device_wifi_layout);
        mRestoreLayout = (LinearLayout) findViewById(R.id.restore_layout);
        mProfileLayout = (LinearLayout) findViewById(R.id.watch_profile_layout);
        mIncomingRestrictionLayout = (LinearLayout) findViewById(R.id.incoming_restriction_layout);
        mBluetoothLayout = (LinearLayout) findViewById(R.id.watch_bluetooth_layout);


        mNickNameTv = (TextView) findViewById(R.id.device_name_tv);
        mPhoneTv = (TextView) findViewById(R.id.device_sim_tv);
        mIMEITv = (TextView) findViewById(R.id.device_imei_tv);
        mStepObjectiveTv = (TextView) findViewById(R.id.step_objective_tv);
        mSleepTimeTv = (TextView) findViewById(R.id.sleep_time_tv);
        mTrackFrequenceTv = (TextView) findViewById(R.id.watch_track_frequency_tv);
        mFrequencyHeartrateTv = (TextView) findViewById(R.id.frequency_heartrate_tv);
        mThesholdHeartrateTv = (TextView) findViewById(R.id.theshold_heartrate_tv);
        mSosDialCycleModeTv = (TextView) findViewById(R.id.sos_dial_cycle_mode_tv);
        mDeviceWifiTv = (TextView) findViewById(R.id.device_wifi_tv);

        mPedometerToggle = (ToggleButton) findViewById(R.id.watch_pedometer_toggle);
        mSleepToggle = (ToggleButton) findViewById(R.id.watch_sleep_toggle);
        mHeartrateToggle = (ToggleButton) findViewById(R.id.watch_heartrate_toggle);
        mFallToggle = (ToggleButton) findViewById(R.id.watch_fall_toggle);
        mTrackToggle = (ToggleButton) findViewById(R.id.watch_track_toggle);
        mProfileToggle = (ToggleButton) findViewById(R.id.watch_profile_toggle);
        mIncomingRestrictionToggle = (ToggleButton) findViewById(R.id.incoming_restriction_toggle);
        mBluetoothToggle = (ToggleButton) findViewById(R.id.watch_bluetooth_toggle);
        mUnbindLayout = (Button) findViewById(R.id.device_unbind_btn);

    }

    /**
     * 初始化数据
     */
    private void initData() {

        mDevice = MyApplication.getInstance().getDeviceDaoInface().viewDevice("iscurrent = ?", new String[]{"1"});
        mPerson = MyApplication.getInstance().getPersonDaoInface().viewPerson(null, null);

        // 是否为管理员
        try {
            if ((mDevice.getGroup_ownerid()).equals(mPerson.mId)) {
                mIsManager = true;
            } else {
                mIsManager = false;
            }
        } catch (Exception e) {
            mIsManager = false;
        }

        if (mDevice != null) {
            String softWare = mDevice.getSoftware_version();
            if (TextUtils.isEmpty(softWare)) {
                softWare = "";
            } else {
                softWare = softWare.toLowerCase();
            }


        }


    }

    private void setListener() {
        mBackTv.setOnClickListener(this);

        mStepObjectiveLayout.setOnClickListener(this);
        mSleepTimeLayout.setOnClickListener(this);
        mTrackFrequencyLayout.setOnClickListener(this);
        mRestoreLayout.setOnClickListener(this);
        mFrequencyHeartrateLayout.setOnClickListener(this);
        mSosDialCycleModeLayout.setOnClickListener(this);
        mDeviceWifiLayout.setOnClickListener(this);
        mPedometerToggle.setOnToggleChanged(this);
        mSleepToggle.setOnToggleChanged(this);
        mHeartrateToggle.setOnToggleChanged(this);
        mFallToggle.setOnToggleChanged(this);
        mTrackToggle.setOnToggleChanged(this);
        mProfileToggle.setOnToggleChanged(this);
        mIncomingRestrictionToggle.setOnToggleChanged(this);
        mBluetoothToggle.setOnToggleChanged(this);
        mUnbindLayout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (!(v.getId() == R.id.back_tv)) {
            if (!mIsManager) {
                ActivityUtil.showToast(mContext, "只有管理员才能操作");
                if (v instanceof CompoundButton) {
                    ((CompoundButton) v).toggle();
                }

                return;
            }
        }
        switch (v.getId()) {
            case R.id.back_tv:
                onBackPressed();
                break;

            default:
                break;
        }

    }

    @Override
    public void onToggle(boolean on, ToggleButton v) {
        if (!mIsManager) {
            ActivityUtil.showToast(mContext, "只有管理员才能操作");
            if (v instanceof ToggleButton) {
                ((ToggleButton) v).toggle2();
            }

            return;
        }
        switch (v.getId()) {

            case R.id.watch_pedometer_toggle:
//                if (mFallToggle.isChecked() && !mPedometerToggle.isChecked()) {
//                    showPedometerTipsDialog("关闭计步监测功能，将同时关闭姿态异常监测功能", v);
//                } else {
//                    editDevice("pedometer_enable", v);
//                }
                break;
            case R.id.watch_sleep_toggle:
                editDevice("sleep_enable", v);
                break;
            case R.id.watch_heartrate_toggle:
                editDevice("heartrate_enable", v);
                break;
            case R.id.watch_track_toggle:
                editDevice("track_enable", v);
                break;
            case R.id.watch_fall_toggle:
//                if (!mPedometerToggle.isChecked() && mFallToggle.isChecked()) {
//                    showFallTipsDialog("开启姿态异常监测功能，将同时开启计步监测功能", v);
//                } else {
//                    editDevice("fall_enable", v);
//                }
                break;
            default:
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDeviceInfo();

    }

    /**
     * 获取设备信息数据
     */
    private void getDeviceInfo() {

        if (mDevice == null || TextUtils.isEmpty(mDevice.mId)) {
            return;
        }

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
                        // TODO
                        showData(device);
                    }
                } else {
                    ActivityUtil.showToast(mContext, result.getError_desc());
                }

            }
        });

    }

    private void showData(Device device) {
        // TODO
        if (null == device) {
            return;
        }

        mNickNameTv.setText(device.getName());
        mPhoneTv.setText(device.getSim_phone());
        mIMEITv.setText(device.mId);

        if ("true".equals(device.getPedometer_enable())) {
            mPedometerToggle.setToggleOn();
        } else {
            mPedometerToggle.setToggleOff();
        }

        if ("true".equals(device.getSleep_enable())) {
            mSleepToggle.setToggleOn();
        } else {
            mSleepToggle.setToggleOff();
        }

        if ("true".equals(device.getHeartrate_enable())) {
            mHeartrateToggle.setToggleOn();
        } else {
            mHeartrateToggle.setToggleOff();
        }

        if ("true".equals(device.getFall_enable())) {
            mFallToggle.setToggleOn();
        } else {
            mFallToggle.setToggleOff();
        }

        if ("true".equals(device.getTrack_enable())) {
            mTrackToggle.setToggleOn();
        } else {
            mTrackToggle.setToggleOff();
        }

        mTrackFrequenceTv.setText(device.getFrequency_location() + "分钟");
        mFrequencyHeartrateTv.setText(device.getFrequency_heartrate() + "分钟");
        int lowValue = StringUtils.string2Int(device.getTheshold_heartrate_l());
        int highValue = StringUtils.string2Int(device.getTheshold_heartrate_h());
        mThesholdHeartrateTv.setText(lowValue + "-" + highValue + "bpm");

        if (TextUtils.isEmpty(device.getIccid2())) {
            mSosDialCycleModeTv.setText("");
        } else {
            if ("1".equals(device.getSos_dial_cycle_times())) {
                mSosDialCycleModeTv.setText("模式一");
            } else if ("9".equals(device.getSos_dial_cycle_times())) {
                mSosDialCycleModeTv.setText("模式二");
            } else {
                mSosDialCycleModeTv.setText("");
            }
        }

        if (device != null) {
            String wifiName = device.getHome_wifi_ssid();
            if (TextUtils.isEmpty(wifiName) && device.mOwner != null) {
                try {
                    wifiName = device.mOwner.getHome_wifi().split("\\|")[1];
                } catch (Exception e) {
                }
            }

            mDeviceWifiTv.setText(wifiName);
        }

        mStepObjectiveTv.setText("每天" + device.getStep_objective() + "步");

        try {
            mSleepTimeTv.setText((device.getSleep_period_begin()).substring(8, 10) + ":" + (device.getSleep_period_begin()).substring(10, 12) + "-" + (device.getSleep_period_end()).substring(8, 10)
                    + ":" + (device.getSleep_period_end()).substring(10, 12));

        } catch (Exception e) {
            mSleepTimeTv.setText("");
        }



    }

    /**
     * 修改CheckBox
     *
     * @param key
     * @param view
     */
    private void editDevice(String key, final View view) {

        if (mDevice == null || TextUtils.isEmpty(mDevice.mId)) {
            ((ToggleButton) view).toggle2();
            return;
        }

        //showLoadingDialog("处理中...");

        String value = "";
        if (view.getId() == R.id.watch_profile_toggle) {
            value = ((ToggleButton) view).isChecked() ? "4" : "2";
        } else {
            value = ((ToggleButton) view).isChecked() ? "1" : "0";
        }
        DeviceApi.getInstance(mContext).editDevice(mDevice.mId, key, value, new HttpCallback() {

            @Override
            public void onError(String error) {
                //dismissLoadingDialog();
                ((ToggleButton) view).toggle2();
            }

            @Override
            public void onComplete(BaseMessage result) {
                //dismissLoadingDialog();
                if (result.isSuccess()) {
                    // TODO
                } else {
                    //ActivityUtil.showToast(mContext, result.getError_desc());
                    if ("109".equals(result.getError_code())) {
                        //showRenewDialog("提示", result.getError_desc());
                    } else {
                        Toasty.error(mContext, result.getError_desc()).show();
                    }
                    ((ToggleButton) view).toggle2();
                }

            }
        });

    }

    private void editDevice(final String key, final String value) {

        if (mDevice == null) {
            return;
        }

        //showLoadingDialog("处理中...");
        DeviceApi.getInstance(mContext).editDevice(mDevice.mId, key, value, new HttpCallback() {

            @Override
            public void onError(String error) {
                //dismissLoadingDialog();
            }

            @Override
            public void onComplete(BaseMessage result) {
                //dismissLoadingDialog();
                if (result.isSuccess()) {
                    // TODO
                    // DB
                    ContentValues cv = new ContentValues();
                    cv.put(key, value);
                    MyApplication.getInstance().getDeviceDaoInface().updateDevice(cv, "_id = ?", new String[]{mDevice.mId});

                    Toasty.success(mContext, "修改成功").show();
                    getDeviceInfo();

                } else {
                    //ActivityUtil.showToast(mContext, result.getError_desc(), 0);
                    if ("109".equals(result.getError_code())) {
                        //showRenewDialog("提示", result.getError_desc());
                    } else {
                        Toasty.error(mContext, result.getError_desc()).show();
                    }
                }

            }
        });

    }


}
