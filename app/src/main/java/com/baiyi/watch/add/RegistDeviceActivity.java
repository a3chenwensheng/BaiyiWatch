package com.baiyi.watch.add;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Group;
import com.baiyi.watch.model.Person;
import com.baiyi.watch.model.SettingSosNumber;
import com.baiyi.watch.net.AuthApi;
import com.baiyi.watch.net.BaseMessage;
import com.baiyi.watch.net.DeviceApi;
import com.baiyi.watch.net.GroupApi;
import com.baiyi.watch.net.HttpCallback;
import com.baiyi.watch.ui.MainActivity;
import com.baiyi.watch.utils.ActivityUtil;

import toasty.Toasty;

/**
 * 
 * Activity
 * 
 * @author 陈文声
 * @email a3chenwensheng@126.com
 * @date 2015-6-2 11:30
 * @version v2.0
 */
public class RegistDeviceActivity extends BaseActivity implements OnClickListener {

	private TextView mBackTv;// 返回

	private EditText mNickNameEdit;
	private EditText mSimPhoneEdit;
	private EditText mImeiEdit;
	private Button mSaveBtn;
	private TextView mMarkTv;
	private TextView mNoticeTv;

	private String imei;//15位
	private String sim_phone;
	private String sim_phone_type;
	private String type;//设备型号
	private String deviceID;//15+5位
	
