package com.baiyi.watch.renew;

import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.MyApplication;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Device;
import com.baiyi.watch.model.Goods;
import com.baiyi.watch.model.Order;
import com.baiyi.watch.model.Person;
import com.baiyi.watch.model.Servicerecord;
import com.baiyi.watch.net.BaseApi;
import com.baiyi.watch.net.BaseMessage;
import com.baiyi.watch.net.DeviceApi;
import com.baiyi.watch.net.HttpCallback;
import com.baiyi.watch.net.OrderApi;
import com.baiyi.watch.net.ParserServer;
import com.baiyi.watch.utils.ActivityUtil;
import com.baiyi.watch.utils.TimeUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import toasty.Toasty;

/**
 * 
 *
 * @author 陈文声
 * @email a3chenwensheng@126.com
 * @date 2016-10-14 11:30
 * @version v3.0
 */
public class ServiceRecordActivity extends BaseActivity implements OnClickListener {

	private TextView mBackTv;// 返回
	
	private CircleImageView mDeviceAvatarImg;// 头像
	private TextView mDeviceNameTv;
	
	private TextView mLeftDayTv;
	private ProgressBar mLeftDayProgressBar;
	private TextView mEndDateTv;
	private LinearLayout mRenewLayout;
	
	private TextView mPlatformServiceTv;
	private TextView mDoubtTv;

	private Device mDevice;
	private Person mPerson;
	
