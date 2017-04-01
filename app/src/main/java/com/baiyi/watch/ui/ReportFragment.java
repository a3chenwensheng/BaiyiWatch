package com.baiyi.watch.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.BaseFragment;
import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Care;
import com.baiyi.watch.model.Daylight_heart_rate;
import com.baiyi.watch.model.Device;
import com.baiyi.watch.model.Fall_report;
import com.baiyi.watch.model.Pedometer;
import com.baiyi.watch.model.Pedometer_activity;
import com.baiyi.watch.model.Resting_heart_rate;
import com.baiyi.watch.model.Sleep;
import com.baiyi.watch.model.Sleep_detail;
import com.baiyi.watch.net.BaseMessage;
import com.baiyi.watch.net.DeviceApi;
import com.baiyi.watch.net.HttpCallback;
import com.baiyi.watch.net.ParserServer;
import com.baiyi.watch.utils.ActivityUtil;
import com.baiyi.watch.utils.StringUtils;
import com.baiyi.watch.utils.TimeUtils;
import com.baiyi.watch.widget.CalorieView;
import com.baiyi.watch.widget.DaylightHeartRateReportView;
import com.baiyi.watch.widget.ExpandableTextView;
import com.baiyi.watch.widget.FallReportView;
import com.baiyi.watch.widget.PedometerActivityReportView;
import com.baiyi.watch.widget.PedometerReportView2;
import com.baiyi.watch.widget.RadarView;
import com.baiyi.watch.widget.RestingHeartRateReportView;
import com.baiyi.watch.widget.SleepReportView2;
import com.baiyi.watch.widget.pulltorefresh.XScrollView;

import org.json.JSONObject;

import java.math.BigDecimal;

import toasty.Toasty;

public class ReportFragment extends BaseFragment implements View.OnClickListener, XScrollView.IXScrollViewListener {

    private XScrollView mScrollView;
    private LinearLayout mEmptyLayout;

    private LinearLayout mPedometerLayout;
    private LinearLayout mSleepLayout;
    private LinearLayout mHeartrateLayout;
    private LinearLayout mFallLayout;
    private LinearLayout mCareLayout;

    private PedometerReportView2 mPedometerReportView;// 一周步数
    private PedometerActivityReportView mPedometerActivityReportView;// 活跃时间
    private SleepReportView2 mSleepReportView;// 睡眠
    private DaylightHeartRateReportView mDaylightHeartRateReportView;// 日间平均心率
    private DaylightHeartRateReportView mNightHeartRateReportView;// 夜间平均心率
    private RestingHeartRateReportView mRestingHeartRateReportView;// 静息心率
    private FallReportView mFallReportView;
    private RadarView mRadarView;
    private CalorieView mCalorieView;// 卡路里
    private TextView mStepTotalTv;// 总步数
    private TextView mDistanceTv;// 总步行距离
    private TextView mCalorieTv;// 卡路里

    private TextView mSleepDurationTv;
    private TextView mDeepSleepDurationTv;
    private TextView mLightSleepDurationTv;
    private TextView mSleepBeginTv;
    private TextView mSleepEndTv;
    private TextView mSleepAwakeTv;
    private ExpandableTextView mExpandableTextView;

    private TextView mPreTv;// 前一周
    private TextView mNextTv;// 后一周
    private TextView mDateTv;
    private final long ONE_WEEK_TIME = 1000 * 60 * 60 * 24 * 7;
    private long mLongDatetime = System.currentTimeMillis();