	private Person mPerson;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist_device);
		initView();
		initData();
		setListener();

	}

	/** 初始化布局 */
	private void initView() {
		mBackTv = (TextView) findViewById(R.id.back_tv);
		mNickNameEdit = (EditText) findViewById(R.id.regist_device_nickname_edit);
		mSimPhoneEdit = (EditText) findViewById(R.id.regist_device_phone_edit);
		mImeiEdit = (EditText) findViewById(R.id.regist_device_imei_edit);
		mSaveBtn = (Button) findViewById(R.id.regist_device_save_btn);
		mMarkTv = (TextView) findViewById(R.id.mark_tv);
		mNoticeTv = (TextView) findViewById(R.id.notice_tv);

	}

	/** 初始化数据 */
	private void initData() {
		
		mPerson = MyApplication.getInstance().getPersonDaoInface().viewPerson(null, null);

		imei = getIntent().getStringExtra("imei");
		sim_phone = getIntent().getStringExtra("sim_phone");
		sim_phone_type = getIntent().getStringExtra("sim_phone_type");
		type = getIntent().getStringExtra("type");
		deviceID = getIntent().getStringExtra("deviceID");

//		if ("BY007".equals(type)) {
//			mMarkTv.setVisibility(View.INVISIBLE);
//			mNoticeTv.setVisibility(View.INVISIBLE);
//		}
		
		if ("S1".equals(type)) {
			mSimPhoneEdit.setVisibility(View.GONE);
		}
		
		if (!TextUtils.isEmpty(sim_phone)) {
			mSimPhoneEdit.setText(sim_phone);
			mSimPhoneEdit.setEnabled(false);
		} else {
			mSimPhoneEdit.setText("");
			mSimPhoneEdit.setEnabled(true);
		}

		if (!TextUtils.isEmpty(imei)) {
			mImeiEdit.setText(imei);
			mImeiEdit.setEnabled(false);
		} else {
			mImeiEdit.setText("");
			mImeiEdit.setEnabled(true);
		}

	}

	private void setListener() {
		mBackTv.setOnClickListener(this);
		mSaveBtn.setOnClickListener(this);
		mNoticeTv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_tv:
			onBackPressed();
			break;
		case R.id.regist_device_save_btn:
			save();
			break;
		case R.id.notice_tv:
			Bundle bundle = new Bundle();
			bundle.putInt("position", 20-1);
			//redictToActivity(mContext, FAQActivity.class, bundle);
			break;
		default:
			break;
		}

	}

	private void save() {

		if (TextUtils.isEmpty(deviceID)) {
			ActivityUtil.showToast(mContext, "无效二维码");
			return;
		}

		if (TextUtils.isEmpty(mNickNameEdit.getText())) {
			ActivityUtil.showToast(mContext, "请输入昵称");
			mNickNameEdit.requestFocus();
			return;
		}
		
		if ("BY007".equals(type)) {
			if (TextUtils.isEmpty(mSimPhoneEdit.getText())) {
				ActivityUtil.showToast(mContext, "请输入设备卡号");
				mSimPhoneEdit.requestFocus();
				return;
			}
		}

		if (TextUtils.isEmpty(mImeiEdit.getText())) {
			ActivityUtil.showToast(mContext, "请输入imei号");
			mImeiEdit.requestFocus();
			return;
		}

		String username = System.currentTimeMillis() + "";
		String password = username;
		registDevice(username, password, mNickNameEdit.getText().toString(), deviceID, mSimPhoneEdit.getText().toString(), sim_phone_type);

	}

	private void registDevice(String username, String password, String nickname, String deviceid, String sim_phone, String sim_phone_type) {

		//showLoadingDialog("绑定中...");
		GroupApi.getInstance(mContext).groupRegist(username, password, nickname, deviceid, sim_phone, sim_phone_type, new HttpCallback() {

			@Override
			public void onError(String error) {
				//dismissLoadingDialog();
			}

			@Override
			public void onComplete(BaseMessage result) {
				//dismissLoadingDialog();
				if (result.isSuccess()) {
					editSosNumber();//默认添加一个亲情号码
					// TODO
					String account = mSputil.getValue("account", "");
					String password = mSputil.getValue("password", "");


						if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
							// TODO
							logout();
						} else {
							// TODO
							login(account, password);
						}


				} else {
					Toasty.error(mContext, result.getError_desc()).show();
				}

			}
		});

	}
	

	private void editSosNumber() {
		String num = "";
		try {
			num = mPerson.getPhone();
		} catch (Exception e) {
		}
		
		if (TextUtils.isEmpty(num)) {
			
			return;
		}
		SettingSosNumber sosNumber = new SettingSosNumber();
		sosNumber.setSeqid("1");
		sosNumber.setName("亲情号1");
		sosNumber.setNum(num);
		sosNumber.setDial_flag("true");
		
		//showLoadingDialog("处理中...");
		DeviceApi.getInstance(mContext).editSosNumber(imei, sosNumber, new HttpCallback() {

			@Override
			public void onError(String error) {
				//dismissLoadingDialog();
				
			}

			@Override
			public void onComplete(BaseMessage result) {
				//dismissLoadingDialog();
				
			}
		});
		
	}

	private void login(final String account, final String password) {

		if (!ActivityUtil.hasNetwork(mContext)) {
			ActivityUtil.showToast(mContext, "请检查网络");
			return;
		}

		//showLoadingDialog("登录中...");
		AuthApi.getInstance(mContext).login(account, password, new HttpCallback() {
			@Override
			public void onComplete(BaseMessage result) {
				//dismissLoadingDialog();
				if (result.isSuccess()) {
					// 登录成功
					mSputil.setValue("account", account);
					mSputil.setValue("password", password);
					mSputil.setValue("auth_type", "");
					mSputil.setValue("auth_uid", "");

					Object bm = result.getResult("Person");
					if (bm != null && bm instanceof Person) {

						Person person = (Person) bm;// 转换成Person实体

						// db
						ActivityUtil.cleartDB();
						MyApplication.getInstance().getPersonDaoInface().addPerson(person);

						if (person.mGroups != null && person.mGroups.size() > 0) {
							for (Group group : person.mGroups) {
								MyApplication.getInstance().getGroupDaoInface().addGroup(group);
							}
							redictToActivity(mContext, MainActivity.class, null);
							if ("S1".equals(type)) {
								Bundle bundle = new Bundle();
								bundle.putString("imei", imei);
								redictToActivity(mContext, SaveDeviceInfoActivity.class, bundle);
							}
														
							//AppManager.getAppManager().finishActivity(LoginActivity.class);
							finish();

						}

					}
				} else {
					// 登录失败
					Toasty.error(mContext, result.getError_desc()).show();
				}
			}

			@Override
			public void onError(String error) {
				//dismissLoadingDialog();
				Toasty.error(mContext, error).show();
			}
		});

	}

}
