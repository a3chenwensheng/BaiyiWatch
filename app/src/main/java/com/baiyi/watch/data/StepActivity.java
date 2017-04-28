package com.baiyi.watch.data;

import android.content.ContentValues;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Device;
import com.baiyi.watch.model.Pedometerdata;
import com.baiyi.watch.model.SettingAlert;
import com.baiyi.watch.net.BaseMessage;
import com.baiyi.watch.net.DeviceApi;
import com.baiyi.watch.net.HttpCallback;
import com.baiyi.watch.utils.StringUtils;
import com.baiyi.watch.utils.TimeUtils;
import com.baiyi.watch.widget.StepArcView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 步数Activity
 *
 * @author 陈文声
 * @version v4.0
 * @email a3chenwensheng@126.com
 * @date 2017-4-27 17:30
 */
public class StepActivity extends BaseActivity implements OnClickListener {

    private TextView mBackTv;// 返回

    private TextView mPreTv;// 前一天
    private TextView mNextTv;// 后一天
    private TextView mDateTv;

    private final long ONE_DAY_TIME = 1000 * 60 * 60 * 24;
    private long mLongDatetime = System.currentTimeMillis();

    private StepArcView mStepArcView;
    private int mStepObjective = 3000;
    private int mSteps = 0;
    private LinearLayout mStepSpliteLayout;

    private Device mDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        initView();
        initData();
        setListener();

        getLastStepData();

    }

    /**
     * 初始化布局
     */
    private void initView() {
        mBackTv = (TextView) findViewById(R.id.back_tv);


        mDateTv = (TextView) findViewById(R.id.step_date_tv);
        mPreTv = (TextView) findViewById(R.id.step_pre_tv);
        mNextTv = (TextView) findViewById(R.id.step_next_tv);
        mNextTv.setVisibility(View.INVISIBLE);

        mStepArcView = (StepArcView) findViewById(R.id.step_arc_view);
        mStepSpliteLayout = (LinearLayout) findViewById(R.id.step_splite_layout);

    }

    /**
     * 初始化数据
     */
    private void initData() {

        mDevice = MyApplication.getInstance().getDeviceDaoInface().viewDevice("iscurrent = ?", new String[]{"1"});
        showDate(mLongDatetime);

    }

    private void setListener() {
        mBackTv.setOnClickListener(this);

        mPreTv.setOnClickListener(this);
        mNextTv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_tv:
                onBackPressed();
                break;
            case R.id.step_pre_tv:
                getPreStepData();
                break;
            case R.id.step_next_tv:
                getNextStepData();
                break;
            default:
                break;
        }

    }

    /**
     * 显示上一天的计步数据
     */
    private synchronized void getPreStepData() {
        mLongDatetime = mLongDatetime - ONE_DAY_TIME;
        getDeviceStepData(TimeUtils.long2StrDate(mLongDatetime, "yyyyMMdd"));

        showDate(mLongDatetime);
    }

    /**
     * 显示下一天的计步数据
     */
    private synchronized void getNextStepData() {
        mLongDatetime = mLongDatetime + ONE_DAY_TIME;
        getDeviceStepData(TimeUtils.long2StrDate(mLongDatetime, "yyyyMMdd"));

        showDate(mLongDatetime);
    }

    /**
     * 显示当天的计步数据
     */
    private synchronized void getLastStepData() {
        mLongDatetime = System.currentTimeMillis();
        getDeviceStepData(TimeUtils.long2StrDate(mLongDatetime, "yyyyMMdd"));

        showDate(mLongDatetime);
    }


    private void showDate(long longDatetime) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - mLongDatetime < ONE_DAY_TIME) {
            mNextTv.setVisibility(View.INVISIBLE);
            mDateTv.setText("今天");
        } else {
            mNextTv.setVisibility(View.VISIBLE);
            mDateTv.setText(TimeUtils.long2StrDate(mLongDatetime, "yyyy年M月d日")/* + TimeUtils.getWeekStr(new Date(mLongDatetime))*/);
        }

    }

    /**
     * 获取设备运动数据
     *
     * @param time
     */
    private void getDeviceStepData(String time) {

        if (mDevice == null || TextUtils.isEmpty(mDevice.mId)) {
            return;
        }

        mStepSpliteLayout.removeAllViews();
        mPreTv.setEnabled(false);
        mNextTv.setEnabled(false);

        //mCircleBar.setProgress(0, 0);

        //showLoadingDialog("加载中...");
        DeviceApi.getInstance(mContext).getDevicePedometerdata(time, mDevice.mId, new HttpCallback() {

            @Override
            public void onError(String error) {
                //dismissLoadingDialog();
                mPreTv.setEnabled(true);
                mNextTv.setEnabled(true);
            }

            @Override
            public void onComplete(BaseMessage result) {
                //dismissLoadingDialog();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        mPreTv.setEnabled(true);
                        mNextTv.setEnabled(true);
                    }
                }, 650);

                if (result.isSuccess()) {

                    mSteps = 0;

                    ArrayList<Pedometerdata> postureDataList = (ArrayList<Pedometerdata>) result.getResultList("Pedometerdata");
                    if (postureDataList != null && postureDataList.size() > 0) {

                        Pedometerdata lastData = postureDataList.get(0);
                        mSteps = StringUtils.string2Int(lastData.getValue());

                        //按时间先后排序
                        sortList(postureDataList);
                        int tempValue = 0;
                        for (Pedometerdata posturedata : postureDataList) {

                            int currentValue = StringUtils.string2Int(posturedata.getValue());
                            if (currentValue > tempValue) {

                                LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.layout_step_splite, null);
                                //layout.setTag(posturedata.mId);
                                TextView valueTv = (TextView) layout.findViewById(R.id.step_splite_value_tv);
                                TextView timeTv = (TextView) layout.findViewById(R.id.step_splite_time_tv);
                                valueTv.setText((currentValue - tempValue) + "");
                                timeTv.setText(TimeUtils.date2Str(TimeUtils.jsonStr2StrDate(posturedata.getTime_begin()), "HH:mm"));

                                mStepSpliteLayout.addView(layout);

                                tempValue = currentValue;
                            }

                       }

                    }

                    mStepArcView.setCurrentCount(mStepObjective, mSteps);
                }
            }
        });

    }

    private void sortList(List<Pedometerdata> listData) {
        if (null == listData) {
            return;
        }

        Collections.sort(listData, new Comparator<Pedometerdata>() {
            public int compare(Pedometerdata arg0, Pedometerdata arg1) {
                return arg0.mTimeBegin.compareTo(arg1.mTimeBegin);
            }
        });

    }

}