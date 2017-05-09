package com.baiyi.watch.record;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baiyi.watch.adapter.SOSAdapter;
import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Device;
import com.baiyi.watch.model.Sosdata;
import com.baiyi.watch.net.BaseMessage;
import com.baiyi.watch.net.DeviceApi;
import com.baiyi.watch.net.HttpCallback;
import com.baiyi.watch.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * SOS记录列表Activity
 * 
 * @author 陈文声
 * @email a3chenwensheng@126.com
 * @date 2015-4-21 10:00
 * @version v2.0
 */
public class ListSOSActivity extends BaseActivity implements OnClickListener {

	private TextView mBackTv;// 返回
	private ListView mListView;//
	private SOSAdapter mSOSAdapter;

	private TextView mPreTv;// 前一天
	private TextView mNextTv;// 后一天
	private TextView mDateTv;
	private TextView mCountTv;
	
	private Device mDevice;
	
	private List<Sosdata> listSosdata = new ArrayList<Sosdata>();
	
	private final long ONE_DAY_TIME = 1000 * 60 * 60 * 24;
	private long mLongDatetime = System.currentTimeMillis();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_sos);
		initView();
		initData();
		setListener();
		
		getDeviceSOSData(TimeUtils.long2StrDate(mLongDatetime, "yyyyMMdd"));

	}

	/** 初始化布局 */
	private void initView() {
		mBackTv = (TextView) findViewById(R.id.back_tv);
		mListView = (ListView) findViewById(R.id.sos_listView);
		mPreTv = (TextView)  findViewById(R.id.sos_pre_tv);
		mNextTv = (TextView)  findViewById(R.id.sos_next_tv);
		mNextTv.setVisibility(View.INVISIBLE);
		mDateTv = (TextView) findViewById(R.id.list_sos_date_tv);
		mCountTv = (TextView) findViewById(R.id.sos_count_tv);
	}

	/** 初始化数据 */
	private void initData() {
		mDateTv.setText(TimeUtils.long2StrDate(mLongDatetime, "yyyy-M-dd"));
		
		mDevice = MyApplication.getInstance().getDeviceDaoInface().viewDevice("iscurrent = ?", new String[] { "1" });

		mSOSAdapter = new SOSAdapter(mContext, listSosdata);
		mListView.setAdapter(mSOSAdapter);
	}

	private void setListener() {
		mBackTv.setOnClickListener(this);
		mPreTv.setOnClickListener(this);
		mNextTv.setOnClickListener(this);
		//mDateTv.setOnClickListener(this);//TODO
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Bundle bundle = new Bundle();
				bundle.putSerializable("sosdata", listSosdata.get(position));
				redictToActivity(mContext, SOSLocationActivity.class, bundle);

			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_tv:
			onBackPressed();
			break;
		case R.id.list_sos_date_tv:
			
		case R.id.sos_pre_tv:
			getPreSOSData();
			break;
		case R.id.sos_next_tv:
			getNextSOSData();
			break;
		default:
			break;
		}

	}
	
	private void getDeviceSOSData(String time) {
		
		if (mDevice == null ||  TextUtils.isEmpty(mDevice.mId)) {
			return;
		}	

		//showLoadingDialog("加载中...");
		DeviceApi.getInstance(mContext).getDeviceSOSData(time, mDevice.mId/*"565999999999998"*/, new HttpCallback() {

			@Override
			public void onError(String error) {
				//dismissLoadingDialog();
				
			}

			@Override
			public void onComplete(BaseMessage result) {
				//dismissLoadingDialog();
				if (result.isSuccess()) {
					listSosdata.clear();
					listSosdata.addAll((List<Sosdata>) result.getResultList("Sosdata"));
					
					mSOSAdapter.notifyDataSetChanged();
					if (mSOSAdapter.getCount() < 1) {
						mCountTv.setTextColor(getResources().getColor(R.color.data_color1));
						mCountTv.setText("当日没有报警");
					}else {
						mCountTv.setTextColor(getResources().getColor(R.color.data_color3));
						mCountTv.setText(String.format("当日报警 %d 次，请警惕", mSOSAdapter.getCount()));
					}
					
				}

			}
		});

	}
	
	/**
	 * 显示上一天的SOS数据
	 */
	private synchronized void getPreSOSData() {
		mLongDatetime = mLongDatetime - ONE_DAY_TIME;
		mDateTv.setText(TimeUtils.long2StrDate(mLongDatetime, "yyyy-M-dd"));
		getDeviceSOSData(TimeUtils.long2StrDate(mLongDatetime, "yyyyMMdd"));

		long currentTimeMillis = System.currentTimeMillis();
		if (currentTimeMillis - mLongDatetime > ONE_DAY_TIME) {
			mNextTv.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * 显示下一天的SOS数据
	 */
	private synchronized void getNextSOSData() {
		mLongDatetime = mLongDatetime + ONE_DAY_TIME;
		mDateTv.setText(TimeUtils.long2StrDate(mLongDatetime, "yyyy-M-dd"));
		
		getDeviceSOSData(TimeUtils.long2StrDate(mLongDatetime, "yyyyMMdd"));

		long currentTimeMillis = System.currentTimeMillis();
		if (currentTimeMillis - mLongDatetime < ONE_DAY_TIME) {
			mNextTv.setVisibility(View.INVISIBLE);
		}

	}

}