	private Servicerecord mServicerecord;
	//private RenewDialog mRenewDialog;//确认充值对话框

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service_record);

		initView();
		initData();
		setListener();

	}

	/** 初始化布局 */
	private void initView() {
		mBackTv = (TextView) findViewById(R.id.back_tv);

		
		mDeviceAvatarImg = (CircleImageView) findViewById(R.id.device_avatar_imv);
		mDeviceNameTv = (TextView) findViewById(R.id.device_name_tv);
		
		mLeftDayTv = (TextView) findViewById(R.id.left_day_tv);
		mLeftDayProgressBar = (ProgressBar) findViewById(R.id.left_day_progressbar);
		
		mEndDateTv = (TextView) findViewById(R.id.end_date_tv);
		mRenewLayout = (LinearLayout) findViewById(R.id.renew_layout);
		
		mPlatformServiceTv = (TextView) findViewById(R.id.platform_service);
		mPlatformServiceTv.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
		mPlatformServiceTv.getPaint().setAntiAlias(true);//抗锯齿
		mDoubtTv = (TextView) findViewById(R.id.doubt_tv);
		
	}

	/** 初始化数据 */
	private void initData() {

		mPerson = MyApplication.getInstance().getPersonDaoInface().viewPerson(null, null);
		mDevice = MyApplication.getInstance().getDeviceDaoInface().viewDevice("iscurrent = ?", new String[] { "1" });
		
		if (mDevice != null) {
			String name = mDevice.getName();
			if (TextUtils.isEmpty(name)) {
				name = "无昵称";
			}
			mDeviceNameTv.setText(name);

			if (mDevice.mOwner != null && !TextUtils.isEmpty(mDevice.mOwner.getAvatar_url())) {
				String avtarUrl = mDevice.mOwner.getAvatar_url();
				if (!avtarUrl.contains("http")) {
					avtarUrl = BaseApi.BASE_Url2 + mDevice.mOwner.getAvatar_url();
				}
				ImageLoader.getInstance().displayImage(avtarUrl, mDeviceAvatarImg, MyApplication.getInstance().getOptions(R.drawable.ic_default_device));

			}
		} else {
			mDeviceNameTv.setText("");
			mDeviceAvatarImg.setImageResource(R.drawable.ic_default_device);
		}

	}

	private void setListener() {
		mBackTv.setOnClickListener(this);
		mRenewLayout.setOnClickListener(this);
		mPlatformServiceTv.setOnClickListener(this);
		mDoubtTv.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_tv:
			onBackPressed();
			break;
		case R.id.renew_layout:
			//redictToActivity(mContext, RenewActivity.class, null);
			getGoodsList("1");
			break;
		default:
			break;
		}

	}

	@Override
	protected void onResume() {
		super.onResume();

		findServiceRecord();
	}

	private void findServiceRecord() {
		if (mDevice == null || TextUtils.isEmpty(mDevice.mId)) {
			// onLoad();
			Toasty.warning(mContext, "请选择设备").show();
			finish();
			return;
		}

		//showLoadingDialog("加载中...");
		DeviceApi.getInstance(mContext).findServiceRecord(mDevice.mId, new HttpCallback() {
			@Override
			public void onError(String error) {
				//dismissLoadingDialog();
				Toasty.error(mContext, error).show();
			}

			@Override
			public void onComplete(BaseMessage result) {
				//dismissLoadingDialog();
				if (result.isSuccess()) {
					try {
						//Servicerecord servicerecord = ParserServer.paserServicerecords(result.getResultSrc()).get(0);
						List<Servicerecord> servicerecords = (List<Servicerecord>) result.getResultList("Servicerecord");
						if (servicerecords != null && !servicerecords.isEmpty()) {
							mServicerecord = servicerecords.get(0);
							
							//ActivityUtil.showToast(mContext, "到期-->"+ TimeUtils.date2Str(TimeUtils.jsonStr2StrDate(servicerecord.getEnd_at()), "yyyyMMdd HHmmss"));
							showData(mServicerecord);
						
						}
						
					} catch (Exception e) {
						
					}
					
				} else {
					Toasty.error(mContext, result.getError_desc()).show();
				}
			}
		});
	}

	protected void showData(Servicerecord servicerecord) {
		if (servicerecord != null) {
			java.util.Date  date = TimeUtils.jsonStr2StrDate(servicerecord.getEnd_at());
			mEndDateTv.setText(TimeUtils.date2Str(date, "yyyy年M月d日"));
			int elapsedDays = TimeUtils.intervalCurrentDate(date);
			if (elapsedDays < 0) {
				mLeftDayTv.setText("");
				mLeftDayProgressBar.setProgress(0);
			}else if (elapsedDays < 1) {
				mLeftDayTv.setText("已到期");
				mLeftDayProgressBar.setProgress(0);
			}else {
				mLeftDayTv.setText(String.format("还剩%d天", elapsedDays));
				mLeftDayProgressBar.setProgress(elapsedDays);
			}

		}else {
			mEndDateTv.setText("");
			mLeftDayProgressBar.setProgress(0);
		}
		
	}
	
	private void getGoodsList(String onsale) {
		if (mPerson == null || TextUtils.isEmpty(mPerson.mId)) {
			//onLoad();
			Toasty.error(mContext, "请登录").show();
			logout();
			return;
		}
		
		//showLoadingDialog("请稍后...");
		OrderApi.getInstance(mContext).getGoodsList(onsale, new HttpCallback() {
			@Override
			public void onError(String error) {
				//onLoad();
				//dismissLoadingDialog();
				Toasty.error(mContext, error).show();
			}

			@Override
			public void onComplete(BaseMessage result) {
				//onLoad();
				//dismissLoadingDialog();
				if (result.isSuccess()) {
					try {
						ArrayList<Goods> goodsList = ParserServer.paserGoods(result.getResultSrc());
						
						//listDatas.addAll(goodsList);
						//改成 取第一个商品
						if (goodsList != null && !goodsList.isEmpty()) {
							Goods goods = goodsList.get(0);
							if (goods != null && mDevice != null ) {
								showRenewDialog(goods);
							}
							
						}
					} catch (Exception e) {
						
					}
					
				} else {
					Toasty.error(mContext, result.getError_desc()).show();
				}
			}
		});
	}
	
	private void showRenewDialog(final Goods goods) {
//		if (mRenewDialog != null) {
//			mRenewDialog.cancel();
//		}
//		mRenewDialog = new RenewDialog(mContext);
//		mRenewDialog.setTitle("温馨提示");
//
//		java.util.Date date = TimeUtils.jsonStr2StrDate(mServicerecord.getEnd_at());
//		Calendar cal = Calendar.getInstance();
//		if (date.compareTo(cal.getTime()) > 0) {
//			cal.setTime(date);
//		}
//		cal.add(Calendar.YEAR,1);
//		String endDate = TimeUtils.date2Str(cal.getTime(), "yyyy年M月d日");
//
//		String imeiStr = mDevice.mId;
//		if (imeiStr.length() >= 6) {
//			imeiStr = imeiStr.substring(imeiStr.length() - 6, imeiStr.length());
//		}
//		String content = "你即将为" + mDevice.getName() + "（IMEI号后六位" + imeiStr + "）续缴1年平台服务费用，续费后到期时间为：\n";
//		mRenewDialog.setData(content, endDate);
//		mRenewDialog.setTitleLineVisibility(View.INVISIBLE);
//		//mWechatTipsDialog1.setCanceledOnTouchOutside(false);
//		mRenewDialog.setButton1("取消", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				mRenewDialog.dismiss();
//			}
//		});
//
//		mRenewDialog.setButton2("确定", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				buy(goods);
//				mRenewDialog.dismiss();
//			}
//		});
//
//		mRenewDialog.show();
	}
	
	private void buy(Goods goods) {
		if (mDevice == null || (mDevice != null && TextUtils.isEmpty(mDevice.mId))) {
			Toasty.warning(mContext, "请选择设备").show();
			return;
		}
		
		if (goods == null || (goods != null && TextUtils.isEmpty(goods.mId))) {
			Toasty.error(mContext, "请刷新商品，稍后重试").show();
			return;
		}
		
		//showLoadingDialog("提交订单...");
		OrderApi.getInstance(mContext).buy(mDevice.mId, goods.mId, new HttpCallback() {
			@Override
			public void onError(String error) {
				//onLoad();
				//dismissLoadingDialog();
				Toasty.error(mContext, error).show();
			}

			@Override
			public void onComplete(BaseMessage result) {
				//onLoad();
				//dismissLoadingDialog();
				if (result.isSuccess()) {
					Order order = null;
					try {
						order = ParserServer.paserOrder(result.getResultSrc());
						//pay(order);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					//TODO
					//确认订单
					
					if (order != null) {
						Bundle bundle = new Bundle();
						bundle.putSerializable("order", order);
						//redictToActivity(mContext, PayActivity.class, bundle);
						
					}
					
				} else {
					Toasty.error(mContext, result.getError_desc()).show();
				}
			}
		});
		
	}

}
