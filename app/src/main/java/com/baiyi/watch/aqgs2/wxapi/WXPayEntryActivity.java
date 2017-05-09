package com.baiyi.watch.aqgs2.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.watch.aqgs2.AppManager;
import com.baiyi.watch.aqgs2.BaseActivity;
import com.baiyi.watch.aqgs2.R;
import com.baiyi.watch.renew.PayActivity;
import com.baiyi.watch.utils.ActivityUtil;
import com.baiyi.watch.utils.Constant;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends BaseActivity implements OnClickListener, IWXAPIEventHandler {

	private TextView mBackTv;// 返回

	private TextView mSuccessTv;
	private LinearLayout mOrderListLayout;

	private IWXAPI api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_result);

		api = WXAPIFactory.createWXAPI(this, Constant.APP_ID);
		api.handleIntent(getIntent(), this);

		mBackTv = (TextView) findViewById(R.id.back_tv);
		mSuccessTv = (TextView) findViewById(R.id.pay_result_success_tv);
		mOrderListLayout = (LinearLayout) findViewById(R.id.order_list_layout);

		mBackTv.setOnClickListener(this);

		mOrderListLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_tv:
			onBackPressed();
			break;
		case R.id.order_list_layout:
			////////////////redictToActivity(mContext, OrderListActivity.class, null);
			////////////////finish();
			break;
		default:
			break;
		}

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

			switch (resp.errCode) {
			case 0:
				if (mSuccessTv == null) {
					mSuccessTv = (TextView) findViewById(R.id.pay_result_success_tv);
				}
				mSuccessTv.setVisibility(View.VISIBLE);
				try {
					//////////////AppManager.getAppManager().finishActivity(PayActivity.class);
					//////////////AppManager.getAppManager().finishActivity(RenewActivity.class);
				} catch (Exception e) {
				}
				break;
			case -1:
				ActivityUtil.showToast(mContext, "支付失败");
				finish();
				break;
			case -2:
				ActivityUtil.showToast(mContext, "取消支付");
				finish();
				break;
			default:
				break;
			}
		}
	}
}