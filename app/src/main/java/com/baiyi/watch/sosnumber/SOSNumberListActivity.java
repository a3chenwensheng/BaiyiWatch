package com.baiyi.watch.sosnumber;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.watch.adapter.SOSNumberAdapter;
import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.dialog.SimpleListDialog;
import com.baiyi.watch.dialog.SimpleListDialogAdapter;
import com.baiyi.watch.model.Device;
import com.baiyi.watch.model.SettingSosNumber;
import com.baiyi.watch.net.BaseMessage;
import com.baiyi.watch.net.DeviceApi;
import com.baiyi.watch.net.HttpCallback;
import com.baiyi.watch.utils.ActivityUtil;
import com.baiyi.watch.utils.StringUtils;
import com.baiyi.watch.widget.swipemenulistview.SwipeMenu;
import com.baiyi.watch.widget.swipemenulistview.SwipeMenuCreator;
import com.baiyi.watch.widget.swipemenulistview.SwipeMenuItem;
import com.baiyi.watch.widget.swipemenulistview.SwipeMenuListView;
import com.baiyi.watch.widget.toggle.ToggleButton;
import com.baiyi.watch.widget.toggle.ToggleButton.OnToggleChanged;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 亲情号码列表 Activity
 * 
 * @author 陈文声
 * @email a3chenwensheng@126.com
 * @date 2015-5-27 15:00
 * @version v2.0
 */
public class SOSNumberListActivity extends BaseActivity implements OnClickListener, OnToggleChanged {

	private TextView mBackTv;// 返回

	private ToggleButton mToggle;

	private Device mDevice;
	
	private SwipeMenuListView mListView;//
	private SOSNumberAdapter mAdapter;
//	private List<SettingSosNumber> mListSosNumbers = new ArrayList<SettingSosNumber>();
	private List<SettingSosNumber> mAllSosNumbers = new ArrayList<SettingSosNumber>();
	
	private SimpleListDialog mSimpleListDialog;// 选项对话框

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sos_number_list);
		initView();
		initData();
		setListener();

	}

	/** 初始化布局 */
	private void initView() {
		mBackTv = (TextView) findViewById(R.id.back_tv);
		mListView = (SwipeMenuListView) findViewById(R.id.sos_number_listView);
		mToggle = (ToggleButton) findViewById(R.id.incoming_restriction_toggle);

	}

	/** 初始化数据 */
	private void initData() {

		mDevice = MyApplication.getInstance().getDeviceDaoInface().viewDevice("iscurrent = ?", new String[] { "1" });
		
		mAdapter = new SOSNumberAdapter(mContext, mAllSosNumbers, mDevice);
		mListView.setAdapter(mAdapter);
	}

	private void setListener() {
		mBackTv.setOnClickListener(this);
		mToggle.setOnToggleChanged(this);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				
				Bundle bundle = new Bundle();
				bundle.putSerializable("SettingSosNumber", mAllSosNumbers.get(position));
				//bundle.putSerializable("device", mDevice);
				redictToActivity(mContext, SOSNumberEditActivity.class, bundle);
			}
		});
		
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				showSimpleListDialog(mAllSosNumbers.get(position));
				return true;
			}
		});
		
		// step 1. create a MenuCreator
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// create "delete" item
				SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
				// set item background
				deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
				// set item width
				deleteItem.setWidth(ActivityUtil.dip2px(mContext, 90));
				// set a icon
				deleteItem.setIcon(R.drawable.ic_delete);
				// add to menu
				menu.addMenuItem(deleteItem);
			}
		};
		// set creator
		mListView.setMenuCreator(creator);

		// step 2. listener item click event
		mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				
				SettingSosNumber item = mAllSosNumbers.get(position);
				switch (index) {

				case 0:
					//mListSosNumbers.remove(position);
					//mAdapter.notifyDataSetChanged();
					clearSosNumber(item);
					break;
				}
				return false;
			}
		});

		// set SwipeListener
		mListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

			@Override
			public void onSwipeStart(int position) {
				// swipe start
			}

			@Override
			public void onSwipeEnd(int position) {
				// swipe end
			}
		});
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_tv:
			onBackPressed();
			break;
			//if (mDevice != null && "BY007".equals(mDevice.getType())) {
		default:
			break;
		}

	}
	
	@Override
	public void onToggle(boolean on, ToggleButton v) {
		switch (v.getId()) {

		case R.id.incoming_restriction_toggle:
			editDevice("incoming_restriction", v);
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
				//dismissLoadingDialog();
				if (result.isSuccess()) {
					mDevice = (Device) result.getResult("Device");
					if (null != mDevice) {
						if ("true".equals(mDevice.getIncoming_restriction())) {
							mToggle.setToggleOn();
						} else {
							mToggle.setToggleOff();
						}
						
						mAllSosNumbers.clear();
						if (mDevice != null && "BY007".equals(mDevice.getType())) {
							for (SettingSosNumber sosNumber : mDevice.mSosNumbers) {
								int seqId = StringUtils.string2Int(sosNumber.getSeqid());
								if (seqId <= 4 ) {
									mAllSosNumbers.add(sosNumber);
								}
							}
						}else {
							mAllSosNumbers.addAll(mDevice.mSosNumbers);
						}

						// 刷新列表
						mAdapter.notifyDataSetChanged();

					}
				}

			}
		});

	}
	
	private void showSimpleListDialog(final SettingSosNumber item) {
		if (mSimpleListDialog != null) {
			mSimpleListDialog.dismiss();
		}
		mSimpleListDialog = new SimpleListDialog(mContext);
		// mSimpleListDialog.setTitle("选择");
		mSimpleListDialog.setTitleLineVisibility(View.INVISIBLE);
		mSimpleListDialog.setAdapter(new SimpleListDialogAdapter(mContext, "删除"));
		mSimpleListDialog.setOnSimpleListItemClickListener(new SimpleListDialog.onSimpleListItemClickListener() {

			@Override
			public void onItemClick(int position) {
				switch (position) {
				case 0:
					//mAllSosNumbers.remove(item);
					//mAdapter.notifyDataSetChanged();
					clearSosNumber(item);
					break;
				default:
					break;
				}

			}
		});
		mSimpleListDialog.show();

	}
	
	private void clearSosNumber(SettingSosNumber settingSosNumber) {

		if (mDevice == null || TextUtils.isEmpty(mDevice.mId)) {
			return;
		}

		//showLoadingDialog("处理中...");
		DeviceApi.getInstance(mContext).clearSosNumber(mDevice.mId, settingSosNumber, new HttpCallback() {

			@Override
			public void onError(String error) {
				//dismissLoadingDialog();

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
		DeviceApi.getInstance(mContext).editDevice(mDevice.mId, key, ((ToggleButton) view).isChecked() ? "1" : "0", new HttpCallback() {

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
					ActivityUtil.showToast(mContext, result.getError_desc());
					((ToggleButton) view).toggle2();
				}

			}
		});

	}

}
