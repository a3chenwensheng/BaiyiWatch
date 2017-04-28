package com.baiyi.watch.data;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Device;
import com.baiyi.watch.model.Heartratedata;
import com.baiyi.watch.net.BaseMessage;
import com.baiyi.watch.net.DeviceApi;
import com.baiyi.watch.net.HttpCallback;
import com.baiyi.watch.utils.TimeUtils;
import com.baiyi.watch.widget.HeartRateView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import toasty.Toasty;

/**
 * 心率Activity
 *
 * @author 陈文声
 * @version v4.0
 * @email a3chenwensheng@126.com
 * @date 2017-4-27 14:30
 */
public class HeartRateActivity extends BaseActivity implements OnClickListener {

    private TextView mBackTv;// 返回

    private TextView mCurrentRateTv;// 最新心率数值

    private HeartRateView mHeartRateView;
    private HorizontalScrollView mScrollView;

    private TextView mPreTv;// 前一天
    private TextView mNextTv;// 后一天
    private TextView mDateTv;

    private final long ONE_DAY_TIME = 1000 * 60 * 60 * 24;
    private long mLongDatetime = System.currentTimeMillis();

    private Heartratedata mCurrentHeartRateData;

    private Device mDevice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate);
        initView();
        initData();
        setListener();

        getLastHeartRateData();

    }

    /**
     * 初始化布局
     */
    private void initView() {
        mBackTv = (TextView) findViewById(R.id.back_tv);

        mCurrentRateTv = (TextView) findViewById(R.id.heart_rate_current_tv);

        mDateTv = (TextView) findViewById(R.id.heart_rate_date_tv);
        mPreTv = (TextView) findViewById(R.id.heart_rate_pre_tv);
        mNextTv = (TextView) findViewById(R.id.heart_rate_next_tv);
        mNextTv.setVisibility(View.INVISIBLE);

        mHeartRateView = (HeartRateView) findViewById(R.id.heart_rate_view);
        mScrollView = (HorizontalScrollView) findViewById(R.id.heart_rate_scroll);

    }

    /**
     * 初始化数据
     */
    private void initData() {

        mDevice = MyApplication.getInstance().getDeviceDaoInface().viewDevice("iscurrent = ?", new String[]{"1"});
        showDate(mLongDatetime);
        mHeartRateView.refresh(null);

    }

    private void setListener() {
        mBackTv.setOnClickListener(this);

        mPreTv.setOnClickListener(this);
        mNextTv.setOnClickListener(this);

        mHeartRateView.setOnSelectedListener(new HeartRateView.OnSelectedListener() {

            @Override
            public void OnSelected(Heartratedata heartratedata) {
                showData(heartratedata);

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_tv:
                onBackPressed();
                break;
            case R.id.heart_rate_pre_tv:
                getPreHeartRateData();
                break;
            case R.id.heart_rate_next_tv:
                getNextHeartRateData();
                break;
            default:
                break;
        }

    }

    /**
     * 显示上一天的心率数据
     */
    private synchronized void getPreHeartRateData() {
        mLongDatetime = mLongDatetime - ONE_DAY_TIME;
        getDeviceHeartRateData(TimeUtils.long2StrDate(mLongDatetime, "yyyyMMdd"));

        showDate(mLongDatetime);
    }

    /**
     * 显示下一天的心率数据
     */
    private synchronized void getNextHeartRateData() {
        mLongDatetime = mLongDatetime + ONE_DAY_TIME;
        getDeviceHeartRateData(TimeUtils.long2StrDate(mLongDatetime, "yyyyMMdd"));

        showDate(mLongDatetime);
    }

    /**
     * 显示当天的心率数据
     */
    private synchronized void getLastHeartRateData() {
        mLongDatetime = System.currentTimeMillis();
        getDeviceHeartRateData(TimeUtils.long2StrDate(mLongDatetime, "yyyyMMdd"));

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
     * 获取设备心率数据
     *
     * @param time
     */
    private void getDeviceHeartRateData(final String time) {

        showData(null);

        if (mDevice == null || TextUtils.isEmpty(mDevice.mId)) {
            return;
        }


        mPreTv.setEnabled(false);
        mNextTv.setEnabled(false);

        mHeartRateView.clear();
        //showLoadingDialog("加载中...");
        DeviceApi.getInstance(mContext).getDeviceHeartrateData(time, mDevice.mId, new HttpCallback() {

            @Override
            public void onError(String error) {
                // dismissLoadingDialog();
                mPreTv.setEnabled(true);
                mNextTv.setEnabled(true);

            }

            @Override
            public void onComplete(BaseMessage result) {
                //dismissLoadingDialog();
                mPreTv.setEnabled(true);
                mNextTv.setEnabled(true);
                if (result.isSuccess()) {
                    ArrayList<Heartratedata> heartRateDataList = (ArrayList<Heartratedata>) result.getResultList("Heartratedata");

                    sortList(heartRateDataList);// 排序(时间从早到晚)

                    try {
                        mCurrentHeartRateData = heartRateDataList.get(heartRateDataList.size() - 1);
                        if (time.equals(TimeUtils.getDateStr("yyyyMMdd"))) {
                            //mLastHeartRateData = heartRateDataList.get(heartRateDataList.size() - 1);
                        }
                    } catch (Exception e) {
                        mCurrentHeartRateData = null;
                        //mLastHeartRateData = null;
                    }

                    // TODO
                    showData(mCurrentHeartRateData);

                    mHeartRateView.clear();
                    mHeartRateView.refresh(heartRateDataList);

                    if (heartRateDataList != null && heartRateDataList.isEmpty()) {
                        Toasty.warning(mContext, "当天没有心率数据").show();
                    }

                    if (mCurrentHeartRateData != null) {
                        mScrollView.smoothScrollTo((int) (getX(mCurrentHeartRateData.getTime_begin()) - (mScreenWidth - 32) / 3.0f * 1.5f), 0);
                    } else {
                        mScrollView.smoothScrollTo(0, 0);
                    }

                }

            }
        });

    }

    private void showData(Heartratedata heartRateData) {
//		if (null != heartRateData) {
//			//mUpdateTimeTv.setText(TimeUtils.intervalTime(TimeUtils.jsonStr2StrDate(heartRateData.getTime_begin())));
//			Date date = TimeUtils.jsonStr2StrDate(heartRateData.getCreated_at());
//			mUpdateTimeTv.setText(TimeUtils.date2Str(date, "HH:mm"));
//			int heartRate = StringUtils.string2Int(heartRateData.getHeartrate());
//			if (heartRate < 60) {
//				mCurrentRateTv.setTextColor(getResources().getColor(R.color.data_color2));
//				mStatusTv.setText("心率过缓");
//			}else if (heartRate <= 100) {
//				mCurrentRateTv.setTextColor(getResources().getColor(R.color.data_color1));
//				mStatusTv.setText("心率正常");
//			}else {
//				mCurrentRateTv.setTextColor(getResources().getColor(R.color.data_color3));
//				mStatusTv.setText("心率过速");
//			}
//			mCurrentRateTv.setText(heartRateData.getHeartrate());
//			//mIndicator.setProgress(StringUtils.string2Int(heartRateData.getHeartrate()));
//		} else {
//			mUpdateTimeTv.setText("--:--");
//			mCurrentRateTv.setTextColor(getResources().getColor(R.color.light_gray));
//			mCurrentRateTv.setText("--");
//			mStatusTv.setText("未测量");
//		}

        if (null != heartRateData) {
            mCurrentRateTv.setText(heartRateData.getHeartrate());
            //mIndicateView.setValue(StringUtils.string2Int(heartRateData.getHeartrate()));
        } else {
            mCurrentRateTv.setText("--");
            //mIndicateView.reset();
        }

    }

    private void sortList(ArrayList<Heartratedata> listData) {
        if (null == listData) {
            return;
        }
        Collections.sort(listData, new Comparator<Heartratedata>() {
            public int compare(Heartratedata arg0, Heartratedata arg1) {
                return arg0.mTimebegin.compareTo(arg1.mTimebegin);
            }
        });
    }

    // 获取开始绘图X轴距离
    private float getX(String time_json) {
        Date date = TimeUtils.jsonStr2StrDate(time_json);
        int hour = date.getHours();
        int min = date.getMinutes();
        float interval_left_right = getResources().getDimension(R.dimen.heart_rate_tb) * 6.0f;
        return (interval_left_right * hour + interval_left_right / 60.0f * min);
    }
}