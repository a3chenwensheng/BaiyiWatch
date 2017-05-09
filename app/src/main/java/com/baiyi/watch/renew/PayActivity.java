package com.baiyi.watch.renew;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.model.Order;
import com.baiyi.watch.utils.Constant;
import com.baiyi.watch.utils.MD5;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 
 * 在线支付Activity
 * 
 * @author 陈文声
 * @email a3chenwensheng@126.com
 * @date 2016-9-28 13:30
 * @version v3.0
 */
public class PayActivity extends BaseActivity implements OnClickListener {

	private TextView mBackTv;// 返回
	
	private TextView mGoodsNameTv;
	private TextView mPriceTv;
	private LinearLayout mPaylayout;
	
	private IWXAPI api;
	
	private Order mOrder;
	//private Goods mGoods;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);
		// 通过WXAPIFactory工厂，获取IWXAPI的实例
		api = WXAPIFactory.createWXAPI(this, null);
		// 将该app注册到微信
		api.registerApp(Constant.APP_ID);

		initView();
		initData();
		setListener();

	}

	/** 初始化布局 */
	private void initView() {
		mBackTv = (TextView) findViewById(R.id.back_tv);
		
		mGoodsNameTv = (TextView) findViewById(R.id.pay_goods_name_tv);
		mPriceTv = (TextView) findViewById(R.id.pay_goods_price_tv);
		mPaylayout = (LinearLayout) findViewById(R.id.pay_layout);

	}

	/** 初始化数据 */
	private void initData() {
		
		mOrder = (Order) getIntent().getSerializableExtra("order");
		//mGoods = (Goods) getIntent().getSerializableExtra("goods");
//		if ( mGoods != null) {
//			mGoodsNameTv.setText(mGoods.getName());
//		}
		
		if (mOrder != null ) {
			mGoodsNameTv.setText(mOrder.getGoods_name());
			mPriceTv.setText("￥" + mOrder.getPrice());
		}

	}

	private void setListener() {
		mBackTv.setOnClickListener(this);
		mPaylayout.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_tv:
			onBackPressed();
			break;
		case R.id.pay_layout:
			if (mOrder != null) {
				pay(mOrder);
			}
			break;
		default:
			break;
		}

	}
	
	@Override
	protected void onStop() {
		super.onStop();
		//dismissLoadingDialog();
		
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	private void pay(Order order) {
		if (order == null || (order != null && TextUtils.isEmpty(order.getPrepay_id()))) {
			return;
		}

		PayReq req = new PayReq();
		req.appId = Constant.APP_ID;
		req.partnerId = order.getMch_id();
		req.prepayId = order.getPrepay_id();
		req.packageValue = "Sign=WXPay";
		req.nonceStr = order.getNonce_str();
		req.timeStamp = String.valueOf(System.currentTimeMillis()).toString().substring(0, 10);
		req.sign = getSign(req);
		
		//showLoadingDialog("");

		//Toast.makeText(mContext, "正常调起支付", Toast.LENGTH_SHORT).show();
		// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
		api.sendReq(req);

	}

	private String getSign(PayReq req) {

		String stringA = "appid=" + req.appId + "&noncestr=" + req.nonceStr + "&package=" + req.packageValue + "&partnerid=" + req.partnerId + "&prepayid=" + req.prepayId + "&timestamp="
				+ req.timeStamp;
		String stringSignTemp = stringA + "&key=55ba4ffd546259043b758ade7ef53c87";
		String sign = MD5.genStr(stringSignTemp).toUpperCase();

		return sign;
	}

}