    private Device mDevice;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragement_report, container, false);
        initView(view);
        initData();
        setListener();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //refreshCurrentDevice();
        getHealthWeekly(TimeUtils.long2StrDate(mLongDatetime - ONE_WEEK_TIME, "yyyyMMdd"), true);

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
        mScrollView = (XScrollView) view.findViewById(R.id.scroll_view);
        mScrollView.setPullRefreshEnable(true);
        mScrollView.setPullLoadEnable(false);
        mScrollView.setAutoLoadEnable(true);
        mScrollView.setIXScrollViewListener(this);
        mScrollView.setRefreshTime(TimeUtils.getDateStr("MM-dd HH:mm:ss"));

        mEmptyLayout = (LinearLayout) view.findViewById(R.id.empty_layout);

        View reportContentView = LayoutInflater.from(mContext).inflate(R.layout.layout_report, null);


        mPedometerLayout = (LinearLayout) reportContentView.findViewById(R.id.report_pedometer_layout);
        mSleepLayout = (LinearLayout) reportContentView.findViewById(R.id.report_sleep_layout);
        mHeartrateLayout = (LinearLayout) reportContentView.findViewById(R.id.report_heartrate_layout);
        mFallLayout = (LinearLayout) reportContentView.findViewById(R.id.report_fall_layout);
        mCareLayout = (LinearLayout) reportContentView.findViewById(R.id.report_care_layout);

        mPedometerReportView = (PedometerReportView2) reportContentView.findViewById(R.id.pedometer_report_view);
        mPedometerActivityReportView = (PedometerActivityReportView) reportContentView.findViewById(R.id.pedometer_activity_report_view);
        mCalorieView = (CalorieView) reportContentView.findViewById(R.id.pedometer_calorie_report_view);
        mSleepReportView = (SleepReportView2) reportContentView.findViewById(R.id.sleep_report_view);
        mDaylightHeartRateReportView = (DaylightHeartRateReportView) reportContentView.findViewById(R.id.daylight_heart_rate_report_view);
        mNightHeartRateReportView = (DaylightHeartRateReportView) reportContentView.findViewById(R.id.night_heart_rate_report_view);
        mRestingHeartRateReportView = (RestingHeartRateReportView) reportContentView.findViewById(R.id.resting_heart_rate_report_view);
        mFallReportView = (FallReportView) reportContentView.findViewById(R.id.fall_report_view);
        mRadarView = (RadarView) reportContentView.findViewById(R.id.care_report_radarview);

        mStepTotalTv = (TextView) reportContentView.findViewById(R.id.pedometer_report_step_total_tv);
        mDistanceTv = (TextView) reportContentView.findViewById(R.id.pedometer_report_distance_tv);
        mCalorieTv = (TextView) reportContentView.findViewById(R.id.pedometer_report_calorie_tv);

        mSleepDurationTv = (TextView) reportContentView.findViewById(R.id.sleep_duration_tv);
        mDeepSleepDurationTv = (TextView) reportContentView.findViewById(R.id.deep_sleep_duration_tv);
        mLightSleepDurationTv = (TextView) reportContentView.findViewById(R.id.light_duration_tv);
        mSleepBeginTv = (TextView) reportContentView.findViewById(R.id.sleep_begin_tv);
        mSleepEndTv = (TextView) reportContentView.findViewById(R.id.sleep_end_tv);
        mSleepAwakeTv = (TextView) reportContentView.findViewById(R.id.sleep_awake_tv);

        mExpandableTextView = (ExpandableTextView) reportContentView.findViewById(R.id.expand_text_view);
        mExpandableTextView.setText(getString(R.string.heart_rate_statement));

        mDateTv = (TextView) reportContentView.findViewById(R.id.report_date_tv);
        mPreTv = (TextView) reportContentView.findViewById(R.id.report_pre_tv);
        mNextTv = (TextView) reportContentView.findViewById(R.id.report_next_tv);
        //mNextTv.setVisibility(View.INVISIBLE);

        mScrollView.setView(reportContentView);

    }

    /**
     * 初始化数据
     */
    private void initData() {
        mPedometerReportView.refresh(null);
        mPedometerActivityReportView.refresh(null);
        mSleepReportView.refresh(null);
        mDaylightHeartRateReportView.refresh(null);
        mNightHeartRateReportView.refresh(null);
        mRestingHeartRateReportView.refresh(null);
        mFallReportView.refresh(null);

        refreshCurrentDevice();

        showDate(mLongDatetime);
    }

    private void setListener() {
        mPreTv.setOnClickListener(this);
        mNextTv.setOnClickListener(this);

        mSleepReportView.setOnSelectedListener(new SleepReportView2.OnSelectedListener() {

            @Override
            public void OnSelected(Sleep_detail sleep_detail) {
                // TODO Auto-generated method stub
                showSleepDetail(sleep_detail);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.report_pre_tv:
                getPreReportData();
                break;
            case R.id.report_next_tv:
                getNextReportData();
                break;
            default:
                break;

        }
    }

    @Override
    public void onRefresh() {
        getHealthWeekly(TimeUtils.long2StrDate(mLongDatetime - ONE_WEEK_TIME, "yyyyMMdd"), false);

    }

    @Override
    public void onLoadMore() {
    }

    private void onLoad() {
        mScrollView.stopRefresh();
        mScrollView.stopLoadMore();
        mScrollView.setRefreshTime(TimeUtils.getDateStr("MM-dd HH:mm:ss"));
    }

    /**
     * 更新当前设备
     */
    public void refreshCurrentDevice() {
        mDevice = MyApplication.getInstance().getDeviceDaoInface().viewDevice("iscurrent = ?", new String[] { "1" });

        if (mDevice != null) {

            if ("S1".equals(mDevice.getType()) || "K1".equals(mDevice.getType()) || "BY102_LOC".equals(mDevice.getType())) {
                mPedometerLayout.setVisibility(View.GONE);
                mSleepLayout.setVisibility(View.GONE);
                mHeartrateLayout.setVisibility(View.GONE);
                mFallLayout.setVisibility(View.GONE);
                mCareLayout.setVisibility(View.GONE);

                mEmptyLayout.setVisibility(View.VISIBLE);
            } else if ("BY102".equals(mDevice.getType()) ) {
                mPedometerLayout.setVisibility(View.VISIBLE);
                mSleepLayout.setVisibility(View.VISIBLE);
                mHeartrateLayout.setVisibility(View.VISIBLE);
                mFallLayout.setVisibility(View.GONE);
                mCareLayout.setVisibility(View.VISIBLE);

                mEmptyLayout.setVisibility(View.GONE);
            }  else if ("BY007".equals(mDevice.getType()) ) {
                mPedometerLayout.setVisibility(View.VISIBLE);
                mSleepLayout.setVisibility(View.GONE);
                mHeartrateLayout.setVisibility(View.GONE);
                mFallLayout.setVisibility(View.VISIBLE);
                mCareLayout.setVisibility(View.VISIBLE);

                mEmptyLayout.setVisibility(View.GONE);
            }  else if ("BY102_NO_HR".equals(mDevice.getType()) ) {
                mPedometerLayout.setVisibility(View.VISIBLE);
                mSleepLayout.setVisibility(View.VISIBLE);
                mHeartrateLayout.setVisibility(View.GONE);
                mFallLayout.setVisibility(View.GONE);
                mCareLayout.setVisibility(View.VISIBLE);

                mEmptyLayout.setVisibility(View.GONE);
            }else {
                mPedometerLayout.setVisibility(View.GONE);
                mSleepLayout.setVisibility(View.GONE);
                mHeartrateLayout.setVisibility(View.GONE);
                mFallLayout.setVisibility(View.GONE);
                mCareLayout.setVisibility(View.GONE);

                mEmptyLayout.setVisibility(View.GONE);
            }
        }

    }

    /**
     * 显示上一周的报告数据
     */
    private synchronized void getPreReportData() {
        mLongDatetime = mLongDatetime - ONE_WEEK_TIME;
        getHealthWeekly(TimeUtils.long2StrDate(mLongDatetime - ONE_WEEK_TIME, "yyyyMMdd"), true);

        showDate(mLongDatetime);
    }

    /**
     * 显示下一周的报告数据
     */
    private synchronized void getNextReportData() {
        mLongDatetime = mLongDatetime + ONE_WEEK_TIME;
        getHealthWeekly(TimeUtils.long2StrDate(mLongDatetime - ONE_WEEK_TIME, "yyyyMMdd"), true);

        showDate(mLongDatetime);
    }

    private void showDate(long longDatetime) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - mLongDatetime < ONE_WEEK_TIME) {
            mNextTv.setVisibility(View.INVISIBLE);
            //mDateTv.setText("今周");
        }else {
            mNextTv.setVisibility(View.VISIBLE);
            //mDateTv.setText(TimeUtils.long2StrDate(mLongDatetime, "yyyy年M月d日")/* + TimeUtils.getWeekStr(new Date(mLongDatetime))*/);
        }

        long lastWeekMillis = mLongDatetime - ONE_WEEK_TIME;
        mDateTv.setText(TimeUtils.getSundayOfThisWeek(lastWeekMillis, "M月d日") + "-" + TimeUtils.getSaturdayOfThisWeek(lastWeekMillis, "M月d日"));

    }

    private void getHealthWeekly(final String time, boolean isShowLoading) {

        if (mDevice == null) {
            onLoad();
            //dismissLoadingDialog();
            return;
        }

        if (mEmptyLayout.getVisibility() == View.VISIBLE) {
            onLoad();
            //dismissLoadingDialog();
            return;
        }

        // mHeartRateReportView.clear();
        mPedometerReportView.clear();
        mPedometerActivityReportView.clear();
        mSleepReportView.clear();
        mDaylightHeartRateReportView.clear();
        mNightHeartRateReportView.clear();
        mRestingHeartRateReportView.clear();
        mFallReportView.clear();
        mRadarView.setData(new double[] { 0, 0, 0 });
        showSleepDetail(null);

        if (isShowLoading) {
           // showLoadingDialog("请求中...");
        }
        DeviceApi.getInstance(mContext).getHealthWeekly(mDevice.mId, time, new HttpCallback() {
            @Override
            public void onError(String error) {
                onLoad();
                //dismissLoadingDialog();
                //ActivityUtil.showToast(mContext, error, 0);
                Toasty.error(mContext, error).show();
            }

            @Override
            public void onComplete(BaseMessage result) {
                onLoad();
                //dismissLoadingDialog();

                Pedometer pedometer = null;
                Pedometer_activity pedometer_activity = null;
                Sleep sleep = null;
                Daylight_heart_rate daylight_heart_rate = null;
                Daylight_heart_rate night_heart_rate = null;
                Resting_heart_rate resting_heart_rate = null;
                Fall_report fall_report = null;
                Care care = null;

                if (result.isSuccess()) {
                    String resultSrc = result.getResultSrc();
                    JSONObject jo = null;

                    try {
                        jo = new JSONObject(resultSrc);
                        pedometer = ParserServer.paserPedometer(jo.getJSONObject("pedometer").toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        jo = new JSONObject(resultSrc);
                        pedometer_activity = ParserServer.paserPedometer_activity(jo.getJSONObject("pedometer_activity").toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        jo = new JSONObject(resultSrc);
                        sleep = ParserServer.paserSleep(jo.getJSONObject("sleep").toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        jo = new JSONObject(resultSrc);
                        daylight_heart_rate = ParserServer.paseDaylight_heart_rate(jo.getJSONObject("daylight_heart_rate").toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        jo = new JSONObject(resultSrc);
                        night_heart_rate = ParserServer.paseDaylight_heart_rate(jo.getJSONObject("night_heart_rate").toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        jo = new JSONObject(resultSrc);
                        resting_heart_rate = ParserServer.paserResting_heart_rate(jo.getJSONObject("resting_heart_rate").toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        jo = new JSONObject(resultSrc);
                        fall_report = ParserServer.paserFall_report(jo.getJSONObject("fall").toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        jo = new JSONObject(resultSrc);
                        care = ParserServer.paserCare(jo.getJSONObject("care").toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                if (pedometer != null) {
                    mPedometerReportView.refresh(pedometer.mDetails);
                    mCalorieView.setMax(StringUtils.string2Float(pedometer.getStepObjective()) * 7);
                    mCalorieView.setProgress(StringUtils.string2Float(pedometer.getStepTotal()));

                    mStepTotalTv.setText(pedometer.getStepTotal());

                    double distance = StringUtils.string2Double(pedometer.getDistance());
                    double calorie = StringUtils.string2Double(pedometer.getCalorie());
                    mDistanceTv.setText(new BigDecimal(distance).setScale(1, BigDecimal.ROUND_HALF_UP) + "");// 四舍五入取整,保留一位小数
                    mCalorieTv.setText(new BigDecimal(calorie).setScale(1, BigDecimal.ROUND_HALF_UP) + "");// 四舍五入取整,保留一位小数
                }

                if (pedometer_activity != null) {
                    mPedometerActivityReportView.refresh(pedometer_activity.mDetails);
                }

                if (sleep != null) {
                    mSleepReportView.refresh(sleep.mDetails);
                }

                if (daylight_heart_rate != null) {
                    mDaylightHeartRateReportView.refresh(daylight_heart_rate.mDetails);
                }

                if (daylight_heart_rate != null) {
                    mNightHeartRateReportView.refresh(night_heart_rate.mDetails);
                }

                if (resting_heart_rate != null) {
                    mRestingHeartRateReportView.refresh(resting_heart_rate.mDetails);
                }

                if (fall_report != null) {
                    mFallReportView.refresh(fall_report.mDetails);
                }

                if (care != null) {
                    mRadarView.setData(
                            new double[] { StringUtils.string2double(care.getPhone_count()), StringUtils.string2double(care.getLocation_count()), StringUtils.string2double(care.getAlert_count()) });
                }

            }
        });

    }

    protected void showSleepDetail(Sleep_detail sleep_detail) {
        if (sleep_detail != null) {
            int all = StringUtils.string2Int(sleep_detail.getSleepDuration());
            int deep = StringUtils.string2Int(sleep_detail.getDeepSleepDuration());
            int mid = StringUtils.string2Int(sleep_detail.getMiddleSleepDuration());
            int light = StringUtils.string2Int(sleep_detail.getLightSleepDuration());
            int awake = all - deep - mid - light;
            mSleepDurationTv.setText(TimeUtils.formatHours(all));
            mDeepSleepDurationTv.setText(TimeUtils.formatHours(deep));
            mLightSleepDurationTv.setText(TimeUtils.formatHours(light));
            mSleepAwakeTv.setText(TimeUtils.formatHours(awake));

            String beginStr = "--";
            try {
                beginStr = sleep_detail.getSleepBegin().substring(10, 16);
            } catch (Exception e) {
            }
            String endStr = "--";
            try {
                endStr = sleep_detail.getSleepEnd().substring(10, 16);
            } catch (Exception e) {
            }
            mSleepBeginTv.setText(beginStr);
            mSleepEndTv.setText(endStr);

        }else {
            mSleepDurationTv.setText("--");
            mDeepSleepDurationTv.setText("--");
            mLightSleepDurationTv.setText("--");
            mSleepBeginTv.setText("--");
            mSleepEndTv.setText("--");
            mSleepAwakeTv.setText("--");
        }

    }

}
