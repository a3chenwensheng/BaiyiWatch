package com.baiyi.watch.remind;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baiyi.watch.adapter.RemindAdapter;
import com.baiyi.watch.adapter.RemindAdapter.OnDeleteListener;
import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.dialog.BaseDialog;
import com.baiyi.watch.model.Device;
import com.baiyi.watch.model.SettingAlert;
import com.baiyi.watch.net.BaseMessage;
import com.baiyi.watch.net.DeviceApi;
import com.baiyi.watch.net.HttpCallback;
import com.baiyi.watch.utils.ActivityUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 腕表提醒列表 Activity
 *
 * @author 陈文声
 * @version v2.0
 * @email a3chenwensheng@126.com
 * @date 2015-4-22 10:30
 */
public class RemindWatchListActivity extends BaseActivity implements OnClickListener {

    private TextView mBackTv;// 返回

    private Device mDevice;

    private ListView mListView;//
    private RemindAdapter mAdapter;
    private List<SettingAlert> mListSettingAlerts = new ArrayList<SettingAlert>();
    private List<SettingAlert> mAllSettingAlerts = new ArrayList<SettingAlert>();

    private LinearLayout mAddLayout;
    private TextView mAddTv;

    private BaseDialog mDeleteDialog;// 删除对话框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_watch_list);
        initView();
        initData();
        setListener();

    }

    /**
     * 初始化布局
     */
    private void initView() {
        mBackTv = (TextView) findViewById(R.id.back_tv);
        mListView = (ListView) findViewById(R.id.remind_listView);

        View footerView = getLayoutInflater().inflate(R.layout.item_alert_footer, null);
        mAddLayout = (LinearLayout) footerView.findViewById(R.id.alert_add_layout);
        mAddTv = (TextView) footerView.findViewById(R.id.alert_add_tv);
        mListView.addFooterView(footerView, null, false);

    }

    /**
     * 初始化数据
     */
    private void initData() {

        mDevice = MyApplication.getInstance().getDeviceDaoInface().viewDevice("iscurrent = ?", new String[]{"1"});

        mAdapter = new RemindAdapter(mContext, mListSettingAlerts, mDevice);
        mListView.setAdapter(mAdapter);
    }

    private void setListener() {
        mBackTv.setOnClickListener(this);
        mAddLayout.setOnClickListener(this);

        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("SettingAlert", mListSettingAlerts.get(position));
                bundle.putSerializable("device", mDevice);
                redictToActivity(mContext, RemindEditWatchActivity.class, bundle);

            }
        });

        mAdapter.setOnDeleteListener(new OnDeleteListener() {

            @Override
            public void OnDeleteListener(int position) {
                showDeleteDialog(position);

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_tv:
                onBackPressed();
                break;
            case R.id.alert_add_layout:
                if (mDevice != null && "BY007".equals(mDevice.getType())) {
                    if (mListSettingAlerts.size() < 4) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("SettingAlert", getEmptyAlert(mAllSettingAlerts));
                        bundle.putSerializable("device", mDevice);
                        redictToActivity(mContext, RemindEditWatchActivity.class, bundle);
                    } else {
                        ActivityUtil.showToast(mContext, "最多添加4个提醒");
                    }
                } else {
                    if (mListSettingAlerts.size() < 10) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("SettingAlert", getEmptyAlert(mAllSettingAlerts));
                        bundle.putSerializable("device", mDevice);
                        redictToActivity(mContext, RemindEditWatchActivity.class, bundle);
                    } else {
                        ActivityUtil.showToast(mContext, "最多添加10个提醒");
                    }
                }
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

        //showLoadingDialog("加载中...");
        DeviceApi.getInstance(mContext).getDeviceInfo(mDevice.mId, new HttpCallback() {

            @Override
            public void onError(String error) {
                //dismissLoadingDialog();

            }

            @Override
            public void onComplete(BaseMessage result) {
                //	dismissLoadingDialog();
                if (result.isSuccess()) {
                    mDevice = (Device) result.getResult("Device");
                    if (null != mDevice) {
                        mAllSettingAlerts.clear();
                        mAllSettingAlerts.addAll(mDevice.mAlerts);
                        mListSettingAlerts.clear();
                        mListSettingAlerts.addAll(filterAlerts(mDevice.mAlerts));

                        int count = mListSettingAlerts.size();
                        int count2 = mListSettingAlerts.size();
                        if (mDevice != null && "BY007".equals(mDevice.getType())) {
                            //mAddTv.setText("您已添加了"+ count +"个提醒，还可以添加" + (4 - count2) + "个提醒");
                            mAddTv.setText("您已添加了" + count2 + "个提醒，还可以添加" + (4 - count2) + "个提醒");
                        } else {
                            //mAddTv.setText("您已添加了"+ count +"个提醒，还可以添加" + (10 - count2) + "个提醒");
                            mAddTv.setText("您已添加了" + count2 + "个提醒，还可以添加" + (10 - count2) + "个提醒");
                        }

                        // 刷新列表
                        mAdapter.notifyDataSetChanged();

                    }
                } else {
                    ActivityUtil.showToast(mContext, result.getError_desc());
                }

            }
        });

    }

    private List<SettingAlert> filterAlerts(List<SettingAlert> alerts) {
        List<SettingAlert> datas = new ArrayList<SettingAlert>();
        for (SettingAlert settingAlert : alerts) {
            if (!TextUtils.isEmpty(settingAlert.getName())) {
                datas.add(settingAlert);
            }
        }
        sortList(datas);//排序

        return datas;
    }

    private void sortList(List<SettingAlert> listData) {
        if (null == listData) {
            return;
        }

        Collections.sort(listData, new Comparator<SettingAlert>() {
            public int compare(SettingAlert arg0, SettingAlert arg1) {
                return arg0.mCreatedAt.compareTo(arg1.mCreatedAt);
            }
        });

    }

    /**
     * 获取一个为空的
     *
     * @param alerts
     * @return
     */
    private SettingAlert getEmptyAlert(List<SettingAlert> alerts) {

        SettingAlert alert = new SettingAlert();
        alert.setSeqid("0");
        for (SettingAlert settingAlert : alerts) {
            if (TextUtils.isEmpty(settingAlert.getName())) {
                alert = settingAlert;
                break;
            }
        }
        return alert;
    }

    private void clearAlert(SettingAlert settingAlert) {

        if (mDevice == null || TextUtils.isEmpty(mDevice.mId)) {
            return;
        }

        //showLoadingDialog("处理中...");
        DeviceApi.getInstance(mContext).clearAlert(mDevice.mId, settingAlert, new HttpCallback() {

            @Override
            public void onError(String error) {
                //	dismissLoadingDialog();

            }

            @Override
            public void onComplete(BaseMessage result) {
                //dismissLoadingDialog();
                if (result.isSuccess()) {
                    getDeviceInfo();
                } else {
                    ActivityUtil.showToast(mContext, result.getError_desc());
                }

            }
        });

    }

    private void showDeleteDialog(final int position) {
        mDeleteDialog = new BaseDialog(mContext);
        mDeleteDialog.setTitle("提示");
        mDeleteDialog.setMessage("是否删除？");
        mDeleteDialog.setTitleLineVisibility(View.INVISIBLE);
        mDeleteDialog.setButton1("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDeleteDialog.dismiss();
            }
        });
        mDeleteDialog.setButton2("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                clearAlert(mListSettingAlerts.get(position));
                mDeleteDialog.dismiss();
            }
        });

        mDeleteDialog.show();
    }

}
