package com.baiyi.watch.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.baiyi.watch.add.AddActivity;
import com.baiyi.watch.aqgs2.BaseFragment;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.device.DeviceSettingsActivity;
import com.baiyi.watch.locus.FenceActivity;
import com.baiyi.watch.record.SelectRecordActivity;
import com.baiyi.watch.remind.RemindWatchListActivity;
import com.baiyi.watch.renew.ServiceRecordActivity;

public class DevicesFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout mFenceLayout;
    private RelativeLayout mRemindLayout;
    private RelativeLayout mSosNumLayout;
    private RelativeLayout mRecordLayout;
    private LinearLayout mSettingsLayout;
    private LinearLayout mAddLayout;
    private LinearLayout mRenewLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragement_devices, container, false);
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
        mFenceLayout = (RelativeLayout) view.findViewById(R.id.fence_layout);
        mRemindLayout = (RelativeLayout) view.findViewById(R.id.remind_layout);
        mSosNumLayout = (RelativeLayout) view.findViewById(R.id.sos_num_layout);
        mRecordLayout = (RelativeLayout) view.findViewById(R.id.record_layout);
        mSettingsLayout = (LinearLayout) view.findViewById(R.id.device_settings_layout);
        mAddLayout = (LinearLayout) view.findViewById(R.id.device_add_layout);
        mRenewLayout = (LinearLayout) view.findViewById(R.id.device_renew_layout);

    }

    /**
     * 初始化数据
     */
    private void initData() {
    }

    private void setListener() {
        mFenceLayout.setOnClickListener(this);
        mRemindLayout.setOnClickListener(this);
        mSosNumLayout.setOnClickListener(this);
        mRecordLayout.setOnClickListener(this);
        mSettingsLayout.setOnClickListener(this);
        mAddLayout.setOnClickListener(this);
        mRenewLayout.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fence_layout:
                redictToActivity(mContext, FenceActivity.class, null);
                break;
            case R.id.remind_layout:
                redictToActivity(mContext, RemindWatchListActivity.class, null);
                break;
            case R.id.sos_num_layout:

                break;
            case R.id.record_layout:
                redictToActivity(mContext, SelectRecordActivity.class, null);
                break;
            case R.id.device_settings_layout:
                redictToActivity(mContext, DeviceSettingsActivity.class, null);
                break;
            case R.id.device_add_layout:
                redictToActivity(mContext, AddActivity.class, null);
                break;
            case R.id.device_renew_layout:
                redictToActivity(mContext, ServiceRecordActivity.class, null);
                break;
            default:
                break;

        }
    }

}
