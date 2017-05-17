package com.baiyi.watch.record;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.baiyi.watch.adapter.FenceDataAdapter;
import com.baiyi.watch.adapter.SOSAdapter;
import com.baiyi.watch.aqgs2.BaseFragment;
import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Device;
import com.baiyi.watch.model.Notification;
import com.baiyi.watch.model.Person;
import com.baiyi.watch.model.Sosdata;
import com.baiyi.watch.net.BaseMessage;
import com.baiyi.watch.net.DeviceApi;
import com.baiyi.watch.net.HttpCallback;
import com.baiyi.watch.net.PersonApi;
import com.baiyi.watch.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import toasty.Toasty;

/**
 *
 * 安全围栏记录列表Fragment
 *
 * @author 陈文声
 * @email a3chenwensheng@126.com
 * @date 2015-5-17 14:00
 * @version v4.0
 */
public class ListFenceDataFragment extends BaseFragment implements View.OnClickListener {

    private ListView mListView;//
    private FenceDataAdapter mAdapter;

    private TextView mPreTv;// 前一天
    private TextView mNextTv;// 后一天
    private TextView mDateTv;
    private TextView mCountTv;

    private Person mPerson;

    private List<Notification> listNotification = new ArrayList<Notification>();

    private final long ONE_DAY_TIME = 1000 * 60 * 60 * 24;
    private long mLongDatetime = System.currentTimeMillis();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_fence_data, container, false);
        initView(view);
        initData();
        setListener();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                getNotification(TimeUtils.long2StrDate(mLongDatetime, "yyyyMMdd"));
            }
        }, 100);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // 相当于Fragment的onResume
            //getData();
        } else {
            // 相当于Fragment的onPause

        }
    }

    /**
     * 初始化布局
     */
    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.fence_listView);
        mPreTv = (TextView) view.findViewById(R.id.fence_pre_tv);
        mNextTv = (TextView) view.findViewById(R.id.fence_next_tv);
        mNextTv.setVisibility(View.INVISIBLE);
        mDateTv = (TextView) view.findViewById(R.id.list_fence_date_tv);
        mCountTv = (TextView) view.findViewById(R.id.fence_count_tv);

    }

    /**
     * 初始化数据
     */
    private void initData() {

        showDate(mLongDatetime);

        mPerson = MyApplication.getInstance().getPersonDaoInface().viewPerson(null, null);

        mAdapter = new FenceDataAdapter(mContext, listNotification);
        mListView.setAdapter(mAdapter);

    }

    private void setListener() {
        mPreTv.setOnClickListener(this);
        mNextTv.setOnClickListener(this);
        //mDateTv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.list_fence_date_tv:

            case R.id.fence_pre_tv:
                getPreNotification();
                break;
            case R.id.fence_next_tv:
                getNextNotification();
                break;
            default:
                break;
        }

    }

    private void getNotification(String time) {

        if (mPerson == null || TextUtils.isEmpty(mPerson.mId)) {
            return;
        }

        cleanData();

        mPreTv.setEnabled(false);
        mNextTv.setEnabled(false);
        //showLoadingDialog("加载中...");
        PersonApi.getInstance(mContext).getNotification(mPerson.mId, "电子围栏报警", time, new HttpCallback() {

            @Override
            public void onError(String error) {
                mPreTv.setEnabled(true);
                mNextTv.setEnabled(true);
                //dismissLoadingDialog();
                Toasty.error(mContext, error).show();
            }

            @Override
            public void onComplete(BaseMessage result) {
                mPreTv.setEnabled(true);
                mNextTv.setEnabled(true);
                //dismissLoadingDialog();
                if (result.isSuccess()) {
                    listNotification.clear();
                    listNotification.addAll((List<Notification>) result.getResultList("Notification"));

                    mAdapter.notifyDataSetChanged();
                    if (mAdapter.getCount() < 1) {
                        mCountTv.setTextColor(getResources().getColor(R.color.data_color1));
                        mCountTv.setText("当日没有安全围栏报警");
                    } else {
                        mCountTv.setTextColor(getResources().getColor(R.color.data_color3));
                        mCountTv.setText(String.format("安全围栏报警 %d 次，请警惕", mAdapter.getCount()));
                    }

                }

            }
        });

    }

    private void cleanData() {
        mCountTv.setText("");
        listNotification.clear();
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 显示上一天数据
     */
    private synchronized void getPreNotification() {
        mLongDatetime = mLongDatetime - ONE_DAY_TIME;
        getNotification(TimeUtils.long2StrDate(mLongDatetime, "yyyyMMdd"));

        showDate(mLongDatetime);

    }

    /**
     * 显示下一天数据
     */
    private synchronized void getNextNotification() {
        mLongDatetime = mLongDatetime + ONE_DAY_TIME;
        getNotification(TimeUtils.long2StrDate(mLongDatetime, "yyyyMMdd"));

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

}
